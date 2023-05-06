package chursinov.tasktrackerapi.api.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnswerDto {

    Boolean answer;

    public static AnswerDto makeDefault(Boolean answer) {
        return builder()
                .answer(answer)
                .build();
    }
}
