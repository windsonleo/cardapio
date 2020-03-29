package com.tecsoluction.cardapio.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tecsoluction.cardapio.entidade.Sorteio;

import java.util.UUID;

@Repository
public interface ISorteioDAO extends JpaRepository<Sorteio, UUID> {
}
