package com.tecsoluction.cardapio.entidade;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.tecsoluction.cardapio.framework.BaseEntity;
import com.tecsoluction.cardapio.util.OrigemAtividade;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ATIVIDADE")
public class Atividade extends BaseEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "nome", nullable = false)
	 @NotNull(message="o nome do Atividade não pode ser nulo")
	 @NotBlank(message="o nome do Atividade não pode ser branco")
    private String nome;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date data;
    
    @Column(name = "origem",nullable=false)
	 @NotNull(message="o origem do Atividade não pode ser nulo")
    @Enumerated(EnumType.STRING)
    private OrigemAtividade origem;
    
    @ManyToOne(targetEntity=Usuario.class)
    @JoinColumn(name = "usuario_id", nullable = false)
	 @NotNull(message="o usuario do Atividade não pode ser nulo")
    private Usuario usuario;



    //CONSTRUTOR PADRAO

    public Atividade() {
        // TODO Auto-generated constructor stub
//        produtos = new ArrayList<Produto>();
    	 data = new Date();
    	 usuario = new Usuario();
    }


//    public Categoria(String id, String nome,Categoria catpai,boolean ativo) {
//        // TODO Auto-generated constructor stub
//    	this.id = id;
//    	this.nome = nome;
//    	this.catpai=catpai;
//    	this.ativo = ativo;
//    }
//
//
//    public Categoria(String id, String nome,boolean ativo) {
//        // TODO Auto-generated constructor stub
//    	this.id = id;
//    	this.nome = nome;
//    	this.ativo = ativo;
//    }


    @Override
    public String toString() {
        return origem.name();
    }
}
