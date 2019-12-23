package com.tecsoluction.cardapio.entidade;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
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
	private String nome;
	
	 @Column(name = "senha", nullable = false)
	private String senha;
	
	 @Column(name = "email", nullable = false)
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
    @OneToMany( cascade = {CascadeType.REFRESH},fetch=FetchType.EAGER)
	 private Set<Produto> indicacoes;

    
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
		
		
	}
	
	
    public void addIndicacao(Produto not){
    	
    	
    	this.getIndicacoes().add(not);
    	
    }


    public void removeIndicacao(Produto not){
    	
    	this.getIndicacoes().remove(not);
    	
    }
	
	    
	    
		@Override
		public String toString() {
			return email;
		}
	
	
	
}
