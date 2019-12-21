package com.tecsoluction.cardapio.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tecsoluction.cardapio.entidade.Produto;

import java.util.List;
import java.util.UUID;

@Repository
public interface IProdutoDAO extends JpaRepository<Produto, UUID> {

    @Query("SELECT p FROM Produto p where p.codebar=:codebar")
    public Produto getProdutoPorCodebar(@Param("codebar") String codebar);

    @Query("SELECT p FROM Produto p where p.categoria=:categoria")
    public List<Produto> getAllProdutoPorCategoria(@Param("categoria") UUID idcategoria);

    List<Produto> getAllByCategoria_Id(UUID idCategoria);
    
    @Query("SELECT p FROM Produto p where p.novo='TRUE'")
    List<Produto> findAllNew();

}
