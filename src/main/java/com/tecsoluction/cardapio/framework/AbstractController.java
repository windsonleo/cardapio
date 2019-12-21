package com.tecsoluction.cardapio.framework;

import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    @PostMapping(value = "add")
    public ModelAndView AdicionarEntity(@ModelAttribute Entity entity) {

        ModelAndView cadastroEntity = new ModelAndView("/private/"+entityAlias+"/cadastro/cadastro" + entityAlias);
        getservice().save(entity);
        cadastroEntity.addObject("entity", entity);
        cadastroEntity.addObject("acao", "salvo");
        cadastroEntity.addObject("sucesso", sucesso);
//        cadastroEntity.addObject("erro", erro);
        
        return cadastroEntity;
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
    public ModelAndView editarEntity(@ModelAttribute Entity entity) {



        ModelAndView cadastroEntity = new ModelAndView("/private/"+entityAlias+"/cadastro/cadastro" + entityAlias);
        
        getservice().edit(entity);

        cadastroEntity.addObject("acao", "add");

       return cadastroEntity;
    }

//    @Transactional
    @GetMapping(value = "delete")
    public ModelAndView deletarEntity(HttpServletRequest request) {

        UUID idf = UUID.fromString(request.getParameter("id"));
        getservice().delete(idf);

        return new ModelAndView("forward:/" + entityAlias + "/movimentacao");
    }
    
    
    private void trataErro(BindingResult result, RedirectAttributes attributes) {
        System.out.println("erro ao add Entidade: " + entityAlias + " erro: " + result.getObjectName());
        System.out.println("erro ap add Entidade: " + entityAlias + " fields erro: " + result.getFieldError());
        System.out.println("erro ao add Entidade: " + entityAlias + " outros erros global: " + result.getGlobalError());
        System.out.println("erro ao add Entidade: " + entityAlias + " outros erros nestedPatch: " + result.getNestedPath());
        attributes.addFlashAttribute("erros", "Erro ao Salvar." + result.getFieldError());
    }



}
