package pombo.spring.projeto_pombo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import pombo.spring.projeto_pombo.auth.AuthenticationService;
import pombo.spring.projeto_pombo.exception.ProjetoPomboException;
import pombo.spring.projeto_pombo.model.entity.Usuario;
import pombo.spring.projeto_pombo.model.enumPombo.EnumPerfilAcesso;
import pombo.spring.projeto_pombo.service.UsuarioService;

@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping("authenticate")
	public String authenticate(Authentication authentication) {
		return authenticationService.authenticate(authentication);
	}
	
	@PostMapping("/novo-usuario")
	@ResponseStatus(code = HttpStatus.CREATED) public void
	registrarUsuario(@RequestBody Usuario novoUsuario) throws
	ProjetoPomboException {
	String senhaCifrada = passwordEncoder.encode(novoUsuario.getSenha());
	novoUsuario.setSenha(senhaCifrada);
	novoUsuario.setPerfil(EnumPerfilAcesso.USUARIO);
	
	usuarioService.inserirUsuario(novoUsuario); } 
 }
 
	
