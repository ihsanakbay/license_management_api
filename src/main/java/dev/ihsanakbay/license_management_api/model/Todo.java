package dev.ihsanakbay.license_management_api.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "todos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class Todo {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private String id;
    private String todoText;
    private Boolean isDone;
    private LocalDateTime deadlineDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "license_id", referencedColumnName = "id", nullable = false)
    private License license;

    public Todo(String todoText, Boolean isDone, LocalDateTime deadlineDate, LocalDateTime createdAt, License license) {
        this.todoText = todoText;
        this.isDone = isDone;
        this.deadlineDate = deadlineDate;
        this.createdAt = createdAt;
        this.license = license;
    }

    public Todo(String todoText, Boolean isDone, LocalDateTime deadlineDate, LocalDateTime createdAt, LocalDateTime updatedAt, License license) {
        this.todoText = todoText;
        this.isDone = isDone;
        this.deadlineDate = deadlineDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.license = license;
    }
}
