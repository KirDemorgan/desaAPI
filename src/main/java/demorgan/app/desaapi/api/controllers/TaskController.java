package demorgan.app.desaapi.api.controllers;

import demorgan.app.desaapi.api.controllers.helpers.ProjectControllerHelper;
import demorgan.app.desaapi.api.controllers.helpers.TaskColumnControllerHelper;
import demorgan.app.desaapi.api.controllers.helpers.TaskControllerHelper;
import demorgan.app.desaapi.api.dto.TaskDto;
import demorgan.app.desaapi.api.factories.TaskDtoFactory;
import demorgan.app.desaapi.store.entities.TaskColumnEntity;
import demorgan.app.desaapi.store.entities.TaskEntity;
import demorgan.app.desaapi.store.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RequestMapping("/api")
@RestController
public class TaskController {

    TaskRepository taskRepository;

    TaskDtoFactory taskDtoFactory;

    ProjectControllerHelper projectControllerHelper;
    TaskControllerHelper taskControllerHelper;
    TaskColumnControllerHelper taskColumnControllerHelper;

    public static final String GET_TASKS = "/projects/{task_column_id}/tasks";
    public static final String CREATE_TASK= "/projects/{task_column_id}/task";
    public static final String UPDATE_TASK = "/projects/{task_column_id}/task";
    public static final String DELETE_TASK= "/projects/{task_column_id}/task";


    @GetMapping(GET_TASKS)
    public List<TaskDto> getTasks(
            @PathVariable("task_column_id") Long taskColumnId
    ) {
        TaskColumnEntity taskColumn = taskColumnControllerHelper.getTaskStateOrThrowException(taskColumnId);

        return taskColumn.getTasks()
                .stream()
                .map(taskDtoFactory::makeTaskDto)
                .collect(Collectors.toList());

    }
}
