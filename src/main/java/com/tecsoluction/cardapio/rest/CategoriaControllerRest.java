package com.tecsoluction.cardapio.rest;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tecsoluction.cardapio.entidade.Categoria;
import com.tecsoluction.cardapio.framework.AbstractRestController;
import com.tecsoluction.cardapio.servico.CategoriaServicoImpl;

@RestController
@RequestMapping(value = "categoria")
public class CategoriaControllerRest extends AbstractRestController<Categoria> {

    private final CategoriaServicoImpl categoriaService;


    @Autowired
    public CategoriaControllerRest(CategoriaServicoImpl dao) {
        this.categoriaService = dao;
    }


    @Override
    protected CategoriaServicoImpl getservice() {
        // TODO Auto-generated method stub
        return categoriaService;
    }

    @RequestMapping(value = "/pai/", method = RequestMethod.GET)
    public List<Categoria> listarCategoriaPai() {

        return categoriaService.getCategoriaPai();

    }


}
