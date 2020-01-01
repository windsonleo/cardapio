package com.tecsoluction.cardapio.framework;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public abstract class AbstractController<Entity> {
	
	private String sucesso = "Sucesso";
	
	private String erro = "Error";


    private final String entityAlias;

    public AbstractController(String entityAlias) {
        this.entityAlias = entityAlias;
    }

    protected abstract AbstractEntityService<Entity> getservice();

    @GetMapping(value = "cadastro")
    public ModelAndView cadastrarEntity() {

        ModelAndView cadastro = new ModelAndView("/private/"+entityAlias+"/cadastro/cadastro" + entityAlias);

        List<Entity> entityList = getservice().findAll();

        cadastro.addObject("acao", "add");
        cadastro.addObject(entityAlias + "List", entityList);

        


        return cadastro;

    }
    
    
    
//    @GetMapping(value = "cadastro")
//    public ModelAndView cadastrarEntity2(Model model) {
//
//        ModelAndView cadastro = new ModelAndView("/private/"+entityAlias+"/cadastro/cadastro" + entityAlias);
//
//        List<Entity> entityList = getservice().findAll();
//
////        cadastro.addObject("acao", "add");
//        model.addAttribute(entityAlias + "List", entityList);
////        cadastro.addObject(entityAlias, entity);
//
//        
//
//
//        return cadastro;
//
//    }
    
    

    @Transactional
    @PostMapping(value = "add")
    public ModelAndView AdicionarEntity(@Valid Entity entity, BindingResult result,Model model) {

  // ModelAndView cadastroEntity = new ModelAndView("/private/"+entityAlias+"/cadastro/cadastro" + entityAlias);
   
   		if(result.hasErrors()){
   			
   			model.addAttribute("erro", result.getFieldError().getDefaultMessage());
   			model.addAttribute(entityAlias, entity);
   			model.addAttribute("acao", "add");
   			
   			
   		}else {
   			
   		 getservice().save(entity);
   		model.addAttribute("sucesso", sucesso);
   		model.addAttribute(entityAlias, entity);
		model.addAttribute("acao", "add");
		
   			
   		}
   		
   		
        return new ModelAndView("private/" + entityAlias + "/cadastro/cadastro" + entityAlias);

    }


    @GetMapping(value = "movimentacao")
    public ModelAndView movimentacaoEntity() {

        ModelAndView movimentacao = new ModelAndView("/private/"+entityAlias+"/movimentacao/movimentacao" + entityAlias);
       
        List<Entity> entityList = getservice().findAll();
       
        movimentacao.addObject(entityAlias + "List", entityList);

        return movimentacao;
    }


    @Transactional
    @GetMapping(value = "editar")
    public ModelAndView editarEntityForm(HttpServletRequest request) {

        Entity entitys = null;

        UUID idf = UUID.fromString(request.getParameter("id"));
        entitys = getservice().findOne(idf);

        ModelAndView cadastroEntity = new ModelAndView("/private/"+entityAlias+"/cadastro/cadastro" + entityAlias);

        cadastroEntity.addObject("acao", "edicao");
        cadastroEntity.addObject(entityAlias, entitys);

        return cadastroEntity;
    }


    @Transactional
    @PostMapping(value = "edicao")
    public ModelAndView editarEntity(@Valid Entity entity, BindingResult result,Model model) {



        ModelAndView cadastroEntity = new ModelAndView("/private/"+entityAlias+"/cadastro/cadastro" + entityAlias);
        
        if(result.hasErrors()){
        	
        	model.addAttribute("acao", "add");
        		model.addAttribute(entityAlias, entity);
        		model.addAttribute("erro", result.getFieldError().getDefaultMessage());
        }else {
        	
        	
        	 getservice().edit(entity);

        	 model.addAttribute("acao", "add");
        	 model.addAttribute(entityAlias, entity);
        	model.addAttribute("sucesso", sucesso);

        	
        }
        
        
       

       return cadastroEntity;
    }

//    @Transactional
    @GetMapping(value = "delete")
    public ModelAndView deletarEntity(HttpServletRequest request) {

        UUID idf = UUID.fromString(request.getParameter("id"));
        getservice().delete(idf);

        return new ModelAndView("redirect:/" + entityAlias + "/movimentacao");
    }
    
    
    private void trataErro(BindingResult result, RedirectAttributes attributes) {
        System.out.println("erro ao add Entidade: " + entityAlias + " erro: " + result.getObjectName());
        System.out.println("erro ap add Entidade: " + entityAlias + " fields erro: " + result.getFieldError());
        System.out.println("erro ao add Entidade: " + entityAlias + " outros erros global: " + result.getGlobalError());
        System.out.println("erro ao add Entidade: " + entityAlias + " outros erros nestedPatch: " + result.getNestedPath());
        attributes.addFlashAttribute("erros", "Erro ao Salvar." + result.getFieldError());
    }



}
