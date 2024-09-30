 package pombo.spring.projeto_pombo.service;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import pombo.spring.projeto_pombo.exception.ProjetoPomboException;
import pombo.spring.projeto_pombo.model.dto.PruuDTO;
import pombo.spring.projeto_pombo.model.entity.Denuncia;
import pombo.spring.projeto_pombo.model.entity.Pruu;
import pombo.spring.projeto_pombo.model.entity.Usuario;
import pombo.spring.projeto_pombo.model.repository.PruuRepository;
import pombo.spring.projeto_pombo.model.repository.UsuarioRepository;
import pombo.spring.projeto_pombo.model.seletor.PruuSeletor;

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
	
	public List<Pruu> pesquisarComSeletor(PruuSeletor seletor) {
		if(seletor.temPaginacao()) {
			int pageNumber = seletor.getPagina();
			int pageSize = seletor.getLimite();
			
			PageRequest pagina = PageRequest.of(pageNumber - 1, pageSize);
			return pruuRepository.findAll(seletor, pagina).toList();
		}
		
		return pruuRepository.findAll();
	}

	
	public List<Pruu> listarTodosPruusPorIdUsuario(String idUsuario){
		Usuario usuario = usuarioRepository.findById(idUsuario).get();
		
		return usuario.getPruss();
	}
	
	public Pruu atualizarPruu(Pruu pruuAlterado) {
		return pruuRepository.save(pruuAlterado);	
	}
	
	public void deletarPruuPorId(String id) {
		Pruu pruuDeletado = pruuRepository.findById(id).get();
		pruuDeletado.setAtivo(false);
		pruuRepository.save(pruuDeletado);
	}

	public Pruu bloquearPruu(String idPruu, String idUsuario) throws ProjetoPomboException {
		Usuario usuario = usuarioRepository.findById(idUsuario).get(); 
		List<Denuncia> denuncias = denunciaService.buscarDenuncias();
		Pruu pruu = null;
		
		if(usuario.isEhAdmin() == true) {
			for (Denuncia denuncia : denuncias) {
				Optional<Pruu> pruuDenunciadoOpt = pruuRepository.findById(denuncia.getPruuId());
				
				if(pruuDenunciadoOpt.isPresent()) {
					Pruu pruuDenunciado = pruuDenunciadoOpt.get();
					if (pruuDenunciado.getUuid().equalsIgnoreCase(idPruu)) {
						pruuDenunciado.setBloqueado(true);
						pruu = pruuRepository.save(pruuDenunciado);
					}
				}
			}
		}else{
			throw new ProjetoPomboException("Usuário não tem permissão!");
		}
		return pruu;
		
	}

	public PruuDTO gerarRelatorioPruu(String idPruu) {
		Pruu pruu = pruuRepository.findById(idPruu).get();
		PruuDTO dto = new PruuDTO();
		
		List<Denuncia> denuncias = denunciaService.buscarDenunciasPorIdPruu(pruu.getUuid());
		
		dto.setIdCriador(pruu.getUsuario().getUuid());
		dto.setNomeCriador(pruu.getUsuario().getNome());
		dto.setQuantCurtidas(pruu.getQuantLikes());
		dto.setQuantDenuncias(denuncias.size());
		
		if (pruu.isBloqueado()) {
			dto.setTexto("Bloqueado pelo administrador!");
		}else {
			dto.setTexto(pruu.getTexto());
		}
		
		return dto;
		
	}

}
