package com.tecsoluction.cardapio.entidade;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@XmlRootElement(name = "garcon")
@EqualsAndHashCode
public class Mensagem implements Serializable {


    private static final long serialVersionUID = 1L;
    
    private UUID id;

    @Column(name = "descricao")
//    @NotBlank(message = "Nome do Banco obrigatorio")
    private String descricao;
    
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
//    @NotBlank(message = "Numero do Banco  obrigatorio")
    private Date data;

//    @ManyToOne(targetEntity=Usuario.class)
    
    //destino
    private Usuario usuario;
    
    //origem
    private UUID idusu;
    
    
	
    public Mensagem() {
    	super();
    
    
    }
    
    public Mensagem(Usuario usu) {
    	super();
    	this.usuario = usu;
    	this.idusu = usu.getId();
    
    
    }



    @Override
    public String toString() {
        return descricao.toUpperCase();
    }

}
