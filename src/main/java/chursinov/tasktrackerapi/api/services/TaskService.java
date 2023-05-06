package chursinov.tasktrackerapi.api.services;

import chursinov.tasktrackerapi.api.dto.TaskDto;
import chursinov.tasktrackerapi.api.factories.TaskDtoFactory;
import chursinov.tasktrackerapi.store.repositories.TaskRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class TaskService {

    TaskRepository taskRepository;
    TaskDtoFactory taskDtoFactory;

    @Transactional
    public TaskDto createTask(TaskDto task) {

        return null;
    }
}
