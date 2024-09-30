 package pombo.spring.projeto_pombo.model.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import pombo.spring.projeto_pombo.model.enumPombo.EnumDenuncia;

@Table
@Entity
@Data
public class Denuncia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@UuidGenerator
	private String uuid; 
	
	@NotBlank(message = "Motivo é obrigatório")
	@Size(min = 1, max = 300)
	private String motivo;
	
	@Column(nullable = false)
    private LocalDateTime dataHoraDenuncia;
	
   @Column(name = "pruu_id", nullable = false)
    private String pruuId;

    @Column(name = "usuario_id", nullable = false)
    private String usuarioId;
    
    @Column(name = "situacao", nullable = false)
    private EnumDenuncia situacao;
	    
    @PrePersist
    protected void onCreate() {
        dataHoraDenuncia = LocalDateTime.now();
    }
}
