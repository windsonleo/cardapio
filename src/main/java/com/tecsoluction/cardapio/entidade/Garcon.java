package com.tecsoluction.cardapio.entidade;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tecsoluction.cardapio.framework.BaseEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "GARCON")
//@XmlRootElement(name = "garcon")
public class Garcon extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 121L;

   
    @Column(name = "nome", nullable = false)
	 @NotNull(message="o nome do Garcon não pode ser nulo")
	 @NotBlank(message="o nome do Garcon não pode ser branco")
    private String nome;

    @Column(name = "foto")
    private String foto;

    @JsonIgnore
    @OneToMany(mappedBy = "garcon",fetch=FetchType.EAGER)
    private List<PedidoVenda> pedidos;
    
    @OneToOne
    private Usuario usuario;


    public Garcon() {

    	
    }


    public Garcon(UUID id, String nome, String foto, boolean ativo) {
        super();
        this.id = id;
        this.nome = nome;
        this.foto = foto;
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return nome;
    }

}
