package com.tecsoluction.cardapio.util;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.tecsoluction.cardapio.entidade.Usuario;
import com.tecsoluction.cardapio.servico.UsuarioServicoImpl;

public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {
	
	@Autowired
	private  UsuarioServicoImpl usuarioService;
	
	private Usuario  usuario ;

    public CustomLogoutSuccessHandler() {
        super();
    }

    // API

    @Override
    public void onLogoutSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException, ServletException {
        final String refererUrl = request.getHeader("Referer");
        System.out.println(refererUrl);
        
        usuario = new Usuario();
//        usuario.setEmail(SecurityContextHolder.getContext().getAuthentication().getName());
//        usuario = usuarioService.findByEmail(usuario.getEmail());
        
        
        if((authentication!= null)) {
        	
        	
        	if(authentication.isAuthenticated()){
        		
           	 System.out.println("usuario desconectado" + authentication.getName());

        		
        		 usuario.setEmail(authentication.getName());
     	        usuario = usuarioService.findByEmail(usuario.getEmail());
     	        
     	        
     	        usuario.setOnline(false);
        		
        		
        	}else{
        		
        		
        		
        	}

        	
        	
        	       
        	        
//        	        usuarioService.edit(usuario);
        	
        	
        	
        } else {
        	
        	 System.out.println("usuario padrão desconectado");
        	 
        	 UUID idf = UUID.fromString("4b71a569-c0bd-41a2-bffe-35e39e1a875a");
             usuario = usuarioService.findOne(idf);
//             usuario.setDataultimoAcesso(new Date());	
             usuario.setOnline(false);
        	
        }
        
        usuarioService.edit(usuario);
        
      //	 System.out.println("usuario desconectado apos" + authentication.getName());

        
        response.sendRedirect(refererUrl);
        
       

        super.onLogoutSuccess(request, response, authentication);
    }
}
