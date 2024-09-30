package pombo.spring.projeto_pombo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import pombo.spring.projeto_pombo.exception.ProjetoPomboException;
import pombo.spring.projeto_pombo.model.entity.Pruu;
import pombo.spring.projeto_pombo.model.entity.Usuario;
import pombo.spring.projeto_pombo.model.repository.PruuRepository;
import pombo.spring.projeto_pombo.model.repository.UsuarioRepository;
import pombo.spring.projeto_pombo.model.seletor.UsuarioSeletor;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PruuRepository pruuRepository;
	
	@Autowired
	private PruuService pruuService;
	
	public Usuario inserirUsuario(Usuario novoUsuario) throws ProjetoPomboException {
		verificarUsuarioExistente(novoUsuario);
		return usuarioRepository.save(novoUsuario);
	}
	
	public List<Usuario> listarTodosUsuarios(){
		return usuarioRepository.findAll();
	}
	
	public Usuario pesquisarUsuarioPorId(String id) {
		return usuarioRepository.findById(id).get();
	}
	
	public Usuario atualizarUsuario(Usuario usuarioAlterado) {
		return usuarioRepository.save(usuarioAlterado);	
	}
	
	public void deletarUsuarioPorId(String id) throws ProjetoPomboException {
		List<Pruu> pruusUsuario = pruuService.listarTodosPruusPorIdUsuario(id);
		if(!pruusUsuario.isEmpty()) {
			throw new ProjetoPomboException("Usuário com pruus criados não podem ser deletados!");
		}
		
		usuarioRepository.deleteById(id);
	}
	
	public void darLike(String idUsuario, String idPruu) throws ProjetoPomboException {
		Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
		Optional<Pruu> pruuOpt = pruuRepository.findById(idPruu);
		
		if(usuarioOpt.isPresent() && pruuOpt.isPresent()) {
			Usuario usuario = usuarioOpt.get();
			Pruu pruu = pruuOpt.get();
			
			if (verificarUsuarioLike(idPruu, idUsuario)) {
				pruu.setQuantLikes(pruu.getQuantLikes() - 1);
				usuario.getPruusCurtidos().remove(pruu);
				usuarioRepository.save(usuario);
			} else {
				pruu.setQuantLikes(pruu.getQuantLikes() + 1); 
				usuario.getPruusCurtidos().add(pruu);
				usuarioRepository.save(usuario);
			}
		} else {
			throw new ProjetoPomboException("Usuario ou Pruu não encontrados.");
		}
	} 
	
	public List<Usuario> buscarUsuariosQueCurtiram(String idPruu){
		Pruu pruuCurtido = pruuRepository.findById(idPruu).get();
		
		return new ArrayList<>(pruuCurtido.getUsuariosQueCurtiram());
	}
	
	public boolean verificarUsuarioLike(String idPruu, String idUsuario) {
	    List<Usuario> usuarios = buscarUsuariosQueCurtiram(idPruu);
	    Usuario usuarioVerificado = usuarioRepository.findById(idUsuario).orElse(null);
	    
	    if (usuarioVerificado == null) {
	        return false; 
	    }
	    
	    for (Usuario usuario : usuarios) {
	        if (usuario.equals(usuarioVerificado)) {
	            return true; 
	        }
	    }
	    return false; 
	}

	public List<Usuario> pesquisarComSeletor(UsuarioSeletor seletor){
		if(seletor.temPaginacao()) {
			int numPage = seletor.getPagina();
			int TamanhoPage = seletor.getLimite();
			
			PageRequest pagina = PageRequest.of(numPage - 1, TamanhoPage);
			return usuarioRepository.findAll(pagina).toList();
		}
		
		return usuarioRepository.findAll();
	}
	
	public void verificarUsuarioExistente(Usuario usuarioVerificacao) throws ProjetoPomboException {
		List<Usuario> usuarios = usuarioRepository.findAll();
		
		for (Usuario usuario : usuarios) {
			if (usuario.getCpf().equals(usuarioVerificacao.getCpf())) {
				throw new ProjetoPomboException("CPF já cadastrado no sistema!");
			}else if(usuario.getEmail().equalsIgnoreCase(usuarioVerificacao.getEmail())){
				throw new ProjetoPomboException("Email já cadastrado no sistema!");
			}
		}
	}
}
