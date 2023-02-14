package dev.ihsanakbay.license_management_api.entities.dto;

import java.time.LocalDateTime;

public record CountryDto(
        String id,
        String code,
        String name,
        Boolean status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
