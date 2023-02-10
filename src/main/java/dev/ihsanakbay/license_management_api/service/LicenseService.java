package dev.ihsanakbay.license_management_api.service;

import dev.ihsanakbay.license_management_api.dto.*;
import dev.ihsanakbay.license_management_api.dto.converter.LicenseDtoConverter;
import dev.ihsanakbay.license_management_api.exception.DataNotFoundException;
import dev.ihsanakbay.license_management_api.model.License;
import dev.ihsanakbay.license_management_api.repository.LicenseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LicenseService {
    private static final Logger logger = LoggerFactory.getLogger(LicenseService.class);
    private final LicenseRepository licenseRepository;
    private final LicenseDtoConverter converter;
    private final CountryService countryService;
    private final TodoService todoService;

    public LicenseService(LicenseRepository licenseRepository, LicenseDtoConverter converter, CountryService countryService, TodoService todoService) {
        this.licenseRepository = licenseRepository;
        this.converter = converter;
        this.countryService = countryService;
        this.todoService = todoService;
    }

    public ServiceResponse<LicenseDto> getLicenseById(String id) {
        return new ServiceResponse<>(
                true,
                "",
                converter.convert(findLicenseById(id))
        );
    }

    public ServiceResponse<List<LicenseDto>> getLicenseByCountry(String code) {
        var licenses = findLicenseByCountry(code)
                .stream()
                .map(converter::convert)
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
        var result = converter.convert(newLicense);

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
        var result = converter.convert(updatedLicense);

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
        var result = converter.convert(licenseRepository.save(founded));
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
