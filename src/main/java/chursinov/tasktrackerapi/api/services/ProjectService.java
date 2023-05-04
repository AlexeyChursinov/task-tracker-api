package chursinov.tasktrackerapi.api.services;

import chursinov.tasktrackerapi.api.dto.ProjectDto;
import chursinov.tasktrackerapi.api.exceptions.BadRequestException;
import chursinov.tasktrackerapi.api.factories.ProjectDtoFactory;
import chursinov.tasktrackerapi.store.entities.ProjectEntity;
import chursinov.tasktrackerapi.store.repositories.ProjectRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional
@Service
public class ProjectService {

    ProjectRepository projectRepository;
    ProjectDtoFactory projectDtoFactory;

    @Transactional
    public ProjectDto createProject(String projectName) {

        Optional<String> projectNameOptional = Optional.ofNullable(projectName);

        String validProjectName = projectNameOptional
                .map(String::trim)
                .filter(name -> !name.isEmpty())
                .orElseThrow(() -> new BadRequestException("Project name should not be empty or contain only spaces."));

        projectRepository
                .findByName(validProjectName)
                .ifPresent(project -> {
                    throw new BadRequestException(String.format("Project \"%s\" already exist.", validProjectName));
                });

        ProjectEntity project = projectRepository.saveAndFlush(
                ProjectEntity.builder()
                        .name(validProjectName)
                        .build()
        );

        return projectDtoFactory.makeProjectDto(project);
    }
}

