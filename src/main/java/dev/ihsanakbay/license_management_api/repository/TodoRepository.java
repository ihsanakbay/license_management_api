package dev.ihsanakbay.license_management_api.repository;

import dev.ihsanakbay.license_management_api.entities.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, String> {
    @Query(value = "SELECT t FROM todos t WHERE t.license.id = :id", nativeQuery = true)
    List<Todo> findByLicenseId(@Param("id") String id);
}
