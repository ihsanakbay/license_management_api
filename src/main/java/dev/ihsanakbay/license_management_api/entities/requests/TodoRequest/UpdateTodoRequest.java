package dev.ihsanakbay.license_management_api.entities.requests.TodoRequest;

import dev.ihsanakbay.license_management_api.entities.model.License;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UpdateTodoRequest(
        @Nullable
        String id,

        @NotBlank
        @NotNull
        String todoText,

        Boolean isDone,
        LocalDateTime deadlineDate,

        @NotBlank
        @NotNull
        License license
) {
}
