package com.tecsoluction.cardapio.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tecsoluction.cardapio.CarrinhoBean;
import com.tecsoluction.cardapio.entidade.Carrinho;
import com.tecsoluction.cardapio.entidade.Item;
import com.tecsoluction.cardapio.entidade.PedidoVenda;
import com.tecsoluction.cardapio.entidade.Produto;
import com.tecsoluction.cardapio.framework.AbstractController;
import com.tecsoluction.cardapio.framework.AbstractEditor;
import com.tecsoluction.cardapio.servico.PedidoVendaServicoImpl;
import com.tecsoluction.cardapio.servico.ProdutoServicoImpl;
import com.tecsoluction.cardapio.util.ModoPreparo;
import com.tecsoluction.cardapio.util.OrigemPedido;
import com.tecsoluction.cardapio.util.SituacaoItem;


/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "pedidovenda/")
public class PedidoVendaController extends AbstractController<PedidoVenda> {

	private static final Logger logger = LoggerFactory.getLogger(PedidoVendaController.class);
	 @Autowired
	private final PedidoVendaServicoImpl PedidoVendaService;
	 
	 
	 @Autowired
	private final ProdutoServicoImpl ProdutoService;
	 
	 private List<PedidoVenda> pedidos;

	 
	 private List<Item> itensprontos ;
	  
	 private Carrinho carrinho = new Carrinho();
	    
	    
		 @Autowired
		 private CarrinhoBean carrinhobean;
		 
		 
	 private List<Item> itensaguardando;
	 
	 
	 private List<Item> itenscancelados;
	 
	 
	 private List<Item> itenspreparacao;
	 
	 private List<Item> itensentregue;
	 
	 private List<Item> itensquente;

	 
	 private List<Item> itensfrio;

	 
	 private List<PedidoVenda> pedidospreparacao;
	 
	 private List<PedidoVenda> pedidosentregue;
	 
	 private List<PedidoVenda> pedidoscancelados;
	 
	 private List<PedidoVenda> pedidosaguardando;
	 
	 private List<PedidoVenda> pedidosprontos;
	 
	 private List<PedidoVenda> pedidoshoje;
	 private OrigemPedido [] origempedidos;


	@Autowired
	public PedidoVendaController(PedidoVendaServicoImpl userService,ProdutoServicoImpl prod) {
		super("pedidovenda");
		this.PedidoVendaService = userService;
		this.ProdutoService = prod;
		
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {

		binder.registerCustomEditor(PedidoVenda.class, new AbstractEditor<PedidoVenda>(this.PedidoVendaService) {
		});

	}

	@ModelAttribute
	public void addAttributes(Model model) {
		
		
		logger.info("Welcome add atribute PedidoVenda Controller !" + model);
        origempedidos = OrigemPedido.values();


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
        pedidos = getservice().findAll();

        
        Date hoje = new Date();
        
//        hoje.;
        
        pedidoshoje = getservice().getAllPedidoPorData(hoje);
        
        
		logger.info("Pedido hoje !" + pedidoshoje);
 
		
	//	 model.addAttribute("pedidovendaList", pedidos);
        
        if(carrinhobean.getCarrinho() == null){
        	carrinho = new Carrinho();
        	UUID uuid = UUID.randomUUID();
 			carrinho.setId(uuid);
 			carrinhobean.SetarCarrinhoSessao(carrinho);
            }else {
            	
//            	UUID uuid = UUID.randomUUID();
//     			carrinho.setId(uuid);
            	
            	
            }
        
        
    	 
    	
        	OrganizarStatusItem(pedidoshoje); 
    	 
    	
        
        
         model.addAttribute("carrinho", carrinhobean.getCarrinho());
		 model.addAttribute("pedidos", pedidos);
		 model.addAttribute("itensaguardando", itensaguardando);
		 model.addAttribute("itenscancelados", itenscancelados);
		 model.addAttribute("itensentregue", itensentregue);
		 model.addAttribute("itenspreparacao", itenspreparacao);
		 model.addAttribute("itensprontos", itensprontos);
		 
		 model.addAttribute("pedidosaguardando", pedidosaguardando);
		 model.addAttribute("pedidoscancelados", pedidoscancelados);
		 model.addAttribute("pedidosentregue", pedidosentregue);
		 model.addAttribute("pedidospreparacao", pedidospreparacao);
		 model.addAttribute("pedidosprontos", pedidosprontos);
		 model.addAttribute("totalitens", carrinhobean.TotalItens());
		 model.addAttribute("pedidoshoje", pedidoshoje);

		 model.addAttribute("origempedidos", origempedidos);

		 
		 
		 

	}
	
	
	
	
//	 @RequestMapping(value = "/adicionarpedidomesa", method = RequestMethod.GET)
//    public ModelAndView AdicionarPedidoMesa(HttpServletRequest request,Model model ) {
//
//        UUID idf = UUID.fromString(request.getParameter("id"));
//
////        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
//        
//        //this.carrinho = request.getAttribute("carrinho");
//        
//       
//        
////        ModelAndView exibircat = new ModelAndView("/public/carrinho/visualizar");
//        
//        Mesa mesa = m
//
//        Produto cat = produtoService.findOne(idf);
//        
//        String msg = "Produto , " + cat.getNome() + " Adiccionado ao Carrinho";
//        
//        
//        ModelAndView exibircatt = new ModelAndView("redirect:/categoria/exibir?id=" +cat.getCategoria().getId());
//        
//        Item item = new Item(cat);
//        
//        item.setQtd("01");
//        item.setTotalItem(item.CalcularTotaItem(item.getQtd()).setScale(2, RoundingMode.UP));
//        item.setSituacao(SituacaoItem.AGUARDANDO);
//        
//        carrinhobean.AddItemCarrinho(item);
//        
//        carrinhobean.getCarrinho().setTotal(carrinhobean.getCarrinho().CalcularTotal().setScale(2, RoundingMode.UP));
//        
//      System.out.println("url : " + request.getRequestURL());  
//      System.out.println("url parameters: " + request.getAttributeNames());  
//        
////        request.setAttribute("carrinho", carrinho); // Setando no escopo de requisição
////        request.getSession().setAttribute("carrinho", carrinho); // Setando no escopo de sessão.
//    //    context.setAttribute("carrinho", carrinho); // Setando no escopo de aplicação
//
//      exibircatt.addObject("carrinho", carrinhobean.getCarrinho());
//      
//      exibircatt.addObject("sucesso", msg);
//
//      
//      model.addAttribute("carrinho", carrinhobean.getCarrinho());
//      model.addAttribute("sucesso", msg);
//
//
//        return exibircatt;
//    }
	
	
    @RequestMapping(value = "/cozinha", method = RequestMethod.GET)
    public ModelAndView ExibirCozinha(HttpServletRequest request) {

//        UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/private/pedidovenda/cozinha");

        pedidoshoje = getservice().getAllPedidoPorData(new Date());
        
      	
      	OrganizarStatusItem(pedidoshoje);  
      	
      	
        if(carrinho == null){
        	carrinho = new Carrinho();
        	UUID uuid = UUID.randomUUID();
 			carrinho.setId(uuid);
 			 carrinhobean.SetarCarrinhoSessao(carrinho);
 		      
        }else {
        	
        	carrinho = carrinhobean.getCarrinho();
            	
            	
            }
        
        
        exibircat.addObject("carrinho", carrinhobean.getCarrinho());
        exibircat.addObject("pedidoshoje", pedidoshoje);

        return exibircat;
    }
    
    
    @RequestMapping(value = "/monitorquente", method = RequestMethod.GET)
    public ModelAndView MonitorCozinhaquente(HttpServletRequest request) {

//        UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/private/pedidovenda/cozinhaquente");

        pedidoshoje = getservice().getAllPedidoPorData(new Date());
        
        
        
      	
      	OrganizarStatusItem(pedidoshoje);  
      	
      	
        if(carrinho == null){
        	carrinho = new Carrinho();
        	UUID uuid = UUID.randomUUID();
 			carrinho.setId(uuid);
 			 carrinhobean.SetarCarrinhoSessao(carrinho);
 		      
        }else {
        	
        	carrinho = carrinhobean.getCarrinho();
            	
            	
            }
        
        
        List<PedidoVenda> pedmonitor= new ArrayList<PedidoVenda>();
        
        pedmonitor.addAll(pedidospreparacao);
        pedmonitor.addAll(pedidosaguardando);
        
        
        exibircat.addObject("carrinho", carrinhobean.getCarrinho());
        exibircat.addObject("itens", itensquente);

        return exibircat;
    }
    
    
    @RequestMapping(value = "/monitorfrio", method = RequestMethod.GET)
    public ModelAndView MonitorCozinhafrio(HttpServletRequest request) {

//        UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/private/pedidovenda/cozinhafrio");

        pedidoshoje = getservice().getAllPedidoPorData(new Date());
        
        
        
      	
      	OrganizarStatusItem(pedidoshoje);  
      	
      	
        if(carrinho == null){
        	carrinho = new Carrinho();
        	UUID uuid = UUID.randomUUID();
 			carrinho.setId(uuid);
 			 carrinhobean.SetarCarrinhoSessao(carrinho);
 		      
        }else {
        	
        	carrinho = carrinhobean.getCarrinho();
            	
            	
            }
        
        
        List<PedidoVenda> pedmonitor= new ArrayList<PedidoVenda>();
        
        pedmonitor.addAll(pedidospreparacao);
        pedmonitor.addAll(pedidosaguardando);
        
        
        exibircat.addObject("carrinho", carrinhobean.getCarrinho());
        exibircat.addObject("itens", itensfrio);

        return exibircat;
    }
    
    
    @RequestMapping(value = "/monitor", method = RequestMethod.GET)
    public ModelAndView MonitorCozinha(HttpServletRequest request) {

//        UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/private/pedidovenda/monitor");

        pedidoshoje = getservice().getAllPedidoPorData(new Date());
        
        
        
      	
      	OrganizarStatusItem(pedidoshoje);  
      	
      	
        if(carrinho == null){
        	carrinho = new Carrinho();
        	UUID uuid = UUID.randomUUID();
 			carrinho.setId(uuid);
 			 carrinhobean.SetarCarrinhoSessao(carrinho);
 		      
        }else {
        	
        	carrinho = carrinhobean.getCarrinho();
            	
            	
            }
        
        
        List<PedidoVenda> pedmonitor= new ArrayList<PedidoVenda>();
        
        pedmonitor.addAll(pedidospreparacao);
        pedmonitor.addAll(pedidosaguardando);
        
        
        exibircat.addObject("carrinho", carrinhobean.getCarrinho());
        exibircat.addObject("pedidoshoje", pedmonitor);

        return exibircat;
    }
    
    
    @RequestMapping(value = "/item/pronto", method = RequestMethod.GET)
    public ModelAndView ExibirCozinhaPRONTO(HttpServletRequest request) {

//        UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/private/pedidovenda/itensPorStatus");
        
        
        
        pedidoshoje = getservice().getAllPedidoPorData(new Date());
        

          	 
          	
              	OrganizarStatusItem(pedidoshoje); 

//        pedidos = getservice().findAllByStatusIsOrderByDataAsc(StatusPedido.PRONTO);
//
//        exibircat.addObject("pedidos", pedidos);
        
        exibircat.addObject("itens", itensprontos);
        
        exibircat.addObject("pedidoshoje", pedidosprontos);

        
        

        return exibircat;
    }
    
    
    @RequestMapping(value = "/item/entregue", method = RequestMethod.GET)
    public ModelAndView ExibirCozinhaENTREGUE(HttpServletRequest request) {

//        UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/private/pedidovenda/itensPorStatus");

     //   pedidos = getservice().findAllByStatusIsOrderByDataAsc(StatusPedido.ENTREGUE);

     //   exibircat.addObject("pedidos", pedidos);
        
        pedidoshoje = getservice().getAllPedidoPorData(new Date());
        

     	 
      	
      	OrganizarStatusItem(pedidoshoje); 
        
        exibircat.addObject("itens", itensentregue);
        exibircat.addObject("pedidoshoje", pedidosentregue);


        return exibircat;
    }
    
    
    @RequestMapping(value = "/item/preparacao", method = RequestMethod.GET)
    public ModelAndView ExibirCozinhaPREPARACAO(HttpServletRequest request) {

//        UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/private/pedidovenda/itensPorStatus");
        
        pedidoshoje = getservice().getAllPedidoPorData(new Date());
        

     	 
      	
      	OrganizarStatusItem(pedidoshoje); 

      //  pedidos = getservice().findAllByStatusIsOrderByDataAsc(StatusPedido.PENDENTE);

        exibircat.addObject("itens", itenspreparacao);
        
        exibircat.addObject("pedidoshoje", pedidospreparacao);


        return exibircat;
    }
    
    
    @RequestMapping(value = "/item/aguardando", method = RequestMethod.GET)
    public ModelAndView ExibirCozinhaAGUARDA(HttpServletRequest request) {

//        UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/private/pedidovenda/itensPorStatus");

//        pedidos = getservice().findAllByStatusIsOrderByDataAsc(StatusPedido.PENDENTE);
//
//        exibircat.addObject("pedidos", pedidos);
        pedidoshoje = getservice().getAllPedidoPorData(new Date());
        

     	 
      	
      	OrganizarStatusItem(pedidoshoje); 
      	
        
        exibircat.addObject("itens", itensaguardando);

        exibircat.addObject("pedidoshoje", pedidosaguardando);

        return exibircat;
    }
    
    
    @RequestMapping(value = "/item/cancelado", method = RequestMethod.GET)
    public ModelAndView ExibirCozinhaCANCELADO(HttpServletRequest request) {

//        UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/private/pedidovenda/itensPorStatus");

//        pedidos = getservice().findAllByStatusIsOrderByDataAsc(StatusPedido.CANCELADO);
//
//        exibircat.addObject("pedidos", pedidos);
        
        pedidoshoje = getservice().getAllPedidoPorData(new Date());
        

     	 
      	
      	OrganizarStatusItem(pedidoshoje); 
        
        
        exibircat.addObject("itens", itenscancelados);
        
        exibircat.addObject("pedidoshoje", pedidoscancelados);


        return exibircat;
    }

    
    
    @RequestMapping(value = "/pedidorapido", method = RequestMethod.GET)
    public ModelAndView ExibirPedidorapido(@RequestParam(value = "erro", required = false) String error, 
    		@RequestParam(value = "id", required = false) String id,@RequestParam(value = "sucesso", required = false) String sucesso,
    		Locale locale, Model model) {

//        UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/private/pedidovenda/pedidorapido");
        
        String mensagem ="";
        
        if(error != null && error !=""){
        	 mensagem = error + "erros";
        	 exibircat.addObject("erro", mensagem);
        	
        }else if(sucesso != null && sucesso !=""){
        	
       	 mensagem = sucesso + "sucesso";
       	exibircat.addObject("sucesso", mensagem);
        	
        }else if(id != null && id !=""){
        	
       	 mensagem =  "sucesso"+id;
       	exibircat.addObject("sucesso", mensagem);
        	
        }

//        pedidos = getservice().findAllByStatusIsOrderByDataAsc(StatusPedido.CANCELADO);
//
//        exibircat.addObject("pedidos", pedidos);
        
//        
//        exibircat.addObject("itens", itenscancelados);
//        
//        exibircat.addObject("pedidos", pedidoscancelados);


        return exibircat;
    }

    
    @RequestMapping(value = "/item/mudarpronto", method = RequestMethod.GET)
    public ModelAndView MudarItemPronto(HttpServletRequest request) {
    	
    	Map<Item,String> itensaux = new HashMap<Item,String>();


        UUID idf = UUID.fromString(request.getParameter("id"));
        
        UUID idfped = UUID.fromString(request.getParameter("idped"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("redirect:/pedidovenda/cozinha");

//        pedidos = getservice().findAllByStatusIsOrderByDataAsc(StatusPedido.CANCELADO);
        
        PedidoVenda pv = getservice().findOne(idfped);
        
        Produto prod = ProdutoService.findOne(idf);
        
//        itensaux = pv.getItems();
        
     //   Item item = new Item(prod);
        
        for (Item key : pv.getItems().keySet()) {
        	
        	if(key.getId().equals(prod.getId())){
        		
        	//	itensaux.remove(key);

        		//key.setSituacao(SituacaoItem.PRONTO);
        		key.MudarStatus(SituacaoItem.PRONTO);
        		
        		itensaux.put(key, key.getQtd());
        		
        	//	pv.addItem(key, key.getQtd());
        		
//        		Item item = new Item(prod);
//        		item.setQtd(key.getQtd());
//        		item.MudarStatus(key.getSituacao());
//        		itensaux.put(key, key.getQtd());
        		
//        		logger.debug("Item entrou pronto: " + item);
        		
        		
        	}else {
        		
        		itensaux.put(key, key.getQtd());
        		
        	//	key.setSituacao(key.getSituacao());
        		
        //		logger.debug("Item não entrou: " + key);
        		
        		
        	}	
        	
        }
       
        pv.getItems().clear();
        pv.setItems(itensaux);

        pv= getservice().save(pv);
      
        pedidoshoje = getservice().getAllPedidoPorData(new Date());
        

     	 
      	
      	OrganizarStatusItem(pedidoshoje); 
        
        
        
//        pedidoshoje = getservice().findAll();

        exibircat.addObject("pedidoshoje", pedidoshoje);
        
        

      //  exibircat.addObject("pedidos", pedidos);

        return exibircat;
    }  
    
    
    
    @RequestMapping(value = "/item/mudarentregar", method = RequestMethod.GET)
    public ModelAndView MudarItemEntregar(HttpServletRequest request) {
    	
    	Map<Item,String> itensaux = new HashMap<Item,String>();


        UUID idf = UUID.fromString(request.getParameter("id"));
        
        UUID idfped = UUID.fromString(request.getParameter("idped"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("redirect:/pedidovenda/cozinha");

//        pedidos = getservice().findAllByStatusIsOrderByDataAsc(StatusPedido.CANCELADO);
        
        PedidoVenda pv = getservice().findOne(idfped);
        
        Produto prod = ProdutoService.findOne(idf);
        
//        itensaux = pv.getItems();
        
     //   Item item = new Item(prod);
        
        for (Item key : pv.getItems().keySet()) {
        	
        	if(key.getId().equals(prod.getId())){
        		
        	//	itensaux.remove(key);

        		//key.setSituacao(SituacaoItem.PRONTO);
        		key.MudarStatus(SituacaoItem.ENTREGUE);
        		
        		itensaux.put(key, key.getQtd());
        		
        	//	pv.addItem(key, key.getQtd());
        		
//        		Item item = new Item(prod);
//        		item.setQtd(key.getQtd());
//        		item.MudarStatus(key.getSituacao());
//        		itensaux.put(key, key.getQtd());
        		
//        		logger.debug("Item entrou pronto: " + item);
        		
        		
        	}else {
        		
        		itensaux.put(key, key.getQtd());
        		
        	//	key.setSituacao(key.getSituacao());
        		
        //		logger.debug("Item não entrou: " + key);
        		
        		
        	}	
        	
        }
       
        pv.getItems().clear();
        pv.setItems(itensaux);

        pv= getservice().save(pv);
        
 pedidoshoje = getservice().getAllPedidoPorData(new Date());
        

     	 
      	
      	OrganizarStatusItem(pedidoshoje); 

        exibircat.addObject("pedidoshoje", pedidoshoje);
        
        

      //  exibircat.addObject("pedidos", pedidos);

        return exibircat;
    }   
    
    
    
    @RequestMapping(value = "/item/mudarpreparacao", method = RequestMethod.GET)
    public ModelAndView MudarItemPreparacao(HttpServletRequest request) {


//    	Map<Item,String> itensaux = null;


    	
    	UUID idf = UUID.fromString(request.getParameter("id"));
        
        UUID idfped = UUID.fromString(request.getParameter("idped"));
        
    	Map<Item,String> itensaux = new HashMap<Item,String>();
        
      

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("redirect:/pedidovenda/cozinha");

//        pedidos = getservice().findAllByStatusIsOrderByDataAsc(StatusPedido.CANCELADO);
        
        PedidoVenda pv = getservice().findOne(idfped);
        
        
        Produto prod = ProdutoService.findOne(idf);
        
   //     itensaux = pv.getItems();
        
     //   Item item = new Item(prod);
        
        for (Item key :  pv.getItems().keySet()) {
        	
        	if(key.getId().equals(prod.getId())){
        		    
        		
        	//	itensaux.remove(key);
        		key.setSituacao(SituacaoItem.EM_EXECUCAO);
        		itensaux.put(key, key.getQtd());
        		
        	//	key.MudarStatus(SituacaoItem.EM_EXECUCAO);
        		
//        		Item item = new Item(prod);
//        		item.setQtd(key.getQtd());
//        		item.MudarStatus(key.getSituacao());
//        		
//        		itensaux.put(key, key.getQtd());
        		
        	//	logger.debug("Item entrou: " + item);
        		
        		
        	}else {
        		
        	//	logger.debug("Item não entrou: " + key);
        		
        	//	key.setSituacao(key.getSituacao());
        		
        		itensaux.put(key, key.getQtd());
        		
        	}	
        	
        }
       
        pv.getItems().clear();
        pv.setItems(itensaux);

        getservice().save(pv);
        
 pedidoshoje = getservice().getAllPedidoPorData(new Date());
        

     	 
      	
      	OrganizarStatusItem(pedidoshoje); 

        exibircat.addObject("pedidoshoje", pedidoshoje);

        return exibircat;
    }  
    
    
    @RequestMapping(value = "/item/mudarcancelar", method = RequestMethod.GET)
    public ModelAndView MudarItemCancelar(HttpServletRequest request) {


//    	Map<Item,String> itensaux = null;


    	
    	UUID idf = UUID.fromString(request.getParameter("id"));
        
        UUID idfped = UUID.fromString(request.getParameter("idped"));
        
    	Map<Item,String> itensaux = new HashMap<Item,String>();
        
      

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("redirect:/pedidovenda/cozinha");

//        pedidos = getservice().findAllByStatusIsOrderByDataAsc(StatusPedido.CANCELADO);
        
        PedidoVenda pv = getservice().findOne(idfped);
        
        
        Produto prod = ProdutoService.findOne(idf);
        
   //     itensaux = pv.getItems();
        
     //   Item item = new Item(prod);
        
        for (Item key :  pv.getItems().keySet()) {
        	
        	if(key.getId().equals(prod.getId())){
        		    
        		
        	//	itensaux.remove(key);
        		key.setSituacao(SituacaoItem.CANCELADO);
        		itensaux.put(key, key.getQtd());
        		
        	//	key.MudarStatus(SituacaoItem.EM_EXECUCAO);
        		
//        		Item item = new Item(prod);
//        		item.setQtd(key.getQtd());
//        		item.MudarStatus(key.getSituacao());
//        		
//        		itensaux.put(key, key.getQtd());
        		
        	//	logger.debug("Item entrou: " + item);
        		
        		
        	}else {
        		
        	//	logger.debug("Item não entrou: " + key);
        		
        	//	key.setSituacao(key.getSituacao());
        		
        		itensaux.put(key, key.getQtd());
        		
        	}	
        	
        }
       
        pv.getItems().clear();
        pv.setItems(itensaux);

        getservice().save(pv);
        
 pedidoshoje = getservice().getAllPedidoPorData(new Date());
        

     	 
      	
      	OrganizarStatusItem(pedidoshoje); 
        exibircat.addObject("pedidoshoje", pedidoshoje);

        return exibircat;
    }  
    
    
    
    
    private void OrganizarStatusItem(List<PedidoVenda> pedidos){
    	
    	itensaguardando = new ArrayList<Item>();
    	itenscancelados = new ArrayList<Item>();
    	itensentregue = new ArrayList<Item>();
    	itenspreparacao = new ArrayList<Item>();
    	itensprontos = new ArrayList<Item>();
    	itensquente = new ArrayList<Item>();
    	itensfrio = new ArrayList<Item>();
    	
    	pedidosaguardando = new ArrayList<PedidoVenda>();
    	pedidoscancelados = new ArrayList<PedidoVenda>();
    	pedidosentregue = new ArrayList<PedidoVenda>();
    	pedidospreparacao  = new ArrayList<PedidoVenda>(); 
    	pedidosprontos = new ArrayList<PedidoVenda>();
    	
    	
    	for( PedidoVenda pv: pedidos){
    		
    		for( Item it: pv.getItems().keySet()){
    			
    			
    			if(it.getSituacao().equals(SituacaoItem.AGUARDANDO)){
    				
    				itensaguardando.add(it);
    				if (pedidosaguardando.contains(pv)){
    					
    					
    				}else {
    				
    				//	boolean todos = false;
    					
    					if(VerificaTodosItens(pv,SituacaoItem.AGUARDANDO)){
    						
    						pedidosaguardando.add(pv);
    						
    					}
    					
    					
    					
    				}
    				
    			}
    			else if (it.getSituacao().equals(SituacaoItem.CANCELADO)){
    				
    				itenscancelados.add(it);
    				
    				if (pedidoscancelados.contains(pv)){
    					
    					
    				}else {
    				
    				//	boolean todos = false;
    					
    					if(VerificaTodosItens(pv,SituacaoItem.CANCELADO)){
    						
    						pedidoscancelados.add(pv);
    						
    					}
    					
    					
    					
    				}
    			}
    			
    			else if (it.getSituacao().equals(SituacaoItem.EM_EXECUCAO)){
    				
    				itenspreparacao.add(it);
    				//pedidospreparacao.add(pv);
    				
    				if (pedidospreparacao.contains(pv)){
    					
    					
    				}else {
    				
    				//	boolean todos = false;
    					
    					if(VerificaTodosItens(pv,SituacaoItem.EM_EXECUCAO)){
    						
    						pedidospreparacao.add(pv);
    						
    					}
    					
    					
    					
    				}
    				
    				
    			}
    			else if (it.getSituacao().equals(SituacaoItem.ENTREGUE)){
    				
    				itensentregue.add(it);
    				//pedidosentregue.add(pv);
    				
    				if (pedidosentregue.contains(pv)){
    					
    					
    				}else {
    				
    				//	boolean todos = false;
    					
    					if(VerificaTodosItens(pv,SituacaoItem.ENTREGUE)){
    						
    						pedidosentregue.add(pv);
    						
    					}
    					
    					
    					
    				}
    				
    				
    			}else {
    				
    				
    				itensprontos.add(it);
    			//	pedidosprontos.add(pv);
    				
    				if (pedidosprontos.contains(pv)){
    					
    					
    				}else {
    				
    				//	boolean todos = false;
    					
    					if(VerificaTodosItens(pv,SituacaoItem.PRONTO)){
    						
    						pedidosprontos.add(pv);
    						
    					}
    					
    					
    					
    				}
    				
    				
    				
    			}
    			
    			
    			if(it.getModopreparo().equals(ModoPreparo.QUENTE)){
    				
    				itensquente.add(it);
    				
    				
    			}else if((it.getModopreparo().equals(ModoPreparo.FRIO))){
    				
    				itensfrio.add(it);
    				
    			}
    			
    		}
    		
    	// final for	
    	}
    	
    	
    	
    }
    
    private boolean VerificaTodosItens(PedidoVenda pv2,SituacaoItem st) {
    
    boolean todos = false;
    	
   	 int qtditempedido = pv2.getItems().size();
   	 
   	 int qtditempronto= 0;
   	 
   	 SituacaoItem situacaopronto = st;
   	 

        for (Item key : pv2.getItems().keySet()) {
          	
          	
          	if(key.getSituacao().equals(situacaopronto)){
          		
          		qtditempronto = qtditempronto +1;
          		
          	}
          	
          	if(qtditempedido == qtditempronto){
          		
          		todos = true;
          		
          	}
          	

          }
        
        
        return todos;
		
	}
    
    
	@Override
	protected PedidoVendaServicoImpl getservice() {
		// TODO Auto-generated method stub
		return PedidoVendaService;
	}

}
