package demorgan.app.desaapi.api.controllers;
import org.springframework.web.bind.annotation.CrossOrigin;
import demorgan.app.desaapi.api.controllers.helpers.ProjectControllerHelper;
import demorgan.app.desaapi.api.controllers.helpers.TaskColumnControllerHelper;
import demorgan.app.desaapi.api.controllers.helpers.TaskControllerHelper;
import demorgan.app.desaapi.api.dto.AnswerDto;
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
@CrossOrigin(origins = "*")
public class TaskController {

    TaskRepository taskRepository;

    TaskDtoFactory taskDtoFactory;

    ProjectControllerHelper projectControllerHelper;
    TaskControllerHelper taskControllerHelper;
    TaskColumnControllerHelper taskColumnControllerHelper;

    public static final String GET_TASKS = "/projects/{task_column_id}/tasks";
    public static final String CREATE_TASK = "/projects/{task_column_id}/task";
    public static final String UPDATE_TASK = "/projects/{task_id}/task";
    public static final String DELETE_TASK = "/projects/{task_id}/task";


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

        if (taskName.trim().isEmpty()) {
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


    @PatchMapping(UPDATE_TASK)
    public TaskDto updateTask(
            @PathVariable(name = "task_id") Long taskId,
            @RequestBody TaskRequestDto taskRequestDto
    ) {
        String taskName = taskRequestDto.getTaskName();
        Optional<String> optionalTaskDescription = taskRequestDto.getOptionalTaskDescription();

        if (taskName.trim().isEmpty()) {
            throw new BadRequestException("Task name can't be empty.");
        }

        TaskEntity task = taskControllerHelper.getTaskOrThrowException(taskId);

        taskRepository
                .findTaskEntityByIdAndNameContainsIgnoreCase(
                        taskId,
                        taskName
                )
                .filter(anotherTask -> !anotherTask.getId().equals(taskId))
                .ifPresent(anotherTask -> {
                    throw new BadRequestException("Task with the same name already exists.");
                });

        task.setName(taskName);

        optionalTaskDescription.ifPresent(task::setDescription);

        task = taskRepository.saveAndFlush(task);

        return taskDtoFactory.makeTaskDto(task);
    }


    @DeleteMapping(DELETE_TASK)
    public AnswerDto deleteTask(
            @PathVariable("task_id") Long taskId
    ) {
        TaskEntity task = taskControllerHelper.getTaskOrThrowException(taskId);

        taskRepository.deleteById(taskId);

        return AnswerDto.builder()
                .answer("Task with id " + taskId + " deleted successfully")
                .build();
    }
}
