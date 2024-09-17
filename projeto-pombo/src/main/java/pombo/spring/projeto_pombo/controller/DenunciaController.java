package pombo.spring.projeto_pombo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	public ResponseEntity<Denuncia> criarPruu(@Valid @RequestBody Denuncia denuncia) throws ProjetoPomboException{
		return ResponseEntity.ok(denunciaService.denunciarPruu(denuncia));
	}
	@Operation(summary = "Listar todos as Denúncias", 
			   description = "Retorna uma lista de todas as denúncias cadastrados no sistema.",
			   responses = {
					@ApiResponse(responseCode = "200", description = "Lista de denúncias retornada com sucesso")
				})
	@GetMapping
	public List<Denuncia> listarTodosUsuarios(){
		List<Denuncia> denuncias = denunciaService.buscarDenuncias();
		return denuncias;
	}
	

}
