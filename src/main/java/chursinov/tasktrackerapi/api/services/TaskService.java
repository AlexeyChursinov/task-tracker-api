package chursinov.tasktrackerapi.api.services;

import chursinov.tasktrackerapi.api.controllers.helpers.ControllerHelper;
import chursinov.tasktrackerapi.api.dto.CreateTaskDto;
import chursinov.tasktrackerapi.api.dto.TaskDto;
import chursinov.tasktrackerapi.api.exceptions.BadRequestException;
import chursinov.tasktrackerapi.api.exceptions.NotFoundException;
import chursinov.tasktrackerapi.api.factories.TaskDtoFactory;
import chursinov.tasktrackerapi.store.entities.ProjectEntity;
import chursinov.tasktrackerapi.store.entities.TaskEntity;
import chursinov.tasktrackerapi.store.enums.TaskPriorityEnum;
import chursinov.tasktrackerapi.store.enums.TaskStatusEnum;
import chursinov.tasktrackerapi.store.repositories.ProjectRepository;
import chursinov.tasktrackerapi.store.repositories.TaskRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class TaskService {

    TaskRepository taskRepository;
    ProjectRepository projectRepository;

    TaskDtoFactory taskDtoFactory;

    ControllerHelper controllerHelper;

    @Transactional(readOnly = true)
    public List<TaskDto> getAllTasks() {

        Stream<TaskEntity> tasks = taskRepository.streamAllBy();

        return tasks
                .map(taskDtoFactory::makeTaskDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public TaskDto createTask(CreateTaskDto task, Long projectId) {

        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException(String.format("Project with id = %d not found", projectId)));

        String validTaskName = task.getName().trim();

        if (validTaskName.isEmpty()) {
            throw new BadRequestException("Task name should not be empty or contain only spaces.");
        }

        TaskEntity taskEntity = TaskEntity.builder()
                .name(validTaskName)
                .description(task.getDescription())
                .project(projectEntity)
                .taskStatus(task.getTaskStatus())
                .taskPriority(task.getTaskPriority())
                .build();

        taskEntity = taskRepository.saveAndFlush(taskEntity);

        return taskDtoFactory.makeTaskDto(taskEntity);
    }

    @Transactional
    public TaskDto updateTask(CreateTaskDto task, Long projectId, Long taskId) {

        controllerHelper.getProjectOrThrowException(projectId);

        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException(String.format("Task with id = %d not found", taskId)));

        String validTaskName = task.getName().trim();
        String validTaskDescription = task.getDescription().trim();

        if (validTaskName.isEmpty()) {
            throw new BadRequestException("Task name should not be empty or contain only spaces.");
        }

        if (validTaskDescription.isEmpty()) {
            throw new BadRequestException("Task description should not be empty or contain only spaces.");
        }

        taskEntity.setName(validTaskName);
        taskEntity.setDescription(validTaskDescription);
        taskEntity.setTaskStatus(task.getTaskStatus());
        taskEntity.setTaskPriority(task.getTaskPriority());

        return taskDtoFactory.makeTaskDto(taskEntity);
    }

    @Transactional
    public void deleteTask(Long taskId) {

        controllerHelper.getTaskOrThrowException(taskId);

        try {
            taskRepository.deleteById(taskId);
        } catch (Exception e) {
            log.error("Failed to delete task with ID " + taskId + ".", e);
            throw new BadRequestException("Failed to delete task with ID " + taskId + ".");
        }
    }

}
