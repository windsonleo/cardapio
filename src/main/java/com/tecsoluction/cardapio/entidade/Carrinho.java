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
public class Carrinho extends BaseEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    @JsonIgnore
    @OneToMany(cascade = {CascadeType.REFRESH},fetch=FetchType.EAGER)
    private List<Item> itens;
    
    
    @Transient
    private BigDecimal subtotal ;

    @Transient
    private BigDecimal total ;
    
    @Transient
    private BigDecimal qtd ;
    
    @Transient
    private Mesa mesa ;
    
    
    @Transient
    private Garcon garcon ;
    
    
    
    //CONSTRUTOR PADRAO

    public Carrinho() {
        // TODO Auto-generated constructor stub
        itens = new ArrayList<Item>();
    }
    
    
    public void addItem(Item item){
    	
    	
    	this.getItens().add(item);
    	
    	
    	
    }
    
    
    public void removeItem(Item item){
    	
    	
    	this.getItens().remove(item);
    	
    	
    	
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

    public BigDecimal CalcularTotal() {
    
    BigDecimal totalpedido = new BigDecimal(0.00).setScale(2, RoundingMode.UP);
	BigDecimal qtdBigaux = new BigDecimal(0.00).setScale(2, RoundingMode.UP);


    for (Item key : this.getItens() ) {
                	
    	
    	String qtd = key.getQtd();
    	
    	qtdBigaux = new  BigDecimal(qtd);
    	
    	
    	BigDecimal itemvalor = new BigDecimal(key.getPrecoUnitario().toString());
    	
    	
    	totalpedido = totalpedido.add(itemvalor.multiply(qtdBigaux));

    	
//    	totalpedido = totalpedido.add(totalped);
    	
//    	totalpedido = totalpedido.add(totalped);

    	
    	
    }
    
    

    return totalpedido;
}

    @Override
    public String toString() {
        return itens.toString();
    }
}
