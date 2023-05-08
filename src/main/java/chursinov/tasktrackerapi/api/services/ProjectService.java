package chursinov.tasktrackerapi.api.services;

import chursinov.tasktrackerapi.api.controllers.helpers.ControllerHelper;
import chursinov.tasktrackerapi.api.dto.ProjectDto;
import chursinov.tasktrackerapi.api.exceptions.BadRequestException;
import chursinov.tasktrackerapi.api.exceptions.NotFoundException;
import chursinov.tasktrackerapi.api.factories.ProjectDtoFactory;
import chursinov.tasktrackerapi.store.entities.ProjectEntity;
import chursinov.tasktrackerapi.store.repositories.ProjectRepository;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class ProjectService {

    @NonNull
    ProjectRepository projectRepository;

    @NonNull
    ProjectDtoFactory projectDtoFactory;

    @NonNull
    ControllerHelper controllerHelper;

    @Transactional(readOnly = true)
    public List<ProjectDto> fetchProjects(Optional<String> optionalPrefixName) {

        optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());

        Stream<ProjectEntity> projectStream = optionalPrefixName
                .map(projectRepository::streamAllByNameStartsWithIgnoreCase)
                .orElseGet(projectRepository::streamAllBy);

        return projectStream
                .map(projectDtoFactory::makeProjectDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProjectDto createProject(String projectName) {

        String validProjectName = projectName.trim();

        if (validProjectName.isEmpty()) {
            throw new BadRequestException("Project name should not be empty or contain only spaces.");
        }

        if (projectRepository.findByName(validProjectName).isPresent()) {
            throw new BadRequestException(String.format("Project with name = %s already exist.", validProjectName));
        }

        ProjectEntity project = projectRepository.saveAndFlush(
                ProjectEntity.builder()
                        .name(validProjectName)
                        .build()
        );

        return projectDtoFactory.makeProjectDto(project);
    }

    @Transactional
    public ProjectDto updateProject(Long projectId, String projectName) {
        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException(String.format("Project with id %d not found", projectId)));

        String validProjectName = projectName.trim();

        if (validProjectName.isEmpty()) {
            throw new BadRequestException("Project name should not be empty or contain only spaces.");
        }

        if (projectRepository.findByName(validProjectName).isPresent()) {
            throw new BadRequestException(String.format("Project %s already exist.", validProjectName));
        }

        projectEntity.setName(validProjectName);
        projectEntity.setUpdatedAt(Instant.now());
        ProjectEntity updatedProject = projectRepository.saveAndFlush(projectEntity);

        return projectDtoFactory.makeProjectDto(updatedProject);
    }


    @Transactional
    public void deleteProject(Long projectId) {

        controllerHelper.getProjectOrThrowException(projectId);

        projectRepository.deleteById(projectId);
    }
}

