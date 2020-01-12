package com.tecsoluction.cardapio.controller;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.tecsoluction.cardapio.entidade.Garcon;
import com.tecsoluction.cardapio.entidade.Role;
import com.tecsoluction.cardapio.entidade.Usuario;
import com.tecsoluction.cardapio.framework.AbstractController;
import com.tecsoluction.cardapio.framework.AbstractEditor;
import com.tecsoluction.cardapio.servico.GarconServicoImpl;
import com.tecsoluction.cardapio.servico.RoleServicoImpl;
import com.tecsoluction.cardapio.servico.UsuarioServicoImpl;


/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "garcon/")
public class GarconController extends AbstractController<Garcon> {

	private static final Logger logger = LoggerFactory.getLogger(GarconController.class);
	 @Autowired
	private final GarconServicoImpl GarconService;
	 
	 private Garcon garcon;
	 
	 
	 private String filename;
	 
	 
		@Autowired
		private ServletContext context;
	 
//	 @Autowired
//	private final RoleServicoImpl roleService;

	@Autowired
	public GarconController(GarconServicoImpl GService) {
		super("garcon");
		this.GarconService = GService;
	
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {

		binder.registerCustomEditor(Garcon.class, new AbstractEditor<Garcon>(this.GarconService) {
		});

	}

	@ModelAttribute
	public void addAttributes(Model model) {

		
		garcon = new Garcon();
		
		filename="vazio.jpg";
		
		
		// List<Role> roleList = rdao.getAll();
		// List<Usuario> usuarioList = dao.getAll();
		////
		//// UnidadeMedida[] umList = UnidadeMedida.values();
		//
		// Usuario usuario = new Usuario();
		// usuario.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		//
		// usuario = usudao.PegarPorNome(usuario.getUsername());
		 model.addAttribute("garcon", garcon);
		 model.addAttribute("filename", filename);
		// model.addAttribute("usuarioList", usuarioList);

	}
	
	
	  @RequestMapping(value = "salvarfotogarcon", method = RequestMethod.POST)
	    public ModelAndView SalvarFotoProduto2(@RequestParam ("file") MultipartFile file, HttpSession session, HttpServletRequest request,
	                             Model model, @ModelAttribute("garcon")  Garcon usuarior) {
	    	
//	    	Usuario usuario = new Usuario();

	        String sucesso = "Sucesso ao salvar foto";
	        
	        String erros = "Falha ao salvar foto";
	        
	        ModelAndView cadastro = new ModelAndView("/private/garcon/cadastro/cadastrogarcon");

//	        String path = session.getServletContext().getRealPath("/WEB-INF/classes/static/img/usuario/");
	        
	        String path = context.getRealPath("/WEB-INF/classes/static/img/garcon/");
	        
	        this.filename = file.getOriginalFilename();
	        
	        
//	        heroku não funfa com essas barras
//	        String caminho = path + "\\" + filename;
	        
	        String caminho = path + filename;
	        


	        System.out.println(" path = "  + path );

//	        System.out.println(" caminho" + caminho);
//	        
//	        System.out.println("request D" + d);

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
	            model.addAttribute("garcon", garcon);
	            System.out.println(" salvou file : " + filename);
	            
	           
//	           usuario.setFoto(filename);

	        } catch (Exception e) {

	            System.out.println(e);

	            model.addAttribute("erros", erros + e);
	            model.addAttribute("acao", "add");
	            model.addAttribute("garcon", garcon);
	            System.out.println(" não salvou file : " + e);

	        }

//	       Usuario usuario =  new Usuario();
	     
	        //usuario.setFoto(filename);
	        
	       return cadastro;

	    }
	
	

	@Override
	protected GarconServicoImpl getservice() {
		// TODO Auto-generated method stub
		return GarconService;
	}

}
