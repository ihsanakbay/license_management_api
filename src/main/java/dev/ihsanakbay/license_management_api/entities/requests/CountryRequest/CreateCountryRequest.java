package dev.ihsanakbay.license_management_api.entities.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCountryRequest(
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
