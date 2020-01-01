package com.tecsoluction.cardapio.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tecsoluction.cardapio.framework.BaseEntity;
import com.tecsoluction.cardapio.util.UnidadeMedida;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity
@Table
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Produto extends BaseEntity implements Serializable {

	
    private static final long serialVersionUID = -5401174413867896341L;

    @Column(name = "foto")
    private String foto;
    @Column(name = "nome")
    private String nome;

    @Column(name = "codebar")
    private String codebar;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "un_medida")
    @Enumerated(EnumType.STRING)
    private UnidadeMedida un_medida;

    @Column(name = "preco_custo")
    private BigDecimal precocusto;

    @Column(name = "preco_venda")
    private BigDecimal precovenda;
    
    @ManyToMany(mappedBy = "produtos")
    private Set<Promocao> promocoes = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;


    @Column(name = "esugestao", nullable = true)
    private boolean esugestao;
    
    
    @Column(name = "novo", nullable = true)
    private boolean novo;
    
    @Column(name = "avaliacao", nullable = true)
    private int avaliacao = 0;


    public Produto(UUID id, String foto, String nome, String codebar, String descricao,
                   UnidadeMedida un, BigDecimal precocusto, BigDecimal precovenda,
                   Categoria cat, boolean ativo, boolean esugestao,boolean novo,int avalia) {
        super();
        this.id = id;
        this.foto = foto;
        this.nome = nome;
        this.codebar = codebar;
        this.descricao = descricao;
        this.un_medida = un;
        this.precocusto = precocusto;
        this.precovenda = precovenda;
        this.categoria = cat;
        this.ativo = ativo;
        this.esugestao = esugestao;
        this.novo = novo;
        this.avaliacao = avalia;
        
        
    }

    public Produto() {
        super();
        
        

    }
    
    
	
    public void addPromo(Promocao not){
    	
    	
    	this.getPromocoes().add(not);
    	
    	not.addProduto(this);
    	
    }


    public void removePromo(Promocao not){
    	
    	this.getPromocoes().remove(not);
    	not.removeProduto(this);
    	
    } 
    
    



    @Override
    public String toString() {
        return nome.toUpperCase();
    }

}
