package com.tecsoluction.cardapio.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tecsoluction.cardapio.entidade.Categoria;
import com.tecsoluction.cardapio.entidade.Promocao;
import com.tecsoluction.cardapio.framework.AbstractController;
import com.tecsoluction.cardapio.framework.AbstractEditor;
import com.tecsoluction.cardapio.servico.CategoriaServicoImpl;
import com.tecsoluction.cardapio.servico.PromocaoServicoImpl;

@Controller
@RequestMapping(value = "categoria/")
public class CategoriaController extends AbstractController<Categoria> {
	 @Autowired
    private CategoriaServicoImpl categoriaService;
	 
	 
		@Autowired
		private PromocaoServicoImpl PromocaoService = new PromocaoServicoImpl();
		
	    
	    private List<Promocao> promocoes = new ArrayList<Promocao>();
	    
	    private List<Categoria> categorias = new ArrayList<Categoria>();
	    
	    
	    private Categoria categoria;
	    
	    
	    


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
        
        categoria = new Categoria();
        
        
        promocoes = PromocaoService.findAll();
        
        categorias = getservice().findAll();        
        
        model.addAttribute("promocoesList", promocoes);
        model.addAttribute("categoriaListall", categorias);
		model.addAttribute("categoria", categoria);
    }
    
    
    
    @RequestMapping(value = "/exibir", method = RequestMethod.GET)
    public ModelAndView ExibirCategoria(HttpServletRequest request) {

        UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/public/exibir");

        Categoria cat = getservice().findOne(idf);

        exibircat.addObject("categoria", cat);

        return exibircat;
    }

    @Override
    protected CategoriaServicoImpl getservice() {
        return categoriaService;
    }

}
