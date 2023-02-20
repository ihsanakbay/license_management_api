package dev.ihsanakbay.license_management_api.entities.requests.CountryRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateCountryRequest(
        @NotBlank
        @NotNull
        String id,
        @NotBlank
        @NotNull
        String code,
        @NotBlank
        @NotNull
        String name,
        @NotBlank
        @NotNull
        Boolean status
) {
}
