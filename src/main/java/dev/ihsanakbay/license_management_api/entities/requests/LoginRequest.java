package dev.ihsanakbay.license_management_api.entities.requests;

public record LoginRequest(
        String username,
        String password
) {
}
