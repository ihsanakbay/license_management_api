package dev.ihsanakbay.license_management_api.service;

import dev.ihsanakbay.license_management_api.entities.dto.CountryDto;
import dev.ihsanakbay.license_management_api.entities.requests.CreateCountryRequest;
import dev.ihsanakbay.license_management_api.entities.responses.ServiceResponse;
import dev.ihsanakbay.license_management_api.entities.requests.UpdateCountryRequest;
import dev.ihsanakbay.license_management_api.exception.DataNotFoundException;
import dev.ihsanakbay.license_management_api.mappers.CountryMapper;
import dev.ihsanakbay.license_management_api.entities.model.Country;
import dev.ihsanakbay.license_management_api.repository.CountryRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Primary
public class CountryService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public CountryService(CountryRepository countryRepository, CountryMapper countryMapper) {
        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
    }


    public ServiceResponse<List<CountryDto>> getCountries() {
        var countries = countryRepository.findAll()
                .stream()
                .map(countryMapper::countryToCountryDto)
                .toList();
        return new ServiceResponse<>(
                true,
                "",
                countries
        );
    }

    public ServiceResponse<CountryDto> getCountryById(String id) {
        return new ServiceResponse<>(
                true,
                "",
                countryMapper.countryToCountryDto(findCountryById(id))
        );
    }

    public ServiceResponse<CountryDto> getCountryByCode(String code) {
        return new ServiceResponse<>(
                true,
                "",
                countryMapper.countryToCountryDto(findCountryByCode(code))
        );
    }

    public ServiceResponse<CountryDto> createCountry(CreateCountryRequest request) {
        var founded = countryRepository.findByCode(request.code()).orElse(null);
        if (founded != null) {
            return new ServiceResponse<>(
                    false,
                    "Country already exist with this code: " + request.code(),
                    null
            );
        }
        Country country = new Country(
                request.code(),
                request.name(),
                request.status(),
                LocalDateTime.now()

        );

        var result = countryMapper.countryToCountryDto(countryRepository.save(country));
        return new ServiceResponse<>(
                true,
                "Country created successfully",
                result
        );
    }

    public ServiceResponse<CountryDto> updateCountry(UpdateCountryRequest request) {
        Country founded = findCountryById(request.id());
        Country country = new Country(
                founded.getId(),
                request.code(),
                request.name(),
                request.status(),
                founded.getCreatedAt(),
                LocalDateTime.now()
        );

        var result = countryMapper.countryToCountryDto(countryRepository.save(country));
        return new ServiceResponse<>(
                true,
                "Country updated successfully",
                result
        );

    }

    public ServiceResponse<Void> deleteCountry(String id) {
        findCountryById(id);

        countryRepository.deleteById(id);

        return new ServiceResponse<>(
                true,
                "Country successfully deleted from the database"
        );
    }

    public ServiceResponse<CountryDto> changeCountryStatus(String id) {
        var founded = findCountryById(id);
        founded.setStatus(!founded.getStatus());
        founded.setUpdatedAt(LocalDateTime.now());
        var result = countryMapper.countryToCountryDto(countryRepository.save(founded));
        return new ServiceResponse<>(
                true,
                "Country status changed successfully",
                result
        );
    }

    protected Country findCountryById(String id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Country couldn't find by id: " + id));
    }

    protected Country findCountryByCode(String code) {
        return countryRepository.findByCode(code)
                .orElseThrow(() -> new DataNotFoundException("Country couldn't find by code: " + code));
    }
}
