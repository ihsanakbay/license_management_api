package dev.ihsanakbay.license_management_api.mappers;

import dev.ihsanakbay.license_management_api.entities.dto.CountryDto;
import dev.ihsanakbay.license_management_api.entities.model.Country;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    Country countryDtoToCountry(CountryDto dto);
    CountryDto countryToCountryDto(Country country);
}
