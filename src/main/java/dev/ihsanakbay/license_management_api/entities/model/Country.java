package dev.ihsanakbay.license_management_api.entities.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "countries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Country {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private String id;
    @Column(unique = true)
    @NotBlank
    @NotNull
    private String code;
    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    private Set<License> licenses;

    public Country(String code, String name, Boolean status, LocalDateTime createdAt) {
        this.code = code;
        this.name = name;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Country(String id, String code, String name, Boolean status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
