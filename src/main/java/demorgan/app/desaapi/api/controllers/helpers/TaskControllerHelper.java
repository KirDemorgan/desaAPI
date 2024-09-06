package demorgan.app.desaapi.api.controllers.helpers;

import demorgan.app.desaapi.api.exceptions.NotFoundException;
import demorgan.app.desaapi.store.entities.TaskEntity;
import demorgan.app.desaapi.store.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
@Transactional
public class TaskControllerHelper {

    TaskRepository taskRepository;

    public TaskEntity getTaskOrThrowException(Long taskId) {

        return taskRepository
                .findById(taskId)
                .orElseThrow(() ->
                        new NotFoundException(
                                String.format(
                                        "Task with \"%s\" doesn't exist.", taskId
                                )
                        )
                );
    }
}