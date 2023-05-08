package chursinov.tasktrackerapi.api.dto;

import chursinov.tasktrackerapi.store.enums.TaskPriorityEnum;
import chursinov.tasktrackerapi.store.enums.TaskStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(parent = TaskDto.class)
public class CreateTaskDto {

    @NonNull
    String name;

    @NonNull
    String description;

    @NonNull
    @Schema(defaultValue = "NEW")
    TaskStatusEnum taskStatus;

    @NonNull
    @Schema(defaultValue = "MEDIUM")
    TaskPriorityEnum taskPriority;
}
