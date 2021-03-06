package com.tecsoluction.cardapio.entidade;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "PROMOCAO")
public class Promocao extends BaseEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "nome", nullable = false)
	 @NotNull(message="o nome do Promocao não pode ser nulo")
	 @NotBlank(message="o nome do Promocao não pode ser branco")
    private String nome;

    
    @Column(name = "descricao", nullable = false)
	 @NotNull(message="o descricao do Promocao não pode ser nulo")
	 @NotBlank(message="o descricao do Promocao não pode ser branco")
    private String descricao;
    
    
    
    //    (cascade = { CascadeType.ALL })
//    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Promocao.class, optional = true)
//    @JoinColumn(name = "catpai_id", nullable = true)
//    private Promocao catpai;


    @JsonIgnore
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "promocao_produto",
    joinColumns = @JoinColumn(name = "idpromocao"),
    inverseJoinColumns = @JoinColumn(name = "idproduto"))
    private Set<Produto> produtos  = new HashSet<>();
    
    
    @Column(name = "corfaixa",nullable = false)
	 @NotNull(message="o corfaixa do Promocao não pode ser nulo")
	 @NotBlank(message="o corfaixa do Promocao não pode ser branco")
    private String corfaixa;

    
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date datavalidade = new Date();
    
    
    
    @Column(name = "desconto",nullable = false)
	 @NotNull(message="o desconto do Promocao não pode ser nulo")
    private BigDecimal desconto;
    
    @Column(name = "foto")
    private String foto;

    //CONSTRUTOR PADRAO

    public Promocao() {
        // TODO Auto-generated constructor stub
//        produtos = new HashSet<Produto>();
//        datavalidade = new Date();
    	
        super();
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
    	
    	not.addPromo(this);
    	
    	
    	
    	
    }


    public void removeProduto(Produto not){
    	
    	this.getProdutos().remove(not);
    	not.removePromo(this);
    	
    } 
    
    
    
    
    
    
    

    @Override
    public String toString() {
        return nome.toUpperCase();
    }
}
