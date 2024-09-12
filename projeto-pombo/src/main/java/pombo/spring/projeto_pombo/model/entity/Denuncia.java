package pombo.spring.projeto_pombo.model.entity;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table
@Entity
@Data
public class Denuncia {
	
	@Id
	@UuidGenerator
	private String uuid; 
	
	//private Pruu pruuDenunciado;
	private Integer usuarioDenunciado;
	private Integer usuarioDenunciador;

}
