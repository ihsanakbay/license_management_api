package dev.ihsanakbay.license_management_api.controller;

import dev.ihsanakbay.license_management_api.dto.LicenseDto;
import dev.ihsanakbay.license_management_api.dto.CreateLicenseRequest;
import dev.ihsanakbay.license_management_api.dto.ServiceResponse;
import dev.ihsanakbay.license_management_api.dto.UpdateLicenseRequest;
import dev.ihsanakbay.license_management_api.service.LicenseService;
import jakarta.validation.Valid;
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
    public ServiceResponse<LicenseDto> createLicense(@Valid @RequestBody CreateLicenseRequest request) {
        return licenseService.createLicense(request);
    }

    @PatchMapping
    public ServiceResponse<LicenseDto> updateLicense(@Valid @RequestBody UpdateLicenseRequest request) {
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
