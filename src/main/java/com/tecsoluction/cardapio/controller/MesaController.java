package com.tecsoluction.cardapio.controller;

import java.util.ArrayList;
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

import com.tecsoluction.cardapio.CarrinhoBean;
import com.tecsoluction.cardapio.entidade.Item;
import com.tecsoluction.cardapio.entidade.Mesa;
import com.tecsoluction.cardapio.entidade.PedidoVenda;
import com.tecsoluction.cardapio.framework.AbstractController;
import com.tecsoluction.cardapio.framework.AbstractEditor;
import com.tecsoluction.cardapio.framework.AbstractEntityService;
import com.tecsoluction.cardapio.servico.MesaServicoImpl;
import com.tecsoluction.cardapio.servico.PedidoVendaServicoImpl;
import com.tecsoluction.cardapio.servico.ProdutoServicoImpl;
import com.tecsoluction.cardapio.util.SituacaoItem;
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
	 
	 @Autowired
	private final PedidoVendaServicoImpl PedidoVendaService;
	 
	 
	 @Autowired
	private final ProdutoServicoImpl ProdutoService;
	    
		 @Autowired
		 private CarrinhoBean carrinhobean;
		 
		 
		 
		 private List<Item> itensaguardando;
		 
		 
		 private List<Item> itenscancelados;
		 
		 
		 private List<Item> itenspreparacao;
		 
		 private List<Item> itensentregue;
		 
		 
		 private List<PedidoVenda> pedidos;
		 
		 private List<Item> itensprontos ;
		 
	 
	 
//	 @Autowired
//	private final RoleServicoImpl roleService;

	@Autowired
	public MesaController(MesaServicoImpl msService,PedidoVendaServicoImpl pvs,ProdutoServicoImpl prd) {
		super("mesa");
		this.mesaService = msService;
		this.PedidoVendaService = pvs;
		this.ProdutoService = prd;
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
		
		logger.info("Welcome add atribute Mesa Controller !" + model);

		////
		 StatusMesa[] status = StatusMesa.values();
		 mesa = new Mesa();
		 
		   pedidos = PedidoVendaService.findAll();
		   
		   OrganizarStatusItem(pedidos);
		//
		// Usuario usuario = new Usuario();
		// usuario.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		//
		// usuario = usudao.PegarPorNome(usuario.getUsername());
		 
		 
	     model.addAttribute("carrinho", carrinhobean.getCarrinho()); 
		 model.addAttribute("status", status);
		 model.addAttribute("mesa", mesa);
		 model.addAttribute("mesaimg", mesaimg);
		 model.addAttribute("itensaguardando", itensaguardando);
		 model.addAttribute("itenscancelados", itenscancelados);
		 model.addAttribute("itensentregue", itensentregue);
		 model.addAttribute("itenspreparacao", itenspreparacao);
		 model.addAttribute("itensprontos", itensprontos);
		   	model.addAttribute("totalitens", carrinhobean.TotalItens());


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
    
    
    
    @RequestMapping(value = "/perfil", method = RequestMethod.GET)
    public ModelAndView ExibirCozinhaPerfil(HttpServletRequest request) {

        UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/private/mesa/perfil");
        
        Mesa mesa = getservice().findOne(idf);
        
//        mesas = getservice().findAll();

//        Categoria cat = getservice().findOne(idf);

        exibircat.addObject("mesa", mesa);

        return exibircat;
    }
    
    
    
    
    private void OrganizarStatusItem(List<PedidoVenda> pedidos){
    	
    	itensaguardando = new ArrayList<Item>();
    	itenscancelados = new ArrayList<Item>();
    	itensentregue = new ArrayList<Item>();
    	itenspreparacao = new ArrayList<Item>();
    	itensprontos = new ArrayList<Item>();
    	
    	
    	for( PedidoVenda pv: pedidos){
    		
    		for( Item it: pv.getItems().keySet()){
    			
    			
    			if(it.getSituacao().equals(SituacaoItem.AGUARDANDO)){
    				
    				itensaguardando.add(it);
    				
    			}
    			else if (it.getSituacao().equals(SituacaoItem.CANCELADO)){
    				
    				itenscancelados.add(it);
    			}
    			
    			else if (it.getSituacao().equals(SituacaoItem.EM_EXECUCAO)){
    				
    				itenspreparacao.add(it);
    				
    			}
    			else if (it.getSituacao().equals(SituacaoItem.ENTREGUE)){
    				
    				itensentregue.add(it);
    				
    			}else {
    				
    				
    				itensprontos.add(it);
    				
    			}
    			
    		}
    		
    	// final for	
    	}
    	
    	
    	
    }
    
	

	@Override
	protected AbstractEntityService<Mesa> getservice() {
		// TODO Auto-generated method stub
		return mesaService;
	}



}
