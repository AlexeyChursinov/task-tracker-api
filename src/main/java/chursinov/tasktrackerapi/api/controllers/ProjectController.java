package chursinov.tasktrackerapi.api.controllers;

import chursinov.tasktrackerapi.api.dto.ProjectDto;
import chursinov.tasktrackerapi.api.services.ProjectService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class ProjectController {

    public static final String CREATE_PROJECT = "/api/projects";
    ProjectService projectService;

    @PostMapping(CREATE_PROJECT)
    public ProjectDto createProject(
            @RequestParam(value = "project_name") String projectName) {

        return projectService.createProject(projectName);
    }

}
