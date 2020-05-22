package com.tecsoluction.cardapio.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tecsoluction.cardapio.framework.BaseEntity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "PREMIO")
public class Premio  extends BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private String name;
    
    @OneToOne(targetEntity=Produto.class,optional=true)
    private Produto produto;
    
    @Column(name = "desconto")
    private BigDecimal desconto;
    
    private String foto;
    
    
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "premio_sorteio",
            joinColumns = @JoinColumn(name = "idpremio"),
            inverseJoinColumns = @JoinColumn(name = "idsorteio"))
	 private Set<Sorteio> sorteios;
    
    
    
    
    
    
    public Premio() {
		// TODO Auto-generated constructor stub
	}

 
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return name;
    }

}
