package chursinov.tasktrackerapi.api.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnswerDto {

    String answer;

    public static AnswerDto makeDefault(String answer) {
        return builder()
                .answer(answer)
                .build();
    }
}
