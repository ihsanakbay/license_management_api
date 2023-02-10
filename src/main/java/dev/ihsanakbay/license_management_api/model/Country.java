package dev.ihsanakbay.license_management_api.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "countries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class Country {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private String id;
    @Column(unique = true)
    private String code;
    private String name;
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
