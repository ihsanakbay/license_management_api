package dev.ihsanakbay.license_management_api.service;

import dev.ihsanakbay.license_management_api.entities.model.Role;
import dev.ihsanakbay.license_management_api.entities.requests.RoleRequest.CreateRoleRequest;
import dev.ihsanakbay.license_management_api.entities.responses.ServiceResponse;
import dev.ihsanakbay.license_management_api.exception.DataNotFoundException;
import dev.ihsanakbay.license_management_api.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public ServiceResponse<List<Role>> getRoles() {
        var roles = roleRepository.findAll();
        return new ServiceResponse<>(
                true,
                "",
                roles
        );
    }

    public ServiceResponse<Role> getRoleById(String id) {
        var role = roleRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Role couldn't find by id: " + id));
        return new ServiceResponse<>(
                true,
                "",
                role
        );
    }

    public ServiceResponse<Role> createRole(CreateRoleRequest request) {
        Role role = new Role(
                request.name()
        );
        return new ServiceResponse<>(
                true,
                "Role created successfully",
                roleRepository.save(role)
        );
    }

    public ServiceResponse<Void> deleteRole(String id) {
        roleRepository.deleteById(id);
        return  new ServiceResponse<>(
            true,
            "Role deleted successfully"
        );
    }
}
