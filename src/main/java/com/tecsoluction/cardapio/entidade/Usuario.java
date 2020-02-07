package com.tecsoluction.cardapio.entidade;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tecsoluction.cardapio.framework.BaseEntity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Usuario  extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	 @Column(name = "nome", nullable = false)
	 @NotNull(message="o nome do Usuario não pode ser nulo")
	 @NotBlank(message="o nome do Usuario não pode ser branco")
	private String nome;
	
	 @Column(name = "senha", nullable = false)
	 @Size(min=6,message="a senha deve possuir no minimo 6 caracteres")
	 @NotNull(message="o senha do Usuario não pode ser nulo")
	 @NotBlank(message="o senha do Usuario não pode ser branco")
	 private String senha;
	
	 @Column(name = "email", nullable = false,unique=true)
	 @Email(message="digite um email valido")
	 @NotNull(message="o email do Usuario não pode ser nulo")
	 @NotBlank(message="o email do Usuario não pode ser branco")
	private String email;
	
	 @Column(name = "foto", nullable = true)
    private String foto;
    
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date datacadastro;
		

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_role",
            joinColumns = @JoinColumn(name = "idusuario"),
            inverseJoinColumns = @JoinColumn(name = "idrole"))
	 private Set<Role> roles;
    
    
    @JsonIgnore
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "usuario_indicacao",
    joinColumns = @JoinColumn(name = "idusuario"),
    inverseJoinColumns = @JoinColumn(name = "idproduto"))
	 private Set<Produto> indicacoes;
    
    
    
		 @Column(name = "facebookid", nullable = true,unique=true)
		 private String facebookid;
	 
	    @Column(name = "online", nullable = true)
	    private boolean online;
	    
	    @Temporal(TemporalType.DATE)
	    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
		private Date dataultimoAcesso;
	    

	    @JsonIgnore
	    @OneToMany(mappedBy = "usuario", cascade = {CascadeType.REFRESH},fetch=FetchType.EAGER)
		 private Set<Atividade> atividades; 

    
//    @JsonIgnore
//    @OneToMany(mappedBy="usuario",fetch=FetchType.EAGER)
//    private Set<Atividade> atividades;
//    
//    
//    @JsonIgnore
//    @OneToMany(mappedBy="usuario",fetch=FetchType.EAGER)
//    private Set<Atendimento> atendimentos;
    
    
    
//    @OneToOne
//    @JoinColumn(name="perfil_id")
//    private Perfil perfil;

//    @OneToOne
//    @JoinColumn(name="telefone_id")
//    private Telefone telefone;
    
    
	public Usuario() {
		
		indicacoes = new HashSet<Produto>();
		datacadastro = new Date();
		atividades = new HashSet<Atividade>();
		
	}
	
	
    public void addIndicacao(Produto not){
    	
    	
    	this.getIndicacoes().add(not);
    	
    }


    public void removeIndicacao(Produto not){
    	
    	this.getIndicacoes().remove(not);
    	
    }
    
    public void addAtividade(Atividade not){
    	
    	
    	this.getAtividades().add(not);
    	
    }


    public void removeAtividade(Atividade not){
    	
    	this.getAtividades().remove(not);
    	
    }
	
	    
	    
		@Override
		public String toString() {
			return email;
		}
	
	
	
}
