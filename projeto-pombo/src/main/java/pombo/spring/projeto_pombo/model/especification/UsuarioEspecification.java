package pombo.spring.projeto_pombo.model.especification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import pombo.spring.projeto_pombo.model.entity.Usuario;
import pombo.spring.projeto_pombo.model.seletor.UsuarioSeletor;

public class UsuarioEspecification {
	
	public static Specification<Usuario> comFiltros(UsuarioSeletor seletor){
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if(seletor.getNome() != null && seletor.getNome().trim().length() > 0) {
				predicates.add(cb.like(root.get("nome"),"%" + seletor.getNome() + "%"));
			}
			
			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}

}
