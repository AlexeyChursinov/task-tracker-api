package chursinov.tasktrackerapi.api.factories;

import chursinov.tasktrackerapi.api.dto.TaskDto;
import chursinov.tasktrackerapi.store.entities.TaskEntity;
import chursinov.tasktrackerapi.store.enums.TaskPriorityEnum;
import chursinov.tasktrackerapi.store.enums.TaskStatusEnum;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TaskDtoFactory {

    public TaskDto makeTaskDto(TaskEntity taskEntity) {
        return TaskDto.builder()
                .id(taskEntity.getId())
                .name(taskEntity.getName())
                .description(taskEntity.getDescription())
                .taskStatus(Optional.ofNullable(taskEntity.getTaskStatus()).orElse(TaskStatusEnum.NEW))
                .taskPriority(Optional.ofNullable(taskEntity.getTaskPriority()).orElse(TaskPriorityEnum.MEDIUM))
                .createdAt(taskEntity.getCreatedAt())
                .build();
    }

}
