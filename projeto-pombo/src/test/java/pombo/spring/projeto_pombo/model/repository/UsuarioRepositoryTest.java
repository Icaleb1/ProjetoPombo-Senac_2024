package pombo.spring.projeto_pombo.model.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.aspectj.apache.bcel.classfile.Unknown;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.TransactionSystemException;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import jakarta.validation.ConstraintViolationException;
import pombo.spring.projeto_pombo.exception.ProjetoPomboException;
import pombo.spring.projeto_pombo.model.entity.Usuario;


@SpringBootTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {
	
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	private void testInserirUsuario_ComTodosCamposPreenchidos() {
		Usuario novoUsuario = new Usuario();
		novoUsuario.setUuid(UUID.randomUUID().toString());
		novoUsuario.setAtivo(true);
		novoUsuario.setCpf("000.000.000-12");
		novoUsuario.setEhAdmin(false);
		novoUsuario.setEmail("test@gmail.com");
		novoUsuario.setNome("Test nome");
		
		Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);
		
		assertNotNull(usuarioSalvo);
		assertThat(usuarioSalvo.getUuid()).isNotNull();
		
	}

	
	//@DisplayName("Deve lançar exceção ao tentar inserir um novo usuário com CPF nulo.")
	@Test
	public void testInserirUsuario_ComCPFnulo() throws ProjetoPomboException {
		Usuario novoUsuario = new Usuario();
		novoUsuario.setUuid(UUID.randomUUID().toString());
		novoUsuario.setAtivo(true);
		novoUsuario.setCpf(null);
		novoUsuario.setEhAdmin(false);
		novoUsuario.setEmail("test@gmail.com");
		novoUsuario.setNome("Test nome");
		
//		try {
//			usuarioRepository.save(novoUsuario);
//		} catch (Exception e) {
//			assertTrue(e.getLocalizedMessage().contains("propertyPath=cpf"));
//		}
		
		assertThatThrownBy(() -> 
				usuarioRepository.save(novoUsuario)).isInstanceOf(TransactionSystemException.class);
	}
	
	@Test
	public void testInserirUsuario_ComCPFInvalido() throws ProjetoPomboException {
		Usuario novoUsuario = new Usuario();
		novoUsuario.setUuid(UUID.randomUUID().toString());
		novoUsuario.setAtivo(true);
		novoUsuario.setCpf("000.000.000-00");
		novoUsuario.setEhAdmin(false);
		novoUsuario.setEmail("test@gmail.com");
		novoUsuario.setNome("Test nome");
		
		assertThatThrownBy(() -> 
				usuarioRepository.save(novoUsuario)).isInstanceOf(TransactionSystemException.class);
	}

	@Test
	public void testInserirUsuario_ComNomeNulo() throws ProjetoPomboException {
		Usuario novoUsuario = new Usuario();
		novoUsuario.setUuid(UUID.randomUUID().toString());
		novoUsuario.setAtivo(true);
		novoUsuario.setCpf("000.000.000-11");
		novoUsuario.setEhAdmin(false);
		novoUsuario.setEmail("test@gmail.com");
		novoUsuario.setNome(null);
		
		
		assertThatThrownBy(() -> 
				usuarioRepository.save(novoUsuario)).isInstanceOf(TransactionSystemException.class);
	}
	
	@Test
	public void testInserirUsuario_ComNomeInvalidoMenorQueDois() throws ProjetoPomboException {
		Usuario novoUsuario = new Usuario();
		novoUsuario.setUuid(UUID.randomUUID().toString());
		novoUsuario.setAtivo(true);
		novoUsuario.setCpf("000.000.000-00");
		novoUsuario.setEhAdmin(false);
		novoUsuario.setEmail("test@gmail.com");
		novoUsuario.setNome("A");
		
		assertThatThrownBy(() -> 
				usuarioRepository.save(novoUsuario)).isInstanceOf(TransactionSystemException.class);
	}

	@Test
	public void testInserirUsuario_ComEmailNulo() throws ProjetoPomboException{
		Usuario novoUsuario = new Usuario();
		novoUsuario.setUuid(UUID.randomUUID().toString());
		novoUsuario.setAtivo(true);
		novoUsuario.setCpf("000.000.000-00");
		novoUsuario.setEhAdmin(false);
		novoUsuario.setEmail(null);
		novoUsuario.setNome("Caleb");
		
		assertThatThrownBy(() -> 
				usuarioRepository.save(novoUsuario)).isInstanceOf(TransactionSystemException.class);
	}
	
	@Test
	public void testInserirUsuario_ComEmailInvalido() throws ProjetoPomboException{
		Usuario novoUsuario = new Usuario();
		novoUsuario.setUuid(UUID.randomUUID().toString());
		novoUsuario.setAtivo(true);
		novoUsuario.setCpf("000.000.000-00");
		novoUsuario.setEhAdmin(false);
		novoUsuario.setEmail("vazio");
		novoUsuario.setNome("Caleb");
		
		assertThatThrownBy(() -> 
				usuarioRepository.save(novoUsuario)).isInstanceOf(TransactionSystemException.class);
	}


		
		
}
	

