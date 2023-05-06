package chursinov.tasktrackerapi.api.factories;

import chursinov.tasktrackerapi.api.dto.TaskDto;
import chursinov.tasktrackerapi.store.entities.TaskEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskDtoFactory {

    public TaskDto makeTaskDto(TaskEntity entity) {

        return TaskDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .description(entity.getDescription())
                .build();
    }

    public List<TaskDto> makeTaskDtoList(List<TaskEntity> taskEntities) {
        return taskEntities.stream()
                .map(this::makeTaskDto)
                .collect(Collectors.toList());
    }
}
