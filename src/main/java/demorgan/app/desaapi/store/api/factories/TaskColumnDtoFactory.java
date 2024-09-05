package demorgan.app.desaapi.store.api.factories;

import demorgan.app.desaapi.store.api.dto.TaskColumnDto;
import demorgan.app.desaapi.store.entities.TaskColumnEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Component
public class TaskColumnDtoFactory {

    TaskDtoFactory taskDtoFactory;

    public TaskColumnDto makeTaskColumnDto(TaskColumnEntity entity){

        return TaskColumnDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .position(entity.getPosition())
                .tasks(entity
                            .getTasks()
                            .stream()
                        .map(taskDtoFactory::makeTaskDto)
                        .collect(Collectors.toList())
                )
                .build();
    }
}
