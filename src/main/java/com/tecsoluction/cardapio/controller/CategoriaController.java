package com.tecsoluction.cardapio.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tecsoluction.cardapio.CarrinhoBean;
import com.tecsoluction.cardapio.entidade.Carrinho;
import com.tecsoluction.cardapio.entidade.Categoria;
import com.tecsoluction.cardapio.entidade.Promocao;
import com.tecsoluction.cardapio.framework.AbstractController;
import com.tecsoluction.cardapio.framework.AbstractEditor;
import com.tecsoluction.cardapio.servico.CategoriaServicoImpl;
import com.tecsoluction.cardapio.servico.PromocaoServicoImpl;

@Controller
@RequestMapping(value = "categoria/")
public class CategoriaController extends AbstractController<Categoria> {
	
	
	private static final Logger logger = LoggerFactory.getLogger(CategoriaController.class);
	
	
	 @Autowired
    private CategoriaServicoImpl categoriaService;
	 
	 
		@Autowired
		private PromocaoServicoImpl PromocaoService = new PromocaoServicoImpl();
		
	    
	    private List<Promocao> promocoes = new ArrayList<Promocao>();
	    
	    private List<Categoria> categorias = new ArrayList<Categoria>();
	    
	    
	    private Categoria categoria;
	    
	    
	    private Carrinho carrinho = new Carrinho();
	    
	    
		 @Autowired
		 private CarrinhoBean carrinhobean;
		 
		 
		 

    @Autowired
    public CategoriaController(CategoriaServicoImpl dao,PromocaoServicoImpl pro) {
        super("categoria");
        this.categoriaService = dao;
        this.PromocaoService = pro;
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Categoria.class, new AbstractEditor<Categoria>(this.categoriaService) {

        });
    }

    @ModelAttribute
    public void addAttributes(Model model) {

//		Usuario usuario = new Usuario();
//		usuario.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//		usuario = userservice.findByUsername(usuario.getUsername());
		
		
      //  List<Categoria> categoriaList = getservice().getCategoriaPai();
    	
    	
		logger.info("Welcome add atribute Categoria Controller !" + model);

        
        categoria = new Categoria();
        
        
        promocoes = PromocaoService.findAll();
        
        categorias = getservice().findAll();  
        
		
//		if(carrinho == null){
//        	
//        	carrinho = new Carrinho();
//        	UUID uuid = UUID.randomUUID();
// 			carrinho.setId(uuid);
//            }else {
//            	
////            	UUID uuid = UUID.randomUUID();
////     			carrinho.setId(uuid);	
//            	  	
//            }
        
        model.addAttribute("promocoesList", promocoes);
        model.addAttribute("categoriaListall", categorias);
		model.addAttribute("categoria", categoria);
        model.addAttribute("carrinho", carrinhobean.getCarrinho()); 
	   	model.addAttribute("totalitens", carrinhobean.TotalItens());


    }
    
    
    
    @RequestMapping(value = "/exibir", method = RequestMethod.GET)
    public ModelAndView ExibirCategoria(HttpServletRequest request, Model model) {

        UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/public/exibir");

        Categoria cat = getservice().findOne(idf);

        exibircat.addObject("categoria", cat);
     
        
        if(carrinho == null){
        	carrinho = new Carrinho();
        	UUID uuid = UUID.randomUUID();
 			carrinho.setId(uuid);
      
            }else {
            	
//            	UUID uuid = UUID.randomUUID();
//     			carrinho.setId(uuid);
            	
            	
            }
        
        exibircat.addObject("carrinho", carrinhobean.getCarrinho());
        
        model.addAttribute("carrinho", carrinhobean.getCarrinho());

        return exibircat;
    }

    @Override
    protected CategoriaServicoImpl getservice() {
        return categoriaService;
    }

}
