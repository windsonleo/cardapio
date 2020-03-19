package com.tecsoluction.cardapio.controller;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.tecsoluction.cardapio.CarrinhoBean;
import com.tecsoluction.cardapio.entidade.Autenticador;
import com.tecsoluction.cardapio.entidade.Carrinho;
import com.tecsoluction.cardapio.entidade.Categoria;
import com.tecsoluction.cardapio.entidade.Produto;
import com.tecsoluction.cardapio.entidade.Promocao;
import com.tecsoluction.cardapio.entidade.Role;
import com.tecsoluction.cardapio.entidade.Usuario;
import com.tecsoluction.cardapio.servico.CategoriaServicoImpl;
import com.tecsoluction.cardapio.servico.ProdutoServicoImpl;
import com.tecsoluction.cardapio.servico.PromocaoServicoImpl;
import com.tecsoluction.cardapio.servico.RoleServicoImpl;
import com.tecsoluction.cardapio.servico.UsuarioServicoImpl;
import com.tecsoluction.cardapio.util.GerenciadorCategorias;
	


@Controller
//@RequestMapping(value = "cardapio/")
public class HomeController {
	
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    
	@Autowired
	private  UsuarioServicoImpl usuarioService;
	
	@Autowired
	private  CategoriaServicoImpl CategoriaService;
	
	@Autowired
	private PromocaoServicoImpl PromocaoService;
	
	
	@Autowired
	private ProdutoServicoImpl ProdutoService;
	
	
	@Autowired
	private ServletContext context;
	
	
	@Autowired
	private RoleServicoImpl RoleService;
	
    
    private List<Usuario> usuarios = new ArrayList<Usuario>();
    
    private List<Categoria> categoriasall = new ArrayList<Categoria>();
    
    private List<Categoria> categoriaspai = new ArrayList<Categoria>();   
//    
//    private List<Categoria> categoriasfilho = new ArrayList<Categoria>();
    
    private List<Role> Rolesall = new ArrayList<Role>();

    
    private List<Promocao> promocoes = new ArrayList<Promocao>();
    
    private List<Produto> destaqueprodLista = new ArrayList<Produto>();

    private List<Produto> indicaprodLista = new ArrayList<Produto>();
    
    private GerenciadorCategorias gerenciacat;
    
    private Usuario usuario ;

	private String filename="avatar_usu.jpg";
	
	 private Carrinho carrinho;
	 
	 private int indexUsuario = 0;
	 
 	private Pageable primeiroResultado = new PageRequest(0, 5);

	 
	 
	 @Autowired
	 private CarrinhoBean carrinhobean;

//    @Autowired 
//    private JavaMailSender mailSender;
  
    
    
    
    
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
		  
//		  if(!carrinho.getItens().isEmpty()){
//				
//				
//				
//			}else {
//				
//				UUID uuid = UUID.randomUUID();
//				carrinho.setId(uuid);
//				
//			}

		  
//		  
//		  if(usuario.getId()!= null){
			  
			  usuario = new Usuario(); 
			  
//		  }else {
//			  
//			  
//			  
//		  }
		 
		  
		  
//		  qtdusuarios = usuarios.size();
		  
		        if(carrinhobean.getCarrinho() == null){
		        	carrinho = new Carrinho();
		        	UUID uuid = UUID.randomUUID();
		 			carrinho.setId(uuid);
		 			carrinhobean.SetarCarrinhoSessao(carrinho);
		          //  model.addAttribute("carrinho", carrinho); 
		            }else {
		            	
//		            	UUID uuid = UUID.randomUUID();
//			 			carrinho.setId(uuid);
		            	
		            }
		        
		     //   carrinhobean.SetarCarrinhoSessao(carrinho);
		  
		  
//		  	List<Produto> produtos = produtoService.findAll();
//
	         categoriaspai = CategoriaService.getCategoriaPai();
	         categoriasall=CategoriaService.findAll();
	         
	         promocoes = PromocaoService.findAll();
	         
	         gerenciacat = new GerenciadorCategorias(categoriasall);
	         
	         Usuario us =   PegarIndicacao();
	         
	         carrinhobean.SetarProdutosIndicaSessao();
	         
	         destaqueprodLista = ProdutoService.ListaProdutoMaiorAvaliacao(primeiroResultado);
	         
	         indicaprodLista = carrinhobean.getProdutosIndica();
	        
//
//	        model.addAttribute("atividades", atividades);
	        model.addAttribute("usuarios", usuarios);
	        model.addAttribute("categoriaListpai", categoriaspai);
	        model.addAttribute("promocoesList", promocoes);
	        model.addAttribute("gerenciacat", gerenciacat);
	        model.addAttribute("categoriaListall", categoriasall);
	        model.addAttribute("usuario", usuario);
	        model.addAttribute("filename", filename);
	        model.addAttribute("carrinho", carrinhobean.getCarrinho());
		   	model.addAttribute("totalitens", carrinhobean.TotalItens());
		   	
		   	model.addAttribute("indicaprodLista", indicaprodLista);


	        
	        model.addAttribute("usuarioIndica", carrinhobean.PegarUsuarioIndicaSessao());  
//	        
	        model.addAttribute("produtoIndica", carrinhobean.PegarProdutoIndicaSessao());  

	       
	        
	        

	        model.addAttribute("destaqueprodLista", destaqueprodLista);
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
       
    	logger.info("Welcome HOMEE /! The client locale is {}.", locale);

        ModelAndView home = new ModelAndView("/public/home");
        
//        home.addObject("usuario", new Usuario());


        return home;
    }
    
    
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView Login(Locale locale, Model model) {
      
    	logger.info("Welcome login! The client locale is {}.", locale);


        ModelAndView login = new ModelAndView("/public/login");
        
        
        
        login.addObject("usuario", usuario);


        return login;
    }
    
    @RequestMapping(value = "/card", method = RequestMethod.GET)
    public ModelAndView card(Locale locale, Model model) {
      
    	logger.info("Welcome card! The client locale is {}.", locale);


        ModelAndView login = new ModelAndView("/public/card");
        
        
//        
//        login.addObject("usuario", usuario);


        return login;
    }
    
    
    @RequestMapping(value = "/card2", method = RequestMethod.GET)
    public ModelAndView card2(Locale locale, Model model) {
      
    	logger.info("Welcome card2! The client locale is {}.", locale);


        ModelAndView login = new ModelAndView("/public/card2");
        
        
//        
//        login.addObject("usuario", usuario);


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
       
    	logger.info("Welcome Erro ! The client locale is {}.", locale);

        ModelAndView home = new ModelAndView("/public/error/erro");
        
        home.addObject("usuario",usuario );


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
       
    	logger.info("Welcome esquecisenha ! The client locale is {}.", locale);

        ModelAndView home = new ModelAndView("/public/esquecisenha");

        home.addObject("usuario",usuario);
        home.addObject("acao","enviar");
//		 model.addAttribute("filename",filename);



        return home;
    }
    
    
    @RequestMapping(value = "/logout",method = RequestMethod.GET )
    public ModelAndView logout(Mode model, HttpServletRequest request,HttpServletResponse response,HttpSession session){
      
		logger.info("Welcome LOGOUT  HOME Controller !" + model);

    	
  //  	request.getSession(true).invalidate();
//        ModelAndView home = new ModelAndView("/public/home");
        
//       Object obj = context.getAttribute("usuarioAtt");
//       
//       System.out.println("objeeto usuario" + obj.toString());
//       
//       logger.debug("objeeto usuario : " + obj.toString());
      
       usuario = new Usuario();
       usuario.setEmail(SecurityContextHolder.getContext().getAuthentication().getName());
       usuario = usuarioService.findByEmail(usuario.getEmail());
       
       usuario.setOnline(false);
       
       usuarioService.edit(usuario);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null)
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        
        System.out.println("logout user page shown--------------------");
        
        ModelAndView home = new ModelAndView("/public/home");
        
        session.invalidate();

       
        return home;       
   }
    
    
    @RequestMapping(value = "/logoutsucess",method = RequestMethod.GET )
    public ModelAndView logoeut(Mode model, HttpServletRequest request,HttpServletResponse response){
      //  request.getSession(true).invalidate();
        ModelAndView home = new ModelAndView("/public/home");
        
//       Object obj = context.getAttribute("usuarioAtt");
//       
//       System.out.println("objeeto usuario" + obj.toString());
       
       logger.debug("objeeto usuario : ");
      
//       usuario = new Usuario();
//       usuario.setEmail(SecurityContextHolder.getContext().getAuthentication().getName());
//       usuario = usuarioService.findByEmail(usuario.getEmail());
//       
//       usuario.setOnline(false);
//       
//       usuarioService.edit(usuario);
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null)
//            new SecurityContextLogoutHandler().logout(request, response, authentication);
        
        System.out.println("logout user page shown--------------------");
       
        return home;       
   }
    
    
    
    @RequestMapping(value = "/esquecisenhaenv", method = RequestMethod.POST)
    public ModelAndView EnviarSenha(Locale locale, Model model, HttpServletRequest request,HttpSession session ,@ModelAttribute Usuario usuario) {
       
    	boolean existe = false;
    	String senha = new String("");
    	Usuario usuariologado = usuario;
    	Usuario usuar = null;
    	
    	
    	String html=null;
    	
    	
    //    String path = session.getServletContext().getRealPath("/WEB-INF/classes/static/img/");
    	
    	String path = context.getRealPath("/WEB-INF/classes/static/img/");
    	
    	
    	logger.info("Welcome enviar senha path img email ! The client locale is {}.", path);

        
        String nomearquivo = "logologinnn.png";
        
        String caminho = path + nomearquivo;

    	
    	
    	logger.info("Welcome enviar senha ! The client locale is {}.", locale);
    	
        ModelAndView home = new ModelAndView("/public/esquecisenha");

    	
    	String sucesso = "Email enviado com sucesso!";
    	String erro = "Erro ao enviar email.";
	
    	String email = request.getParameter("email");
    	
    	
    	if(email != null && email !=""){
    		
    		usuar = usuarioService.findByEmail(email);
        	 
        	 html = CriarBody(usuar);

    		
    	}else {
    		
    		usuario.setEmail(email);
   		 model.addAttribute("erro",erro);
   		 model.addAttribute("usuario",usuario);
   		 model.addAttribute("acao","enviar");
   		 
   		 return	home;
    	}
    	
    	
    	
    	if((usuar.getSenha() != null) && (usuar.getSenha()!= "")){
    		
    		
    		senha = usuar.getSenha();
    		existe=true;
    		
    	}else {
    		
    		 model.addAttribute("erro",erro);
    		 model.addAttribute("usuario",usuar);
    		 model.addAttribute("acao","enviar");
    		 
    	return	home;
    		
    	
    	}
    	
    	

    	if(existe) {
    	
    		Properties props = new Properties();
    	   props.setProperty("mail.smtp.user","fabriciopiercing@gmail.com" );   //setei o login
    	   props.setProperty("mail.smtp.password", "senpai2020"); // e a senha
    	   props.setProperty("mail.transport.protocol", "smtp");
    	   props.put("mail.smtp.starttls.enable","true"); //não sei ao certo para que serve, mas tive que colocar...
    	   props.setProperty("mail.smtp.auth", "true");  //setei a autenticação  
//    	   props.setProperty("mail.smtp.starttls.required","true");
    	   props.setProperty( "mail.smtp.quitwait", "false");
    	   props.setProperty("mail.smtp.host", "smtp.googlemail.com");
    	   String user = props.getProperty("mail.smtp.user");
    	   String passwordd = props.getProperty("mail.smtp.password");
    	   props.put("mail.smtp.port","587");
    	 //  props.put("mail.smtp.ssl.trust", "smtp.googlemail.com");
    	   
    	   props.put("mail.smtp.ssl.enable", "false");
    	   
    	 
    	   
    	   Autenticador auth = null;
    		
    		
    		auth = new Autenticador (user, passwordd);
    	   
    	   

    	   // Get the Session object.
    		Session sessionmail = Session.getInstance(props, auth);
    		sessionmail.setDebug(true);
    		
    		 MimeBodyPart messageBodyPart = new MimeBodyPart();
    		
    		 try {
    				messageBodyPart.setContent(html, "text/html;charset=utf-8");
    			} catch (MessagingException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
    		    


    		    Multipart multipart = new MimeMultipart();
    		    try {
    				multipart.addBodyPart(messageBodyPart);
    			} catch (MessagingException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
    		    
    	
    		    String logoid = "sushilogo";
    			//    
    			    MimeBodyPart imagePart = new MimeBodyPart();
    			    try {
						imagePart.setHeader("Content-ID", "<" + logoid + ">");
					} catch (MessagingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
    			    try {
						imagePart.setDisposition(MimeBodyPart.INLINE);
					} catch (MessagingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
    			    
    			    
    			     
//    			    String imageFilePath = arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(), "") + "img3.png";
    			    		
    			    System.out.println("img: "+ caminho);
    			    
    		
    			        try {
							imagePart.attachFile(caminho);
						} catch (IOException | MessagingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}


    			   
    			    
    			    
    			    
    			    
    				
    				 try {
						multipart.addBodyPart(imagePart);
					} catch (MessagingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
    			    

    		
    		
    		 try {
    		
    		 Message message = new MimeMessage(sessionmail);

  	 	   // Set From: header field of the header.
  		   message.setFrom(new InternetAddress("fabriciopiercing@gmail.com"));

  		   // Set To: header field of the header.
  		   message.addRecipients(Message.RecipientType.TO,
  	            InternetAddress.parse(usuar.getEmail()));
  		   
//  		   message.addRecipients(Message.RecipientType.BCC,
//  		            InternetAddress.parse(jtxtusuario.getText().trim()));
//  		   
  		   message.addRecipients(Message.RecipientType.BCC,
  		            InternetAddress.parse("windson.m.bezerra@gmail.com"));

  		   // Set Subject: header field
  		   message.setSubject("Recuperação de Senha ");

  		   // Send the actual HTML message, as big as you like
//  		   message.setContent();
  		 message.setContent(multipart);
  		   
//  		   message.setText("recup senha " + usu.getSenha());
  		   message.saveChanges();

  		   // Send message
  		   Transport.send(message);
  		  
  		   	
  		   System.out.println("Sent message successfully....");

  		 model.addAttribute("usuario",usuar);
 		 model.addAttribute("sucesso",sucesso);
 		 model.addAttribute("acao","enviado");
// 		 model.addAttribute("filename",filename);


  	    } catch (MessagingException e) {
  		   e.printStackTrace();		
  		   

  		   
  		   throw new RuntimeException(e);
  		   
  		   
  	    }
    		
    		
    	}else {
    		
   		 model.addAttribute("erro","usuario não existe");
   		 model.addAttribute("usuario",usuario);
//   		 model.addAttribute("filename",filename);	
    		
    	}
      
        return home;
    }
    
    public String CriarBody(Usuario usuario){    
    	
    	
    StringBuilder stringbuilder = new StringBuilder();
	
	String src2 = "cid:sushilogo";
	
	String html =
			
			
			"<p align=\"middle\" ><img src= \"" + src2 + "\" alt=\"governo_desc\" width=\"150px;\" height=\"100px;\" align=\"middle\" />"
					+ "</p>"+
			
			
			"<h2 align=\"middle\" >Recuperação de Senha :</h2>"+
			
			
			"<p> <b>" + usuario.getNome() + "</b> Conforme Solicitado sua senha é <b>" + usuario.getSenha()+ "</b> violação(s) "
			+ "Caso não tenha Solicitado sua Senha no Aplicativo de Cardapio do restaurante<b>Sushi Senpai</b>"
			+ "Por favor entrar em contato conosco. </p>"+
			
			 



" <tbody>";
 
	stringbuilder.append(html);

String html2 = "</tbody> <p align=\"middle\" > <h3 align=\"middle\" > <i>Recife, " + FormatadorData(new Date() )+ "</i> </h3> </p>" +

//stringbuilder.append("<h1 align=\"right\" > <i>Recife, " + new Date() +"</i></h1>");
"<p align=\"middle\"><img src= \"" + src2 + "\" alt=\"governo_desc\" width=\"150px;\" height=\"100px;\" align=\"middle\" /> </p>" +
"<p align=\"middle\"><b>Sushi Senpai</b> </p>"  ;
			
	
	
stringbuilder.append(html2);


		
			
			
	
	
	return stringbuilder.toString();
}
    
    
private String FormatadorData(Date data){
		
		String dataformatadastring = null;
		
//		SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyyy HH:mm");    
//		Date dat = null;
//		
//		
//		try {
//			dat = fmt.parse(data);
//		//	 trace.setText("ok format data");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			 trace.setText("nok format data");
//			// detentoErro.add(arg0)
//		}
		
		SimpleDateFormat fmt2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");    
		//Date dat2 = null;
		
		//dat2 = fmt2.format(dat);
		
		dataformatadastring=fmt2.format(data);
		
//		dataformatadastring.replace("\\", "-");
//		dataformatadastring.replace(" ", "-");
//		dataformatadastring.replace(":", "-");
		
		System.out.println("data formatda " + dataformatadastring);
		
		
		return dataformatadastring;
	}
    
    
    
    
    @RequestMapping(value = "/registro", method = RequestMethod.GET)
    public ModelAndView Registro(Locale locale, Model model) {
       
    	logger.info("Welcome registro ! The client locale is {}.", locale);

        ModelAndView home = new ModelAndView("/public/registro");
        
       Usuario usuario = new Usuario();
       
       usuario.setFoto(filename);
       
       
        
        Rolesall = RoleService.findAll();
        
        model.addAttribute("roles",Rolesall);
        model.addAttribute("usuario",usuario);
        model.addAttribute("filename",filename);
		 model.addAttribute("acao","add");


        return home;
    }
    
    
    @RequestMapping(value = "/registroenv", method = RequestMethod.POST)
    public ModelAndView RegistroENV(Locale locale, Model model ,@ModelAttribute Usuario usuario2) {
       
    	logger.info("Welcome registro env ! The client locale is {}.", locale);

        ModelAndView home = new ModelAndView("/public/registro");
        
        usuarioService.save(usuario2);
        
//       Usuario usuariologado = usuario;
        
        model.addAttribute("roles",Rolesall);
        model.addAttribute("usuario",usuario2);
        model.addAttribute("acao","edit");
        model.addAttribute("filename",filename);

        model.addAttribute("sucesso","Salvo ! Confira seus dados");

        

//        return new ModelAndView("redirect:/registro").addObject("usuario", usuario2).addObject("filename", filename)
//        		.addObject("acao", "edit").addObject("roles", Rolesall);
        
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
    
    @RequestMapping(value = "salvarfotoregistro", method = RequestMethod.POST)
    public ModelAndView SalvarFotoPr2d(@RequestParam ("file") MultipartFile file, HttpSession session, HttpServletRequest request,
                             Model model, @ModelAttribute Usuario usuarior) {
    	
    	logger.info("Welcome salvarfotousuarioReg ! The model {}.");	
//   	Usuario usuario = null;
   	
//   Usuario	usuariologadoo = usuarior;
    	
//    	this.usuario = usuarior;
    	

        Rolesall = RoleService.findAll();

        String sucesso = "Sucesso ao salvar foto";
        
        String erros = "Falha ao salvar foto";
        
        ModelAndView cadastro = new ModelAndView("/public/registro");

//        String path = session.getServletContext().getRealPath("/WEB-INF/classes/static/img/usuario/");
        
//        String path = context.getRealPath("/WEB-INF/classes/static/img/usuario/");
        
        String path = context.getRealPath("/WEB-INF/classes/static/img/usuario/");
        
        this.filename= file.getOriginalFilename();
        
        
//        heroku não funfa com essas barras
//        String caminho = path + "\\" + filename;
        
        String caminho = path + filename;
        


        System.out.println(" path realpath = "  + path );
        
        logger.debug("caminho foto registro : " + caminho);

//        System.out.println(" caminho" + caminho);
//        
//        System.out.println("request D" + d);

        try {

            byte barr[] = file.getBytes();

            BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(caminho));
            bout.write(barr);
            bout.flush();
            bout.close();

            
            
            this.usuario.setFoto(filename);
            model.addAttribute("sucesso", sucesso);
//            cadastro.addObject("filename", filename);
//            cadastro.addObject("acao", "add");
//            cadastro.addObject("usuario", usuario);

            
            System.out.println(" salvou file : " + filename);
            
           
//           usuario.setFoto(filename);

        } catch (Exception e) {

            System.out.println(e);

            model.addAttribute("erro", erros + e);
//            cadastro.addObject("acao", "add");
//            cadastro.addObject("filename", filename);

           // cadastro.addObject("usuario", usuario);            
            System.out.println(" não salvou file : " + e.getCause());

        }

//       Usuario usuario =  new Usuario();
     
//        this.usuariologado.setFoto(filename);
        
//        usuario.setFoto(filename);
        
        model.addAttribute("roles", Rolesall);
        model.addAttribute("usuario", usuario); 
        model.addAttribute("filename", filename);
        model.addAttribute("acao", "add");

     //   usuarior.setFoto(filename);
//       return new ModelAndView("redirect:/registro").addObject("usuario", usuarior).addObject("filename", filename)
//    		   .addObject("acao", "add").addObject("roles", Rolesall);
        
        return cadastro;

    }
    
	private Usuario PegarIndicacao() {
		// TODO Auto-generated method stub
		
	    Usuario usu = null;
        usuarios = usuarioService.findAll();
        
        carrinhobean.SetarUsuariosIndicaSessao(usuarios);
        
        int index = usuarios.size();
        
      //  acessoubanco = true;
        
        if(indexUsuario <  index){
        
        usu = IndicacaoUsuario(usuarios);
       
        
        }else {
        	
        	indexUsuario = 0;	
        	
        	 usu = IndicacaoUsuario(usuarios);
        	
        }
        
		
		return usu;
	}
    
    
    private Usuario IndicacaoUsuario(List<Usuario> usuarios2) {

//		for(Usuario us: usuarios2){
    	
    	
    	Usuario us = usuarios2.get(indexUsuario);
			
			if(!us.getIndicacoes().isEmpty()){
				
				carrinhobean.SetarUsuarioIndicaSessao(us);
				
				 indexUsuario++;
				 		
				 
				return us;
			}else {
				
				
				
				
				indexUsuario++;
				PegarIndicacao();
				
//				Usuario uss = usuarios2.get(indexUsuario);
//				
//				if(!uss.getIndicacoes().isEmpty()){
//					
//					carrinhobean.SetarUsuarioIndicaSessao(uss);
//					
//					 indexUsuario++;
//					 		
//					 
//					return uss;
//				}else {
//					
//					 indexUsuario++;
//					IndicacaoUsuario(usuarios2);
//				}
				
				return null;
			}
			
//		}

	}

//        
//    }  

}
