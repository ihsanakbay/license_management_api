package dev.ihsanakbay.license_management_api.entities.dto;

import java.time.LocalDateTime;

public record TodoDto(
        String id,
        String todoText,
        Boolean isDone,
        LocalDateTime deadlineDate
) {
}
