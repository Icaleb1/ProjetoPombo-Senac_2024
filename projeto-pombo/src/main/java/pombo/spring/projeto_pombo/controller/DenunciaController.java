package pombo.spring.projeto_pombo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import pombo.spring.projeto_pombo.exception.ProjetoPomboException;
import pombo.spring.projeto_pombo.model.dto.DenunciaDTO;
import pombo.spring.projeto_pombo.model.dto.PruuDTO;
import pombo.spring.projeto_pombo.model.entity.Denuncia;
import pombo.spring.projeto_pombo.model.entity.Pruu;
import pombo.spring.projeto_pombo.service.DenunciaService;

@RestController
@RequestMapping(path = "/api/denuncias")
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:5500"}, maxAge = 3600)
public class DenunciaController {
	
	@Autowired
	private DenunciaService denunciaService;
	
	
	@Operation(summary = "Inserir nova denúncia", 
			   description = "Adiciona uma nova denúncia ao sistema.", 
			   responses = {
				   @ApiResponse(responseCode = "201", description = "Denúncia criada com sucesso", 
							 content = @Content(mediaType = "application/json",
							 schema = @Schema(implementation = Denuncia.class))),
				   @ApiResponse(responseCode = "400", description = "Erro de validação ou regra de negócio", 
					    	 content = @Content(mediaType = "application/json", 
					    	 examples = @ExampleObject(value = "{\"message\": \"Erro de validação: campo X é obrigatório\", \"status\": 400}")))})
	@PostMapping
	public ResponseEntity<Denuncia> denunciar(@Valid @RequestBody Denuncia denuncia) throws ProjetoPomboException{
		return ResponseEntity.ok(denunciaService.denunciarPruu(denuncia));
	}
	
	@Operation(summary = "Listar todos as Denúncias", 
			   description = "Retorna uma lista de todas as denúncias cadastrados no sistema.",
			   responses = {
					@ApiResponse(responseCode = "200", description = "Lista de denúncias retornada com sucesso")
				})
	@GetMapping
	public List<Denuncia> buscarTodasDenuncias(){
		List<Denuncia> denuncias = denunciaService.buscarDenuncias();
		return denuncias;
	}
	
	@Operation(summary = "Pesquisar denúncia por ID", 
			   description = "Busca uma denúncia específica pelo seu ID.")
	@GetMapping(path = "/{id_denuncia}")
	public ResponseEntity<Denuncia> buscarDenunciaPorId(@PathVariable String id_denuncia) throws ProjetoPomboException {
		Denuncia denuncia = denunciaService.buscarDenuncia(id_denuncia);
		return ResponseEntity.ok(denuncia);
	}
	
	@Operation(summary = "Atualizar situação da denúncia", description = "Atualiza a situação de uma denúncia existente.")
	@PutMapping
	public ResponseEntity<Denuncia> atualizarSituacaoDenuncia(@Valid @RequestBody Denuncia denunciaAlterada) throws ProjetoPomboException{
		return ResponseEntity.ok(denunciaService.atualizarSituacaoDenuncia(denunciaAlterada));
	}
	
	@Operation(summary = "Gera o Relatório de uma denúncia pelo ID Pruu", 
			   description = "Gera um relatório de uma denúncia específica pelo ID de um Pruu")
	@GetMapping(path = "/relatorio/{idPruu}")
	public ResponseEntity<DenunciaDTO> pesquisarRelatorioPruu(@PathVariable String idPruu) throws ProjetoPomboException {
		DenunciaDTO dto = denunciaService.gerarRelatorioPorIdPruu(idPruu);
		return ResponseEntity.ok(dto);
	}
	
}
