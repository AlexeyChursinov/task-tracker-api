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

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class ProjectService {

    ProjectRepository projectRepository;
    ProjectDtoFactory projectDtoFactory;

    @Transactional
    public ProjectDto createProject(String projectName) {

        String validProjectName = projectName.trim();

        if (validProjectName.isEmpty()) {
            throw new BadRequestException("Project name should not be empty or contain only spaces.");
        }

        if (projectRepository.findByName(validProjectName).isPresent()) {
            throw new BadRequestException(String.format("Project \"%s\" already exist.", validProjectName));
        }

        ProjectEntity project = projectRepository.saveAndFlush(
                ProjectEntity.builder()
                        .name(validProjectName)
                        .build()
        );

        return projectDtoFactory.makeProjectDto(project);
    }

    @Transactional
    public List<ProjectDto> fetchProjects(Optional<String> optionalPrefixName) {

        optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());

        Stream<ProjectEntity> projectStream = optionalPrefixName
                .map(projectRepository::streamAllByNameStartsWithIgnoreCase)
                .orElseGet(projectRepository::streamAllBy);

        return projectStream
                .map(projectDtoFactory::makeProjectDto)
                .collect(Collectors.toList());
    }
}

