package demorgan.app.desaapi.store.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectDto {

    @NonNull
    Long id;

    @NonNull
    String name;

    @JsonProperty("created_at")
    @NonNull
    Instant createdAt;

    @JsonProperty("created_at")
    @NonNull
    Instant updatedAt;
}
