package demorgan.app.desaapi.store.repositories;

import demorgan.app.desaapi.store.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
