package com.tecsoluction.cardapio.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import com.tecsoluction.cardapio.util.SituacaoItem;
import com.tecsoluction.cardapio.util.UnidadeMedida;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@EqualsAndHashCode(exclude={"codigo","nome","descricao","precoUnitario","precoCusto","un_medida","totalItem","situacao"})
//@ToString(includeFieldNames=false,exclude={"id", "codigo","descricao","precoUnitario","precoCusto","un_medida","totalItem","situacao"})
//@EqualsAndHashCode(exclude={"codigo","nome","descricao","precoUnitario","precoCusto","un_medida","situacao","totalItem"})
@EqualsAndHashCode
public  class Item implements Serializable, Comparable<Item>{



	private static final long serialVersionUID = 1L;

	private UUID id;
    
    private String codigo;

    private String nome;

    private String descricao;

    private BigDecimal precoUnitario;
    
    private BigDecimal precoCusto;
    
  
    @Enumerated(EnumType.STRING)
    private UnidadeMedida un_medida;

    @Transient
    private BigDecimal totalItem ;
    

    @Enumerated(EnumType.STRING)
    private SituacaoItem situacao;
    
    private String foto;


    private String qtd;


    public Item() {
    	super();
//    	this.un_medida = UnidadeMedida.UND;
//    	this.situacao = SituacaoItem.AGUARDANDO;

    }
    
    

    public Item(Produto produto) {
    	super();
    	this.id = produto.getId();
        this.codigo = produto.getCodebar();
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.precoUnitario = produto.getPrecovenda();
        this.precoCusto = produto.getPrecocusto();
        this.un_medida = produto.getUn_medida();
        this.foto = produto.getFoto();
  //      this.situacao = SituacaoItem.AGUARDANDO;

        

    }
    
    public Item(Produto produto,String qtd) {
    	super();
    	this.id = produto.getId();
        this.codigo = produto.getCodebar();
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.precoUnitario = produto.getPrecovenda();
        this.precoCusto = produto.getPrecocusto();
        this.un_medida = produto.getUn_medida();
        this.foto = produto.getFoto();
        this.qtd = qtd;
     //   this.situacao = SituacaoItem.AGUARDANDO;


        

    }
    
@Override
public String toString() {

	return id.toString();
}



@Override
public int compareTo(Item arg0) {

	return this.codigo.compareTo(arg0.getCodigo());
}

public BigDecimal CalcularTotaItem(String qtd) {

	BigDecimal qtdAuxBig = new BigDecimal(qtd).setScale(2, RoundingMode.UP);


    
  return precoUnitario.multiply(qtdAuxBig).setScale(2, RoundingMode.UP);

}


public String TotalizacaoPoritem(String qtd){
	BigDecimal total = getPrecoUnitario().setScale(2, RoundingMode.UP);
	BigDecimal qtdaux = new BigDecimal(qtd).setScale(2, RoundingMode.UP);
	
	
	return total.multiply(qtdaux).toString();
	
	
}


public void MudarStatus(SituacaoItem sit){
	
	
	this.situacao = sit;
	
}



/* (non-Javadoc)
 * @see java.lang.Object#hashCode()
 */
//@Override
//public int hashCode() {
//	final int prime = 31;
//	int result = 1;
//	result = prime * result + ((nome == null) ? 0 : nome.hashCode());
//	return result;
//}



/* (non-Javadoc)
 * @see java.lang.Object#equals(java.lang.Object)
 */
//@Override
//public boolean equals(Object obj) {
//	if (this == obj)
//		return true;
//	if (obj == null)
//		return false;
//	if (getClass() != obj.getClass())
//		return false;
//	Item other = (Item) obj;
//	if (nome == null) {
//		if (other.nome != null)
//			return false;
//	} else if (!nome.equals(other.nome))
//		return false;
//	return true;
//}

@Override
public boolean equals(Object obj) {
    if (!(obj instanceof Item)) {
        return false;
    }
    final Item other = (Item) obj;
    return this.id.equals(other.getId());
}



@Override
public int hashCode() {
	// TODO Auto-generated method stub
	return super.hashCode();
}

}
