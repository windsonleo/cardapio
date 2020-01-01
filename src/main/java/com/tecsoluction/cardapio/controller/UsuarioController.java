package com.tecsoluction.cardapio.controller;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tecsoluction.cardapio.entidade.Categoria;
import com.tecsoluction.cardapio.entidade.Produto;
import com.tecsoluction.cardapio.entidade.Role;
import com.tecsoluction.cardapio.entidade.Usuario;
import com.tecsoluction.cardapio.framework.AbstractController;
import com.tecsoluction.cardapio.framework.AbstractEditor;
import com.tecsoluction.cardapio.servico.CategoriaServicoImpl;
import com.tecsoluction.cardapio.servico.ProdutoServicoImpl;
import com.tecsoluction.cardapio.servico.RoleServicoImpl;
import com.tecsoluction.cardapio.servico.UsuarioServicoImpl;






@Controller
@RequestMapping("usuario/")
public class UsuarioController extends AbstractController<Usuario> {
	
	private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

	@Autowired
	 private final RoleServicoImpl roleservico;
	@Autowired
	 private final UsuarioServicoImpl ususervice;
	
	@Autowired
	 private final ProdutoServicoImpl produtoservico;
	
	 @Autowired
	    private final CategoriaServicoImpl categoriaService;
	 
		
		@Autowired
		private ServletContext context;
	
	
	 
	 private Usuario usuario = new Usuario(); ;
	 
	 private String filename;
	 
	
	@Autowired
    public UsuarioController(RoleServicoImpl roleimpl,UsuarioServicoImpl usuimpl,ProdutoServicoImpl prod,CategoriaServicoImpl cate) {
		super("usuario");
		this.roleservico = roleimpl;
		this.ususervice = usuimpl;
		this.produtoservico = prod;
		this.categoriaService = cate;
		
	}


    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {

        binder.registerCustomEditor(Role.class, new AbstractEditor<Role>(this.roleservico) {
        });

    }
	
    @ModelAttribute
    public void addAttributes(Model model) {

    	logger.info("Welcome add atribute Usuario Controller !" + usuario);

    	
    	List<Role> roles = roleservico.findAll();
    	
    	
    
    		
    		
    		
    		filename="avatar_usu.jpg";
    
    	
//    	Genero[] generos = Genero.values();

//        usuario = new Usuario();
//        usuario.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//        usuario = ususervice.findByUsername(usuario.getUsername());

                
//        model.addAttribute("usuarioAtt", usuario);
//        model.addAttribute("generos", generos);
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", roles);
        model.addAttribute("filename", filename);

        

    }
    
    @RequestMapping(value = "/perfil", method = RequestMethod.GET)
    public ModelAndView profileUsuario(HttpServletRequest request) {

        UUID idf = UUID.fromString(request.getParameter("id"));

        ModelAndView profileusuario = new ModelAndView("/private/usuario/perfil");

        Usuario usuario = getservice().findOne(idf);

        profileusuario.addObject("usuario", usuario);

        return profileusuario;
    }
    
    
    
    
//    
//    @RequestMapping(value = "/registro", method = RequestMethod.GET)
//    public ModelAndView Registro(Locale locale, Model model) {
//       
//    	logger.info("Welcome registro usu ! The client locale is {}.", locale);
//
//        ModelAndView home = new ModelAndView("/public/registro");
//
//
//        return home;
//    }
//    
//    @RequestMapping(value = "/registro", method = RequestMethod.POST)
//    public ModelAndView RegistroPost(Locale locale, Model model, HttpServletRequest request,@ModelAttribute Usuario usuarior) {
//       
//    	logger.info("Welcome registro ! The client locale is {}.", locale);
//
//        ModelAndView home = new ModelAndView("/public/registro");
//        
////        Usuario usuario = new Usuario();
//        
////        usuario.setUsername(request.getParameter("username"));
////        usuario.setEmail(request.getParameter("email"));
////        usuario.setSenha(request.getParameter("senha"));
////        usuario.setRoles(new HashMap().put(arg0, arg1));
//       
//        getservice().save(usuarior);
//        
//        
//
//
//        return new ModelAndView("forward:/registro");
//    }
    
    
    // verificar tmanho do arquivo e se o arquivo ja existe
    @RequestMapping(value = "salvarfotousuario", method = RequestMethod.POST)
    public ModelAndView SalvarFotoProduto2(@RequestParam ("file") MultipartFile file, HttpSession session, HttpServletRequest request,
                             Model model, @ModelAttribute("usuario")  Usuario usuarior) {
    	
//    	Usuario usuario = new Usuario();

        String sucesso = "Sucesso ao salvar foto";
        
        String erros = "Falha ao salvar foto";
        
        ModelAndView cadastro = new ModelAndView("/private/usuario/cadastro/cadastrousuario");

//        String path = session.getServletContext().getRealPath("/WEB-INF/classes/static/img/usuario/");
        
        String path = context.getRealPath("/WEB-INF/classes/static/img/usuario/");
        
        this.filename = file.getOriginalFilename();
        
        
//        heroku não funfa com essas barras
//        String caminho = path + "\\" + filename;
        
        String caminho = path + filename;
        


        System.out.println(" path = "  + path );

//        System.out.println(" caminho" + caminho);
//        
//        System.out.println("request D" + d);

        try {

            byte barr[] = file.getBytes();

            BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(caminho));
            bout.write(barr);
            bout.flush();
            bout.close();

            usuarior.setFoto(filename);
            
            model.addAttribute("sucesso", sucesso);
            model.addAttribute("filename", filename);
            model.addAttribute("acao", "add");
            model.addAttribute("usuario", usuario);
            System.out.println(" salvou file : " + filename);
            
           
//           usuario.setFoto(filename);

        } catch (Exception e) {

            System.out.println(e);

            model.addAttribute("erros", erros + e);
            model.addAttribute("acao", "add");
            model.addAttribute("usuario", usuario);
            System.out.println(" não salvou file : " + e);

        }

//       Usuario usuario =  new Usuario();
     
        //usuario.setFoto(filename);
        
       return cadastro;

    }
    
    
    @RequestMapping(value = "/indica", method = RequestMethod.GET)
    public ModelAndView IndicaProduto(HttpServletRequest request) {

        UUID idf = UUID.fromString(request.getParameter("id"));
        
       

       // ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");

        Produto cat = produtoservico.findOne(idf);
        
      //  ModelAndView exibircat = new ModelAndView("/private/categoria/exibir?id=" + cat.getCategoria().getId());
        
//        UUID idfcat = UUID.fromString(cat.getCategoria().getId());
        
        Categoria cate = categoriaService.findOne(cat.getCategoria().getId());
        
        Usuario usuario = new Usuario();
        usuario.setEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        usuario = getservice().findByEmail(usuario.getEmail());
        
        if(usuario != null){
        	
        	usuario.addIndicacao(cat);
        	
        	
        }else {
        	
        	
        	
        	
        }
        
        getservice().edit(usuario);

     //   exibircat.addObject("categoria", cat);

        return new ModelAndView("redirect:/categoria/exibir?id="+ cate.getId()).addObject("categoria", cate);
    } 
    
    
    
    @RequestMapping(value = "/lock", method = RequestMethod.GET)
    public ModelAndView lock(Locale locale, Model model, HttpServletRequest request) {
      
    	logger.info("Welcome LOCK! The client locale is {}.", locale);
    	
//    	String email = request.getParameter("email");
//
//    	this.usuario = getservice().findByEmail(email);
    	
  	
    	ModelAndView login = new ModelAndView("/public/lock");
        
        
        
//        login.addObject("usuario", usuario);


        return login;
    }
    
    
    @RequestMapping(value = "/unlock", method = RequestMethod.POST)
    public ModelAndView Unlock(Locale locale, Model model, HttpServletRequest request) {
      
    	logger.info("Welcome UNLOCK! The client locale is {}.", locale);
    	
    	ModelAndView login = new ModelAndView("/public/lock");
    	
    	String senha = request.getParameter("senha");
    	
        Usuario usuarioaux = new Usuario();
        usuarioaux.setEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        usuarioaux = getservice().findByEmail(usuarioaux.getEmail());
        
        usuario.setSenha(senha);
        
        
        if(usuarioaux.getSenha().equals(usuario.getSenha())){
        	
        	 login = new ModelAndView("redirect:/home");
            
            
            
            login.addObject("usuario", usuario);
        	
        }else {
        	
        	
        	 login = new ModelAndView("redirect:/usuario/lock");
            
            login.addObject("usuario", usuario);
        	
        	
        }

   
    	
  	
    	


        return login;
    }
    

	@Override
	protected UsuarioServicoImpl getservice() {

		return ususervice;
	}
    
    

}
