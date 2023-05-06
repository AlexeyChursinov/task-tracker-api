package chursinov.tasktrackerapi.api.factories;

import chursinov.tasktrackerapi.api.dto.ProjectDto;
import chursinov.tasktrackerapi.store.entities.ProjectEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectDtoFactory {

    TaskDtoFactory taskDtoFactory;

    public ProjectDto makeProjectDto(ProjectEntity entity) {
        return ProjectDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreateAt())
                .updatedAt(entity.getUpdatedAt())
                .tasks(Optional.ofNullable(entity.getTasks())
                        .map(tasks -> tasks.stream()
                                .map(taskDtoFactory::makeTaskDto)
                                .collect(Collectors.toList()))
                        .orElse(Collections.emptyList()))
                .build();
    }

}

