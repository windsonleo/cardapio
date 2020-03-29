package com.tecsoluction.cardapio.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tecsoluction.cardapio.entidade.Premio;
import com.tecsoluction.cardapio.entidade.Produto;
import com.tecsoluction.cardapio.entidade.Usuario;
import com.tecsoluction.cardapio.framework.AbstractController;
import com.tecsoluction.cardapio.servico.PremioServicoImpl;
import com.tecsoluction.cardapio.servico.ProdutoServicoImpl;
import com.tecsoluction.cardapio.servico.SorteioServicoImpl;
import com.tecsoluction.cardapio.servico.UsuarioServicoImpl;






@Controller
@RequestMapping("sorteio/")
public class SorteioController extends AbstractController<com.tecsoluction.cardapio.entidade.Sorteio> {
	


	private static final Logger logger = LoggerFactory.getLogger(SorteioController.class);
    
	@Autowired
	private  UsuarioServicoImpl usuarioService;
	
	@Autowired
	private ProdutoServicoImpl produtoservice;
	
	@Autowired
	private PremioServicoImpl premioservice;
	
//	@Autowired
//	private ClienteServicoImpl clienteservice;
	
	
	@Autowired
	private SorteioServicoImpl sorteioservice;
	
//	 private Cliente cliente;
	
	
	private List<Produto> produtos;
	
	private List<Premio> premios;
	
//	private List<Cliente> clientes;
	
	private List<Usuario> usuarios;
	
	 private String filename="vazio.jpg";
	 
	 private com.tecsoluction.cardapio.entidade.Sorteio sorteio;
	 
	 private Premio premio;
	
	
	
    public SorteioController(SorteioServicoImpl soteio, PremioServicoImpl prem,ProdutoServicoImpl prod,UsuarioServicoImpl usu) {
		super("sorteio");
		// TODO Auto-generated constructor stub
		this.sorteioservice = soteio;
		this.premioservice = prem;
//		this.clienteservice = cli;
		this.produtoservice = prod;
		this.usuarioService =usu;
	}

	
	  @ModelAttribute
	    public void addAttributes(Model model) {
		  
		  
		 		  
		  
		  produtos = produtoservice.findAll();
		  
		  premios = premioservice.findAll();
		  
//		  clientes = clienteservice.findAll();
		  
		  usuarios = usuarioService.findAll();

	             
//		  cliente = new Cliente();
		  
		  sorteio = new com.tecsoluction.cardapio.entidade.Sorteio();
		  
		  premio = new Premio();
		  premio.setFoto("fly.jpg");
		  premio.setName("??????????");
		  premio.setDesconto(new BigDecimal("0.00"));
	        
	        
	        model.addAttribute("produtos", produtos);
	        model.addAttribute("premios", premios);
//	        model.addAttribute("clientes", clientes);
	        model.addAttribute("usuarios", usuarios);
	        model.addAttribute("filename", filename);
//	        model.addAttribute("cliente", cliente);
	        model.addAttribute("sorteio", sorteio);
	        model.addAttribute("premio", premio);
	        
	        


	    }
	
	    @RequestMapping(value = "/sorteio", method = RequestMethod.GET)
	    public ModelAndView Sorteio(Locale locale, Model model) {
	      
	    	logger.info("Welcome Sorteio! The client locale is {}.", locale);


	        ModelAndView login = new ModelAndView("/private/sorteio/sorteio");
	        
//	        login.addObject("usuario", new Usuario());


	        return login;
	    }
	    
	    
	    @RequestMapping(value = "/sorteiar", method = RequestMethod.GET)
	    public ModelAndView Sorteiar(Locale locale, Model model) {
	      
	    	logger.info("Welcome Sorteio ar! The client locale is {}.", locale);


	        ModelAndView login = new ModelAndView("/private/sorteio/sorteiar");
	        
//	        login.addObject("usuario", new Usuario());


	        return login;
	    }
	    
	    
	    
	    
	    
	    @RequestMapping(value = "/GerarMultiplos", method = RequestMethod.GET)
	    public ModelAndView GerarMultiplos(Locale locale, Model model) {
	      
	    	logger.info("Welcome GerarMultiplos! The client locale is {}.", locale);
	    	
	    	
	    	
	    	for (int i = 0; i < 10; i++) {
	    		
	    		com.tecsoluction.cardapio.entidade.Sorteio sorteio = new com.tecsoluction.cardapio.entidade.Sorteio();
	    		sorteio.setAtivo(true);
	    		sorteio.setUsuario(null);
	    		sorteio.setPremio(null);
	    		sorteio.setData(new Date());
	    		sorteio.setResgatado(false);
	    		sorteio.setValido(true);
	    		
	    		getservice().save(sorteio);
	    		
	    		
				
			}


//	        ModelAndView login = new ModelAndView("/private/sorteio/sorteiar");
	        
//	        login.addObject("usuario", new Usuario());


	        return new ModelAndView("forward:/sorteio/movimentacao");
	    }
	    
	    
	    @RequestMapping(value = "/validarSorteio", method = RequestMethod.POST)
	    public ModelAndView validarSorteio(HttpServletRequest request,Locale locale,@ModelAttribute com.tecsoluction.cardapio.entidade.Sorteio model) {
	      
	    	logger.info("Welcome validarSorteio! The client locale is {}.", locale);
	    	
	    	
	    	//confirmar id do sorteio (ativo,valido,resgatado nao)
	    	
	    	UUID id = UUID.fromString(request.getParameter("id"));
	    	
	    	
	    	String cpf = request.getParameter("cpf");
	    	
	    	com.tecsoluction.cardapio.entidade.Sorteio sorteio = getservice().findOne(id);
	    	
	    	sorteio.setResgatado(true);
	    	
	    	
	    	
	    	
//	    	
//	    	Cliente cliente = clienteservice.findByCpf(cpf);
	    	
//	    	sorteio.setCliente(cliente);
	    	
//	    	cliente.addSorteios(sorteio);
	    	
	    	getservice().edit(sorteio);
	    	
//	    	clienteservice.edit(cliente);
	    	
	    	
//	    	System.out.println("id sorteio winds:" + id.toString());
	    	
//	    	System.out.println("cpf cliente windson:" + cpf);
	    	
	    	//confirmar cliente pelo cpf
	    	
	    	


	    	
	    	ModelAndView login = new ModelAndView("/private/sorteio/sorteiar");
	    	
	    	
	    	login.addObject("sorteio", sorteio);
	        
//	        login.addObject("usuario", new Usuario());


	    	return login;
	    }
	    
	    
	    @RequestMapping(value = "/salvarSorteio", method = RequestMethod.POST)
	    public ModelAndView salvarSorteio(HttpServletRequest request,Locale locale,@ModelAttribute com.tecsoluction.cardapio.entidade.Sorteio model) {
	      
	    	logger.info("Welcome salvarSorteio! The client locale is {}.", locale);
	    	
	    	
	    	//confirmar id do sorteio (ativo,valido,resgatado nao)
	    	
	    	UUID id = UUID.fromString(request.getParameter("id"));
	    	
	    	UUID idprem = UUID.fromString(request.getParameter("premio"));
	    	
	    	Premio premio = premioservice.findOne(idprem);
	    	
	    	
	    	
	    	
//	    	String cpf = request.getParameter("cpf");
	    	
	    	com.tecsoluction.cardapio.entidade.Sorteio sorteio = getservice().findOne(id);
	    	
//	    	sorteio.setResgatado(true);
	    	
	    	
	    	
	    	
//	    	
//	    	Cliente cliente = clienteservice.findByCpf(cpf);
	    	
//	    	sorteio.setCliente(cliente);
	    	
	    	sorteio.setPremio(premio);
	    	
//	    	cliente.addSorteios(sorteio);
	    	
	    	getservice().edit(sorteio);
	    	
//	    	premio.getSorteios().add(sorteio);
//	    	
//	    	premioservice.edit(premio);
	    	
//	    	clienteservice.edit(cliente);
	    	
	    	
//	    	System.out.println("id sorteio winds:" + id.toString());
	    	
//	    	System.out.println("cpf cliente windson:" + cpf);
	    	
	    	//confirmar cliente pelo cpf
	    	
	    	


	    	
	    	ModelAndView login = new ModelAndView("/private/sorteio/sorteiar");
	    	
	    	
	    	login.addObject("sorteio", sorteio);
	        
//	        login.addObject("usuario", new Usuario());


	    	return login;
	    }


		@Override
		protected SorteioServicoImpl getservice() {
			// TODO Auto-generated method stub
			return sorteioservice;
		}
	
   



}