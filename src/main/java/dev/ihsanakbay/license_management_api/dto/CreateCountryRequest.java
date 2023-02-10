package dev.ihsanakbay.license_management_api.dto;

public record CreateCountryRequest(
        String code,
        String name,
        Boolean status
) {
}
