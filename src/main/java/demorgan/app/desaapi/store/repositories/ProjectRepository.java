package demorgan.app.desaapi.store.repositories;

import demorgan.app.desaapi.store.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.Stream;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    Stream<ProjectEntity> streamAllByNameStartsWithIgnoreCase(String prefixName);
    Stream<ProjectEntity> streamAllBy();
    Optional<ProjectEntity> findByName(String name);

}
