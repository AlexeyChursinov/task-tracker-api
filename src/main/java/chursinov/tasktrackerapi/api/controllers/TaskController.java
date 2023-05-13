package chursinov.tasktrackerapi.api.controllers;

import chursinov.tasktrackerapi.api.dto.AnswerDto;
import chursinov.tasktrackerapi.api.dto.CreateTaskDto;
import chursinov.tasktrackerapi.api.dto.TaskDto;
import chursinov.tasktrackerapi.api.responses.ErrorResponse400;
import chursinov.tasktrackerapi.api.responses.ErrorResponse404;
import chursinov.tasktrackerapi.api.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Tasks", description = "Tasks controller")
public class TaskController {

    private static final String MEDIA_TYPE = "application/json";

    private static final String GET_TASK = "/get-tasks";
    private static final String CREATE_TASK = "/create-task";
    private static final String UPDATE_TASK = "/update-task";
    private static final String DELETE_TASK = "/delete-task/{id}";

    private final TaskService taskService;

    @GetMapping(value = GET_TASK, produces = MEDIA_TYPE)
    @Operation(summary = "Get all tasks")
    @ApiResponse(responseCode = "200", description = "Tasks successfully received")
    public List<TaskDto> getAllTasks() {

        return taskService.getAllTasks();
    }

    @PostMapping(value = CREATE_TASK, produces = MEDIA_TYPE)
    @Operation(summary = "Create task")
    @ApiResponse(responseCode = "200", description = "Task successfully created")
    @ErrorResponse400
    public TaskDto createTask(@RequestBody CreateTaskDto task,
                              @Parameter(description = "Project ID")
                              @RequestParam("id") Long projectId) {

        return taskService.createTask(task, projectId);
    }

    @PutMapping(value = UPDATE_TASK, produces = MEDIA_TYPE)
    @Operation(summary = "Update task")
    @ApiResponse(responseCode = "200", description = "Task successfully updated")
    @ErrorResponse400
    public TaskDto updateTask(@RequestBody CreateTaskDto task,
                              @RequestParam Long projectId,
                              @RequestParam Long taskId) {

        return taskService.updateTask(task, projectId, taskId);
    }

    @DeleteMapping(value = DELETE_TASK, produces = MEDIA_TYPE)
    @Operation(summary = "Delete task")
    @ApiResponse(responseCode = "200", description = "Task successfully deleted")
    @ErrorResponse400
    @ErrorResponse404
    public AnswerDto deleteTask(@Parameter(description = "Task ID")
                                @PathVariable("id") Long taskId) {

        taskService.deleteTask(taskId);

        return AnswerDto.makeDefault("Task successfully deleted");
    }

}
