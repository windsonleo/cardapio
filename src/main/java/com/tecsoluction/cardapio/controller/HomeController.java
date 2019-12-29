package com.tecsoluction.cardapio.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tecsoluction.cardapio.entidade.Categoria;
import com.tecsoluction.cardapio.entidade.Produto;
import com.tecsoluction.cardapio.entidade.Promocao;
import com.tecsoluction.cardapio.entidade.Usuario;
import com.tecsoluction.cardapio.servico.CategoriaServicoImpl;
import com.tecsoluction.cardapio.servico.ProdutoServicoImpl;
import com.tecsoluction.cardapio.servico.PromocaoServicoImpl;
import com.tecsoluction.cardapio.servico.UsuarioServicoImpl;
import com.tecsoluction.cardapio.util.GerenciadorCategorias;

	


@Controller
public class HomeController {
	
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    
	@Autowired
	private  UsuarioServicoImpl usuarioService = new UsuarioServicoImpl();
	
	@Autowired
	private  CategoriaServicoImpl CategoriaService = new CategoriaServicoImpl();
	
	@Autowired
	private PromocaoServicoImpl PromocaoService = new PromocaoServicoImpl();
	
	
	@Autowired
	private ProdutoServicoImpl ProdutoService = new ProdutoServicoImpl();
	
	
    
    private List<Usuario> usuarios = new ArrayList<Usuario>();
    
    private List<Categoria> categoriasall = new ArrayList<Categoria>();
    
    private List<Categoria> categoriaspai = new ArrayList<Categoria>();   
//    
//    private List<Categoria> categoriasfilho = new ArrayList<Categoria>();
    
    
    
    private List<Promocao> promocoes = new ArrayList<Promocao>();
    
    private GerenciadorCategorias gerenciacat;

    
    

    
    private Usuario usuariologado;

    @Autowired 
    private JavaMailSender mailSender;
  
    
    
    
    
//    private int qtdatividade;
// 
//    private int qtdusuarios;
//    
//    private int qtdpacientes;
//    
//    private int qtdpacientesaltas;
//    
//    private int qtdpacientesinternados;

	
	  @ModelAttribute
	    public void addAttributes(Model model) {
		  
		  
		  usuarios = usuarioService.findAll();

		  
		  
		  
		  
//		  qtdusuarios = usuarios.size();
		  

		  
		  
//		  	List<Produto> produtos = produtoService.findAll();
//
	         categoriaspai = CategoriaService.getCategoriaPai();
	         categoriasall=CategoriaService.findAll();
	         
	         promocoes = PromocaoService.findAll();
	         
	         gerenciacat = new GerenciadorCategorias(categoriasall);
	        
//
//	        model.addAttribute("atividades", atividades);
	        model.addAttribute("usuarios", usuarios);
	        model.addAttribute("categoriaListpai", categoriaspai);
	        model.addAttribute("promocoesList", promocoes);
	        model.addAttribute("gerenciacat", gerenciacat);
	        model.addAttribute("categoriaListall", categoriasall);
//	        model.addAttribute("eventos", eventos);
//	        model.addAttribute("patologias", patologias);
//	        model.addAttribute("qtdpacientesaltas", qtdpacientesaltas);
//	        model.addAttribute("qtdpacientesinternados", qtdpacientesinternados);

	        
	        
	        
	        
//	        model.addAttribute("produtos", produtos);


	    }
	
	
	
    private List<Categoria> PegarCategoriasFilhas(Categoria categoria) {
		
    	
    	
    	List<Categoria> categoriasfilhos = new ArrayList<Categoria>();
    	
    	
    	
    	for(Categoria categoriaaux : categoriasall){
    		
    		
    		if(categoriaaux.getCatpai().equals(categoria)){
    			
    			
    			categoriasfilhos.add(categoriaaux);
    			
    		}else {
    			
    			
    			
    			
    		}
    		
    		
    		
    	}


//    	categoriasfilhos = CategoriaService.getCategoriasFilho(categoria.getId());
//    	
//    	if((categoriasfilhos != null) && (!categoriasfilhos.isEmpty())){
//    		
//    		
//    		
//    		
//    		
//    	}else {
//    		
//    			
//    		categoriasfilhos = new ArrayList<Categoria>();
//    		
//    	}

    	
		return categoriasfilhos;
	}
    
    
    
    private List<Produto> PegarProdutoPorCategoria(Categoria categoria) {
		
    	List<Produto> produtosPorCategoria = null;


    	produtosPorCategoria = ProdutoService.getAllProdutoPorCategoria(categoria.getId());
    	
    	if((produtosPorCategoria != null) && (!produtosPorCategoria.isEmpty())){
    		
    		
    		
    		
    		
    	}else {
    		
    			
    		produtosPorCategoria = new ArrayList<Produto>();
    		
    	}

    	
		return produtosPorCategoria;
	}
    
    
    
    
//    
    
//    private int VerificarInternacoes(List<Paciente> pacientes2) {
//		
//    	
//    	pacientesInternados = new ArrayList<Paciente>();
//    	
//    	qtdpacientesinternados = 0;
//
//    	for (Paciente paciente : pacientes2) {
//    		
//    		
//    		if(paciente.isInternacao()){
//    			
//    			pacientesInternados.add(paciente);
//    			qtdpacientesinternados = qtdpacientesinternados + 1;
//    			
//    			
//    		}
//    		
//			
//		}
//    	
//    	
//    	
//    	
//		return qtdpacientesinternados;
//	}



	@RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView Home(Locale locale, Model model) {
       
    	
    	
    	logger.info("Welcome Home /home! The client locale is {}.", locale);

        ModelAndView home = new ModelAndView("/public/home");
        
        
//         Atividade atividade = new Atividade();
//         
//         status = StatusAtividade.values();
         
         
//         
//         home.addObject("atividade", atividade);
//         home.addObject("status", status);
//         home.addObject("statustratamento", statustratamento);
        
       
//        home.addObject("usuario", new Usuario());


        return home;
    }
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView Homem(Locale locale, Model model) {
       
    	logger.info("Welcome login /! The client locale is {}.", locale);

        ModelAndView home = new ModelAndView("/public/home");
        
//        home.addObject("usuario", new Usuario());


        return home;
    }
    
    
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView Login(Locale locale, Model model) {
      
    	logger.info("Welcome login! The client locale is {}.", locale);


        ModelAndView login = new ModelAndView("/public/login");
        
        
        
        login.addObject("usuario", usuariologado);


        return login;
    }
    
    
    
//    @RequestMapping(value = "/autenticar", method = RequestMethod.POST)
//    public ModelAndView Autenticar(Locale locale, Model model) {
//      
//    	logger.info("Welcome Autenticar! The client locale is {}.", locale);
//
//    	this.usuariologado = get
//    	
//    	
//       
//    	
//    	
//    	
//    	ModelAndView login = new ModelAndView("/public/login");
//        
//        
//        
//        login.addObject("usuario", usuariologado);
//
//
//        return login;
//    }
    
    
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public ModelAndView LoginAuth(Locale locale, Model model) {
//      
//    	logger.info("Welcome login autorização! The client locale is {}.", locale);
//    	
//    	
//    	  ModelAndView login = new ModelAndView("/private/home");
//    	  
//    	  usuariologado = usuarioService.findByEmail(usuariologado.getEmail());
//    	  
//    	 
//
//    	
//    	if(usuariologado != null){
//    		
//    		
//    		System.out.println("usuario loado ok");
//    		
//    		 login.addObject("usuario", usuariologado);
//    		
//    	}else {
//    		
//    		System.out.println("usuario loado nulo");
//    		
//    		login.addObject("usuario", new Usuario());
//    		
//    		
//    	}
//    	
//      
//        
//        
//
//
//        return login;
//    }
    
//    @RequestMapping(value = "/calendario", method = RequestMethod.GET)
//    public ModelAndView Calendario(Locale locale, Model model) {
//      
//    	logger.info("Welcome Calendario! The client locale is {}.", locale);
//
//
//        ModelAndView login = new ModelAndView("/private/calendario");
//        
////        login.addObject("usuario", new Usuario());
//
//
//        return login;
//    }
    
    
    @RequestMapping(value = "/erro", method = RequestMethod.GET)
    public ModelAndView Error(Locale locale, Model model) {
       
    	logger.info("Welcome Error ! The client locale is {}.", locale);

        ModelAndView home = new ModelAndView("/public/error/erro");
        
        home.addObject("usuario", new Usuario());


        return home;
    }
    
    
    
    @RequestMapping(value = "/accessdenied", method = RequestMethod.GET)
    public ModelAndView accessdenied(Locale locale, Model model) {
       
    	logger.info("Welcome accessdenied ! The client locale is {}.", locale);

        ModelAndView home = new ModelAndView("/public/accessdenied");


        return home;
    }
    
    
    @RequestMapping(value = "/esquecisenha", method = RequestMethod.GET)
    public ModelAndView Cadastros(Locale locale, Model model) {
       
    	logger.info("Welcome cadastros ! The client locale is {}.", locale);

        ModelAndView home = new ModelAndView("/public/esquecisenha");


        return home;
    }
    
    
    
    @RequestMapping(value = "/esquecisenhaenv", method = RequestMethod.POST)
    public ModelAndView EnviarSenha(Locale locale, Model model, HttpServletRequest request) {
       
    	boolean existe = false;
    	String senha = new String();
    	Usuario usu = null;
    	
    	
    	logger.info("Welcome enviar senha ! The client locale is {}.", locale);
    	
        ModelAndView home = new ModelAndView("/public/esquecisenha");

    	
    	String sucesso = "Email enviado com sucesso!";
    	String erro = "Erro ao enviar email.";
	
    	String email = request.getParameter("email");
    	
    	
    	if(email != null && email !=""){
    		
        	 usu = usuarioService.findByEmail(email);

    		
    	}else {
    		
    		
    	}
    	
    	
    	
    	if(usu.getSenha() != null){
    		
    		
    		senha = usu.getSenha();
    		existe=true;
    		
    	}else {
    		
    		 model.addAttribute("erro","usuario não existe");
    	return	home;
    		
    	
    	}
    	
    

    	if(existe){
    		
    		  SimpleMailMessage message = new SimpleMailMessage();

    	        message.setText("Olá Voce Recebeu este Email do Restaurante Sushi Senpai" +"Sua Senha é: " + senha +"\n" + "considere mudar sua senha");
    	        message.setTo(email);
    	       message.setFrom("Sushi Senpai - Cardapio");

    	        try {
    	            mailSender.send(message);
    	            
    	            model.addAttribute("sucesso",sucesso);
    	            model.addAttribute("usuario",usu);
//    	            home.addObject("sucesso", sucesso);
//    	            return home;
    	        } catch (Exception e) {
    	        	
    	        	 model.addAttribute("erro",erro);
    	            e.printStackTrace();
//    	            home.addObject("erro", erro + e);
//    	            return home;
    	        }
    		
    		
    	}else {
    		
   		 model.addAttribute("erro","usuario não existe");

    		
    		
    	}
      
        return home;
    }
    
    
    
    
    @RequestMapping(value = "/registro", method = RequestMethod.GET)
    public ModelAndView Registro(Locale locale, Model model) {
       
    	logger.info("Welcome registro ! The client locale is {}.", locale);

        ModelAndView home = new ModelAndView("/public/registro");


        return home;
    }
    
//    @RequestMapping(value = "/encontros", method = RequestMethod.GET)
//    public ModelAndView Encontros(Locale locale, Model model) {
//       
//    	logger.info("Welcome encontros ! The client locale is {}.", locale);
//
//        ModelAndView home = new ModelAndView("/public/encontros");
//
//
//        return home;
//    }
    
//    @RequestMapping(value = "/movimentacoes", method = RequestMethod.GET)
//    public ModelAndView Movimentacoes(Locale locale, Model model) {
//       
//    	logger.info("Welcome movimentacoes ! The client locale is {}.", locale);
//
//        ModelAndView home = new ModelAndView("/private/movimentacoes");
//
//
//        return home;
//    }
    
//    @RequestMapping(value = "/financeiro", method = RequestMethod.GET)
//    public ModelAndView ffinanceiro(Locale locale, Model model) {
//       
//    	logger.info("Welcome financeiro ! The client locale is {}.", locale);
//
//        ModelAndView home = new ModelAndView("/private/financeiro");
//
//
//        return home;
//    }
    
    
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView Checkout(Locale locale, Model model) {
       
    	logger.info("Welcome profile ! The client locale is {}.", locale);

        ModelAndView home = new ModelAndView("/private/profile");


        return home;
    }
    
    @RequestMapping(value = "/ajuda", method = RequestMethod.GET)
    public ModelAndView Ajuda(Locale locale, Model model) {
       
    	logger.info("Welcome ajuda ! The client locale is {}.", locale);

        ModelAndView home = new ModelAndView("/public/ajuda");


        return home;
    }
    
//    @RequestMapping(value = "/contato", method = RequestMethod.GET)
//    public ModelAndView Contato(Locale locale, Model model) {
//       
//    	logger.info("Welcome Contato ! The client locale is {}.", locale);
//
//        ModelAndView home = new ModelAndView("/public/contato");
//
//
//        return home;
//    }
    
    
//    @RequestMapping(value = "/entregas", method = RequestMethod.GET)
//    public ModelAndView Entrega(Locale locale, Model model) {
//       
//    	logger.info("Welcome Entrega ! The client locale is {}.", locale);
//
//        ModelAndView home = new ModelAndView("/public/entrega");
//
//
//        return home;
//    }
    
     
    
//    @RequestMapping(value = "/catalogo", method = RequestMethod.GET)
//    public ModelAndView Catalogo(Locale locale, Model model) {
//       
//    	logger.info("Welcome catalogo ! The client locale is {}.", locale);
//
//        ModelAndView home = new ModelAndView("/public/catalogo");
//
//
//        return home;
//    }
    
    
//    @RequestMapping(value = "/novos", method = RequestMethod.GET)
//    public ModelAndView novos(Locale locale, Model model) {
//       
//    	logger.info("Welcome novos! The client locale is {}.", locale);
//
//        ModelAndView home = new ModelAndView("/public/novos");
//
//
//        return home;
//    }
     
//    @RequestMapping(value = "/aempresa", method = RequestMethod.GET)
//    public ModelAndView AeMPRESA(Locale locale, Model model) {
//       
//    	logger.info("Welcome ampresa ! The client locale is {}.", locale);
//
//        ModelAndView home = new ModelAndView("/public/empresa");
//
//
//        return home;
//    }
     
//    @RequestMapping(value = "/indicacao", method = RequestMethod.GET)
//    public ModelAndView Indicacao(Locale locale, Model model) {
//       
//    	logger.info("Welcome indicacao ! The client locale is {}.", locale);
//
//        ModelAndView home = new ModelAndView("/public/indicacao");
//
//
//        return home;
//    }
     
//    @RequestMapping(value = "/ofertas", method = RequestMethod.GET)
//    public ModelAndView Ofertas(Locale locale, Model model) {
//       
//    	logger.info("Welcome ofertas ! The client locale is {}.", locale);
//
//        ModelAndView home = new ModelAndView("/public/ofertas");
//
//
//        return home;
//    }
    
//    @RequestMapping(value = "/rotas", method = RequestMethod.GET)
//    public ModelAndView Rotas(Locale locale, Model model) {
//       
//    	logger.info("Welcome ofertas ! The client locale is {}.", locale);
//
//        ModelAndView home = new ModelAndView("/private/rotas/rotas");
//
//
//        return home;
//    }
    
//    @RequestMapping(value = "/enviaremail", method = RequestMethod.POST)
//    public ModelAndView enviaremail(Locale locale, Model model) {
//       
//    	logger.info("Welcome enviaremail ! The client locale is {}.", locale);
//    	
//        ModelAndView home = new ModelAndView("/public/home");
//
//    	senderMailService = new SenderMailService();
//    	
//    	senderMailService.enviar();
//    	
//
//        return home;
//    }
   
//    @RequestMapping(value = "/enviaremail", method = RequestMethod.GET)
//    public ModelAndView enviaremail(Locale locale, Model model, HttpServletRequest request) {
//       
//    	
//    	logger.info("Welcome enviaremail ! The client locale is {}.", locale);
//    	
//    	String sucesso = "Email enviado com sucesso!";
//    	
//    	String erro = "Erro ao enviar email.";
//    	
//    	UUID idf = UUID.fromString("2700325f-cc1b-428b-b617-0a3a5f57a246");
//    	
////    	Empresa empresa = empresaServico.findOne(idf);
//    
//    	
//    	String email = request.getParameter("email");
//    	
//
//        ModelAndView home = new ModelAndView("/public/home");
//       
//
//        SimpleMailMessage message = new SimpleMailMessage();
//
//        message.setText("Olá Voce Recebeu este Email da Tecshop");
//        message.setTo(email);
////        message.setFrom(empresa.getEmail());
//
//        try {
//            mailSender.send(message);
//            home.addObject("sucesso", sucesso);
//            return home;
//        } catch (Exception e) {
//            e.printStackTrace();
//            home.addObject("erro", erro + e);
//            return home;
//        }
//        
//    }  

}
