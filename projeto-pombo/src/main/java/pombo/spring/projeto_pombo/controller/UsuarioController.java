package pombo.spring.projeto_pombo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import pombo.spring.projeto_pombo.exception.ProjetoPomboException;
import pombo.spring.projeto_pombo.model.entity.Usuario;
import pombo.spring.projeto_pombo.service.UsuarioService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(path = "/api/usuarios")
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:5500"}, maxAge = 3600)
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Operation(summary = "Cadastrar novo Usuário", 
			   description = "Adiciona um novo usuário ao sistema.", 
			   responses = {
				   @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso", 
							 content = @Content(mediaType = "application/json",
							 schema = @Schema(implementation = Usuario.class))),
				   @ApiResponse(responseCode = "400", description = "Erro de validação ou regra de negócio", 
					    	 content = @Content(mediaType = "application/json", 
					    	 examples = @ExampleObject(value = "{\"message\": \"Erro de validação: campo X é obrigatório\", \"status\": 400}")))})
	@PostMapping
	public ResponseEntity<Usuario> inserirUsuario(@Valid @RequestBody Usuario novoUsuario) {
		Usuario usuarioSalvo = usuarioService.inserirUsuario(novoUsuario);
		return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED); 
	}
	
	@Operation(summary = "Listar todos os usuários", 
			   description = "Retorna uma lista de todos os usuários cadastrados no sistema.",
			   responses = {
					@ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
				})
	@GetMapping
	public List<Usuario> listarTodosUsuarios(){
		List<Usuario> usuarios = usuarioService.listarTodosUsuarios();
		return usuarios;
	}

	@Operation(summary = "Pesquisar usuário por ID", 
			   description = "Busca um usuário específico pelo seu ID.")
	@GetMapping(path = "/{id}")
	public ResponseEntity<Usuario> listarPorId(@PathVariable String id) throws ProjetoPomboException {
		Usuario usuario = usuarioService.pesquisarUsuarioPorId(id);
		return ResponseEntity.ok(usuario);
	}
	
	@Operation(summary = "Atualizar usuário existente", description = "Atualiza os dados de um usuário existente.")
	@PutMapping
	public ResponseEntity<Usuario> atualizarUsuario(@Valid @RequestBody Usuario usuarioAlterado) throws ProjetoPomboException{
		return ResponseEntity.ok(usuarioService.atualizarUsuario(usuarioAlterado));
	}
	
	
	@Operation(summary = "Deletar usuário por ID", description = "Remove um usuário específico pelo seu ID.")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarUsuarioPorId(@PathVariable String id){
		usuarioService.deletarUsuarioPorId(id);
		return ResponseEntity.noContent().build();
	}
	
	 @Operation(summary = "Dar like em um Pruu", 
             description = "Permite que um usuário dê like em um Pruu especificado pelo ID.",
             responses = {
                 @ApiResponse(responseCode = "200", description = "Like adicionado com sucesso"),
                 @ApiResponse(responseCode = "404", description = "Usuário ou Pruu não encontrado")
             })
	 @PostMapping("/{idUsuario}/like/{idPruu}")
	 public ResponseEntity<String> darLike(
			 @PathVariable String idUsuario,
			 @PathVariable String idPruu){
		 try {
			 usuarioService.darLike(idUsuario, idPruu);
			 return ResponseEntity.ok("Like adicionado com sucesso!");
		 } catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	 }
	
	
	
	
}


 