package com.tecsoluction.cardapio.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tecsoluction.cardapio.CarrinhoBean;
import com.tecsoluction.cardapio.RestauranteBean;
import com.tecsoluction.cardapio.entidade.Carrinho;
import com.tecsoluction.cardapio.entidade.Garcon;
import com.tecsoluction.cardapio.entidade.Item;
import com.tecsoluction.cardapio.entidade.Mesa;
import com.tecsoluction.cardapio.entidade.PedidoVenda;
import com.tecsoluction.cardapio.entidade.Produto;
import com.tecsoluction.cardapio.entidade.Usuario;
import com.tecsoluction.cardapio.servico.CategoriaServicoImpl;
import com.tecsoluction.cardapio.servico.GarconServicoImpl;
import com.tecsoluction.cardapio.servico.MesaServicoImpl;
import com.tecsoluction.cardapio.servico.PedidoVendaServicoImpl;
import com.tecsoluction.cardapio.servico.ProdutoServicoImpl;
import com.tecsoluction.cardapio.util.GerenciadorCategorias;
import com.tecsoluction.cardapio.util.OrigemPedido;
import com.tecsoluction.cardapio.util.SituacaoItem;


/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "carrinho/")
public class CarrinhoController  {

	private static final Logger logger = LoggerFactory.getLogger(CarrinhoController.class);
	
	private Carrinho carrinho;
	
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
		 
			@Autowired
			private  CategoriaServicoImpl CategoriaService;
		 
		 
		 private List<Mesa> mesas;
		 
		 private List<Garcon> garcons;
		 
		 @Autowired
		 private CarrinhoBean carrinhobean;
		 
		 
		 private OrigemPedido [] origempedidos;
		 
		 @Autowired
		 private RestauranteBean restaurantebean;
		 
		 private Pageable primeiroResultado = new PageRequest(0, 5);
		 
		    private GerenciadorCategorias gerenciacat;
		    
		    private List<Usuario> usuarios = new ArrayList<Usuario>();

			 private int indexUsuario = 0;

	 
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
		
		logger.info("Welcome add atribute Carrinho Controller !" + model);


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
        origempedidos = OrigemPedido.values();
        
        
        for(Mesa mes:mesas){
        	
        	mes.CalcularTotal();
        	
        }
		
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
        if(carrinhobean.getCarrinho() == null){
	        	carrinho = new Carrinho();
	        	UUID uuid = UUID.randomUUID();
	 			carrinho.setId(uuid);
//	            model.addAttribute("carrinho", carrinho); 
	 			carrinhobean.SetarCarrinhoSessao(carrinho);

	            }else {
	            	
	            	
//	            	if(carrinho.getId()==null){
//	            		
//	            		carrinho = carrinhobean.getCarrinho();
//			        	UUID uuid = UUID.randomUUID();
//			 			carrinho.setId(uuid);	
//			 			carrinhobean.SetarCarrinhoSessao(carrinho);
//	            		
//	            	}else {
//	            		
//	            		
//	            		
//	            		
//	            	}
	            	            	
	            	
	            }
		
	        
	        
	//	Object carrinhoses = request.getSession().getAttribute("nome"); // getAttribute("

		
		
	     model.addAttribute("carrinho", carrinhobean.getCarrinho()); 
		 model.addAttribute("mesas", mesas);
		 model.addAttribute("garcons", garcons);
		 model.addAttribute("totalitens", carrinhobean.TotalItens());
		 model.addAttribute("origempedidos", origempedidos);

		 


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
    
    
    @RequestMapping(value = "/voltar", method = RequestMethod.GET)
    public ModelAndView ExibirCategoriaa(HttpServletRequest request,final HttpServletResponse response) {
    	
        final String refererUrl = request.getHeader("Referer");
        
        
        System.out.println("obj request: " + request);
        
        System.out.println("obj response: " + response);
        
        
        
        try {
			response.sendRedirect(refererUrl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
        
        String msg = "Produto , " + cat.getNome() + " Adiccionado ao Carrinho";
        
        
        ModelAndView exibircatt = new ModelAndView("redirect:/categoria/exibir?id=" +cat.getCategoria().getId());
        
        Item item = new Item(cat);
        
        item.setQtd("1");
        item.setTotalItem(item.CalcularTotaItem(item.getQtd()).setScale(2, RoundingMode.UP));
        item.setSituacao(SituacaoItem.AGUARDANDO);
        
        carrinhobean.AddItemCarrinho(item);
        
        carrinhobean.getCarrinho().setTotal(carrinhobean.getCarrinho().CalcularTotal().setScale(2, RoundingMode.UP));
        
      System.out.println("url : " + request.getRequestURL());  
      System.out.println("url parameters: " + request.getAttributeNames());  
        
//        request.setAttribute("carrinho", carrinho); // Setando no escopo de requisição
//        request.getSession().setAttribute("carrinho", carrinho); // Setando no escopo de sessão.
    //    context.setAttribute("carrinho", carrinho); // Setando no escopo de aplicação

      exibircatt.addObject("carrinho", carrinhobean.getCarrinho());
      
      exibircatt.addObject("sucesso", msg);

      
//      model.addAttribute("carrinho", carrinhobean.getCarrinho());
//      model.addAttribute("sucesso", msg);


        return exibircatt;
    }
    
    
    @RequestMapping(value = "/adicionarhome", method = RequestMethod.GET)
    public ModelAndView AdicionarProdutoCarrinhoHome(HttpServletRequest request,final HttpServletResponse response,Model model) {
    	
        
    	 UUID idf = UUID.fromString(request.getParameter("id"));

//       ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
       
       //this.carrinho = request.getAttribute("carrinho");
       
      
       
       ModelAndView exibircat = new ModelAndView("redirect:/home");

       Produto cat = produtoService.findOne(idf);
       
       String msg = "Produto , " + cat.getNome() + " Adiccionado ao Carrinho";
       
       
//       ModelAndView exibircatt = new ModelAndView("redirect:/home" );
       
       Item item = new Item(cat);
       
       item.setQtd("1");
       item.setTotalItem(item.CalcularTotaItem(item.getQtd()).setScale(2, RoundingMode.UP));
       item.setSituacao(SituacaoItem.AGUARDANDO);
       
       carrinhobean.AddItemCarrinho(item);
       
       carrinhobean.getCarrinho().setTotal(carrinhobean.getCarrinho().CalcularTotal().setScale(2, RoundingMode.UP));
      
       
     System.out.println("url : " + request.getRequestURL());  
     System.out.println("url parameters: " + request.getAttributeNames().toString());  
       
//       request.setAttribute("carrinho", carrinho); // Setando no escopo de requisição
//       request.getSession().setAttribute("carrinho", carrinho); // Setando no escopo de sessão.
   //    context.setAttribute("carrinho", carrinho); // Setando no escopo de aplicação
     
    
     
    
     
     gerenciacat = new GerenciadorCategorias(restaurantebean.getRestaurante().getCategorias());
     
     Usuario us =   PegarIndicacao();
     
     carrinhobean.SetarProdutosIndicaSessao();
     
   //  destaqueprodLista = ProdutoService.ListaProdutoMaiorAvaliacao(primeiroResultado);
     
    // indicaprodLista = carrinhobean.getProdutosIndica();

     exibircat.addObject("carrinho", carrinhobean.getCarrinho());
     exibircat.addObject("totalitens", carrinhobean.TotalItens());
     exibircat.addObject("promocoesList", restaurantebean.getRestaurante().getPromocoes());
     exibircat.addObject("categoriaListall", restaurantebean.getRestaurante().getCategorias());
     exibircat.addObject("usuarioIndica", carrinhobean.PegarUsuarioIndicaSessao());  
//     
     exibircat.addObject("produtoIndica", carrinhobean.PegarProdutoIndicaSessao());  

     exibircat.addObject("destaqueprodLista",produtoService.ListaProdutoMaiorAvaliacao(primeiroResultado));

     exibircat.addObject("indicaprodLista", carrinhobean.getProdutosIndica());
     
     exibircat.addObject("categoriaListpai", CategoriaService.getCategoriaPai());


     
     exibircat.addObject("sucesso", msg);
    	
    	
     
     
     model.addAttribute("carrinho", carrinhobean.getCarrinho());
     model.addAttribute("totalitens", carrinhobean.TotalItens());
     model.addAttribute("promocoesList", restaurantebean.getRestaurante().getPromocoes());
     model.addAttribute("categoriaListall", restaurantebean.getRestaurante().getCategorias());
     model.addAttribute("usuarioIndica", carrinhobean.PegarUsuarioIndicaSessao());  
//     
     model.addAttribute("produtoIndica", carrinhobean.PegarProdutoIndicaSessao());  

     model.addAttribute("destaqueprodLista",produtoService.ListaProdutoMaiorAvaliacao(primeiroResultado));

     model.addAttribute("indicaprodLista", carrinhobean.getProdutosIndica());
     
     model.addAttribute("categoriaListpai", CategoriaService.getCategoriaPai());


     
     model.addAttribute("sucesso", msg);
    	
    	
    	
    	
    	final String refererUrl = request.getHeader("Referer");
    	        
        
        System.out.println("obj request: " + request);
        
        System.out.println("obj response: " + response);
        
        
        
//        try {
//			response.sendRedirect(refererUrl);
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
       

      
//      model.addAttribute("carrinho", carrinhobean.getCarrinho());
//      model.addAttribute("sucesso", msg);


        return exibircat;
    }
    
    
    
    
    @RequestMapping(value = "/adicionarpedidorapido", method = RequestMethod.GET)
    public ModelAndView AdicionarProdutoCarrinhoPed(HttpServletRequest request,Model model ) {

        UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        //this.carrinho = request.getAttribute("carrinho");
        
       
        
        ModelAndView exibircat = new ModelAndView("/private/pedidovenda/pedidorapido");

        Produto cat = produtoService.findOne(idf);
        
        String msg = "Produto , " + cat.getNome() + " Adiccionado ao Carrinho";
        
        
        ModelAndView exibircatt = new ModelAndView("/private/pedidovenda/pedidorapido");
        
        Item item = new Item(cat);
        
        item.setQtd("1");
        item.setTotalItem(item.CalcularTotaItem(item.getQtd()).setScale(2, RoundingMode.UP));
        item.setSituacao(SituacaoItem.AGUARDANDO);
        
        carrinhobean.AddItemCarrinho(item);
       
        carrinhobean.getCarrinho().setSubtotal(carrinhobean.getCarrinho().CalcularTotal().setScale(2, RoundingMode.UP));
        carrinhobean.getCarrinho().setTotal(carrinhobean.getCarrinho().CalcularTotal().setScale(2, RoundingMode.UP));
        
      System.out.println("url : " + request.getRequestURL());  
      System.out.println("url parameters: " + request.getAttributeNames());  
        
//        request.setAttribute("carrinho", carrinho); // Setando no escopo de requisição
//        request.getSession().setAttribute("carrinho", carrinho); // Setando no escopo de sessão.
    //    context.setAttribute("carrinho", carrinho); // Setando no escopo de aplicação

      exibircatt.addObject("carrinho", carrinhobean.getCarrinho());
      
      exibircatt.addObject("sucesso", msg);
      
      exibircatt.addObject("totalitens", carrinhobean.TotalItens());
      
      exibircatt.addObject("origempedidos", origempedidos);


      
//      model.addAttribute("carrinho", carrinhobean.getCarrinho());
//      model.addAttribute("sucesso", msg);


      return new ModelAndView("redirect:/pedidovenda/pedidorapido?sucesso="+msg);    }
    
    @RequestMapping(value = "/adicionarperfil", method = RequestMethod.GET)
    public ModelAndView AdicionarProdutoCarrinhoPerfil(HttpServletRequest request,Model model ) {

        UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        //this.carrinho = request.getAttribute("carrinho");
        
       
        
        ModelAndView exibircat = new ModelAndView("/public/carrinho/visualizar");

        Produto cat = produtoService.findOne(idf);
        
        String msg = "Produto , " + cat.getNome() + " Adiccionado ao Carrinho";
        
        
        ModelAndView exibircatt = new ModelAndView("redirect:/produto/perfil?id="+ cat.getId() );
        
        Item item = new Item(cat);
        
        item.setQtd("1");
        item.setTotalItem(item.CalcularTotaItem(item.getQtd()).setScale(2, RoundingMode.UP));
        item.setSituacao(SituacaoItem.AGUARDANDO);
        
        carrinhobean.AddItemCarrinho(item);
        
        carrinhobean.getCarrinho().setTotal(carrinhobean.getCarrinho().CalcularTotal().setScale(2, RoundingMode.UP));
        
      System.out.println("url : " + request.getRequestURL());  
      System.out.println("url parameters: " + request.getAttributeNames());  
        
//        request.setAttribute("carrinho", carrinho); // Setando no escopo de requisição
//        request.getSession().setAttribute("carrinho", carrinho); // Setando no escopo de sessão.
    //    context.setAttribute("carrinho", carrinho); // Setando no escopo de aplicação

      exibircatt.addObject("carrinho", carrinhobean.getCarrinho());
      
      exibircatt.addObject("sucesso", msg);

      
//      model.addAttribute("carrinho", carrinhobean.getCarrinho());
//      model.addAttribute("sucesso", msg);


        return exibircatt;
    }
    
    @RequestMapping(value = "/adicionarcardapio", method = RequestMethod.GET)
    public ModelAndView AdicionarProdutoCarrinhoPedr(HttpServletRequest request,Model model ) {

        UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        //this.carrinho = request.getAttribute("carrinho");
        
       
        
        ModelAndView exibircat = new ModelAndView("/public/cardapio");

        Produto cat = produtoService.findOne(idf);
        
        String msg = "Produto , " + cat.getNome() + " Adiccionado ao Carrinho";
        
        
        ModelAndView exibircatt = new ModelAndView("redirect:/cardapio/cardapio");
        
        Item item = new Item(cat);
        
        item.setQtd("1");
        item.setTotalItem(item.CalcularTotaItem(item.getQtd()).setScale(2, RoundingMode.UP));
        item.setSituacao(SituacaoItem.AGUARDANDO);
        
        carrinhobean.AddItemCarrinho(item);
        
        carrinhobean.getCarrinho().setTotal(carrinhobean.getCarrinho().CalcularTotal().setScale(2, RoundingMode.UP));
        
      System.out.println("url : " + request.getRequestURL());  
      System.out.println("url parameters: " + request.getAttributeNames());  
        
//        request.setAttribute("carrinho", carrinho); // Setando no escopo de requisição
//        request.getSession().setAttribute("carrinho", carrinho); // Setando no escopo de sessão.
    //    context.setAttribute("carrinho", carrinho); // Setando no escopo de aplicação

      exibircatt.addObject("carrinho", carrinhobean.getCarrinho());
      
      exibircatt.addObject("sucesso", msg);
      
      exibircatt.addObject("totalitens", carrinhobean.TotalItens());


      
//      model.addAttribute("carrinho", carrinhobean.getCarrinho());
//      model.addAttribute("sucesso", msg);


        return exibircatt;
    }
    
    
    @RequestMapping(value = "/adicionaroferta", method = RequestMethod.GET)
    public ModelAndView AdicionarProdutoCarrinhoPedraa(HttpServletRequest request,Model model ) {

        UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        //this.carrinho = request.getAttribute("carrinho");
        
       
        
        ModelAndView exibircat = new ModelAndView("/public/ofertas");

        Produto cat = produtoService.findOne(idf);
        
        String msg = "Produto , " + cat.getNome() + " Adiccionado ao Carrinho";
        
        
        ModelAndView exibircatt = new ModelAndView("redirect:/promocao/ofertas");
        
        Item item = new Item(cat);
        
        item.setQtd("1");
        item.setTotalItem(item.CalcularTotaItem(item.getQtd()).setScale(2, RoundingMode.UP));
        item.setSituacao(SituacaoItem.AGUARDANDO);
        
        carrinhobean.AddItemCarrinho(item);
        
        carrinhobean.getCarrinho().setTotal(carrinhobean.getCarrinho().CalcularTotal().setScale(2, RoundingMode.UP));
        
      System.out.println("url : " + request.getRequestURL());  
      System.out.println("url parameters: " + request.getAttributeNames());  
        
//        request.setAttribute("carrinho", carrinho); // Setando no escopo de requisição
//        request.getSession().setAttribute("carrinho", carrinho); // Setando no escopo de sessão.
    //    context.setAttribute("carrinho", carrinho); // Setando no escopo de aplicação

      exibircatt.addObject("carrinho", carrinhobean.getCarrinho());
      
      exibircatt.addObject("sucesso", msg);
      
      exibircatt.addObject("totalitens", carrinhobean.TotalItens());
      
      exibircatt.addObject("promocaoList", restaurantebean.getRestaurante().getPromocoes());

      
      


      
//      model.addAttribute("carrinho", carrinhobean.getCarrinho());
//      model.addAttribute("sucesso", msg);


        return exibircatt;
    }
    
    
    @RequestMapping(value = "/finalizar", method = RequestMethod.GET)
    public ModelAndView FinalizarCarrinho(HttpServletRequest request,Model model) {

  //      UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/public/carrinho/finalizar");
        
        origempedidos = OrigemPedido.values();
        
//        Mesa mesa = new Mesa();
        
        
        OrigemPedido origem = OrigemPedido.BALCAO;
        
        mesas = mesaService.findAll();
        garcons = garconService.findAll();
        
        Garcon garcon =garcons.get(0) ;
        
        Mesa mesa = mesas.get(0);


//        Produto cat = produtoService.findOne(idf);
//        
//        Item item = new Item(cat);
//        
        carrinhobean.getCarrinho().setSubtotal(carrinhobean.getCarrinho().CalcularTotal().setScale(2, RoundingMode.UP));
        carrinhobean.getCarrinho().setTotal(carrinhobean.getCarrinho().CalcularTotal().setScale(2, RoundingMode.UP));
        carrinhobean.getCarrinho().setQtd(new BigDecimal(carrinhobean.getCarrinho().getItens().size()).setScale(2, RoundingMode.UP));
        carrinhobean.getCarrinho().setMesa(mesa);
        carrinhobean.getCarrinho().setGarcon(garcon);
        carrinhobean.getCarrinho().setOrigempedido(origem);
        

        
        
        

        exibircat.addObject("carrinho", carrinhobean.getCarrinho());
        
        exibircat.addObject("mesas", mesas);
        
        exibircat.addObject("origempedidos", origempedidos);
        
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

        origempedidos = OrigemPedido.values();
       
        
        
//        model.addAttribute("carrinho", carrinhobean.getCarrinho());

        exibircat.addObject("carrinho", carrinhobean.getCarrinho());
        
        exibircat.addObject("origempedidos", origempedidos);
        
        
       // return new ModelAndView("redirect:/carrinho/finalizar") ;
        
        return exibircat;
    }
    
    
    
    
    
    
    @RequestMapping(value = "/deleteitempedrapido", method = RequestMethod.GET)
    public ModelAndView DeletarItemPedrAPIDO(HttpServletRequest request,Model model) {
    	
    	List<Item> itensaux = new ArrayList<Item>();

        UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/private/pedidovenda/pedidorapido");

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

        origempedidos = OrigemPedido.values();
       
        
        
//        model.addAttribute("carrinho", carrinhobean.getCarrinho());

        exibircat.addObject("carrinho", carrinhobean.getCarrinho());
        
        exibircat.addObject("origempedidos", origempedidos);
        
        
       // return new ModelAndView("redirect:/carrinho/finalizar") ;
        
        return exibircat;
    }
    
    
    @RequestMapping(value = "/finalizaCarrinho", method = RequestMethod.POST)
    public ModelAndView FinalizarCarrinhopOST(HttpServletRequest request,@RequestParam(value = "id", required = false) String id,Locale locale, Model model,@RequestParam(value = "idmesa", required = false) String idmesa,@RequestParam(value = "idgarcon", required = false) String idgarcon,
    		@RequestParam(value = "idorigem", required = false) String idorigem) {
    	
    	
    	if(id != null && id !=""){
    		UUID idf = UUID.fromString(request.getParameter("id"));
    		
    	}
    	
    	if(idmesa != null && idmesa !=""){
            UUID idfmesa = UUID.fromString(request.getParameter("idmesa"));
            Mesa mesa = mesaService.findOne(idfmesa);
            carrinhobean.getCarrinho().setMesa(mesa);


    	}
    	
    	if(idgarcon != null && idgarcon !=""){
            UUID idfgarcon = UUID.fromString(request.getParameter("idgarcon"));
            Garcon garcon = garconService.findOne(idfgarcon);
            carrinhobean.getCarrinho().setGarcon(garcon);


    	}
    	
        
    	if(idorigem != null && idorigem !=""){
            String idforigem = request.getParameter("idorigem");
            OrigemPedido or = OrigemPedido.valueOf(idforigem);
            carrinhobean.getCarrinho().setOrigempedido(or);

    	}
        
        
        String sucesso="Carrinho Finalizado com Sucesso, Nº Pedido";
        
        String erro="Falha ao Finalizar Carrinho";
        
        origempedidos = OrigemPedido.values();
        mesas = mesaService.findAll();
        garcons = garconService.findAll();

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("redirect:/carrinho/finalizar?id=" + carrinhobean.getCarrinho().getId() );

//        Produto cat = produtoService.findOne(idf);
//        
//        Item item = new Item(cat);
//        
//        this.carrinho.setSubtotal(carrinho.CalcularTotal());
//        this.carrinho.setTotal(carrinho.CalcularTotal());
//        this.carrinho.setQtd(new BigDecimal(carrinho.getItens().size()));
//        

        

        
        PedidoVenda pv = new PedidoVenda(carrinhobean.getCarrinho());
        
        pv.setTotal(pv.CalcularTotal(pv.getItems()));
        
        if(carrinhobean.getCarrinho().getMesa() != null){
        	
        	Mesa mesaa =pv.getMesa();
        	mesaa.CalcularTotal();
        	
        	
        }
        
        
       pv = pedidovendaService.save(pv);
       
       List<PedidoVenda> pedidos = pedidovendaService.findAll();
       
//       Carrinho carrinhoo = new Carrinho();
//       
//	   	UUID uuid = UUID.randomUUID();
//	   	carrinhoo.setId(uuid);	
//       
//       carrinhobean.SetarCarrinhoSessao(carrinhoo);
       
       carrinhobean.getCarrinho().getItens().clear();
       
//       String resp = "<p>Sucesso</p>";
        

//        exibircat.addObject("carrinho", carrinho);
        
        exibircat.addObject("mesas", mesas);
//        
        exibircat.addObject("carrinho", carrinhobean.getCarrinho());
        exibircat.addObject("sucesso", sucesso);
        exibircat.addObject("origempedidos", origempedidos);
        exibircat.addObject("garcons", garcons);


        
//       model.addAttribute("pedidovendaList", pedidos);
//       model.addAttribute("carrinho", carrinhobean.getCarrinho());
//       model.addAttribute("sucesso", sucesso);

//        return   new ModelAndView("redirect:/home") ;
        return exibircat;
    }
    

    @RequestMapping(value = "/cancelar", method = RequestMethod.GET)
    public ModelAndView CancelarCarrinho(HttpServletRequest request,Model model) {

        
        ModelAndView exibircat = new ModelAndView("/public/carrinho/visualizar");


//       Carrinho carrinhoo = new Carrinho();
//       
//	   	UUID uuid = UUID.randomUUID();
//	   	carrinhoo.setId(uuid);	
//       
//       carrinhobean.SetarCarrinhoSessao(carrinhoo);
        
        carrinhobean.getCarrinho().getItens().clear();
        carrinhobean.getCarrinho().setTotal(carrinhobean.getCarrinho().CalcularTotal().setScale(2, RoundingMode.UP).setScale(2, RoundingMode.UP));

       
        exibircat.addObject("carrinho", carrinhobean.getCarrinho());
        
        exibircat.addObject("totalitens", carrinhobean.TotalItens());

        return exibircat;
    }
    
	private Usuario PegarIndicacao() {
		// TODO Auto-generated method stub
		
	    Usuario usu = null;
        usuarios = restaurantebean.getRestaurante().getUsuarios();
        
        carrinhobean.SetarUsuariosIndicaSessao(restaurantebean.getRestaurante().getUsuarios());
        
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

//			for(Usuario us: usuarios2){
	    	
	    	
	    	Usuario us = usuarios2.get(indexUsuario);
				
				if(!us.getIndicacoes().isEmpty()){
					
					carrinhobean.SetarUsuarioIndicaSessao(us);
					
					 indexUsuario++;
					 		
					 
					return us;
				}else {
					
					
					
					
					indexUsuario++;
					PegarIndicacao();
					
//					Usuario uss = usuarios2.get(indexUsuario);
//					
//					if(!uss.getIndicacoes().isEmpty()){
//						
//						carrinhobean.SetarUsuarioIndicaSessao(uss);
//						
//						 indexUsuario++;
//						 		
//						 
//						return uss;
//					}else {
//						
//						 indexUsuario++;
//						IndicacaoUsuario(usuarios2);
//					}
					
					return null;
				}
				
//			}

		}

}
