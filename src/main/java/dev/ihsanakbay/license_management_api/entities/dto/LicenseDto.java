package dev.ihsanakbay.license_management_api.entities.dto;

import dev.ihsanakbay.license_management_api.entities.model.License;

import java.time.LocalDateTime;
import java.util.Set;

public record LicenseDto(
        String id,
        String productName,
        String number,
        License.LicenseType licenseType,
        String owner,
        String distributor,
        Boolean status,
        LocalDateTime closureDate,
        LocalDateTime validityDate,
        String description,
        String storageCondition,
        String shelfLife,
        String batchSize,
        String apiSupplier,
        String packingInfo,
        String tag,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        CountryDto country,
        Set<TodoDto> todos
) {
}
