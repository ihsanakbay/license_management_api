package dev.ihsanakbay.license_management_api.mappers;

import dev.ihsanakbay.license_management_api.entities.dto.LicenseDto;
import dev.ihsanakbay.license_management_api.entities.model.License;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LicenseMapper {
    License licenseDtoToLicense(LicenseDto dto);
    LicenseDto licenseToLicenseDto(License license);
}
