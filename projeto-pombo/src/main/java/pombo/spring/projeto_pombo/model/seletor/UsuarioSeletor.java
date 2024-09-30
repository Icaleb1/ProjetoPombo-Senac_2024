package pombo.spring.projeto_pombo.model.seletor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import pombo.spring.projeto_pombo.model.entity.Usuario;

@Data
public class UsuarioSeletor extends BaseSeletor implements Specification<Usuario>{
	
	private String nome;
	
	public boolean temFiltro() {
		return  (this.stringValida(this.nome)); 
	}

	@Override
	public Predicate toPredicate(Root<Usuario> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		 List<Predicate> predicates = new ArrayList<>();

         if(this.stringValida(this.nome)) {
            predicates.add(cb.like(root.get("nome"), "%" + this.getNome() + "%"));
         }
        return cb.and(predicates.toArray(new Predicate[0]));
	}
}
