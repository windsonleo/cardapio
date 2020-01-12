package com.tecsoluction.cardapio.dao;


import java.util.UUID;

import com.tecsoluction.cardapio.entidade.Mesa;

@org.springframework.stereotype.Repository
public interface IMesaDAO extends org.springframework.data.jpa.repository.JpaRepository<Mesa, UUID> {
	
	
	
}
