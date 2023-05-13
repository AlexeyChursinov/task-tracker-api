package chursinov.tasktrackerapi.api.services;

import chursinov.tasktrackerapi.api.dto.TaskDto;
import chursinov.tasktrackerapi.api.factories.TaskDtoFactory;
import chursinov.tasktrackerapi.store.entities.TaskEntity;
import chursinov.tasktrackerapi.store.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;

    @Mock
    TaskDtoFactory taskDtoFactory;

    @InjectMocks
    TaskService taskService;

    @Test
    void getAllTasks_ReturnsEmptyList_WhenNoTasksFound() {
        // Arrange
        when(taskRepository.streamAllBy()).thenReturn(Stream.empty());

        // Act
        List<TaskDto> result = taskService.getAllTasks();

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void getAllTasks_ReturnsListOfTaskDtos_WhenTasksFound() {
        // Arrange
        TaskEntity taskEntity1 = new TaskEntity();
        TaskEntity taskEntity2 = new TaskEntity();
        List<TaskEntity> taskEntities = Arrays.asList(taskEntity1, taskEntity2);
        when(taskRepository.streamAllBy()).thenReturn(taskEntities.stream());

        TaskDto taskDto1 = new TaskDto();
        TaskDto taskDto2 = new TaskDto();
        List<TaskDto> expected = Arrays.asList(taskDto1, taskDto2);
        when(taskDtoFactory.makeTaskDto(taskEntity1)).thenReturn(taskDto1);
        when(taskDtoFactory.makeTaskDto(taskEntity2)).thenReturn(taskDto2);

        // Act
        List<TaskDto> result = taskService.getAllTasks();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getAllTasks_ThrowsException_WhenTaskDtoFactoryThrowsException() {
        // Arrange
        TaskEntity taskEntity1 = new TaskEntity();
        TaskEntity taskEntity2 = new TaskEntity();
        List<TaskEntity> taskEntities = Arrays.asList(taskEntity1, taskEntity2);
        when(taskRepository.streamAllBy()).thenReturn(taskEntities.stream());

        RuntimeException exception = new RuntimeException("Test exception");
        when(taskDtoFactory.makeTaskDto(any(TaskEntity.class))).thenThrow(exception);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> taskService.getAllTasks());

        // Verify
        verify(taskRepository).streamAllBy();
        verify(taskDtoFactory).makeTaskDto(taskEntity1);
        verifyNoMoreInteractions(taskDtoFactory);
    }
}