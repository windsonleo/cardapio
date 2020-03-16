package com.tecsoluction.cardapio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.tecsoluction.cardapio.entidade.Carrinho;
import com.tecsoluction.cardapio.entidade.Item;
import com.tecsoluction.cardapio.entidade.Produto;
import com.tecsoluction.cardapio.entidade.Usuario;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope(value="session",proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
@Setter
public class CarrinhoBean {
	
	
	private Carrinho carrinho;
	
	private List<Usuario> usuariosIndica;
	
	private List<Produto> produtosIndica = new ArrayList<Produto>();
	
	private Produto produtoIndica;
	
	private Usuario usuarioIndica;
	
	private int index = 0;

	
	
	
	public CarrinhoBean() {

	
		carrinho = new Carrinho();
	}


	public void SetarCarrinhoSessao(Carrinho car){
		
		
		this.carrinho = car;
		
	}
	
	public void SetarUsuariosIndicaSessao(List<Usuario> usus){
		
		
		this.usuariosIndica = usus;
		
	}
	
	
	public void SetarUsuarioIndicaSessao(Usuario usu){
		
		
		this.usuarioIndica = usu;
		
	}
	
	public Usuario PegarUsuarioIndicaSessao(){
		
		return usuarioIndica;
	}
	
	
	public void SetarProdutosIndicaSessao(){
		
//		produtosIndica.clear();
		
		for(Produto prod : getUsuarioIndica().getIndicacoes()){
			
			this.produtosIndica.add(prod);
			
		}
		
//		this.produtoIndica = prod;
		
	}
	
	public Produto PegarProdutoIndicaSessao(){
		
		if(index < produtosIndica.size()){
			
			this.produtoIndica = produtosIndica.get(index);
			index++;
			
		}else {
			
			index = 0;
			this.produtoIndica = produtosIndica.get(index);
			
		}
		
		return produtoIndica;
	}
	
	public List<Item> PegarItensCarrinhoSessao(){
		
		return carrinho.getItens();
	}
	
	public void AddItemCarrinho(Item it){
		
		
	if(this.carrinho.getItens().contains(it)){
		
		int index = carrinho.getItens().indexOf(it);
		
		Item item = carrinho.getItens().get(index);
		String qtd = item.getQtd();
		String qtd2 = it.getQtd();
		
		BigDecimal quant1 = new BigDecimal(qtd);
		
		BigDecimal quant2 = new BigDecimal(qtd2);
		
		quant1=quant1.add(quant2);
		
		item.setQtd(quant1.toString());
		item.CalcularTotaItem(quant1.toString());
		
		
	}else {
		
		this.carrinho.addItem(it);
		
	}
		
		
		
	}
	
	
	public void RemoveItemCarrinho(Item it){
		
		this.carrinho.removeItem(it);
		
	}
	
	
	public BigDecimal TotalItens() {

		BigDecimal qtd = new BigDecimal(0);
		
		
		if((this.carrinho.getItens() != null) && (!this.carrinho.getItens().isEmpty())){
		
		for(Item us: carrinho.getItens()){
			
			BigDecimal qtdaux = new BigDecimal(us.getQtd());
			qtd = qtd.add(qtdaux);
			
		}
		
		}else {
			
			
			
			
		}
		
		
		return qtd;
	}
	
	
}
