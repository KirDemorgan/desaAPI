package demorgan.app.desaapi.api.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskRequestDto {
    String taskName;
    Optional<String> optionalTaskDescription;
}