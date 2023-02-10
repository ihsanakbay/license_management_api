package dev.ihsanakbay.license_management_api.dto;

import dev.ihsanakbay.license_management_api.model.License;

import java.time.LocalDateTime;

public record CreateTodoRequest(
        String todoText,
        Boolean isDone,
        LocalDateTime deadlineDate,
        License license
) {
}
