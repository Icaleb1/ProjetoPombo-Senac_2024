package pombo.spring.projeto_pombo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pombo.spring.projeto_pombo.model.entity.Denuncia;

@Repository
public interface DenunciaRepository extends JpaRepository<Denuncia, String>{

}
