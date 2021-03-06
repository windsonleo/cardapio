package com.tecsoluction.cardapio.controller;

import java.util.List;
import java.util.Locale;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tecsoluction.cardapio.entidade.Categoria;
import com.tecsoluction.cardapio.entidade.Produto;
import com.tecsoluction.cardapio.entidade.ProdutoComposto;
import com.tecsoluction.cardapio.framework.AbstractEditor;
import com.tecsoluction.cardapio.servico.CategoriaServicoImpl;
import com.tecsoluction.cardapio.servico.ProdutoCompostoServicoImpl;
import com.tecsoluction.cardapio.servico.ProdutoServicoImpl;


@Controller
@RequestMapping(value = "cardapio/")
public class CardapioController   {
	
	
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	 @Autowired
	private 
	CategoriaServicoImpl categoriaService;
	       
	 @Autowired
    private
	ProdutoServicoImpl produtoService;
   
	 @Autowired
    private 
    ProdutoCompostoServicoImpl produtocompostoService;

	
    private List<Produto> produtos;

    
    @Autowired
    public CardapioController(CategoriaServicoImpl dao,ProdutoServicoImpl proddao,ProdutoCompostoServicoImpl comp) {
        this.categoriaService = dao;
        this.produtoService = proddao;
        this.produtocompostoService = comp;
    }



    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {

        binder.registerCustomEditor(Categoria.class, new AbstractEditor<Categoria>(this.categoriaService) {
        });
        	
        	binder.registerCustomEditor(Produto.class, new AbstractEditor<Produto>(this.produtoService) {
        
        });
        	
        	binder.registerCustomEditor(ProdutoComposto.class, new AbstractEditor<ProdutoComposto>(this.produtocompostoService) {
                
            });
        

    }

    @ModelAttribute
    public void addAttributes(Model model) {

    	
    	List<Produto> sugestaoList = produtoService.findAll();
        
        List<Categoria> categoriaList = categoriaService.findAll();
        
        
        model.addAttribute("categoriaList", categoriaList);
        model.addAttribute("sugestaoList", sugestaoList);

    }
    
    
	@RequestMapping(value = "inicio", method = RequestMethod.GET)
	public ModelAndView Cardapio(Locale locale, Model model,HttpServletRequest request) {
		
		
		ModelAndView cardapio = new ModelAndView("cardapio");
				
		return cardapio;
	}
	
	
	public List<Produto> pegarProdutoPorCategoria(Categoria categoria){
		
		
		
		
		return null;
	}

	@RequestMapping(value = "produtoporcategoria", method = RequestMethod.GET)
	public ModelAndView CardapioProdutoPorCatgeeoria(Locale locale, Model model,HttpServletRequest request) {
		
		ModelAndView cardapio = new ModelAndView("produtoporcategoria");
		
		return cardapio;
	}
	
	@RequestMapping(value = "new", method = RequestMethod.GET)
	public ModelAndView CardapioP(Locale locale, Model model,HttpServletRequest request) {
		
		ModelAndView cardapio = new ModelAndView("cardapionew");
		
		return cardapio;
	}
	
	
	@RequestMapping(value = "cardapio", method = RequestMethod.GET)
	public ModelAndView CardapioPr(@RequestParam(value = "erro", required = false) String error, 
	@RequestParam(value = "id", required = false) String id,@RequestParam(value = "sucesso", required = false) String sucesso,
	Locale locale, Model model) {

    	logger.info("Welcome cardapio /cardapio! The client locale is {}.", locale);

		ModelAndView cardapio = new ModelAndView("public/cardapio");
		
		  String mensagem ="";
	        
	        if(error != null && error !=""){
	        	 mensagem = error + "erros";
	        	 cardapio.addObject("erro", mensagem);
	        	
	        }else if(sucesso != null && sucesso !=""){
	        	
	       	 mensagem = sucesso + "sucesso";
	       	cardapio.addObject("sucesso", mensagem);
	        	
	        }else if(id != null && id !=""){
	        	
	       	 mensagem =  "sucesso"+id;
	       	cardapio.addObject("sucesso", mensagem);
	        	
	        }
		
		
		return cardapio;
	}
	
	
	
}

