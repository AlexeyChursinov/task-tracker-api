package chursinov.tasktrackerapi.api.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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

    @NonNull
    Instant createdAt;

    @NonNull
    Instant updatedAt;

    List<TaskDto> tasks = new ArrayList<>();;

}
