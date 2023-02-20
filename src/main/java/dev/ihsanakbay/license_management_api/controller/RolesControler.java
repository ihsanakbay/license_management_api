package dev.ihsanakbay.license_management_api.controller;

import dev.ihsanakbay.license_management_api.entities.model.Role;
import dev.ihsanakbay.license_management_api.entities.requests.RoleRequest.CreateRoleRequest;
import dev.ihsanakbay.license_management_api.entities.responses.ServiceResponse;
import dev.ihsanakbay.license_management_api.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/roles")
public class RolesControler {

    private final RoleService service;

    public RolesControler(RoleService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ServiceResponse<Role> getRoleById(@PathVariable("id") String id) {
        return service.getRoleById(id);
    }

    @GetMapping()
    public ServiceResponse<List<Role>> getRoles() {
        return service.getRoles();
    }

    @PostMapping
    public ServiceResponse<Role> createRole(@RequestBody CreateRoleRequest request) {
        return service.createRole(request);
    }

    @DeleteMapping("/{id}")
    public ServiceResponse<Void> deleteRole(@PathVariable("id") String id) {
        return service.deleteRole(id);
    }
}
