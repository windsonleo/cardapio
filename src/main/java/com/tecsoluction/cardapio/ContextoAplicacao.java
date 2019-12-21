package com.tecsoluction.cardapio;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.tecsoluction.cardapio.entidade.Categoria;
import com.tecsoluction.cardapio.entidade.Usuario;
import com.tecsoluction.cardapio.exception.CustomGenericException;
import com.tecsoluction.cardapio.servico.CategoriaServicoImpl;
import com.tecsoluction.cardapio.servico.UsuarioServicoImpl;





/**
 * Created by clebr on 01/09/2016.
 */
@ControllerAdvice
public class ContextoAplicacao {
	
	 @Autowired
	 private
	 UsuarioServicoImpl userService;
	 
	 @Autowired
	    private CategoriaServicoImpl categoriaService;
	 
	 private List<Categoria> categoriaLista ;

	// @Autowired
	// public ContextoAplicacao(UsuarioServicoImpl sevice) {
	//
	// this.userservice = sevice;
	// }

	@Autowired
	public ContextoAplicacao() {

		// this.userservice = sevice;
	}

	@ModelAttribute
	public void addAttributes(Model model) {

		// Usuario usuarioAtt = dao.PegarPorId(100L);
		//
		// model.addAttribute("usuarioAtt", usuarioAtt);
		
        //inicio de ususario logado
		
		List<Categoria> categorias = new ArrayList<Categoria>();
		
		categorias = categoriaService.findAll();
		
		categoriaLista = CategoriasComProduto(categorias);
        
        Usuario usuario = new Usuario();
        usuario.setEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        usuario = userService.findByEmail(usuario.getEmail());
        
        //verifica se há usuario cadastrado
        if(usuario == null) {
        	
        model.addAttribute("mensagem", "Carregado Usuario Cliente");
        
        usuario = new Usuario();
        UUID idf = UUID.fromString("4b71a569-c0bd-41a2-bffe-35e39e1a875a");
        usuario = userService.findOne(idf);
        	
        	
        } else {
    	
	        model.addAttribute("mensagem", "Bem-Vindo " + usuario.getEmail());

        
        }

        
        model.addAttribute("usuarioAtt", usuario);
        model.addAttribute("categoriaLista", categoriaLista);
	}

	@ExceptionHandler(CustomGenericException.class)
	public ModelAndView handleCustomException(CustomGenericException ex) {

		ModelAndView model = new ModelAndView("/public/error/erro");
		model.addObject("errCode", ex.getErrCode());
		model.addObject("errMsg", ex.getErrMsg());

		return model;

	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(Exception ex) {

		ModelAndView model = new ModelAndView("/public/error/erro");
		model.addObject("errMsg", ex.toString());

		return model;

	}
	
	
	public List<Categoria> CategoriasComProduto(List<Categoria> cats){
		
		List<Categoria> validas = new ArrayList<Categoria>();
		
		String insumo = "INSUMOS";
		
		for(Categoria cat : cats) {
			
			
			if((cat.getProdutos().size() > 0) && (!cat.getNome().equals(insumo))){
				
				
				validas.add(cat);
				
				
			}else {
				
				
				
				
			}
			
		}
		
		
		return validas;
		
	}

}
