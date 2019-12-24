package com.tecsoluction.cardapio.entidade;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tecsoluction.cardapio.framework.BaseEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "PROMOCAO")
public class Promocao extends BaseEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "nome", nullable = false)
    private String nome;

    //    (cascade = { CascadeType.ALL })
//    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Promocao.class, optional = true)
//    @JoinColumn(name = "catpai_id", nullable = true)
//    private Promocao catpai;


    @JsonIgnore
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "promocao_produto",
    joinColumns = @JoinColumn(name = "idpromocao"),
    inverseJoinColumns = @JoinColumn(name = "idproduto"))
    private Set<Produto> produtos;
    
    private String corfaixa;

    
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date datavalidade;
    
    
    
    @Column(name = "desconto")
    private BigDecimal desconto;
    
    @Column(name = "foto")
    private String foto;

    //CONSTRUTOR PADRAO

    public Promocao() {
        // TODO Auto-generated constructor stub
        produtos = new HashSet<Produto>();
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

    
	
    public void addProduto(Produto not){
    	
    	
    	this.getProdutos().add(not);
    	
    }


    public void removeProduto(Produto not){
    	
    	this.getProdutos().remove(not);
    	
    } 
    
    
    

    @Override
    public String toString() {
        return nome.toUpperCase();
    }
}
