package dev.ihsanakbay.license_management_api.service;

import dev.ihsanakbay.license_management_api.entities.dto.LicenseDto;
import dev.ihsanakbay.license_management_api.entities.requests.CreateLicenseRequest;
import dev.ihsanakbay.license_management_api.entities.requests.CreateTodoRequest;
import dev.ihsanakbay.license_management_api.entities.requests.UpdateLicenseRequest;
import dev.ihsanakbay.license_management_api.entities.requests.UpdateTodoRequest;
import dev.ihsanakbay.license_management_api.entities.responses.ServiceResponse;
import dev.ihsanakbay.license_management_api.exception.DataNotFoundException;
import dev.ihsanakbay.license_management_api.mappers.LicenseMapper;
import dev.ihsanakbay.license_management_api.entities.model.License;
import dev.ihsanakbay.license_management_api.repository.LicenseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LicenseService {
    private static final Logger logger = LoggerFactory.getLogger(LicenseService.class);
    private final LicenseRepository licenseRepository;
    private final CountryService countryService;
    private final TodoService todoService;
    private final LicenseMapper licenseMapper;

    public LicenseService(LicenseRepository licenseRepository, CountryService countryService, TodoService todoService, LicenseMapper licenseMapper) {
        this.licenseRepository = licenseRepository;
        this.countryService = countryService;
        this.todoService = todoService;
        this.licenseMapper = licenseMapper;
    }


    public ServiceResponse<LicenseDto> getLicenseById(String id) {
        return new ServiceResponse<>(
                true,
                "",
                licenseMapper.licenseToLicenseDto(findLicenseById(id))
        );
    }

    public ServiceResponse<List<LicenseDto>> getLicenseByCountry(String code) {
        var licenses = findLicenseByCountry(code)
                .stream()
                .map(licenseMapper::licenseToLicenseDto)
                .collect(Collectors.toList());

        return new ServiceResponse<>(
                true,
                "",
                licenses
        );
    }

    public ServiceResponse<LicenseDto> createLicense(CreateLicenseRequest request) {
        var country = countryService.findCountryByCode(request.countryCode());
        logger.info("Country received");
        if (country == null) {
            return new ServiceResponse<>(
                    false,
                    "Country doesn't exist with code: " + request.countryCode()
            );
        }

        License license = new License(
                request.productName(),
                request.number(),
                request.licenseType(),
                request.owner(),
                request.distributor(),
                request.status(),
                request.closureDate(),
                request.validityDate(),
                request.description(),
                request.storageCondition(),
                request.shelfLife(),
                request.batchSize(),
                request.apiSupplier(),
                request.packingInfo(),
                request.tag(),
                LocalDateTime.now(),
                country
        );
        License newLicense = licenseRepository.save(license);
        var result = licenseMapper.licenseToLicenseDto(newLicense);

        if (request.todos().isPresent()) {
            request.todos().get().forEach(t -> {
                CreateTodoRequest todoRequest = new CreateTodoRequest(
                        t.todoText(),
                        t.isDone(),
                        t.deadlineDate(),
                        newLicense
                );
                todoService.createTodo(todoRequest);
            });
        }

        return new ServiceResponse<>(
                true,
                "License created successfully",
                result
        );
    }

    public ServiceResponse<LicenseDto> updateLicense(UpdateLicenseRequest request) {
        License founded = findLicenseById(request.id());
        License license = new License(
                founded.getId(),
                request.productName(),
                request.number(),
                request.licenseType(),
                request.owner(),
                request.distributor(),
                request.status(),
                request.closureDate(),
                request.validityDate(),
                request.description(),
                request.storageCondition(),
                request.shelfLife(),
                request.batchSize(),
                request.apiSupplier(),
                request.packingInfo(),
                request.tag(),
                founded.getCreatedAt(),
                LocalDateTime.now(),
                founded.getCountry()
        );

        License updatedLicense = licenseRepository.save(license);
        var result = licenseMapper.licenseToLicenseDto(updatedLicense);

        if (request.todos().isPresent()) {
            request.todos().get().forEach(t -> {
                if (t.id().equals(new UUID(0, 0).toString())) {
                    CreateTodoRequest todoRequest = new CreateTodoRequest(
                            t.todoText(),
                            t.isDone(),
                            t.deadlineDate(),
                            updatedLicense
                    );
                    todoService.createTodo(todoRequest);
                } else {
                    UpdateTodoRequest todoRequest = new UpdateTodoRequest(
                            t.id(),
                            t.todoText(),
                            t.isDone(),
                            t.deadlineDate(),
                            updatedLicense
                    );
                    todoService.updateTodo(todoRequest);
                }
            });
        }

        return new ServiceResponse<>(
                true,
                "License updated successfully",
                result
        );

    }

    public ServiceResponse<Void> deleteLicense(String id) {
        findLicenseById(id);
        licenseRepository.deleteById(id);

        return new ServiceResponse<>(
                true,
                "License successfully deleted from the database"
        );
    }

    public ServiceResponse<LicenseDto> changeLicenseStatus(String id) {
        var founded = findLicenseById(id);
        founded.setStatus(!founded.getStatus());
        founded.setUpdatedAt(LocalDateTime.now());
        var result = licenseMapper.licenseToLicenseDto(licenseRepository.save(founded));
        return new ServiceResponse<>(
                true,
                "License status changed successfully",
                result
        );
    }

    private License findLicenseById(String id) {
        return licenseRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("License couldn't find by id: " + id));
    }

    private List<License> findLicenseByCountry(String code) {
        return licenseRepository.findLicensesByCountryCode(code)
                .orElseThrow(() -> new DataNotFoundException("License couldn't find by country code: " + code));
    }
}
