package com.tecsoluction.cardapio.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tecsoluction.cardapio.entidade.Premio;

@Repository
public interface IPremioDAO extends JpaRepository<Premio, UUID> {
}
