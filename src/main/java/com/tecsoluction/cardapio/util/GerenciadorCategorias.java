package com.tecsoluction.cardapio.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.tecsoluction.cardapio.entidade.Categoria;
import com.tecsoluction.cardapio.entidade.Produto;

import lombok.Getter;
import lombok.Setter;




@Getter
@Setter
public class GerenciadorCategorias implements Serializable{
	
	
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private Categoria categoriapai; 
  
  private List<Categoria> categoriasfilho;

  
  private List<Produto> produtosPorCategoria;
  
  
  private List<Categoria> categoriasAll;

  
  
  
  
  public GerenciadorCategorias() {

  
  
  }
  
  
  
   public GerenciadorCategorias(List<Categoria> all) {

   this.categoriasAll = all;
   
   
   }
   
   
   public List<Categoria> PegarCategoriasFilhas(Categoria categoria) {
		
   	
   	
	   categoriasfilho = new ArrayList<Categoria>();
   	
   	
   	
   	for(Categoria categoriaaux : categoriasAll){
   		
   		
   		if(categoriaaux.getCatpai().equals(categoria)){
   			
   			
   			categoriasfilho.add(categoriaaux);
   			
   		}else {
   			
   			
   			
   			
   		}
   		
   		
   		
   	}


//   	categoriasfilhos = CategoriaService.getCategoriasFilho(categoria.getId());
//   	
//   	if((categoriasfilhos != null) && (!categoriasfilhos.isEmpty())){
//   		
//   		
//   		
//   		
//   		
//   	}else {
//   		
//   			
//   		categoriasfilhos = new ArrayList<Categoria>();
//   		
//   	}

   	
		return categoriasfilho;
	}
   
   
   
   

	
   public List<Produto> PegarProdutoPorCategoria(Categoria categoria) {
	   

	   categoriapai = null;
	   
	   categoriapai = categoria;
		
   	 produtosPorCategoria = null;


   	produtosPorCategoria = categoriapai.getProdutos();
   	
   	if((produtosPorCategoria != null) && (!produtosPorCategoria.isEmpty())){
   		
   		
   		
   		
   		
   	}else {
   		
   			
   		produtosPorCategoria = new ArrayList<Produto>();
   		
   	}

   	
		return produtosPorCategoria;
	}
   
   
   
   @Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
   
   
   @Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
   
   
   @Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	

}
