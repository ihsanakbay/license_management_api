package dev.ihsanakbay.license_management_api.service;

import dev.ihsanakbay.license_management_api.entities.model.FileData;
import dev.ihsanakbay.license_management_api.entities.requests.FileDataRequest;
import dev.ihsanakbay.license_management_api.entities.responses.ServiceResponse;
import dev.ihsanakbay.license_management_api.repository.FileDataRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class FileDataService {
    private final FileDataRepository repository;
    private final LicenseService licenseService;

    @Value("${upload.path}")
    private String uploadFolderPathProp;

    public FileDataService(FileDataRepository repository, LicenseService licenseService) {
        this.repository = repository;
        this.licenseService = licenseService;
    }

    public ServiceResponse<List<FileData>> getFilesByLicenseId(String licenseId) {
        var files = repository.findByLicenseId(licenseId).orElse(new ArrayList<>());
        return new ServiceResponse<>(
                true,
                "",
                files
        );
    }

    public ServiceResponse<FileData> getFileById(String id) {
        var file = repository.findById(id).orElse(null);
        if (file != null) {
            return new ServiceResponse<>(
                    true,
                    "",
                    file
            );
        }
        return new ServiceResponse<>(
                false,
                "File not found"
        );
    }

    private FileData uploadFileToDB(FileDataRequest request) {
        FileData fileData = new FileData(
                request.name(),
                request.folderPath(),
                request.licenseId(),
                true,
                LocalDateTime.now()
        );

        return repository.save(fileData);
    }

    public ServiceResponse<?> removeFileFromDB(String id) {
        repository.deleteById(id);
        return new ServiceResponse<>(
                true,
                "File successfully removed from db"
        );
    }

    public boolean removeFileFromFolder(String filePath) {
        File file = new File(filePath);
        return file.delete();
    }

    public ServiceResponse<FileData> changeStatus(String id) {
        var founded = repository.findById(id).orElse(null);
        if (founded != null) {
            FileData file = new FileData(
                    founded.getId(),
                    founded.getName(),
                    founded.getFolderPath(),
                    founded.getLicenseId(),
                    !founded.getStatus(),
                    founded.getCreatedAt(),
                    LocalDateTime.now()
            );
            var result = repository.save(file);
            return new ServiceResponse<>(
                    true,
                    "File status changed successfully",
                    result
            );
        }
        return new ServiceResponse<>(
                false,
                "File not found."
        );
    }

    @Async
    public CompletableFuture<ServiceResponse<?>> uploadFilesToFolder(List<MultipartFile> files, String licenseId) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy HH:mm:ss");
        SimpleDateFormat formatterDate = new SimpleDateFormat("dd_MM_yyyy");
        SimpleDateFormat formatterTime = new SimpleDateFormat("HH-mm-ss");
        Date date = new Date();

        var license = licenseService.findLicenseById(licenseId);
        if (license == null) {
            return CompletableFuture.completedFuture(new ServiceResponse<>(
                    false,
                    "License not found with this id: ", licenseId));
        }
        final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10 MB
        String uploadPath = String.format("%s/%s/%s/%s/%s/%s/",
                uploadFolderPathProp,
                license.getCountry().getCode(),
                license.getLicenseType().toString(),
                license.getDistributor(),
                formatterDate.format(date),
                formatterTime.format(date));

        try {
            File directory = new File(uploadPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            for (MultipartFile file : files) {
                if (file.getSize() > MAX_FILE_SIZE) {
                    return CompletableFuture.completedFuture(new ServiceResponse<>(
                            false,
                            "File size is too large (Max: 10MB)"));
                }
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());

                String uniqueFileName = formatter.format(date) + "_" + fileName;
                uniqueFileName = uniqueFileName.replaceAll("[^a-zA-Z0-9.-]", "_");
                String filePathString = uploadPath + uniqueFileName;
                Path filePath = Paths.get(uploadPath, uniqueFileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                FileDataRequest fileDataRequest = new FileDataRequest(
                        uniqueFileName,
                        filePathString,
                        license.getId(),
                        true
                );

                uploadFileToDB(fileDataRequest);
            }
            return CompletableFuture.completedFuture(new ServiceResponse<>(
                    true,
                    "Files uploaded successfully"
            ));
        } catch (IOException e) {
            return CompletableFuture.completedFuture(new ServiceResponse<>(
                    false,
                    "Failed to upload files"));
        }
    }
}