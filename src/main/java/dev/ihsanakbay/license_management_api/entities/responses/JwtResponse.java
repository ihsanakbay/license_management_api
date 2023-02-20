package dev.ihsanakbay.license_management_api.entities.responses;

import java.util.List;

public record JwtResponse(
        String token,
        String username,
        List<String> roles
) {
}
