package demorgan.app.desaapi.store.repositories;

import demorgan.app.desaapi.store.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    Optional<TaskEntity> findTaskEntityByIdAndNameContainsIgnoreCase(Long taskId, String taskName);

}
