package com.tecsoluction.cardapio;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.tecsoluction.cardapio.entidade.Carrinho;
import com.tecsoluction.cardapio.entidade.Item;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope(value="session",proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
@Setter
public class CarrinhoBean {
	
	
	private Carrinho carrinho;
	


	public void SetarCarrinhoSessao(Carrinho car){
		
		
		this.carrinho = car;
		
	}
	
	
	public List<Item> PegarItensCarrinhoSessao(){
		
		return carrinho.getItens();
	}
	
	public void AddItemCarrinho(Item it){
		
		this.carrinho.addItem(it);
		
	}
	
	
	public void RemoveItemCarrinho(Item it){
		
		this.carrinho.removeItem(it);
		
	}
	
	
}
