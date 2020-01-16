package com.tecsoluction.cardapio.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
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
import com.tecsoluction.cardapio.entidade.Carrinho;
import com.tecsoluction.cardapio.entidade.Categoria;
import com.tecsoluction.cardapio.entidade.Garcon;
import com.tecsoluction.cardapio.entidade.Item;
import com.tecsoluction.cardapio.entidade.Mesa;
import com.tecsoluction.cardapio.entidade.PedidoVenda;
import com.tecsoluction.cardapio.entidade.Produto;
import com.tecsoluction.cardapio.entidade.Role;
import com.tecsoluction.cardapio.entidade.Usuario;
import com.tecsoluction.cardapio.framework.AbstractController;
import com.tecsoluction.cardapio.framework.AbstractEditor;
import com.tecsoluction.cardapio.servico.GarconServicoImpl;
import com.tecsoluction.cardapio.servico.MesaServicoImpl;
import com.tecsoluction.cardapio.servico.PedidoVendaServicoImpl;
import com.tecsoluction.cardapio.servico.ProdutoServicoImpl;
import com.tecsoluction.cardapio.servico.RoleServicoImpl;
import com.tecsoluction.cardapio.servico.UsuarioServicoImpl;
import com.tecsoluction.cardapio.util.SituacaoItem;


/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "carrinho/")
public class CarrinhoController  {

	private static final Logger logger = LoggerFactory.getLogger(CarrinhoController.class);
	
	private Carrinho carrinho = new Carrinho();
	
	 @Autowired
	    private  ProdutoServicoImpl produtoService;
	 
	 
	 @Autowired
	    private  PedidoVendaServicoImpl pedidovendaService;
	 
		@Autowired
		private ServletContext context;
		
		 @Autowired
		    private  GarconServicoImpl garconService;
		 
		 
		 @Autowired
		    private  MesaServicoImpl mesaService;
		 
		 
		 private List<Mesa> mesas;
		 
		 private List<Garcon> garcons;
		 
		 @Autowired
		 private CarrinhoBean carrinhobean;
		 
		
	 
	 
//	 @Autowired
//	private final UsuarioServicoImpl userService;
//	 @Autowired
//	private final RoleServicoImpl roleService;
//
//	@Autowired
//	public CarrinhoController(UsuarioServicoImpl userService, RoleServicoImpl roleService) {
//		super("role");
//		this.userService = userService;
//		this.roleService = roleService;
//	}

//	@InitBinder
//	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
//
//		binder.registerCustomEditor(Usuario.class, new AbstractEditor<Usuario>(this.userService) {
//		});
//
//	}

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
		
//		carrinho = new Carrinho();
		
		// usuario = usudao.PegarPorNome(usuario.getUsername());
		
//		Object carrinhoserv = context.getAttribute("carrinho");
//		
//		this.carrinho = (Carrinho) carrinhoserv;
		
        mesas = mesaService.findAll();
        garcons = garconService.findAll();
		
//		if(!carrinho.getItens().isEmpty()){
//			
//			
//			
//		}else {
//        carrinho = new Carrinho();
//			UUID uuid = UUID.randomUUID();
//			carrinho.setId(uuid);
			
//		}
//		
//		
	        if(carrinho == null){
	        	carrinho = new Carrinho();
	        	UUID uuid = UUID.randomUUID();
	 			carrinho.setId(uuid);
//	            model.addAttribute("carrinho", carrinho); 
	            }else {
	            	
	            	
	            	if(carrinho.getId()==null){
	            		
	            		carrinho = carrinhobean.getCarrinho();
			        	UUID uuid = UUID.randomUUID();
			 			carrinho.setId(uuid);	
			 			carrinhobean.SetarCarrinhoSessao(carrinho);
	            		
	            	}else {
	            		
	            		
	            		
	            		
	            	}
	            	            	
	            	
	            }
		
	        
	        
	//	Object carrinhoses = request.getSession().getAttribute("nome"); // getAttribute("

		
		
	     model.addAttribute("carrinho", carrinhobean.getCarrinho()); 
		 model.addAttribute("mesas", mesas);
		 model.addAttribute("garcons", garcons);

	}
	
	
    @RequestMapping(value = "/visualizar", method = RequestMethod.GET)
    public ModelAndView ExibirCategoria(HttpServletRequest request) {

//        UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/public/carrinho/visualizar");
        
        carrinhobean.getCarrinho().setTotal(carrinhobean.getCarrinho().CalcularTotal().setScale(2, RoundingMode.UP).setScale(2, RoundingMode.UP));

//        Categoria cat = getservice().findOne(idf);

        exibircat.addObject("carrinho", carrinhobean.getCarrinho());

        return exibircat;
    }
    
    
    @RequestMapping(value = "/adicionar", method = RequestMethod.GET)
    public ModelAndView AdicionarProdutoCarrinho(HttpServletRequest request,Model model ) {

        UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        //this.carrinho = request.getAttribute("carrinho");
        

        
        ModelAndView exibircat = new ModelAndView("/public/carrinho/visualizar");

        Produto cat = produtoService.findOne(idf);
        
        
        ModelAndView exibircatt = new ModelAndView("redirect:/categoria/exibir?id=" +cat.getCategoria().getId() );
        
        Item item = new Item(cat);
        
        item.setQtd("01");
        item.setTotalItem(item.CalcularTotaItem(item.getQtd()).setScale(2, RoundingMode.UP));
        item.setSituacao(SituacaoItem.AGUARDANDO);
        
        carrinhobean.AddItemCarrinho(item);
        
        carrinhobean.getCarrinho().setTotal(carrinhobean.getCarrinho().CalcularTotal().setScale(2, RoundingMode.UP));
        
      System.out.println("url : " + request.getRequestURL());  
      System.out.println("url parameters: " + request.getAttributeNames().toString());  
        
//        request.setAttribute("carrinho", carrinho); // Setando no escopo de requisição
//        request.getSession().setAttribute("carrinho", carrinho); // Setando no escopo de sessão.
    //    context.setAttribute("carrinho", carrinho); // Setando no escopo de aplicação

      exibircatt.addObject("carrinho", carrinhobean.getCarrinho());
      model.addAttribute("carrinho", carrinhobean.getCarrinho());

        return exibircatt;
    }
    
    
    @RequestMapping(value = "/finalizar", method = RequestMethod.GET)
    public ModelAndView FinalizarCarrinho(HttpServletRequest request,Model model) {

  //      UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/public/carrinho/finalizar");

//        Produto cat = produtoService.findOne(idf);
//        
//        Item item = new Item(cat);
//        
        carrinhobean.getCarrinho().setSubtotal(carrinhobean.getCarrinho().CalcularTotal().setScale(2, RoundingMode.UP));
        carrinhobean.getCarrinho().setTotal(carrinhobean.getCarrinho().CalcularTotal().setScale(2, RoundingMode.UP));
        carrinhobean.getCarrinho().setQtd(new BigDecimal(carrinhobean.getCarrinho().getItens().size()).setScale(2, RoundingMode.UP));
        
        mesas = mesaService.findAll();
        garcons = garconService.findAll();

        exibircat.addObject("carrinho", carrinhobean.getCarrinho());
        
        exibircat.addObject("mesas", mesas);
        
        exibircat.addObject("garcons", garcons);

        return exibircat;
    }
    
    
    @RequestMapping(value = "/deleteitem", method = RequestMethod.GET)
    public ModelAndView DeletarItem(HttpServletRequest request,Model model) {
    	
    	List<Item> itensaux = new ArrayList<Item>();

        UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/public/carrinho/finalizar");

        Produto cat = produtoService.findOne(idf);
//        
     //   Item item = new Item(cat,"01");
        
   //     itensaux = carrinho.getItens();
//        
//        this.carrinho.removeItem(item);
        
   //     carrinho.getItens().remove(item);
        
        for (Item key : carrinhobean.getCarrinho().getItens()) {
        	
        	if(key.getId().equals(cat.getId())){
        		        		
        	//	key.setSituacao(SituacaoItem.EM_EXECUCAO);
        		
        	//	carrinho.removeItem(key);
        		
        		itensaux.add(key);
        		
        	}else {
        		
        		
        		
        	}	
        	
        }
        
//        carrinho.getItens().clear();
//        carrinho.setItens(itensaux);
        
        carrinhobean.getCarrinho().getItens().removeAll(itensaux);
        
        
        
        
        carrinhobean.getCarrinho().setSubtotal(carrinhobean.getCarrinho().CalcularTotal().setScale(2, RoundingMode.UP));
        carrinhobean.getCarrinho().setTotal(carrinhobean.getCarrinho().CalcularTotal().setScale(2, RoundingMode.UP));
        carrinhobean.getCarrinho().setQtd(new BigDecimal(carrinhobean.getCarrinho().getItens().size()).setScale(2, RoundingMode.UP));

        model.addAttribute("carrinho", carrinhobean.getCarrinho());

        exibircat.addObject("carrinho", carrinhobean.getCarrinho());
        
        
       // return new ModelAndView("redirect:/carrinho/finalizar") ;
        
        return exibircat;
    }
    
    
    @RequestMapping(value = "/finalizaCarrinho", method = RequestMethod.POST)
    public ModelAndView FinalizarCarrinhopOST(HttpServletRequest request,Model model) {

        UUID idf = UUID.fromString(request.getParameter("id"));
        
        String sucesso="Carrinho Finalizado com Sucesso, Nº Pedido";
        
        String erro="Falha ao Finalizar Carrinho";

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/private/pedidovenda/movimentacao/movimentacaopedidovenda");

//        Produto cat = produtoService.findOne(idf);
//        
//        Item item = new Item(cat);
//        
//        this.carrinho.setSubtotal(carrinho.CalcularTotal());
//        this.carrinho.setTotal(carrinho.CalcularTotal());
//        this.carrinho.setQtd(new BigDecimal(carrinho.getItens().size()));
//        
//        mesas = mesaService.findAll();
//        garcons = garconService.findAll();
        
        
        
        PedidoVenda pv = new PedidoVenda(carrinhobean.getCarrinho());
        
        pv.setTotal(pv.CalcularTotal(pv.getItems()));
        
        
       pv = pedidovendaService.save(pv);
       
       List<PedidoVenda> pedidos = pedidovendaService.findAll();
       
       Carrinho carrinhoo = new Carrinho();
       
	   	UUID uuid = UUID.randomUUID();
	   	carrinhoo.setId(uuid);	
       
       carrinhobean.SetarCarrinhoSessao(carrinhoo);
       
//       String resp = "<p>Sucesso</p>";
        

//        exibircat.addObject("carrinho", carrinho);
//        
//        exibircat.addObject("mesas", mesas);
//        
        exibircat.addObject("carrinho", carrinhobean.getCarrinho());
        exibircat.addObject("sucesso", sucesso);
        
       model.addAttribute("pedidovendaList", pedidos);
       model.addAttribute("carrinho", carrinhobean.getCarrinho());
       model.addAttribute("sucesso", sucesso);

        return exibircat;
    }
    

    @RequestMapping(value = "/cancelar", method = RequestMethod.GET)
    public ModelAndView CancelarCarrinho(HttpServletRequest request,Model model) {

        
        ModelAndView exibircat = new ModelAndView("redirect:/home");


       Carrinho carrinhoo = new Carrinho();
       
	   	UUID uuid = UUID.randomUUID();
	   	carrinhoo.setId(uuid);	
       
       carrinhobean.SetarCarrinhoSessao(carrinhoo);
       
        exibircat.addObject("carrinho", carrinhobean.getCarrinho());
        
       model.addAttribute("carrinho", carrinhobean.getCarrinho());

        return exibircat;
    }

}
