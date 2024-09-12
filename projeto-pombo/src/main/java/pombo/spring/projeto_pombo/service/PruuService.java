 package pombo.spring.projeto_pombo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pombo.spring.projeto_pombo.exception.ProjetoPomboException;
import pombo.spring.projeto_pombo.model.entity.Pruu;
import pombo.spring.projeto_pombo.model.entity.Usuario;
import pombo.spring.projeto_pombo.model.repository.PruuRepository;
import pombo.spring.projeto_pombo.model.repository.UsuarioRepository;

@Service
public class PruuService {
	
	@Autowired
	private PruuRepository pruuRepository;
	private UsuarioRepository usuarioRepository;
	
	public Pruu criarPruu(Pruu pruuEnviado) {
		pruuEnviado.getUsuario().getPruss().add(pruuEnviado);
		return pruuRepository.save(pruuEnviado);	
	}
	
	public List<Pruu> listarTodosPruus(){
		return pruuRepository.findAll();
	}
	
	public Pruu pesquisarPruuPorId(String id) {
		return pruuRepository.findById(id).get();
	}
	
	//TODO: listar Pruus de um usuário
	//public List<Pruu> listarTodosPruusPorIdUsuario(String id){
	//	return pruuRepository.findAllById(id);
	//}
	
	public Pruu atualizarPruu(Pruu pruuAlterado) {
		return pruuRepository.save(pruuAlterado);	
	}
	
	public void deletarPruuPorId(String id) {
		pruuRepository.deleteById(id);
	}


}
