	package com.tecsoluction.cardapio.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyClass;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tecsoluction.cardapio.util.OrigemPedido;
import com.tecsoluction.cardapio.util.StatusPedido;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PEDIDO_VENDA")
public class PedidoVenda extends Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

//    @ManyToOne
//    @JoinColumn(name = "cliente_id")
//    private Cliente cliente;


    @ManyToOne
    @JoinColumn(name = "mesa_id",nullable = true)
    private Mesa mesa;

    @ManyToOne
    @JoinColumn(name = "garcon_id",nullable = true)
    private Garcon garcon;


    // VENDA OOU COMPRA
    @Enumerated(EnumType.STRING)
    private OrigemPedido origempedido;

    private boolean ispago = false;

    //aberto,pendente,fechado,cancelado
    @Enumerated(EnumType.STRING)
    private StatusPedido status;


    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name = "itens_pedidovenda", joinColumns = @JoinColumn(name = "id"))
    @JsonManagedReference
//    @Lob
    @Column(name = "qtd")
    @MapKeyColumn(name = "idit")
    private Map<Item, String> items = new HashMap<Item, String>();


    public PedidoVenda() {
        super();
        
        this.items = new HashMap<Item, String>();


    }


    public PedidoVenda( Mesa mesa, Garcon garcon, OrigemPedido origempedido) {
        super();
//        this.cliente = cliente;
        this.mesa = mesa;
        this.garcon = garcon;
        this.origempedido = origempedido;
        this.items = new HashMap<Item, String>();
    }
    
    
    public PedidoVenda(Carrinho carrinho) {
        super();
//        this.cliente = cliente;
        this.mesa = carrinho.getMesa();
        this.garcon = carrinho.getGarcon();
        this.items = new HashMap<Item, String>();
        this.status = StatusPedido.PENDENTE;
        this.data = new Date();
        this.origempedido = OrigemPedido.MESA;
        
        SetarItems(carrinho.getItens());
    }

    private void SetarItems(List<Item> itens) {

    	
    	
    	for( Item it : itens){
    		
    		if(items.containsKey(it)){
    			
    			int qtd = Integer.parseInt(it.getQtd());
    			qtd = qtd +1;
    			String qtdaux = String.valueOf(qtd);
    			it.setQtd(qtdaux);
    			
    		//	this.removeItem(it);

    			this.addItem(it, it.getQtd());
    		//	this.items.replace(it, qtdaux);
    			
    			
    		}else {
    			
    			this.addItem(it, it.getQtd());
    			
    		}
    		
    		//this.items.put(it, it.getQtd());
    		
    	}
    	
    	
	}


	@Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString().toUpperCase();
    }

    public void addItem(Item item, String qtd){
    	
    	
    	this.getItems().put(item, qtd);
    	
    	
    	
    }
    
    
    public void removeItem(Item item){
    	
    	
    	this.getItems().remove(item);
	
    	
    }
    
    public BigDecimal getTotalVenda(){
    	
    	
    	return  CalcularTotal(getItems());
    }

}
