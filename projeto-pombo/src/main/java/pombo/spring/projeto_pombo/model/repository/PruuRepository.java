package pombo.spring.projeto_pombo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import pombo.spring.projeto_pombo.model.entity.Pruu;

@Repository
public interface PruuRepository extends JpaRepository<Pruu, String>, JpaSpecificationExecutor<Pruu>{

}
