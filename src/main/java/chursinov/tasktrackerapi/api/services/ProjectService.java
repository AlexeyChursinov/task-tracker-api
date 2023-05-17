package chursinov.tasktrackerapi.api.services;

import chursinov.tasktrackerapi.api.controllers.helpers.ControllerHelper;
import chursinov.tasktrackerapi.api.dto.ProjectDto;
import chursinov.tasktrackerapi.api.exceptions.BadRequestException;
import chursinov.tasktrackerapi.api.exceptions.NotFoundException;
import chursinov.tasktrackerapi.api.factories.ProjectDtoFactory;
import chursinov.tasktrackerapi.store.entities.ProjectEntity;
import chursinov.tasktrackerapi.store.repositories.ProjectRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class ProjectService {

    ProjectRepository projectRepository;

    ProjectDtoFactory projectDtoFactory;

    ControllerHelper controllerHelper;

    @Transactional(readOnly = true)
    public List<ProjectDto> fetchProjects(Optional<String> optionalPrefixName) {

        String prefixName = optionalPrefixName.orElse(null);

        Stream<ProjectEntity> projectStream = prefixName == null
                ? projectRepository.streamAllBy()
                : projectRepository.streamAllByNameStartsWithIgnoreCase(prefixName);

        return projectStream
                .map(projectDtoFactory::makeProjectDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProjectDto getProject(Long projectId) {
        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException(String.format("Project with id %d not found", projectId)));

        return projectDtoFactory.makeProjectDto(projectEntity);
    }


    @Transactional
    public ProjectDto createProject(String projectName) {

        validateProjectName(projectName);

        ProjectEntity project = projectRepository.saveAndFlush(
                ProjectEntity.builder()
                        .name(projectName)
                        .build()
        );

        return projectDtoFactory.makeProjectDto(project);
    }

    public ProjectDto updateProject(Long projectId, String projectName) {
        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException(String.format("Project with id %d not found", projectId)));

        validateProjectName(projectName);

        projectEntity.setName(projectName);
        projectEntity.setUpdatedAt(Instant.now());
        projectRepository.saveAndFlush(projectEntity);

        return projectDtoFactory.makeProjectDto(projectEntity);
    }

    @Transactional
    public void deleteProject(Long projectId) {

        controllerHelper.getProjectOrThrowException(projectId);

        projectRepository.deleteById(projectId);
    }

    private void validateProjectName(String projectName) {
        if (projectName.isEmpty()) {
            throw new BadRequestException("Project name should not be empty or contain only spaces.");
        }

        if (projectRepository.findByName(projectName).isPresent()) {
            throw new BadRequestException(String.format("Project %s already exist.", projectName));
        }
    }
}

