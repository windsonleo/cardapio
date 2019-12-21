package com.tecsoluction.cardapio.entidade;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
	
	private String nomeempresa;
	
	private String logo;
	
	private String banner1;
	private String banner2;
	private String banner3;
	
	
	private String urlface;
	private String urlinsta;
	private String urlgmail;
	
	
	private String horaabertura;
	private String horafechamento;
	
	
    private String cortopo;
    
    
    private String cormenu;
    
    private String corcard;
  
    
    
	public Configuracao() {
		
		
		
	}
	
	
   
	
	    
	    
		@Override
		public String toString() {
			return nomeempresa;
		}
	
	
	
}
