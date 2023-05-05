package chursinov.tasktrackerapi.api.controllers;

import chursinov.tasktrackerapi.api.dto.ProjectDto;
import chursinov.tasktrackerapi.api.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestController
@Tag(name = "Projects", description = "Projects controller")
public class ProjectController {

    ProjectService projectService;

    public static final String CREATE_PROJECT = "/api/projects";
    public static final String FETCH_PROJECT = "/api/projects";

    @PostMapping(value = CREATE_PROJECT, produces = "application/json")
    @Operation(summary = "Create project")
    @ApiResponse(responseCode = "200", description = "Project successfully created")
    public ProjectDto createProject(
            @RequestParam(value = "project_name") String projectName) {

        return projectService.createProject(projectName);
    }

    @GetMapping(FETCH_PROJECT)
    public List<ProjectDto> fetchProjects(
            @RequestParam(value = "prefix_name", required = false) Optional<String> optionalPrefixName) {

        return projectService.fetchProjects(optionalPrefixName);
    }

}
