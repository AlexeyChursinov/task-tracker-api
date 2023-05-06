package chursinov.tasktrackerapi.api.controllers.helpers;

import chursinov.tasktrackerapi.api.exceptions.NotFoundException;
import chursinov.tasktrackerapi.store.repositories.ProjectRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
@Transactional
public class ControllerHelper {

    ProjectRepository projectRepository;

    public void getProjectOrThrowException(Long projectId) {
        projectRepository
                .findById(projectId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Project with %s doesn't exist.", projectId))
                );
    }
}
