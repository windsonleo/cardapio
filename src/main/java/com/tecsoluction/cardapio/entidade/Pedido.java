package com.tecsoluction.cardapio.entidade;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.tecsoluction.cardapio.framework.BaseEntity;
import com.tecsoluction.cardapio.util.StatusPedido;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Pedido extends BaseEntity {

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
	protected Date data;


    @Column(name = "total")
    private BigDecimal  total ;

//    @ManyToMany(mappedBy = "pedidos",fetch=FetchType.EAGER)
//    @JsonIgnore
//    private List<Pagamento> pagamento;

    //aberto,pendente,fechado,cancelado
    @Enumerated(EnumType.STRING)
    private StatusPedido status;
    
    
    @Column(name = "horapedido",nullable = true)
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern ="HH:mm:ss")
  //  @NotNull(message="o hora do pedido não pode ser nulo")
    protected Date horapedido;

    
    private boolean ispago = false;
    
    private boolean novo = true;
    
//    @ManyToOne
//    @JoinColumn(name="conta_id")
//    private Conta conta;
    
    
    private String obs;

    
    public Pedido() {

    	
    }

    public BigDecimal getTotal() {


        return total;
    }

    public void setTotal(BigDecimal total) {
       
    	this.total = total.setScale(2, RoundingMode.UP);
    }


    @Override
    public String toString() {

        return String.valueOf(id);
    }
    
    

    public BigDecimal CalcularTotal(Map<Item, String> itens) {

    	BigDecimal totalpedido = new BigDecimal("0.00").setScale(2, RoundingMode.UP);
    	BigDecimal totalpedidoaux = new BigDecimal("0.00").setScale(2, RoundingMode.UP);


        for (Item key : itens.keySet()) {
        	
        	//QTD ITEM
        	String total = itens.get(key);
        	
        	totalpedidoaux = new  BigDecimal(total).setScale(2, RoundingMode.UP);
        	
        	BigDecimal totalped = new BigDecimal(key.getPrecoUnitario().toString()).setScale(2, RoundingMode.UP);
        	
        	totalped = totalped.multiply(totalpedidoaux).setScale(2, RoundingMode.UP);
        	

        	totalpedido = totalpedido.add(totalped).setScale(2, RoundingMode.UP);
        }



        return totalpedido;
    }
    
    
    
}
