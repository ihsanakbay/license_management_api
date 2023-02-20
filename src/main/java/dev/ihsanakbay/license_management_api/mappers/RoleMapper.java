package dev.ihsanakbay.license_management_api.mappers;

import dev.ihsanakbay.license_management_api.entities.dto.RoleDto;
import dev.ihsanakbay.license_management_api.entities.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role roleDtoToRole(RoleDto dto);

    RoleDto roleToRoleDto(Role role);
}
