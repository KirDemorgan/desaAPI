package demorgan.app.desaapi.store.repositories;

import demorgan.app.desaapi.store.entities.TaskColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskColumnRepository extends JpaRepository<TaskColumnEntity, Long> {
    Optional<TaskColumnEntity> findTaskColumnEntityByProjectIdAndNameContainsIgnoreCase(Long projectId, String taskStateName);

}
