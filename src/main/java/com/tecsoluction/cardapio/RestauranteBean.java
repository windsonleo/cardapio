package com.tecsoluction.cardapio;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.tecsoluction.cardapio.entidade.Restaurante;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope(value="session",proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
@Setter
public class RestauranteBean {
	
	
	private Restaurante restaurante = new Restaurante();

	
	
	
	
	public RestauranteBean() {

	
		//carrinho = new Carrinho();
	
	}
	
	
	public void SetarRestauranteSessao(Restaurante resta){
		
		this.restaurante = resta;
		
	}
	
	


	
	
	
}
