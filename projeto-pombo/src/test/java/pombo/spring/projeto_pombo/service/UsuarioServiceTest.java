package pombo.spring.projeto_pombo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import pombo.spring.projeto_pombo.exception.ProjetoPomboException;
import pombo.spring.projeto_pombo.model.entity.Usuario;
import pombo.spring.projeto_pombo.model.repository.UsuarioRepository;

@SpringBootTest
@ActiveProfiles("test") 
public class UsuarioServiceTest {
	
	@Mock
	private UsuarioRepository usuarioRepository;

	@InjectMocks
	private UsuarioService usuarioService;
	
	private List<Usuario> usuarios = new ArrayList<>();
	
	 @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);
	        carregarUsuarios();
	    }
	 
	private void carregarUsuarios() {
	        usuarios = new ArrayList<>();
	        for (int i = 0; i < 10; i++) {
	            Usuario usuario = new Usuario();
	            usuario.setUuid(UUID.randomUUID().toString());
	            usuario.setAtivo(true); 
	            usuario.setCpf("000.000.000-" + i); 
	            usuario.setEhAdmin(i % 2 == 0); 
	            usuario.setEmail("usuario" + i + "@teste.com"); 
	            usuario.setNome("Usuario Teste " + i); 
	            
	            usuarios.add(usuario);
	            
	        }
	        
	    }
	 
	 @Test
	    @DisplayName("Deve retornar todos usuários")
    public void testPesquisarTodas() {
    	when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> resultado = usuarioService.listarTodosUsuarios();

        assertThat(resultado).isNotNull();
        assertThat(resultado.size()).isEqualTo(10);
    }
 
 @Test
 	@DisplayName("Deve retornar um usuário por UUID")
 	public void testPesquisarPorUUID() throws ProjetoPomboException {
	 Usuario usuarioTest = new Usuario();
	 usuarioTest.setUuid(UUID.randomUUID().toString());
	 
	 when(usuarioRepository.findById(usuarioTest.getUuid())).thenReturn(Optional.of(usuarioTest));
	 
	Usuario resultado = usuarioService.pesquisarUsuarioPorId(usuarioTest.getUuid()); 
	 
    assertThat(resultado).isNotNull();
    assertThat(resultado.getUuid()).isEqualTo(usuarioTest.getUuid());
	 
 }

 @Test
    @DisplayName("Deve lançar exceção ao tentar buscar um usuário que não existe")
    public void testPesquisarPorId_UsuarioNaoEncontrado() {
        when(usuarioRepository.findById(null)).thenReturn(Optional.empty());

        ProjetoPomboException exception = org.junit.jupiter.api.Assertions.assertThrows(ProjetoPomboException.class, () -> {
            usuarioService.pesquisarUsuarioPorId(null);
        });

        assertThat(exception.getMessage()).isEqualTo("Usuário não encontrado!");
    }

@Test
	@DisplayName("Deve inserir um novo usuário com sucesso.")
	public void testInserirUsuario_ComSucesso() throws ProjetoPomboException {
		Usuario novoUsuario = new Usuario();
		novoUsuario.setUuid(UUID.randomUUID().toString());
		novoUsuario.setAtivo(true);
		novoUsuario.setCpf("000.000.000-00");
		novoUsuario.setEhAdmin(false);
		novoUsuario.setEmail("test@gmail.com");
		novoUsuario.setNome("Test nome");
		
		when(usuarioRepository.save(novoUsuario)).thenReturn(novoUsuario);
		
		Usuario resultado = usuarioService.inserirUsuario(novoUsuario);
		
		assertThat(resultado).isNotNull();
	
}

	@Test
	@DisplayName("Deve lançar exceção ao inserir um novo usuário com CPF nulo.")
	public void testInserirUsuario_ComCamposNulos() throws ProjetoPomboException {
		Usuario novoUsuario = new Usuario();
		novoUsuario.setUuid(UUID.randomUUID().toString());
		novoUsuario.setAtivo(true);
		novoUsuario.setCpf(null);
		novoUsuario.setEhAdmin(false);
		novoUsuario.setEmail("test@gmail.com");
		novoUsuario.setNome("Test nome");
		
		when(usuarioRepository.save(novoUsuario)).thenReturn(novoUsuario);
		
		ProjetoPomboException exception = org.junit.jupiter.api.Assertions.assertThrows(ProjetoPomboException.class, () -> {
            usuarioService.inserirUsuario(novoUsuario);
        });

		
		Usuario resultado = usuarioService.inserirUsuario(novoUsuario);
		
		assertThat(exception.getMessage()).isEqualTo("CPF é obrigatório");
	
}
	
	


	
}
