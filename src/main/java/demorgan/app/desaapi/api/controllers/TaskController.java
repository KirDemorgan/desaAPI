package demorgan.app.desaapi.api.controllers;

import demorgan.app.desaapi.api.controllers.helpers.ProjectControllerHelper;
import demorgan.app.desaapi.api.controllers.helpers.TaskColumnControllerHelper;
import demorgan.app.desaapi.api.controllers.helpers.TaskControllerHelper;
import demorgan.app.desaapi.api.dto.TaskDto;
import demorgan.app.desaapi.api.dto.TaskRequestDto;
import demorgan.app.desaapi.api.exceptions.BadRequestException;
import demorgan.app.desaapi.api.factories.TaskDtoFactory;
import demorgan.app.desaapi.store.entities.TaskColumnEntity;
import demorgan.app.desaapi.store.entities.TaskEntity;
import demorgan.app.desaapi.store.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
        TaskColumnEntity taskColumn = taskColumnControllerHelper.getTaskColumnOrThrowException(taskColumnId);

        return taskColumn.getTasks()
                .stream()
                .map(taskDtoFactory::makeTaskDto)
                .collect(Collectors.toList());
    }


    @PostMapping(CREATE_TASK)
    public TaskDto createTask(
            @PathVariable(name = "task_column_id") Long taskColumnId,
            @RequestBody TaskRequestDto taskRequestDto
            ) {

        String taskName = taskRequestDto.getTaskName();
        Optional<String> optionalTaskDescription = taskRequestDto.getOptionalTaskDescription();

        if(taskName.trim().isEmpty()){
            throw new BadRequestException("Task name can't be empty.");
        }

        TaskColumnEntity taskColumn = taskColumnControllerHelper.getTaskColumnOrThrowException(taskColumnId);

        if (taskColumn.getTasks()
                .stream()
                .anyMatch(task -> task.getName()
                        .equals(taskName))) {
            throw new BadRequestException("Task with the same name already exists.");
        }

        TaskEntity task = taskRepository.saveAndFlush(
                TaskEntity.builder()
                        .name(taskName)
                        .description(String.valueOf(optionalTaskDescription))
                        .taskColumn(taskColumn)
                        .build()
        );
        return taskDtoFactory.makeTaskDto(task);
    }
}
