package com.tecsoluction.cardapio.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.servlet.ModelAndView;

import com.tecsoluction.cardapio.entidade.Mesa;
import com.tecsoluction.cardapio.entidade.Role;
import com.tecsoluction.cardapio.entidade.Usuario;
import com.tecsoluction.cardapio.framework.AbstractController;
import com.tecsoluction.cardapio.framework.AbstractEditor;
import com.tecsoluction.cardapio.framework.AbstractEntityService;
import com.tecsoluction.cardapio.servico.MesaServicoImpl;
import com.tecsoluction.cardapio.servico.RoleServicoImpl;
import com.tecsoluction.cardapio.servico.UsuarioServicoImpl;
import com.tecsoluction.cardapio.util.StatusMesa;


/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "mesa/")
public class MesaController extends AbstractController<Mesa> {

	private static final Logger logger = LoggerFactory.getLogger(MesaController.class);
	 @Autowired
	private final MesaServicoImpl mesaService;
	 
	 private Mesa mesa;
	 
	 private List<Mesa> mesas;
	 
	 private String mesaimg = "mesa.jpg";
	 
	 
//	 @Autowired
//	private final RoleServicoImpl roleService;

	@Autowired
	public MesaController(MesaServicoImpl msService) {
		super("mesa");
		this.mesaService = msService;
//		this.roleService roleService;= 
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {

		binder.registerCustomEditor(Mesa.class, new AbstractEditor<Mesa>(this.mesaService) {
		});

	}

	@ModelAttribute
	public void addAttributes(Model model) {

		// List<Role> roleList = rdao.getAll();
		// List<Usuario> usuarioList = dao.getAll();
		////
		 StatusMesa[] status = StatusMesa.values();
		 mesa = new Mesa();
		//
		// Usuario usuario = new Usuario();
		// usuario.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		//
		// usuario = usudao.PegarPorNome(usuario.getUsername());
		 model.addAttribute("status", status);
		 model.addAttribute("mesa", mesa);
		 model.addAttribute("mesaimg", mesaimg);

	}
	
	
	
    @RequestMapping(value = "/salao", method = RequestMethod.GET)
    public ModelAndView ExibirCozinha(HttpServletRequest request) {

//        UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/private/mesa/salao");
        
        mesas = getservice().findAll();

//        Categoria cat = getservice().findOne(idf);

        exibircat.addObject("mesaList", mesas);

        return exibircat;
    }
	

	@Override
	protected AbstractEntityService<Mesa> getservice() {
		// TODO Auto-generated method stub
		return mesaService;
	}



}
