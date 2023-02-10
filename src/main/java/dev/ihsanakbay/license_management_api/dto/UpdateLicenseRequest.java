package dev.ihsanakbay.license_management_api.dto;

import dev.ihsanakbay.license_management_api.model.License;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public record UpdateLicenseRequest(
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
        String countryCode,
        Optional<List<TodoUpdateRequest>> todos
) {
}
