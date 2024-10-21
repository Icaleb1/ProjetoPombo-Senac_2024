package pombo.spring.projeto_pombo.model.entity;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import pombo.spring.projeto_pombo.model.enumPombo.EnumPerfilAcesso;

@Entity
@Table
@Data
public class Usuario implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@UuidGenerator
	private String uuid;
	
	@NotBlank(message = "Nome é obrigatório")
	@Size(min = 3, max = 255)
	private String nome;
	
	@Email
	private String email;
	
	private String senha;
	
	@NotBlank(message = "CPF é obrigatório")
	@CPF
	private String cpf;
	
	@Enumerated(EnumType.STRING)
	private EnumPerfilAcesso perfil;
	
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
	@JsonIgnore
	private List<Pruu> pruusCurtidos;
	
	private boolean ativo;
	
	
	 @PrePersist
	    protected void onCreate() {
	        ativo = true;
	    }


	@Override
	public java.util.Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		
		list.add(new SimpleGrantedAuthority(perfil.toString()));
		
		return list;
	}


	@Override
	public String getPassword() {
		return this.senha;
	}


	@Override
	public String getUsername() {
		return this.email;
	}
	 


}
