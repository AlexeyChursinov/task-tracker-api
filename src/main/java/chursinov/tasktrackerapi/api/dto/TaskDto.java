package chursinov.tasktrackerapi.api.dto;

import chursinov.tasktrackerapi.store.enums.TaskPriorityEnum;
import chursinov.tasktrackerapi.store.enums.TaskStatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    @JsonProperty("created_at")
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
