package com.tecsoluction.cardapio.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.mapping.Value;
import org.hibernate.validator.constraints.NotBlank;

import com.tecsoluction.cardapio.framework.BaseEntity;
import com.tecsoluction.cardapio.util.UnidadeMedida;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@EqualsAndHashCode(callSuper = false)
@Entity
@Table
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Produto extends BaseEntity implements Serializable {

	
    private static final long serialVersionUID = -5401174413867896341L;

    @Column(name = "foto")
    private String foto;
    
    @Column(name = "nome", nullable = false)
	 @NotNull(message="o nome do Produto não pode ser nulo")
	 @NotBlank(message="o nome do Produto não pode ser branco")
    private String nome;

    @Column(name = "codebar", nullable = false,unique=true)
	 @NotNull(message="o codebar do Produto não pode ser nulo")
	 @NotBlank(message="o codebar do Produto não pode ser branco")
    private String codebar;

    @Column(name = "descricao", nullable = false)
	 @NotNull(message="o descricao do Produto não pode ser nulo")
	 @NotBlank(message="o descricao do Produto não pode ser branco")
    private String descricao;

    @Column(name = "un_medida",nullable=false)
	 @NotNull(message="o un_medida do Produto não pode ser nulo")
    @Enumerated(EnumType.STRING)
    private UnidadeMedida un_medida;

    @Column(name = "preco_custo")
    private BigDecimal precocusto;

    @Column(name = "preco_venda")
    private BigDecimal precovenda;
    
    @ManyToMany(mappedBy = "produtos")
    private Set<Promocao> promocoes = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "categoria_id", nullable = false)
	 @NotNull(message="o categoria do Produto não pode ser nulo")
    private Categoria categoria;


    @Column(name = "esugestao", nullable = true)
    private boolean esugestao;
    
    
    @Column(name = "novo", nullable = true)
    private boolean novo;
    
    @Column(name = "avaliacao", nullable = true)
    private int avaliacao = 0;
    
    @Column(name = "tempopreparo", nullable = true)
//    @NotNull(message="o tempopreparo do Produto não pode ser nulo")
    private int tempopreparo = 0;
    
    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name="produto_notas")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Integer> notas = new ArrayList<Integer>();
    
    @ManyToMany(mappedBy="indicacoes")
    private Set<Usuario> usuariosIndica;


    public Produto(UUID id, String foto, String nome, String codebar, String descricao,
                   UnidadeMedida un, BigDecimal precocusto, BigDecimal precovenda,
                   Categoria cat, boolean ativo, boolean esugestao,boolean novo,int avalia,int temp) {
        super();
        this.id = id;
        this.foto = foto;
        this.nome = nome;
        this.codebar = codebar;
        this.descricao = descricao;
        this.un_medida = un;
        this.precocusto = precocusto;
        this.precovenda = precovenda;
        this.categoria = cat;
        this.ativo = ativo;
        this.esugestao = esugestao;
        this.novo = novo;
        this.avaliacao = avalia;
        this.tempopreparo = temp;
        
        
    }

    public Produto() {
        super();
        
        

    }
    
    
	
    public void addPromo(Promocao not){
    	
    	
    	this.getPromocoes().add(not);
    	
    	not.addProduto(this);
    	
    }


    public void removePromo(Promocao not){
    	
    	this.getPromocoes().remove(not);
    	not.removeProduto(this);
    	
    } 
    
    
    public void addNota(int not){
    	
    	
    	this.getNotas().add(not);
    	
    	
    }


    public void removenota(int not){
    	
    	this.getNotas().remove(not);
    	
    } 


    @Override
    public String toString() {
        return nome.toUpperCase();
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (codebar == null) {
			if (other.codebar != null)
				return false;
		} else if (!codebar.equals(other.codebar))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codebar == null) ? 0 : codebar.hashCode());
		return result;
	}
    
    
   public int CalcularAvaliacao(){
	   
	   BigDecimal db = null; 
	   
	  
//	    Collections.sort(getNotas());
////	    Integer min = getNotas().get(0);
////	    Integer max = getNotas().get(getNotas().size() - 1);
//	    List<Integer> notes = new ArrayList<Integer>();
	    int soma = 0;
	    
	    int divisor = getNotas().size();
	    
	    
	    
	    for (Integer i : getNotas()) {
	     //   notes.add(10 - (int) 9.0 * (i - min) / (max - min));
	    	
	    	soma = soma + i;
	    	
	    }
	    
	    db=  new BigDecimal(soma/divisor).setScale(1,RoundingMode.HALF_EVEN);
	    
	   // setAvaliacao(db.intValue());
	    
	    System.out.println("notas BIGDECIMAL : " + db.toString() + "\n");
	   
	   
	   return db.intValue();
   }

}
