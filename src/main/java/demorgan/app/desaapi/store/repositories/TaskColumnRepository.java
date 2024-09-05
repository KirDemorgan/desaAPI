package demorgan.app.desaapi.store.repositories;

import demorgan.app.desaapi.store.entities.TaskColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskColumnRepository extends JpaRepository<TaskColumnEntity, Long> {
}
