package dev.ihsanakbay.license_management_api.dto;

import dev.ihsanakbay.license_management_api.model.License;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<TodoDto> todos
) {
}
