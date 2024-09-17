package pombo.spring.projeto_pombo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pombo.spring.projeto_pombo.model.entity.Denuncia;
import pombo.spring.projeto_pombo.model.repository.DenunciaRepository;

@Service
public class DenunciaService {
	@Autowired
	private DenunciaRepository denunciaRepository;
	
	public Denuncia denunciarPruu(Denuncia denuncia) {
		return denunciaRepository.save(denuncia);
		
	}
	
	public List<Denuncia> buscarDenuncias(){
		return denunciaRepository.findAll();		
	}

}
