package com.tecsoluction.cardapio.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
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
@Table(name = "SORTEIO")
public class Sorteio  extends BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//    private String name;
    
    @OneToOne(targetEntity=Usuario.class)
    private Usuario usuario;
    
//    @Column(name = "desconto")
//    private BigDecimal desconto;
    
    @OneToOne(targetEntity=Premio.class)
    private Premio premio;
    
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date data;
    
    private boolean valido = true;
    
    
    private boolean resgatado = false;
    
    
    
    
    public Sorteio() {
		// TODO Auto-generated constructor stub
    	
	}

 
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return premio.getName();
    }

}
