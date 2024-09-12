package pombo.spring.projeto_pombo.model.entity;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table
@Data
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@UuidGenerator
	private String uuid;
	
	@NotBlank(message = "Nome é obrigatório")
	@Size(min = 3, max = 255)
	private String nome;
	
	@Email
	private String email;
	
	@NotBlank(message = "CPF é obrigatório")
	@CPF
	private String cpf;
	
	@NotNull(message = "É administrador obrigatório")
	private boolean ehAdmin;
	
	@JsonBackReference
	@OneToMany(mappedBy = "usuario")
	private List<Pruu> pruss = new ArrayList<>();
	
	@ManyToMany
	@JoinTable(
		name = "Usuario_like",
		joinColumns = @JoinColumn(name = "id_usuario"),
		inverseJoinColumns = @JoinColumn(name = "id_pruu")
	)
	private List<Pruu> pruusCurtidos;
	
//	@OneToOne
//	@JoinTable(
//			name = "Usuario_denuncia",
//			joinColumns = @JoinColumn(name = "id_usuario"),
//			inverseJoinColumns = @JoinColumn(name = "id_pruu"))
//	private List<Pruu> prussDenunciados;
	
}
