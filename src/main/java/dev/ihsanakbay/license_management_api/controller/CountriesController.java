package dev.ihsanakbay.license_management_api.controller;

import dev.ihsanakbay.license_management_api.entities.dto.CountryDto;
import dev.ihsanakbay.license_management_api.entities.requests.CreateCountryRequest;
import dev.ihsanakbay.license_management_api.entities.responses.ServiceResponse;
import dev.ihsanakbay.license_management_api.entities.requests.UpdateCountryRequest;
import dev.ihsanakbay.license_management_api.service.CountryService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountriesController {
    private final CountryService countryService;

    public CountriesController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public ServiceResponse<List<CountryDto>> getCountries() {
        return countryService.getCountries();

    }

    @GetMapping("/{id}")
    public ServiceResponse<CountryDto> getCountryById(@PathVariable("id") String id) {
        return countryService.getCountryById(id);
    }

    @GetMapping("/filter/{code}")
    public ServiceResponse<CountryDto> getCountryByCode(@PathVariable("code") String code) {
        return countryService.getCountryByCode(code);
    }

    @PostMapping
    public ServiceResponse<CountryDto> createCountry(@Validated @RequestBody CreateCountryRequest request) {
        return countryService.createCountry(request);
    }

    @PutMapping
    public ServiceResponse<CountryDto> updateCountry(@Validated @RequestBody UpdateCountryRequest request) {
        return countryService.updateCountry(request);
    }

    @PatchMapping("/{id}")
    public ServiceResponse<CountryDto> changeStatus(@PathVariable("id") String id) {
        return countryService.changeCountryStatus(id);
    }

    @DeleteMapping("/{id}")
    public ServiceResponse<Void> deleteCountry(@PathVariable("id") String id) {
        return countryService.deleteCountry(id);
    }
}
