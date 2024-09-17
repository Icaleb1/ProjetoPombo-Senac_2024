package pombo.spring.projeto_pombo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pombo.spring.projeto_pombo.exception.ProjetoPomboException;
import pombo.spring.projeto_pombo.model.entity.Pruu;
import pombo.spring.projeto_pombo.model.entity.Usuario;
import pombo.spring.projeto_pombo.model.repository.PruuRepository;
import pombo.spring.projeto_pombo.model.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PruuRepository pruuRepository;
	
	public Usuario inserirUsuario(Usuario novoUsuario) {
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
	
	public void deletarUsuarioPorId(String id) {
		usuarioRepository.deleteById(id);
	}
	
	public void darLike(String idUsuario, String idPruu) {
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
			throw new RuntimeException("Usuario ou Pruu não encontrados.");
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

	
}
