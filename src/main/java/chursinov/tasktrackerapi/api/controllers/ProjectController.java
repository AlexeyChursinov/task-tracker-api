package chursinov.tasktrackerapi.api.controllers;

import chursinov.tasktrackerapi.api.controllers.helpers.ControllerHelper;
import chursinov.tasktrackerapi.api.dto.AnswerDto;
import chursinov.tasktrackerapi.api.dto.ProjectDto;
import chursinov.tasktrackerapi.api.responses.ErrorResponse400;
import chursinov.tasktrackerapi.api.responses.ErrorResponse404;
import chursinov.tasktrackerapi.api.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/projects")
@Tag(name = "Projects", description = "Projects controller")
public class ProjectController {

    ProjectService projectService;
    ControllerHelper controllerHelper;

    public static final String FETCH_PROJECT = "/get-projects";
    public static final String CREATE_PROJECT = "/create-project";
    public static final String UPDATE_PROJECT = "/update-project/{project_id}";
    public static final String DELETE_PROJECT = "/delete-project/{project_id}";

    @GetMapping(value = FETCH_PROJECT, produces = "application/json")
    @Operation(summary = "Get projects")
    @ApiResponse(responseCode = "200", description = "Project successfully received")
    public List<ProjectDto> fetchProjects(
            @RequestParam(value = "prefix_name", required = false) Optional<String> optionalPrefixName) {

        return projectService.fetchProjects(optionalPrefixName);
    }

    @PostMapping(value = CREATE_PROJECT, produces = "application/json")
    @Operation(summary = "Create project")
    @ApiResponse(responseCode = "200", description = "Project successfully created")
    public ProjectDto createProject(
            @RequestParam(value = "project_name") String projectName) {

        return projectService.createProject(projectName);
    }

    @PutMapping(value = UPDATE_PROJECT, produces = "application/json")
    @Operation(summary = "Update project")
    @ApiResponse(responseCode = "200", description = "Project successfully updated")
    @ErrorResponse400
    @ErrorResponse404
    public ProjectDto updateProject(
            @PathVariable("project_id") Long projectId,
            @RequestParam("project_name") String projectName) {

        return projectService.updateProject(projectId, projectName);

    }

    @DeleteMapping(value = DELETE_PROJECT, produces = "application/json")
    @Operation(summary = "Delete project")
    @ApiResponse(responseCode = "200", description = "Project successfully deleted")
    @ErrorResponse404
    public AnswerDto deleteProject(@PathVariable("project_id") Long projectId) {

        controllerHelper.getProjectOrThrowException(projectId);

        projectService.deleteProject(projectId);

        return AnswerDto.makeDefault(true);
    }
}
