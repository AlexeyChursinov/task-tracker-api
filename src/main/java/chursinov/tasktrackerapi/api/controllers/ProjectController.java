package chursinov.tasktrackerapi.api.controllers;

import chursinov.tasktrackerapi.api.dto.AnswerDto;
import chursinov.tasktrackerapi.api.dto.ProjectDto;
import chursinov.tasktrackerapi.api.responses.ErrorResponse400;
import chursinov.tasktrackerapi.api.responses.ErrorResponse404;
import chursinov.tasktrackerapi.api.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    public static final String MEDIA_TYPE = "application/json";

    private static final String FETCH_PROJECT = "/get-projects";
    private static final String GET_PROJECT = "/get-project/{id}";
    private static final String CREATE_PROJECT = "/create-project";
    private static final String UPDATE_PROJECT = "/update-project/{id}";
    private static final String DELETE_PROJECT = "/delete-project/{id}";

    private final ProjectService projectService;

    @GetMapping(value = FETCH_PROJECT, produces = MEDIA_TYPE)
    @Operation(summary = "Get all projects")
    @ApiResponse(responseCode = "200", description = "Projects successfully received")
    @ErrorResponse400
    public List<ProjectDto> fetchProjects(
            @RequestParam(value = "prefixName", required = false) Optional<String> optionalPrefixName) {

        return projectService.fetchProjects(optionalPrefixName);
    }

    @GetMapping(value = GET_PROJECT, produces = MEDIA_TYPE)
    @Operation(summary = "Get project by ID")
    @ApiResponse(responseCode = "200", description = "Project successfully received")
    @ErrorResponse400
    public ProjectDto getProject(@Parameter(description = "Project ID")
                                 @PathVariable("id") Long projectId) {

        return projectService.getProject(projectId);
    }

    @PostMapping(value = CREATE_PROJECT, produces = MEDIA_TYPE)
    @Operation(summary = "Create project")
    @ApiResponse(responseCode = "200", description = "Project successfully created")
    @ErrorResponse400
    public ProjectDto createProject(@Parameter(description = "Project Name")
                                    @RequestParam String projectName) {

        return projectService.createProject(projectName);
    }

    @PutMapping(value = UPDATE_PROJECT, produces = MEDIA_TYPE)
    @Operation(summary = "Update project")
    @ApiResponse(responseCode = "200", description = "Project successfully updated")
    @ErrorResponse400
    @ErrorResponse404
    public ProjectDto updateProject(
            @Parameter(description = "Project ID")
            @PathVariable("id") Long projectId,
            @Parameter(description = "Project Name")
            @RequestParam String projectName) {

        return projectService.updateProject(projectId, projectName);

    }

    @DeleteMapping(value = DELETE_PROJECT, produces = MEDIA_TYPE)
    @Operation(summary = "Delete project")
    @ApiResponse(responseCode = "200", description = "Project successfully deleted")
    @ErrorResponse400
    @ErrorResponse404
    public AnswerDto deleteProject(@Parameter(description = "Project ID")
                                   @PathVariable("id") Long projectId) {

        projectService.deleteProject(projectId);

        return AnswerDto.makeDefault("Project successfully deleted");
    }
}
