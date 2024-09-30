package pombo.spring.projeto_pombo.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import pombo.spring.projeto_pombo.model.repository.UsuarioRepository;

@SpringBootTest
@ActiveProfiles("test") 
public class UsuarioServiceTest {
	
	@Mock
	private UsuarioRepository usuarioRepository;

	@InjectMocks
	private UsuarioService usuarioService;
	
	
	
}
