package chursinov.tasktrackerapi.api.dto;

import chursinov.tasktrackerapi.store.enums.TaskPriorityEnum;
import chursinov.tasktrackerapi.store.enums.TaskStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(subTypes = {CreateTaskDto.class})
public class TaskDto {

    @NonNull
    Long id;

    @NonNull
    String name;

    @NonNull
    Instant createdAt;

    @NonNull
    String description;

    @NonNull
    @Schema(defaultValue = "NEW")
    TaskStatusEnum taskStatus;

    @NonNull
    @Schema(defaultValue = "MEDIUM")
    TaskPriorityEnum taskPriority;
}
