package com.tecsoluction.cardapio.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tecsoluction.cardapio.entidade.Fornecedor;

import java.util.List;
import java.util.UUID;

@Repository
public interface IFornecedorDAO extends JpaRepository<Fornecedor, UUID> {


//	  @Query("SELECT p FROM fornecedor p join fetch p.recebimento")
//	    List<Recebimento> getRecebimentosAll();

}
