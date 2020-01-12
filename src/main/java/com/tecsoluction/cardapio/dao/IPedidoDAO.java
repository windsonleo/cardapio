package com.tecsoluction.cardapio.dao;


import java.util.UUID;

import com.tecsoluction.cardapio.entidade.Pedido;

@org.springframework.stereotype.Repository
public interface IPedidoDAO extends org.springframework.data.jpa.repository.JpaRepository<Pedido, UUID> {
}
