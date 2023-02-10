package dev.ihsanakbay.license_management_api.dto;

import java.time.LocalDateTime;

public record TodoCreateRequest(
        String todoText,
        Boolean isDone,
        LocalDateTime deadlineDate
) {
}
