package dev.ihsanakbay.license_management_api.entities.requests;

import dev.ihsanakbay.license_management_api.entities.model.License;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public record UpdateLicenseRequest(
        String id,
        @NotBlank
        @NotNull
        String productName,
        @NotBlank
        @NotNull
        String number,
        @NotBlank
        @NotNull
        License.LicenseType licenseType,
        @NotBlank
        @NotNull
        String owner,
        @NotBlank
        @NotNull
        String distributor,
        @NotBlank
        @NotNull
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
        @NotBlank
        @NotNull
        String countryCode,
        Optional<List<UpdateTodoRequest>> todos
) {
}
