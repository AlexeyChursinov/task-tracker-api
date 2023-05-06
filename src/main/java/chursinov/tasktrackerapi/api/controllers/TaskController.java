package chursinov.tasktrackerapi.api.controllers;

import chursinov.tasktrackerapi.api.dto.TaskDto;
import chursinov.tasktrackerapi.api.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Tasks", description = "Tasks controller")
public class TaskController {

    TaskService taskService;

    public static final String CREATE_TASK = "/create-task";

    @PostMapping(value = CREATE_TASK, produces = "application/json")
    @Operation(summary = "Create task")
    @ApiResponse(responseCode = "200", description = "Task successfully received")
    public TaskDto createProject(@RequestBody TaskDto task) {

        return taskService.createTask(task);
    }

}
