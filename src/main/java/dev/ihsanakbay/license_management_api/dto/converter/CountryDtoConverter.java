package dev.ihsanakbay.license_management_api.dto.converter;

import dev.ihsanakbay.license_management_api.dto.CountryDto;
import dev.ihsanakbay.license_management_api.model.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryDtoConverter {

    public CountryDto convertToCountryDto(Country from) {
        return new CountryDto(
                from.getId(),
                from.getCode(),
                from.getName(),
                from.getStatus(),
                from.getCreatedAt(),
                from.getUpdatedAt()
        );
    }
}
