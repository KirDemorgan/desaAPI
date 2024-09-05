package demorgan.app.desaapi.api.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AnswerDto {

    String answer;

    public static AnswerDto makeDefaults(String answer) {
        return builder()
                .answer(answer)
                .build();
    }
}
