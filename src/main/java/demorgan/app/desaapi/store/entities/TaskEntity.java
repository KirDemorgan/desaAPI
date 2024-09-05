package demorgan.app.desaapi.store.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Builder
@Table(name = "task")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(unique = true)
    String name;

    @Builder.Default
    Instant createdAt = Instant.now();

    String description;
}