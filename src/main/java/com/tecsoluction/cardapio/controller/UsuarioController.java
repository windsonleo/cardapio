package com.tecsoluction.cardapio.controller;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Date;
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

import com.tecsoluction.cardapio.entidade.Atividade;
import com.tecsoluction.cardapio.entidade.Categoria;
import com.tecsoluction.cardapio.entidade.Mensagem;
import com.tecsoluction.cardapio.entidade.Produto;
import com.tecsoluction.cardapio.entidade.Role;
import com.tecsoluction.cardapio.entidade.Usuario;
import com.tecsoluction.cardapio.framework.AbstractController;
import com.tecsoluction.cardapio.framework.AbstractEditor;
import com.tecsoluction.cardapio.servico.AtividadeServicoImpl;
import com.tecsoluction.cardapio.servico.CategoriaServicoImpl;
import com.tecsoluction.cardapio.servico.ProdutoServicoImpl;
import com.tecsoluction.cardapio.servico.RoleServicoImpl;
import com.tecsoluction.cardapio.servico.UsuarioServicoImpl;
import com.tecsoluction.cardapio.util.OrigemAtividade;






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
	    private final AtividadeServicoImpl atividadeService;
	 
	 
		@Autowired
		private ServletContext context;
	
	
	 
	 private Usuario usuario; 
	 
	 private String filename;
	 
	 private List<Usuario> usus;
	 
	
	@Autowired
    public UsuarioController(RoleServicoImpl roleimpl,UsuarioServicoImpl usuimpl,ProdutoServicoImpl prod,CategoriaServicoImpl cate,
    		AtividadeServicoImpl atvs) {
		super("usuario");
		this.roleservico = roleimpl;
		this.ususervice = usuimpl;
		this.produtoservico = prod;
		this.categoriaService = cate;
		this.atividadeService = atvs;
		
	}


    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {

        binder.registerCustomEditor(Role.class, new AbstractEditor<Role>(this.roleservico) {
        });

    }
	
    @ModelAttribute
    public void addAttributes(Model model) {

    	if(usuario == null){
    		
    		usuario = new Usuario(); 	

    		
    	}

    	
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
    public ModelAndView profileUsuario(HttpServletRequest request,Model model,@RequestParam(value = "erro", required = false) String error, 
    		@RequestParam(value = "id", required = false) String id,@RequestParam(value = "sucesso", required = false) String sucesso,
    		Locale locale) {
    	
        ModelAndView profileusuario = new ModelAndView("/private/usuario/perfil");

		  String mensagem ="";
	        
	        if(error != null && error !=""){
	        	 mensagem = error + "erros";
	        	 profileusuario.addObject("erro", mensagem);
	        	
	        }else if(sucesso != null && sucesso !=""){
	        	
	       	 mensagem = sucesso + "sucesso";
	       	profileusuario.addObject("sucesso", mensagem);
	        	
	        }else if(id != null && id !=""){
	        	
	       	 mensagem =  "sucesso"+id;
	       //	cardapio.addObject("sucesso", mensagem);
	        	
	        }
    	

        UUID idf = UUID.fromString(request.getParameter("id"));


        Usuario usuario = getservice().findOne(idf);
        
//    	Mensagem evolucao = new Mensagem();
        
    	  Date datanow = new Date();

        profileusuario.addObject("usuario", usuario);
        
        profileusuario.addObject("datanow", datanow);

        profileusuario.addObject("mensagem", new Mensagem());


        return profileusuario;
    }
    
    @RequestMapping(value = "/allonline", method = RequestMethod.GET)
    public ModelAndView profileUsuarioOnline(HttpServletRequest request) {

//        UUID idf = UUID.fromString(request.getParameter("id"));

        ModelAndView profileusuario = new ModelAndView("/private/usuario/online");
        
        
        usus = getservice().findAll();

//        Usuario usuario = getservice().findOne(idf);
//
        profileusuario.addObject("usuarioList", usus);
        
        

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
    public ModelAndView IndicaProduto(HttpServletRequest request, Model model) {
    	
    	 Atividade atividade = null;
    	
    	String sucesso = "Produto Indicado Com Sucesso !";
    	
    	String erro = "Erro ao Indicar  !";

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
        	   atividade = CriarAtividadeIndicar(usuario,cat);
        	  atividade.setUsuario(usuario);
        	  
        	 
        	  usuario.addAtividade(atividade);
        	
        	model.addAttribute("sucesso", sucesso);
        	model.addAttribute("categoria", cate);
        	
        }else {
        	
        	model.addAttribute("erro", erro);
        	model.addAttribute("categoria", cate);
        	
        }
        
        atividadeService.save(atividade);
        
        getservice().edit(usuario);

     //   exibircat.addObject("categoria", cat);

        return new ModelAndView("redirect:/categoria/exibir?id="+ cate.getId()).addObject("sucesso", sucesso);
    } 
    
    
    
    private Atividade CriarAtividadeIndicar(Usuario usuario2, Produto cat) {
		// TODO Auto-generated method stub
    	
    	Atividade atividade = new Atividade();
    	atividade.setNome("Indicou " + cat.getNome());
    	atividade.setOrigem(OrigemAtividade.INDICOU_PRODUTO);
//    	atividade.setUsuario(usuario2);
    	
//    	usuario2.addAtividade(atividade);
    	
    	
		return atividade;
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
    
    
    @RequestMapping(value = "/addmensagem", method = RequestMethod.POST)
    public ModelAndView AddEvolucaoPaciente(HttpServletRequest request,Model model) {

    	String sucesso = "Msg Enviada Com Sucesso !";

    	
        UUID idf = UUID.fromString(request.getParameter("id"));

       // ModelAndView profilepaciente = new ModelAndView("/private/usuario/perfil");
        
    //    ModelAndView profilepaciente = new ModelAndView("forward:/usuario/perfil?id=" + this.usuario.getId()).addObject("sucesso", sucesso);


        this.usuario = getservice().findOne(idf);
        
	   	 Usuario usuario;
	 	
	   	 String mail =SecurityContextHolder.getContext().getAuthentication().getName();
	        
	   	 usuario = getservice().findByEmail(mail);
        
	   	Mensagem evolucao = new Mensagem(usuario);
	   	UUID uuid = UUID.randomUUID();
	   	evolucao.setId(uuid);
        evolucao.setUsuario(usuario);
        evolucao.setData(new Date());
        evolucao.setDescricao(request.getParameter("descricao"));
        evolucao.setIdusu(usuario.getId());
        
        this.usuario.addMensagem(evolucao);
        
//	   	Mensagem evolucaoo = new Mensagem(usuario);

//	   	UUID uuid2 = UUID.randomUUID();
//	   	evolucaoo.setId(uuid2);
//        evolucaoo.setUsuario(usuario);
//        evolucaoo.setData(new Date());
//        evolucaoo.setDescricao(request.getParameter("descricao"));
//        
//        usuario.addMensagem(evolucaoo);
        
        getservice().edit(this.usuario);
//        getservice().edit(usuario);
        
//        ModelAndView profilepaciente = new ModelAndView("forward:/usuario/perfil?id=" + this.usuario.getId()).addObject("sucesso", sucesso);

        
        Date datanow = new Date();

//        profilepaciente.addObject("usuario", this.usuario);
//        profilepaciente.addObject("datanow", datanow);
//        profilepaciente.addObject("mensagem", new Mensagem());
        
//        model.addAttribute("usuario", this.usuario);
//        model.addAttribute("mensagem", new Mensagem());

//        model.addAttribute("mensagem", new Mensagem());

  //      model.addAttribute("sucesso", sucesso);


//        return profilepaciente;
        
       return new ModelAndView("redirect:/usuario/perfil?id="+this.usuario.getId()).addObject("sucesso", sucesso);

    }
    
    
    @RequestMapping(value = "/removeMensagem", method = RequestMethod.GET)
    public ModelAndView rEMOVEEvolucaoPaciente(HttpServletRequest request,Model model) {

    	
    	String sucesso = "msg removida Com Sucesso !";

        UUID idf = UUID.fromString(request.getParameter("id"));
    	
    	String idff = request.getParameter("idmensagem");


        this.usuario = getservice().findOne(idf);
        
	   	 Usuario usuario;
	 	
	   	 String mail =SecurityContextHolder.getContext().getAuthentication().getName();
	        
	   	 usuario = getservice().findByEmail(mail);
//        
//        Evolucao evolucao = new Evolucao();
//        
//        evolucao.setUsuario(usuario);
//        evolucao.setData(new Date());
//        evolucao.setDescricao(request.getParameter("descricao"));
	   	 
//	   	 usuario.getMensagens().
        
        int index = Integer.valueOf(idff);
        
//        usuario.removeMensagem(index);
        
        this.usuario.removeMensagem(index);
        
        logger.info("Welcome Remove Evolucao Paciente Controller index: !" + idff);
        
        getservice().edit(this.usuario);
//        getservice().edit(usuario);
        
        
        
        Date datanow = new Date();

//        profilepaciente.addObject("paciente", paciente);
//        profilepaciente.addObject("datanow", datanow);
//        profilepaciente.addObject("evolucao", new Evolucao());

//        return new ModelAndView("redirect:/usuario/perfil?id=" + this.usuario.getId());
        
       return new ModelAndView("redirect:/usuario/perfil?id=" + this.usuario.getId()).addObject("sucesso", sucesso);

    }
    
    

	@Override
	protected UsuarioServicoImpl getservice() {

		return ususervice;
	}
    
    

}
