package com.tecsoluction.cardapio.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tecsoluction.cardapio.entidade.Produto;
import com.tecsoluction.cardapio.framework.AbstractRestController;
import com.tecsoluction.cardapio.servico.ProdutoServicoImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "produto")
public class ProdutoControllerRest extends AbstractRestController<Produto> {

    private
    ProdutoServicoImpl produtoService;

    @Autowired
    public ProdutoControllerRest(ProdutoServicoImpl dao) {
        this.produtoService = dao;
    }


    @RequestMapping(value = "/porcategoria/{id}", method = RequestMethod.GET)
    public List<Produto> listarProdutoCategoria(@PathVariable String id) {

        UUID idf = UUID.fromString(id);

        return produtoService.getAllProdutoPorCategoria(idf);

    }


    @Override
    protected ProdutoServicoImpl getservice() {

        return produtoService;
    }
}
