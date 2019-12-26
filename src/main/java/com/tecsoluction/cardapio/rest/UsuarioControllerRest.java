package com.tecsoluction.cardapio.rest;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tecsoluction.cardapio.entidade.Usuario;
import com.tecsoluction.cardapio.framework.AbstractRestController;
import com.tecsoluction.cardapio.servico.UsuarioServicoImpl;

@RestController
@RequestMapping(value = "usuario")
public class UsuarioControllerRest extends AbstractRestController<Usuario> {
    
	
	@Autowired
    private final UsuarioServicoImpl userService;


    @Autowired
    public UsuarioControllerRest(UsuarioServicoImpl dao) {
        this.userService = dao;
    }
    
    
    @RequestMapping(value = "/usuarioSave", method =  RequestMethod.POST)
    public Usuario Post(@Valid @RequestBody Usuario pessoa)
    {
        return getservice().save(pessoa);
    }
    
    
    
    @RequestMapping(value = "/usuario/all", method = RequestMethod.GET)
    public List<Usuario> ListAllUsuario() {

        return getservice().findAll();

    }
    

    @Override
    protected UsuarioServicoImpl getservice() {
        // TODO Auto-generated method stub
        return userService;
    }


}
