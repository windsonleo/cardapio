package com.tecsoluction.cardapio.dao;



import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tecsoluction.cardapio.entidade.Garcon;
import com.tecsoluction.cardapio.entidade.Usuario;

@org.springframework.stereotype.Repository
public interface IGarconDAO extends org.springframework.data.jpa.repository.JpaRepository<Garcon, UUID> {



    @Query("SELECT p FROM Garcon p where p.usuario=:usuario")
    Garcon getGarconByUser(@Param("usuario")Usuario usuario);

}
