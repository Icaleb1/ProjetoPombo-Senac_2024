package pombo.spring.projeto_pombo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import pombo.spring.projeto_pombo.exception.ProjetoPomboException;
import pombo.spring.projeto_pombo.model.dto.PruuDTO;
import pombo.spring.projeto_pombo.model.entity.Pruu;
import pombo.spring.projeto_pombo.model.entity.Usuario;
import pombo.spring.projeto_pombo.model.seletor.PruuSeletor;
import pombo.spring.projeto_pombo.service.PruuService;

@RestController
@RequestMapping(path = "/api/pruus")
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:5500"}, maxAge = 3600)
public class PruuController {
	
	@Autowired
	private PruuService pruuService;
	
	
	@Operation(summary = "Cadastrar novo Pruu", 
			   description = "Adiciona um novo pruu ao sistema.", 
			   responses = {
				   @ApiResponse(responseCode = "201", description = "Pruu criado com sucesso", 
							 content = @Content(mediaType = "application/json",
							 schema = @Schema(implementation = Pruu.class))),
				   @ApiResponse(responseCode = "400", description = "Erro de validação ou regra de negócio", 
					    	 content = @Content(mediaType = "application/json", 
					    	 examples = @ExampleObject(value = "{\"message\": \"Erro de validação: campo X é obrigatório\", \"status\": 400}")))})
	@PostMapping
	public ResponseEntity<Pruu> criarPruu(@Valid @RequestBody Pruu pruuEnviado) throws ProjetoPomboException{
		return ResponseEntity.ok(pruuService.criarPruu(pruuEnviado));
	}
	
	
	@Operation(summary = "Listar todos os pruus", 
			   description = "Retorna uma lista de todos os pruus cadastrados no sistema.",
			   responses = {
					@ApiResponse(responseCode = "200", description = "Lista de pruus retornada com sucesso")
				})
	@GetMapping
	public List<Pruu> listarTodosUsuarios(){
		List<Pruu> pruus = pruuService.listarTodosPruus();
		return pruus;
	}
	
	@Operation(summary = "Pesquisar pruus com filtros", 
			   description = "Retorna uma lista de pruus que atendem aos critérios especificados no seletor.")
	@PostMapping("/filtro")
	public List<Pruu> pesquisarComSeletor(@RequestBody PruuSeletor seletor) {
		return pruuService.pesquisarComSeletor(seletor);
	}
	
	@Operation(summary = "Listar todos os pruus de um usuário", 
			   description = "Retorna uma lista de todos os pruus de um usuário cadastrado no sistema.",
			   responses = {
					@ApiResponse(responseCode = "200", description = "Lista de pruus retornada com sucesso")
				})
	@GetMapping(path = "por_usuario/{idUsuario}")
	public List<Pruu> listarTodosUsuarios(@PathVariable String idUsuario){
		List<Pruu> pruus = pruuService.listarTodosPruusPorIdUsuario(idUsuario);
		return pruus;
	}

	@Operation(summary = "Pesquisar pruu por ID", 
			   description = "Busca um pruu específico pelo seu ID.")
	@GetMapping(path = "/{id}")
	public ResponseEntity<Pruu> listarPorId(@PathVariable String id) throws ProjetoPomboException {
		Pruu pruu = pruuService.pesquisarPruuPorId(id);
		return ResponseEntity.ok(pruu);
	}
	
	@Operation(summary = "Gera Relatório de Pruu por ID", 
			   description = "Gera um relatório de um pruu específico pelo seu ID.")
	@GetMapping(path = "/relatorio/{idPruu}")
	public ResponseEntity<PruuDTO> pesquisarRelatorioPruu(@PathVariable String idPruu) throws ProjetoPomboException {
		PruuDTO dto = pruuService.gerarRelatorioPruu(idPruu);
		return ResponseEntity.ok(dto);
	}
	
	@Operation(summary = "Atualizar pruu existente", description = "Atualiza os dados de um pruu existente.")
	@PutMapping
	public ResponseEntity<Pruu> atualizarPruu(@Valid @RequestBody Pruu pruuAlterado) throws ProjetoPomboException{
		return ResponseEntity.ok(pruuService.atualizarPruu(pruuAlterado));
	}
	
	
	@Operation(summary = "Deletar pruu por ID", description = "Remove um pruu específico pelo seu ID.")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarPruuPorId(@PathVariable String id){
		pruuService.deletarPruuPorId(id);
		return ResponseEntity.noContent().build();
	}
	
	
	@Operation(summary = "Bloquear um pru denunciado", description = "Atualiza os dados de um pruu denunciado.")
	@PutMapping("/{idUsuario}/bloquear/{idPruu}")
	public ResponseEntity<Pruu> bloquearPruu(@PathVariable String idPruu, @PathVariable String idUsuario) throws ProjetoPomboException{
		return ResponseEntity.ok(pruuService.bloquearPruu(idPruu, idUsuario));
	}

}
