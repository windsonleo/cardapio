package com.tecsoluction.cardapio.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tecsoluction.cardapio.entidade.Promocao;

@Repository
public interface IPromocaoDAO extends JpaRepository<Promocao, UUID> {

//    @Query("SELECT p FROM Produto p where p.codebar=:codebar")
//    public Produto getProdutoPorCodebar(@Param("codebar") String codebar);
//
//    @Query("SELECT p FROM Produto p where p.categoria=:categoria")
//    public List<Produto> getAllProdutoPorCategoria(@Param("categoria") UUID idcategoria);
//
//    List<Produto> getAllByCategoria_Id(UUID idCategoria);
//    
//    @Query("SELECT p FROM Produto p where p.novo='TRUE'")
//    List<Produto> findAllNew();

}
