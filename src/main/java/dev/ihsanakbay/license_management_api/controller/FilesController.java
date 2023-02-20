package dev.ihsanakbay.license_management_api.controller;

import dev.ihsanakbay.license_management_api.entities.responses.ServiceResponse;
import dev.ihsanakbay.license_management_api.service.FileDataService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/files")
public class FilesController {
    private final FileDataService service;

    public FilesController(FileDataService service) {
        this.service = service;
    }

    @GetMapping("/{licenseId}")
    public ServiceResponse<?> getFilesByLicenseId(@PathVariable String licenseId) {
        return service.getFilesByLicenseId(licenseId);
    }

    @PostMapping("/upload/{licenseId}")
    public ServiceResponse<?> uploadFiles(@RequestParam List<MultipartFile> files, @PathVariable("licenseId") String licenseId) {
        try {
            CompletableFuture<ServiceResponse<?>> future = service.uploadFilesToFolder(files, licenseId);
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            return new ServiceResponse<>(
                    false,
                    "Failed to upload files"
            );
        }
    }

    @PutMapping("/{id}")
    public ServiceResponse<?> changeStatus(@PathVariable String id) {
        return service.changeStatus(id);
    }


    @DeleteMapping("/{id}")
    public ServiceResponse<?> removeFileFromDb(@PathVariable String id) {
        try {
            var founded = service.getFileById(id);
            if (founded.getSuccess()) {
                service.removeFileFromDB(id);
                service.removeFileFromFolder(founded.getData().getFolderPath());
                return new ServiceResponse<>(
                        true,
                        "Successfully removed file from db and folder"
                );
            }
            return new ServiceResponse<>(
                    false,
                    founded.getMessage()
            );
        } catch (Exception e) {
            return new ServiceResponse<>(
                    false,
                    "Failed to remove file"
            );
        }
    }
}

