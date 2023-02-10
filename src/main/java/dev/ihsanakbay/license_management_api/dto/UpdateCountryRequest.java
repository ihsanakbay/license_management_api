package dev.ihsanakbay.license_management_api.dto;

public record UpdateCountryRequest(
        String id,
        String code,
        String name,
        Boolean status
) {
}
