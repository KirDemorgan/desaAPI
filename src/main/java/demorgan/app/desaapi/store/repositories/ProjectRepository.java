package demorgan.app.desaapi.store.repositories;

import demorgan.app.desaapi.store.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
}
