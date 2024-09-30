package pombo.spring.projeto_pombo.model.seletor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import pombo.spring.projeto_pombo.model.entity.Pruu;

@Data
public class PruuSeletor extends BaseSeletor implements Specification<Pruu>{
	
	private LocalDate dataInicioCriacao;
	private LocalDate dataFimCriacao;
	
	public boolean temFiltro() {
		return (this.dataInicioCriacao != null)
				|| (this.dataFimCriacao != null);
	}
	
	@Override
	public Predicate toPredicate(Root<Pruu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<>();
		aplicarFiltroPeriodo(root, cb, predicates, dataInicioCriacao, dataFimCriacao, "dataHoraCriacao");
	
		return cb.and(predicates.toArray(new Predicate[0]));
	}

}
