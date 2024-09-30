package pombo.spring.projeto_pombo.model.dto;

import lombok.Data;

@Data
public class DenunciaDTO {
	
	private String idPruuDenunciado;
	private int quantDenunciasPruu;
	private int quantDenunciasPendentes;
	private int quantDenunciasAnalisadas;
	

}
