package com.tecsoluction.cardapio.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tecsoluction.cardapio.entidade.Categoria;
import com.tecsoluction.cardapio.entidade.Configuracao;


@Repository
public interface IConfiguracaoDAO extends JpaRepository<Configuracao, UUID> {
	
//    @Query("SELECT p FROM Categoria p where p.catpai=(SELECT id FROM Categoria m  where m.nome='PAI')")
//    public List<Categoria> getCategoriaPai();
//    
//    @Query("SELECT p FROM Categoria p where p.catpai=(:catpai)")
//    public List<Categoria> getCategoriasFilho(@Param("catpai")UUID idPai);
//    
//    @Query("SELECT p FROM Categoria p where p.nome='PAI'")
//    public Categoria getOnlyCategoriaPai();
//    
//    @Query("SELECT p FROM Categoria p where p.nome='INSUMOS'")
//    public Categoria getOnlyCategoriaExcludeCardapio();

}
