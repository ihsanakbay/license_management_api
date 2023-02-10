package dev.ihsanakbay.license_management_api.repository;

import dev.ihsanakbay.license_management_api.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {
    Optional<Country> findByCode(String code);
}
