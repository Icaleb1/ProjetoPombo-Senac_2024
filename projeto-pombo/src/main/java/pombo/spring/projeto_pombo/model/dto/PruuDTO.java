package pombo.spring.projeto_pombo.model.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import pombo.spring.projeto_pombo.model.entity.Usuario;

@Data
public class PruuDTO {
	
	private String texto;
	private int quantCurtidas;
	private String nomeCriador;
	private String idCriador;
	private int quantDenuncias;

}
