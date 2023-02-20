package dev.ihsanakbay.license_management_api.repository;

import dev.ihsanakbay.license_management_api.entities.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
