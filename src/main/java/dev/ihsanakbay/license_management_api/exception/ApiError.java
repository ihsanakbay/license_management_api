package dev.ihsanakbay.license_management_api.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ApiError(
        LocalDateTime timestamp,
        HttpStatus status,
        String message,
        List errors
) {
}
