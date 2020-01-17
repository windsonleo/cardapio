package com.tecsoluction.cardapio.rest;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tecsoluction.cardapio.entidade.Produto;
import com.tecsoluction.cardapio.entidade.ProdutoComposto;
import com.tecsoluction.cardapio.framework.AbstractRestController;
import com.tecsoluction.cardapio.servico.ProdutoCompostoServicoImpl;

@RestController
@RequestMapping(value = "produtocomposto")
public class ProdutoCompostoControllerRest extends AbstractRestController<ProdutoComposto> {

    private
    ProdutoCompostoServicoImpl produtocompostoService;

    private
    List<Produto> produtos;

    @Autowired
    public ProdutoCompostoControllerRest(ProdutoCompostoServicoImpl dao) {
        this.produtocompostoService = dao;
    }


    @RequestMapping(value = "/porcategoria/{id}", method = RequestMethod.GET)
    public List<Produto> listarProdutoCategoria(@PathVariable String id) {

        UUID idf = UUID.fromString(id);

        return produtos = produtocompostoService.getAllProdutoPorCategoria(idf);

    }


    @Override
    protected ProdutoCompostoServicoImpl getservice() {

        return produtocompostoService;
    }


}
