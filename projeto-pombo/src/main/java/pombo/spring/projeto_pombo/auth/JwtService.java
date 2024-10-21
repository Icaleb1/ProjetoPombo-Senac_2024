package pombo.spring.projeto_pombo.auth;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import pombo.spring.projeto_pombo.model.entity.Usuario;

@Service
public class JwtService {
	private final JwtEncoder jwtEncoder;
	
	public JwtService(JwtEncoder jwtEncoder) {
		this.jwtEncoder = jwtEncoder;
	}
	
	public String getGenerateToken(Authentication authentication) {
		Instant now = Instant.now();
		
		long dezHorasEmSegundo = 36000L;
		
		String rles = authentication
				.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(" "));
		
		Usuario usuairoAutenticado = (Usuario) authentication.getPrincipal();
		
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("projeto-pombo")
				.issuedAt(now)
				.expiresAt(now.plusSeconds(dezHorasEmSegundo))
				.subject(authentication.getName())
				.claim("roles", rles)
				.claim("idUsuario", usuairoAutenticado.getUuid())
				.build();
		
		return jwtEncoder.encode(
				JwtEncoderParameters.from(claims)).getTokenValue();
		
	}
}
