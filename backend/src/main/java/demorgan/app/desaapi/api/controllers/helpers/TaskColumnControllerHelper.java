package demorgan.app.desaapi.api.controllers.helpers;

import demorgan.app.desaapi.api.exceptions.NotFoundException;
import demorgan.app.desaapi.store.entities.TaskColumnEntity;
import demorgan.app.desaapi.store.repositories.TaskColumnRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
@Transactional
public class TaskColumnControllerHelper {

    TaskColumnRepository taskColumnRepository;

    public TaskColumnEntity getTaskColumnOrThrowException(Long taskStateId) {

        return taskColumnRepository
                .findById(taskStateId)
                .orElseThrow(() ->
                        new NotFoundException(
                                String.format(
                                        "Task column with \"%s\" id doesn't exist.",
                                        taskStateId
                                )
                        )
                );
    }
}
