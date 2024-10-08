package demorgan.app.desaapi.api.factories;

import demorgan.app.desaapi.api.dto.ProjectDto;
import demorgan.app.desaapi.store.entities.ProjectEntity;
import demorgan.app.desaapi.store.entities.TaskColumnEntity;
import org.springframework.stereotype.Component;

@Component
public class ProjectDtoFactory {

    public ProjectDto makeProjectDto(ProjectEntity entity) {

        return ProjectDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .taskColumnIds(entity.getTaskColumn().stream()
                        .map(TaskColumnEntity::getId)
                        .toList()
                )
                .build();
    }
}
