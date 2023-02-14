package dev.ihsanakbay.license_management_api.entities.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "licenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class License {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private String id;
    @NotBlank
    @NotNull
    private String productName;
    @NotBlank
    @NotNull
    private String number;
    @NotBlank
    @NotNull
    private LicenseType licenseType;
    @NotBlank
    @NotNull
    private String owner;
    @NotBlank
    @NotNull
    private String distributor;
    @NotBlank
    @NotNull
    private Boolean status;
    private LocalDateTime closureDate;
    private LocalDateTime validityDate;
    private String description;
    private String storageCondition;
    private String shelfLife;
    private String batchSize;
    private String apiSupplier;
    private String packingInfo;
    private String tag;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
    @NotBlank
    @NotNull
    private Country country;
    @OneToMany(mappedBy = "license", fetch = FetchType.LAZY)
    private Set<Todo> todos;

    public enum LicenseType {
        CERTIFIED, PROJECT
    }


    public License(String id, String productName, String number, LicenseType licenseType, String owner, String distributor, Boolean status, LocalDateTime closureDate, LocalDateTime validityDate, String description, String storageCondition, String shelfLife, String batchSize, String apiSupplier, String packingInfo, String tag, LocalDateTime createdAt, LocalDateTime updatedAt, Country country) {
        this.id = id;
        this.productName = productName;
        this.number = number;
        this.licenseType = licenseType;
        this.owner = owner;
        this.distributor = distributor;
        this.status = status;
        this.closureDate = closureDate;
        this.validityDate = validityDate;
        this.description = description;
        this.storageCondition = storageCondition;
        this.shelfLife = shelfLife;
        this.batchSize = batchSize;
        this.apiSupplier = apiSupplier;
        this.packingInfo = packingInfo;
        this.tag = tag;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.country = country;
    }

    public License(String productName, String number, LicenseType licenseType, String owner, String distributor, Boolean status, LocalDateTime closureDate, LocalDateTime validityDate, String description, String storageCondition, String shelfLife, String batchSize, String apiSupplier, String packingInfo, String tag, LocalDateTime createdAt, Country country) {
        this.productName = productName;
        this.number = number;
        this.licenseType = licenseType;
        this.owner = owner;
        this.distributor = distributor;
        this.status = status;
        this.closureDate = closureDate;
        this.validityDate = validityDate;
        this.description = description;
        this.storageCondition = storageCondition;
        this.shelfLife = shelfLife;
        this.batchSize = batchSize;
        this.apiSupplier = apiSupplier;
        this.packingInfo = packingInfo;
        this.tag = tag;
        this.createdAt = createdAt;
        this.country = country;
    }

    public License(String productName, String number, LicenseType licenseType, String owner, String distributor, Boolean status, LocalDateTime closureDate, LocalDateTime validityDate, String description, String storageCondition, String shelfLife, String batchSize, String apiSupplier, String packingInfo, String tag, LocalDateTime createdAt, LocalDateTime updatedAt, Country country, Set<Todo> todos) {
        this.productName = productName;
        this.number = number;
        this.licenseType = licenseType;
        this.owner = owner;
        this.distributor = distributor;
        this.status = status;
        this.closureDate = closureDate;
        this.validityDate = validityDate;
        this.description = description;
        this.storageCondition = storageCondition;
        this.shelfLife = shelfLife;
        this.batchSize = batchSize;
        this.apiSupplier = apiSupplier;
        this.packingInfo = packingInfo;
        this.tag = tag;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.country = country;
        this.todos = todos;
    }
}