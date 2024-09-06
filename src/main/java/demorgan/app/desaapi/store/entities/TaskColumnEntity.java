package demorgan.app.desaapi.store.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Builder
@Table(name = "task_column")
public class TaskColumnEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    String name;

    @Builder.Default
    Long position = 0L;

    @ManyToOne
    ProjectEntity project;

    @Builder.Default
    Instant createdAt = Instant.now();

    @Builder.Default
    Instant updatedAt = Instant.now();

    @Builder.Default
    @OneToMany
    @JoinColumn(name = "task_column_id", referencedColumnName = "id")
    List<TaskEntity> tasks = new ArrayList<>();
}
