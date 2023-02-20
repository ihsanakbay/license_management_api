package dev.ihsanakbay.license_management_api.repository;

import dev.ihsanakbay.license_management_api.entities.model.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LicenseRepository  extends JpaRepository<License, String> {
    @Query("SELECT l FROM License l JOIN l.country c WHERE c.code = :code")
    Optional<List<License>> findLicensesByCountryCode(String code);
}
