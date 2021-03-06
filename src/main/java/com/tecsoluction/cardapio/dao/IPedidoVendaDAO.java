package com.tecsoluction.cardapio.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tecsoluction.cardapio.entidade.PedidoVenda;
import com.tecsoluction.cardapio.util.StatusPedido;


@org.springframework.stereotype.Repository
public interface IPedidoVendaDAO extends JpaRepository<PedidoVenda, UUID> {


    @Query("SELECT p FROM PedidoVenda p where p.mesa=:mesa AND p.status='ABERTO' OR p.status='PENDENTE' OR p.status='PRONTO'")
    List<PedidoVenda> getAllPedidoPorMesa(@Param("mesa") UUID idmesa);

    @Query("SELECT p FROM PedidoVenda p where p.data=:dataini")
    List<PedidoVenda> getAllPedidoPorData(@Param("dataini") Date dataini);

    @Query("SELECT p FROM PedidoVenda p where p.origempedido='INTERNET' OR p.origempedido='TELEVENDAS'")
    List<PedidoVenda> getAllPedidoDelivery();
    
//    @Query("SELECT p FROM PedidoVenda p where p.origempedido='INTERNET' OR p.origempedido='TELEVENDAS'")
//    List<PedidoVenda> getAllPedidos();


    List<PedidoVenda> findAllByStatusIsOrderByDataAsc(StatusPedido status);
    
//    @Query("SELECT count(c) FROM PedidoVenda p, Cliente c where p.cliente = c.id order by p.data desc")
//    long findClienteByPedidoVenda();
    
    
    @Query("SELECT p FROM PedidoVenda p where p.novo='TRUE'")
    List<PedidoVenda> findAllNew();
}
