 package pombo.spring.projeto_pombo.service;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pombo.spring.projeto_pombo.exception.ProjetoPomboException;
import pombo.spring.projeto_pombo.model.entity.Denuncia;
import pombo.spring.projeto_pombo.model.entity.Pruu;
import pombo.spring.projeto_pombo.model.entity.Usuario;
import pombo.spring.projeto_pombo.model.repository.PruuRepository;
import pombo.spring.projeto_pombo.model.repository.UsuarioRepository;

@Service
public class PruuService {
	
	@Autowired
	private PruuRepository pruuRepository;
	
	@Autowired
	private DenunciaService denunciaService;
	
	@Autowired
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
	public List<Pruu> listarTodosPruusPorIdUsuario(String idUsuario){
		Usuario usuario = usuarioRepository.findById(idUsuario).get();
		
		return usuario.getPruss();
	}
	
	public Pruu atualizarPruu(Pruu pruuAlterado) {
		return pruuRepository.save(pruuAlterado);	
	}
	
	public void deletarPruuPorId(String id) {
		pruuRepository.deleteById(id);
	}

	public Pruu bloquearPruu(String idPruu) {
		List<Denuncia> denuncias = denunciaService.buscarDenuncias();
		Pruu pruu = null;
		
		for (Denuncia denuncia : denuncias) {
			Optional<Pruu> pruuDenunciadoOpt = pruuRepository.findById(denuncia.getPruuId());
			
			if(pruuDenunciadoOpt.isPresent()) {
				Pruu pruuDenunciado = pruuDenunciadoOpt.get();
				if (pruuDenunciado.getUuid() == idPruu) {
					pruuDenunciado.setBloqueado(true);
					pruu = pruuRepository.save(pruuDenunciado);
				}
			}
		}
		return pruu;
		
	}
}
