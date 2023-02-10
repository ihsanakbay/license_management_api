package dev.ihsanakbay.license_management_api.dto;

import java.time.LocalDateTime;

public record TodoDto(
        String id,
        String todoText,
        Boolean isDone,
        LocalDateTime deadlineDate
) {
}
