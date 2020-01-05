package com.tecsoluction.cardapio;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.tecsoluction.cardapio.entidade.Categoria;
import com.tecsoluction.cardapio.entidade.Configuracao;
import com.tecsoluction.cardapio.entidade.Usuario;
import com.tecsoluction.cardapio.exception.CustomGenericException;
import com.tecsoluction.cardapio.servico.CategoriaServicoImpl;
import com.tecsoluction.cardapio.servico.ConfiguracaoServicoImpl;
import com.tecsoluction.cardapio.servico.UsuarioServicoImpl;





/**
 * Created by clebr on 01/09/2016.
 */
@ControllerAdvice
public class ContextoAplicacao {
	
	 @Autowired
	 private
	 UsuarioServicoImpl userService;
	 
	 @Autowired
	    private CategoriaServicoImpl categoriaService;
	 
	 private List<Categoria> categoriaLista ;
	 
	 private Configuracao configuracaoAtual; 
	 
	 
	 private List<Configuracao> configuracaoLista ;
	 
	 
	 @Autowired
	  private ConfiguracaoServicoImpl ConfiguracaoService;
	 
	 
	 private Usuario usuario;
	 
	 private Date hoje;
	 	
	 private boolean estaaberto=true;
	 	

	// @Autowired
	// public ContextoAplicacao(UsuarioServicoImpl sevice) {
	//
	// this.userservice = sevice;
	// }

	@Autowired
	public ContextoAplicacao() {

		// this.userservice = sevice;
	}

	@ModelAttribute
	public void addAttributes(Model model) {

		// Usuario usuarioAtt = dao.PegarPorId(100L);
		//
		// model.addAttribute("usuarioAtt", usuarioAtt);
		
        //inicio de ususario logado
		
		List<Categoria> categorias = new ArrayList<Categoria>();
		
		categorias = categoriaService.findAll();
		
		categoriaLista = CategoriasComProduto(categorias);
		Long qtdRegistroConfiuracao = ConfiguracaoService.count();
		
		if(qtdRegistroConfiuracao != null && qtdRegistroConfiuracao > 0 ){
			
			
			configuracaoLista = ConfiguracaoService.PegarConfiguracaoAtualLista();
			
			configuracaoAtual = configuracaoLista.get(0);
			
		}else {
			
			
			configuracaoAtual = ConfiguracaoService.PegarConfiguracaoAtual();
			
		}
		
		if(configuracaoAtual == null){
			
			Date dati = new Date();
			dati.setHours(18);
			dati.setMinutes(00);
			
			Date datf = new Date();
			datf.setHours(23);
			datf.setMinutes(00);
			
			configuracaoAtual = new Configuracao();
			
			configuracaoAtual.setAtivo(true);
			configuracaoAtual.setBanner1("banner.png");
			configuracaoAtual.setBanner2("banner.png");
			configuracaoAtual.setBanner3("banner.png");
			configuracaoAtual.setCorcard("blue");
			configuracaoAtual.setCormenu("blue");
			configuracaoAtual.setCortopo("blue");
			configuracaoAtual.setLogo("logo.png");
			configuracaoAtual.setNomeempresa("Teste");
			configuracaoAtual.setUrlface("https://www.facebook.com");
			configuracaoAtual.setUrlgmail("https://www.gmail.com");
			configuracaoAtual.setUrlinsta("https://www.instagram.com");
			configuracaoAtual.setHoraabertura(dati);
			configuracaoAtual.setHorafechamento(datf);

			
			
			
		}
		
		
		
        
         usuario = new Usuario();
        usuario.setEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        usuario = userService.findByEmail(usuario.getEmail());
        
        //verifica se há usuario cadastrado
        if(usuario == null) {
        	
        model.addAttribute("mensagem", "Carregado Usuario Cliente");
        
        usuario = new Usuario();
        UUID idf = UUID.fromString("4b71a569-c0bd-41a2-bffe-35e39e1a875a");
        usuario = userService.findOne(idf);
        	
        	
        } else {
    	
	        model.addAttribute("mensagem", "Bem-Vindo " + usuario.getEmail());

        
        }
        
         hoje = new Date();
         
         estaaberto = VerificaHorarioFechamento(configuracaoAtual);
        

        
        model.addAttribute("usuarioAtt", usuario);
        model.addAttribute("categoriaLista", categoriaLista);
        model.addAttribute("configuracaoAtual", configuracaoAtual);
        model.addAttribute("hoje", hoje);
        model.addAttribute("estaaberto", estaaberto);
        
        
	}

	@ExceptionHandler(CustomGenericException.class)
	public ModelAndView handleCustomException(CustomGenericException ex) {

		ModelAndView model = new ModelAndView("/public/error/erro");
		model.addObject("errCode", ex.getErrCode());
		model.addObject("errMsg", ex.getErrMsg());
        model.addObject("usuarioAtt", usuario);
        model.addObject("categoriaLista", categoriaLista);
        model.addObject("configuracaoAtual", configuracaoAtual);
        model.addObject("hoje", hoje);

		return model;

	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(Exception ex) {

		ModelAndView model = new ModelAndView("/public/error/erro");
		model.addObject("errMsg", ex.toString());
        model.addObject("usuarioAtt", usuario);
        model.addObject("categoriaLista", categoriaLista);
        model.addObject("configuracaoAtual", configuracaoAtual);
        model.addObject("hoje", hoje);

		return model;

	}
	
	
	public List<Categoria> CategoriasComProduto(List<Categoria> cats){
		
		List<Categoria> validas = new ArrayList<Categoria>();
		
		String insumo = "INSUMOS";
		
		for(Categoria cat : cats) {
			
			
			if((cat.getProdutos().size() > 0) && (!cat.getNome().equals(insumo))){
				
				
				validas.add(cat);
				
				
			}else {
				
				
				
				
			}
			
		}
		
		
		return validas;
		
	}
	
	public boolean VerificaHorarioFechamento(Configuracao configuracao) {
      
	boolean	aberto = true;
		
		SimpleDateFormat sdfConvert = new SimpleDateFormat("HH:mm:ss");
        Date horaabertura = configuracao.getHoraabertura();
        Date horafechamento = configuracao.getHorafechamento();
        Date horaagora = new Date();
        
        Date datafor = null;
        
        
       try {
    	   datafor = sdfConvert.parse(sdfConvert.format(horaagora));
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      

        if ((datafor.getTime() > horaabertura.getTime()) && (datafor.getTime() < horafechamento.getTime())){
           
        	aberto = true;
        	System.out.print("Aberto!");
       
        
        }else if ((datafor.getTime() > horaabertura.getTime()) && (datafor.getTime() > horafechamento.getTime())){
            
        	System.out.print("Fechado!");
        	System.out.print("hora agora!" + datafor.getTime() );
        	System.out.print("hora abre!" + horaabertura.getTime());
        	System.out.print("hora fecha!  " + horafechamento.getTime());
        	
        	aberto = false;
        }else {
        	
        	
        	aberto = true;
        	
        }
	
	
	return aberto;

}
	
}
