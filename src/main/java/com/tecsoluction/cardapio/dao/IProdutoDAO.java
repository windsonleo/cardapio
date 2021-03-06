package com.tecsoluction.cardapio.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tecsoluction.cardapio.entidade.Produto;

@Repository
public interface IProdutoDAO extends JpaRepository<Produto, UUID> {

    @Query("SELECT p FROM Produto p where p.codebar=:codebar")
    public Produto getProdutoPorCodebar(@Param("codebar") String codebar);

    @Query("SELECT p FROM Produto p where p.categoria=:categoria")
    public List<Produto> getAllProdutoPorCategoria(@Param("categoria") UUID idcategoria);

    List<Produto> getAllByCategoria_Id(UUID idCategoria);
    
    @Query("SELECT p FROM Produto p where p.novo='TRUE'")
    List<Produto> findAllNew();
    
    @Query("SELECT p FROM Produto p ORDER BY p.avaliacao DESC")
    List<Produto> ListaProdutoMaiorAvaliacao(Pageable pageable);
    
    @Query("SELECT p FROM Produto p ORDER BY p.precovenda ASC")
    List<Produto> ListaProdutoMenorPreco(Pageable pageable);

}
