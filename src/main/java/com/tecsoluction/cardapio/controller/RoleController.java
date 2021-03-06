package com.tecsoluction.cardapio.controller;

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

import com.tecsoluction.cardapio.entidade.Role;
import com.tecsoluction.cardapio.entidade.Usuario;
import com.tecsoluction.cardapio.framework.AbstractController;
import com.tecsoluction.cardapio.framework.AbstractEditor;
import com.tecsoluction.cardapio.servico.RoleServicoImpl;
import com.tecsoluction.cardapio.servico.UsuarioServicoImpl;


/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "role/")
public class RoleController extends AbstractController<Role> {

	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	 @Autowired
	private final UsuarioServicoImpl userService;
	 @Autowired
	private final RoleServicoImpl roleService;

	@Autowired
	public RoleController(UsuarioServicoImpl userService, RoleServicoImpl roleService) {
		super("role");
		this.userService = userService;
		this.roleService = roleService;
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
		// model.addAttribute("roleList", roleList);
		// model.addAttribute("usuarioList", usuarioList);

	}

	@Override
	protected RoleServicoImpl getservice() {
		// TODO Auto-generated method stub
		return roleService;
	}

}
