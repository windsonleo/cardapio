package com.tecsoluction.cardapio.controller;

import java.util.List;
import java.util.UUID;

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

import com.tecsoluction.cardapio.entidade.Categoria;
import com.tecsoluction.cardapio.entidade.PedidoVenda;
import com.tecsoluction.cardapio.entidade.Role;
import com.tecsoluction.cardapio.entidade.Usuario;
import com.tecsoluction.cardapio.framework.AbstractController;
import com.tecsoluction.cardapio.framework.AbstractEditor;
import com.tecsoluction.cardapio.servico.PedidoVendaServicoImpl;
import com.tecsoluction.cardapio.servico.RoleServicoImpl;
import com.tecsoluction.cardapio.servico.UsuarioServicoImpl;


/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "pedidovenda/")
public class PedidoVendaController extends AbstractController<PedidoVenda> {

	private static final Logger logger = LoggerFactory.getLogger(PedidoVendaController.class);
	 @Autowired
	private final PedidoVendaServicoImpl PedidoVendaService;
	 
	 private List<PedidoVenda> pedidos;


	@Autowired
	public PedidoVendaController(PedidoVendaServicoImpl userService) {
		super("pedidovenda");
		this.PedidoVendaService = userService;
		
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {

		binder.registerCustomEditor(PedidoVenda.class, new AbstractEditor<PedidoVenda>(this.PedidoVendaService) {
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
	
    @RequestMapping(value = "/cozinha", method = RequestMethod.GET)
    public ModelAndView ExibirCozinha(HttpServletRequest request) {

//        UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/private/pedidovenda/cozinha");

        pedidos = getservice().findAll();

        exibircat.addObject("pedidos", pedidos);

        return exibircat;
    }

	@Override
	protected PedidoVendaServicoImpl getservice() {
		// TODO Auto-generated method stub
		return PedidoVendaService;
	}

}
