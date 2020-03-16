package com.tecsoluction.cardapio.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tecsoluction.cardapio.entidade.Produto;
import com.tecsoluction.cardapio.entidade.ProdutoComposto;



@org.springframework.stereotype.Repository
public interface IProdutoCompostoDAO extends org.springframework.data.jpa.repository.JpaRepository<ProdutoComposto, UUID> {


	
	@Query("SELECT p FROM ProdutoComposto p where p.categoria=:categoria")
	public List<Produto> getAllProdutoPorCategoria(@Param("categoria") UUID idcategoria);
	
    @Query("SELECT p FROM ProdutoComposto p ORDER BY p.precovenda ASC")
    List<ProdutoComposto> ListaProdutoCompostoMenorPreco(Pageable pageable);
	

}
