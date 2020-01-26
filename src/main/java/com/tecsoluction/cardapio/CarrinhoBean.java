package com.tecsoluction.cardapio;

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
		
		produtosIndica.clear();
		
		for(Produto prod : getUsuarioIndica().getIndicacoes()){
			
			produtosIndica.add(prod);
			
		}
		
//		this.produtoIndica = prod;
		
	}
	
	public Produto PegarProdutoIndicaSessao(){
		
		if(index <= produtosIndica.size()){
			
			this.produtoIndica = produtosIndica.get(index);
			index++;
			
		}else {
			
			index = 0;
			
		}
		
		return produtoIndica;
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
