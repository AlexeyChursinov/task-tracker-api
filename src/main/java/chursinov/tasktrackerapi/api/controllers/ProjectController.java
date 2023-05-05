package chursinov.tasktrackerapi.api.controllers;

import chursinov.tasktrackerapi.api.dto.ProjectDto;
import chursinov.tasktrackerapi.api.services.ProjectService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestController
public class ProjectController {

    ProjectService projectService;

    public static final String CREATE_PROJECT = "/api/projects";
    public static final String FETCH_PROJECT = "/api/projects";

    @PostMapping(CREATE_PROJECT)
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
