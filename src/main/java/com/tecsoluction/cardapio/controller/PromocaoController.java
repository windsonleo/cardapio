package com.tecsoluction.cardapio.controller;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tecsoluction.cardapio.entidade.Promocao;
import com.tecsoluction.cardapio.entidade.Role;
import com.tecsoluction.cardapio.entidade.Usuario;
import com.tecsoluction.cardapio.framework.AbstractController;
import com.tecsoluction.cardapio.framework.AbstractEditor;
import com.tecsoluction.cardapio.servico.PromocaoServicoImpl;
import com.tecsoluction.cardapio.servico.RoleServicoImpl;
import com.tecsoluction.cardapio.servico.UsuarioServicoImpl;


/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "promocao/")
public class PromocaoController extends AbstractController<Promocao> {

	private static final Logger logger = LoggerFactory.getLogger(PromocaoController.class);
	 @Autowired
	private final UsuarioServicoImpl userService;
	 @Autowired
	private final PromocaoServicoImpl promocaoService;
	 
	 
	 private Promocao promocao;
	 
	 
	    private String filename="promo.jpg";
	    
	    
	    

	@Autowired
	public PromocaoController(UsuarioServicoImpl userService, PromocaoServicoImpl roleService) {
		super("promocao");
		this.userService = userService;
		this.promocaoService = roleService;
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {

		binder.registerCustomEditor(Usuario.class, new AbstractEditor<Usuario>(this.userService) {
		});

	}

	@ModelAttribute
	public void addAttributes(Model model) {

		// List<Role> roleList = rdao.getAll();
		// List<Usuario> usuarioList = dao.getAll();
		////
		//// UnidadeMedida[] umList = UnidadeMedida.values();
		//
		// Usuario usuario = new Usuario();
		// usuario.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		//
		// usuario = usudao.PegarPorNome(usuario.getUsername());
		// model.addAttribute("usuarioAtt", usuario);
		
		promocao = new Promocao();
		
		
		
		 model.addAttribute("promocao", promocao);
		 model.addAttribute("filename", filename);

	}
	
	
	  @PostMapping(value = "salvarfotopromocao")
	    public ModelAndView SalvarFotoPromo(@RequestParam ("file") MultipartFile file, HttpSession session,
	                                    HttpServletRequest request, Model model) {

	        String sucesso = "Sucesso ao salvar foto";
	        
	        String erros = "Falha ao salvar foto";

	        String path = session.getServletContext().getRealPath("/WEB-INF/classes/static/img/promocao/");
	        
//	        String path = session.getServletContext().getRealPath("/");

	        String d = request.getContextPath();

	    //   String pathh = "resources/static/img/produto/";
	        // string pathh = file.get

//	        String filename = file.getOriginalFilename();
	        
	        this.filename = file.getOriginalFilename();
	        
	        
	        String caminho = path +filename;

	        System.out.println("Caminho" + path + " " + filename);

//	        System.out.println("request end" + d + pathh + "/" + filename);

	        try {

	            byte barr[] = file.getBytes();

	            BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(caminho));
	            bout.write(barr);
	            bout.flush();
	            bout.close();

	            model.addAttribute("sucesso", sucesso);
	            model.addAttribute("filename", filename);
	            model.addAttribute("acao", "add");
	            
	            System.out.println(" salvou file : " + filename);

	        } catch (Exception e) {

	            System.out.println(e);

//	            model.addAttribute("erros", erros + e);
//	            model.addAttribute("produto", new Produto());
	            
	            model.addAttribute("erros", erros + e);
	            model.addAttribute("acao", "add");
	            
	            System.out.println(" não salvou file : " + e);

	        }
	        
	        this.promocao.setFoto(filename);

	        return new ModelAndView("redirect:/promocao/cadastro").addObject("promocao", promocao);

	    }


	@Override
	protected PromocaoServicoImpl getservice() {
		// TODO Auto-generated method stub
		return promocaoService;
	}

}
