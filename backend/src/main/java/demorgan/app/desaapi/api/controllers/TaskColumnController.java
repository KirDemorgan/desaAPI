package demorgan.app.desaapi.api.controllers;
import org.springframework.web.bind.annotation.CrossOrigin;
import demorgan.app.desaapi.api.controllers.helpers.ProjectControllerHelper;
import demorgan.app.desaapi.api.controllers.helpers.TaskColumnControllerHelper;
import demorgan.app.desaapi.api.dto.AnswerDto;
import demorgan.app.desaapi.api.dto.TaskColumnDto;
import demorgan.app.desaapi.api.exceptions.BadRequestException;
import demorgan.app.desaapi.api.factories.TaskColumnDtoFactory;
import demorgan.app.desaapi.store.entities.ProjectEntity;
import demorgan.app.desaapi.store.entities.TaskColumnEntity;
import demorgan.app.desaapi.store.repositories.TaskColumnRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@RestController
public class TaskColumnController {

    TaskColumnRepository taskColumnRepository;

    TaskColumnDtoFactory taskColumnDtoFactory;

    ProjectControllerHelper projectControllerHelper;

    TaskColumnControllerHelper taskColumnControllerHelper;

    public static final String GET_TASK_COLUMNS = "/projects/{project_id}/task-columns";
    public static final String CREATE_TASK_COLUMN = "/projects/{project_id}/task-columns";
    public static final String UPDATE_TASK_COLUMN = "/task-columns/{task_column_id}";
    public static final String CHANGE_TASK_COLUMN_POSITION = "/task-columns/{task_column_id}/position/change";
    public static final String DELETE_TASK_COLUMN = "/task-columns/{task_column_id}";



    @GetMapping(GET_TASK_COLUMNS)
    public List<TaskColumnDto> getTaskColumns(@PathVariable("project_id") Long projectId) {

        ProjectEntity project = projectControllerHelper.getProjectOrThrowException(projectId);

        return project.getTaskColumn()
                .stream()
                .map(taskColumnDtoFactory::makeTaskColumnDto)
                .collect(Collectors.toList());
    }


    @PostMapping(CREATE_TASK_COLUMN)
    public TaskColumnDto createTaskColumn(
            @PathVariable(name = "project_id") Long projectId,
            @RequestParam(name = "column_name") String columnName
    ) {
        if (columnName.trim().isEmpty()) {
            throw new BadRequestException("Task column name can't be empty.");
        }

        ProjectEntity project = projectControllerHelper.getProjectOrThrowException(projectId);

        for (TaskColumnEntity taskColumn : project.getTaskColumn()) {
            if (taskColumn.getName().equals(columnName)) {
                throw new BadRequestException("Task column with the same name already exists.");
            }
        }

        TaskColumnEntity taskColumn = taskColumnRepository.saveAndFlush(
                TaskColumnEntity.builder()
                        .name(columnName)
                        .project(project)
                        .build()
        );
        return taskColumnDtoFactory.makeTaskColumnDto(taskColumn);
    }


    @PatchMapping(UPDATE_TASK_COLUMN)
    public TaskColumnDto updateTaskColumn(
            @PathVariable(name = "task_column_id") Long taskColumnId,
            @RequestParam(name = "task_column_name") String columnName
    ) {
        if (columnName.trim().isEmpty()) {
            throw new BadRequestException("Task column name can't be empty.");
        }

        TaskColumnEntity taskColumn = taskColumnControllerHelper.getTaskColumnOrThrowException(taskColumnId);

        taskColumnRepository
                .findTaskColumnEntityByProjectIdAndNameContainsIgnoreCase(
                        taskColumn.getProject().getId(),
                        columnName
                )
                .filter(anotherTaskState -> !anotherTaskState.getId().equals(taskColumnId))
                .ifPresent(anotherTaskState -> {
                    throw new BadRequestException(String.format("Task state \"%s\" already exists.", columnName));
                });
        taskColumn.setName(columnName);

        taskColumn = taskColumnRepository.saveAndFlush(taskColumn);

        return taskColumnDtoFactory.makeTaskColumnDto(taskColumn);
    }


    @PatchMapping(CHANGE_TASK_COLUMN_POSITION)
    public TaskColumnDto changeTaskColumnPosition(
            @PathVariable(name = "task_column_id") Long taskColumnId,
            @RequestParam(name = "task_column_new_position") Long newPosition
    ) {
        TaskColumnEntity taskColumn = taskColumnControllerHelper.getTaskColumnOrThrowException(taskColumnId);

        taskColumn.setPosition(newPosition);

        taskColumn = taskColumnRepository.saveAndFlush(taskColumn);

        return taskColumnDtoFactory.makeTaskColumnDto(taskColumn);
    }


    @DeleteMapping(DELETE_TASK_COLUMN)
    public AnswerDto deleteColumn(
            @PathVariable(name = "task_column_id") Long taskColumnId
    ) {
        TaskColumnEntity taskColumn = taskColumnControllerHelper.getTaskColumnOrThrowException(taskColumnId);

        taskColumnRepository.deleteById(taskColumnId);

        return AnswerDto.builder()
                .answer("Task column with id " + taskColumnId + " deleted successfully")
                .build();
    }
}
