package dev.ihsanakbay.license_management_api.entities.dto;

import java.util.Set;

public record UserDto(
        String username,
        String email,
        String fullName,
        String distributor,
        Set<RoleDto> roles
) {
}
