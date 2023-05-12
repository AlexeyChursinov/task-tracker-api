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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Tasks", description = "Tasks controller")
public class TaskController {

    public static final String CREATE_TASK = "/create-task";
    public static final String UPDATE_TASK = "/update-task";
    public static final String DELETE_TASK = "/delete-task/{id}";

    private final TaskService taskService;

    @PostMapping(value = CREATE_TASK, produces = "application/json")
    @Operation(summary = "Create task")
    @ApiResponse(responseCode = "200", description = "Task successfully created")
    @ErrorResponse400
    public TaskDto createTask(@RequestBody CreateTaskDto task,
                              @Parameter(description = "Project ID")
                              @RequestParam("id") Long projectId) {

        return taskService.createTask(task, projectId);
    }

    @PutMapping(value = UPDATE_TASK, produces = "application/json")
    @Operation(summary = "Update task")
    @ApiResponse(responseCode = "200", description = "Task successfully updated")
    @ErrorResponse400
    public TaskDto updateTask(@RequestBody CreateTaskDto task,
                              @RequestParam Long projectId,
                              @RequestParam Long taskId) {

        return taskService.updateTask(task, projectId, taskId);
    }

    @DeleteMapping(value = DELETE_TASK, produces = "application/json")
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
