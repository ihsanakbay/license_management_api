package dev.ihsanakbay.license_management_api.repository;

import dev.ihsanakbay.license_management_api.entities.model.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileDataRepository extends JpaRepository<FileData, String> {
    Optional<List<FileData>> findByLicenseId(String licenseId);
}
