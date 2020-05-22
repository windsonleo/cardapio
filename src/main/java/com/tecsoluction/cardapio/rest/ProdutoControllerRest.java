package com.tecsoluction.cardapio.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tecsoluction.cardapio.entidade.Produto;
import com.tecsoluction.cardapio.entidade.Usuario;
import com.tecsoluction.cardapio.framework.AbstractRestController;
import com.tecsoluction.cardapio.servico.ProdutoServicoImpl;

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
    
    
    @RequestMapping(value = "/all", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Produto> ListAllProduto() {

        return getservice().findAll();

    }


    @Override
    protected ProdutoServicoImpl getservice() {

        return produtoService;
    }
}
