package dev.ihsanakbay.license_management_api.entities.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "files_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FileData {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private String id;
    @Column(unique = true)
    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    private String folderPath;
    @NotBlank
    @NotNull
    private String licenseId;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public FileData(String name, String folderPath, String licenseId, Boolean status, LocalDateTime createdAt) {
        this.name = name;
        this.folderPath = folderPath;
        this.licenseId = licenseId;
        this.status = status;
        this.createdAt = createdAt;
    }
}
