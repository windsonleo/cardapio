package com.tecsoluction.cardapio.entidade;


import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tecsoluction.cardapio.framework.BaseEntity;
import com.tecsoluction.cardapio.util.OrigemPedido;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Restaurante extends BaseEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    @JsonIgnore
    @OneToMany(fetch=FetchType.EAGER)
    private List<Garcon> garcons = new ArrayList<Garcon>();
    
    @JsonIgnore
    @OneToMany(fetch=FetchType.EAGER)
    private List<Mesa> mesas = new ArrayList<Mesa>();
    
    @JsonIgnore
    @OneToMany(fetch=FetchType.EAGER)
    private List<Promocao> promocoes = new ArrayList<Promocao>();
    
    @JsonIgnore
    @OneToMany(fetch=FetchType.EAGER)
    private List<Usuario> usuarios = new ArrayList<Usuario>();
    
    @JsonIgnore
    @OneToMany(fetch=FetchType.EAGER)
    private List<Produto> produtos = new ArrayList<Produto>();
    
    
    @JsonIgnore
    @OneToMany(fetch=FetchType.EAGER)
    private List<ProdutoComposto> produtoscompostos = new ArrayList<ProdutoComposto>();
    
    
    @JsonIgnore
    @OneToMany(fetch=FetchType.EAGER)
    private List<Categoria> categorias = new ArrayList<Categoria>();
    
    @JsonIgnore
    @OneToMany(fetch=FetchType.EAGER)
    private List<PedidoVenda> pedidosvendas = new ArrayList<PedidoVenda>();
    
    @JsonIgnore
    @OneToMany(fetch=FetchType.EAGER)
    private List<Configuracao> configuracoes = new ArrayList<Configuracao>();
    
    
    
    @JsonIgnore
    @OneToMany(fetch=FetchType.EAGER)
    private List<Produto> produtosemoferta = new ArrayList<Produto>();
    
    @JsonIgnore
    @OneToMany(fetch=FetchType.EAGER)
    private List<Promocao> promocoesdehoje = new ArrayList<Promocao>();
    
    
    @JsonIgnore
    @OneToMany(fetch=FetchType.EAGER)
    private List<PedidoVenda> pedidosvendashoje = new ArrayList<PedidoVenda>();
    
    
    @JsonIgnore
    @OneToMany(fetch=FetchType.EAGER)
    private List<Categoria> categoriascomproduto = new ArrayList<Categoria>();
    
    
    @JsonIgnore
    @OneToMany(fetch=FetchType.EAGER)
    private List<Produto> produtosmaioravaliacao = new ArrayList<Produto>();
    
    @JsonIgnore
    @OneToMany(fetch=FetchType.EAGER)
    private List<Produto> produtosmaisindicados = new ArrayList<Produto>();
    
    @JsonIgnore
    @OneToMany(fetch=FetchType.EAGER)
    private List<Usuario> usuariosonline = new ArrayList<Usuario>();
    

    public Restaurante() {

    	
    	super();
    	
    }
    
    
    
    public List<Produto> ProdutosEmPromocaoHoje(){
    	
    	
    	return null;
    }
    
    
    public List<Produto> ProdutosMaiorAvaliacao(){
    	
    	
    	return null;
    }
    
    
    public List<Produto> ProdutosMaisIndicados(){
    	
    	
    	return null;
    }
    
    
    public List<Promocao> PromocaoHoje(){
    	
    	
    	return null;
    }
    
    
    public List<PedidoVenda> PedidoVendaHoje(){
    	
    	
    	return null;
    }
    
    
    public List<Mesa> MesaAbertaHoje(){
    	
    	
    	return null;
    }
    
    
    public List<Mesa> MesaReservadaHoje(){
    	
    	
    	return null;
    }
    
    public Configuracao ConfiguracaoAtiva(){
    	
    	
    	return null;
    }
    
    
    public List<Categoria> CategoriaComProduto(){
    	
    	
    	return null;
    }
    
    public List<Categoria> CategoriasPai(){
    	
    	
    	return null;
    }
    
    public List<Categoria> CategoriasInsumos(){
    	
    	
    	return null;
    }
    
  

    @Override
    public String toString() {
        return id.toString();
    }
}
