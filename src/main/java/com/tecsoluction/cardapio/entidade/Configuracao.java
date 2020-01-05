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
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tecsoluction.cardapio.framework.BaseEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "CONFIGURACAO")
@EqualsAndHashCode(callSuper = true)
public class Configuracao  extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	
    @Column(name = "nomeempresa",nullable = false)
	 @NotNull(message="o nomeempresa do Configuracao não pode ser nulo")
	 @NotBlank(message="o nomeempresa do Configuracao não pode ser branco")
	private String nomeempresa;
	
    @Column(name = "logo",nullable = false)
	 @NotNull(message="o logo do Configuracao não pode ser nulo")
	 @NotBlank(message="o logo do Configuracao não pode ser branco")
	private String logo;
    @Column(name = "banner1",nullable = false)
	 @NotNull(message="o banner1 do Configuracao não pode ser nulo")
	 @NotBlank(message="o banner1 do Configuracao não pode ser branco")
	private String banner1;
    @Column(name = "banner2",nullable = false)
	 @NotNull(message="o banner2 do Configuracao não pode ser nulo")
	 @NotBlank(message="o banner2 do Configuracao não pode ser branco")
	private String banner2;
    @Column(name = "banner3",nullable = false)
	 @NotNull(message="o banner3 do Configuracao não pode ser nulo")
	 @NotBlank(message="o banner3 do Configuracao não pode ser branco")
	private String banner3;
	
    @Column(name = "urlface",nullable = false)
	 @NotNull(message="o urlface do Configuracao não pode ser nulo")
	 @NotBlank(message="o urlface do Configuracao não pode ser branco")
	private String urlface;
    @Column(name = "urlinsta",nullable = false)
	 @NotNull(message="o urlinsta do Configuracao não pode ser nulo")
	 @NotBlank(message="o urlinsta do Configuracao não pode ser branco")
	private String urlinsta;
    @Column(name = "urlgmail",nullable = false)
	 @NotNull(message="o urlgmail do Configuracao não pode ser nulo")
	 @NotBlank(message="o urlgmail do Configuracao não pode ser branco")
	private String urlgmail;
	
    @Column(name = "horaabertura",nullable = false)
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern ="HH:mm:ss")
    @NotNull(message="o horaabertura do Configuracao não pode ser nulo")
	private Date horaabertura;
   
    
    @Column(name = "horafechamento",nullable = false)
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern ="HH:mm:ss")
    @NotNull(message="o horafechamento do Configuracao não pode ser nulo")
	private Date horafechamento;
	
    @Column(name = "cortopo",nullable = false)
	 @NotNull(message="o cortopo do Configuracao não pode ser nulo")
	 @NotBlank(message="o cortopo do Configuracao não pode ser branco")
    private String cortopo;
    
    @Column(name = "cormenu",nullable = false)
	 @NotNull(message="o cormenu do Configuracao não pode ser nulo")
	 @NotBlank(message="o cormenu do Configuracao não pode ser branco")
    private String cormenu;
    
    @Column(name = "corcard",nullable = false)
	 @NotNull(message="o corcard do Configuracao não pode ser nulo")
	 @NotBlank(message="o corcard do Configuracao não pode ser branco")
    private String corcard;
    
    
  
    
    
	public Configuracao() {
		
		
		
	}
	
	
   
	
	    
	    
		@Override
		public String toString() {
			return nomeempresa;
		}
	
	
	
}
