package com.tecsoluction.cardapio.entidade;


import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tecsoluction.cardapio.framework.BaseEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "CATEGORIA")
public class Categoria extends BaseEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "nome", nullable = false)
	 @NotNull(message="o nome do categoria não pode ser nulo")
	 @NotBlank(message="o nome do categoria não pode ser branco")
    private String nome;

    //    (cascade = { CascadeType.ALL })
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Categoria.class, optional = true)
    @JoinColumn(name = "catpai_id", nullable = true)
    private Categoria catpai;


    @JsonIgnore
    @OneToMany(mappedBy = "categoria", cascade = {CascadeType.REFRESH},fetch=FetchType.EAGER)
    private List<Produto> produtos;
    
    
    @Transient
    private BigDecimal menorPreco;
    
    @Transient
    private BigDecimal maiorPreco;


    //CONSTRUTOR PADRAO

    public Categoria() {
        // TODO Auto-generated constructor stub
        produtos = new ArrayList<Produto>();
    }


//    public Categoria(String id, String nome,Categoria catpai,boolean ativo) {
//        // TODO Auto-generated constructor stub
//    	this.id = id;
//    	this.nome = nome;
//    	this.catpai=catpai;
//    	this.ativo = ativo;
//    }
//
//
//    public Categoria(String id, String nome,boolean ativo) {
//        // TODO Auto-generated constructor stub
//    	this.id = id;
//    	this.nome = nome;
//    	this.ativo = ativo;
//    }

    
    public void CalcularMenorMaiorPreco(){
    	
    	BigDecimal menorPrecoauxnew = new BigDecimal("0.00").setScale(2, RoundingMode.UP);
    	BigDecimal	maiorPrecoaux = new BigDecimal("0.00").setScale(2, RoundingMode.UP);
    	BigDecimal	Precoaux = new BigDecimal("0.00").setScale(2, RoundingMode.UP);

    	
    	for(Produto prd : getProdutos()){
    		
    		
    		Precoaux = prd.getPrecovenda();
    		
    		if(Precoaux.compareTo(maiorPrecoaux) ==1  ){
    			
    			maiorPrecoaux = Precoaux;
    			
    			
    		}
    		else if(Precoaux.compareTo(menorPrecoauxnew) ==1 ){
        			
        			System.out.println("valor2 é maior");
        			
        		}else {
        			       			
        			menorPrecoauxnew = Precoaux;
        			
        			
        		}
    			
    			
    		}
    	
    	maiorPreco = maiorPrecoaux;
    	menorPreco = menorPrecoauxnew;
    		
    		
    		
    		
    	}
    	
    	

    @Override
    public String toString() {
        return nome.toUpperCase();
    }
}
