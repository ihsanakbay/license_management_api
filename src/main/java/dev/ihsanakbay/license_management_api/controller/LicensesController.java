package dev.ihsanakbay.license_management_api.controller;

import dev.ihsanakbay.license_management_api.entities.dto.LicenseDto;
import dev.ihsanakbay.license_management_api.entities.requests.CreateLicenseRequest;
import dev.ihsanakbay.license_management_api.entities.responses.ServiceResponse;
import dev.ihsanakbay.license_management_api.entities.requests.UpdateLicenseRequest;
import dev.ihsanakbay.license_management_api.service.LicenseService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/licenses")
public class LicensesController {
    private final LicenseService licenseService;

    public LicensesController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @GetMapping("/filter/{code}")
    public ServiceResponse<List<LicenseDto>> getLicensesByCountry(@PathVariable("code") String code) {
        return licenseService.getLicenseByCountry(code);

    }

    @GetMapping("/{id}")
    public ServiceResponse<LicenseDto> getLicenseById(@PathVariable("id") String id) {
        return licenseService.getLicenseById(id);
    }

    @PostMapping
    public ServiceResponse<LicenseDto> createLicense(@Validated @RequestBody CreateLicenseRequest request) {
        return licenseService.createLicense(request);
    }

    @PutMapping
    public ServiceResponse<LicenseDto> updateLicense(@Validated @RequestBody UpdateLicenseRequest request) {
        return licenseService.updateLicense(request);
    }

    @PatchMapping("/{id}")
    public ServiceResponse<LicenseDto> changeStatus(@PathVariable("id") String id) {
        return licenseService.changeLicenseStatus(id);
    }

    @DeleteMapping("/{id}")
    public ServiceResponse<Void> deleteLicense(@PathVariable("id") String id) {
        return licenseService.deleteLicense(id);
    }
}
