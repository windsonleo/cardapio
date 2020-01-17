package com.tecsoluction.cardapio.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

		
	//	 model.addAttribute("pedidovendaList", pedidos);
        
        if(carrinho == null){
        	carrinho = new Carrinho();
        	UUID uuid = UUID.randomUUID();
 			carrinho.setId(uuid);
      
            }else {
            	
//            	UUID uuid = UUID.randomUUID();
//     			carrinho.setId(uuid);
            	
            	
            }
        
        
    	 
    	
        	OrganizarStatusItem(pedidos); 
    	 
    	
        
        
         model.addAttribute("carrinho", carrinhobean.getCarrinho());
		 model.addAttribute("pedidos", pedidos);
		 model.addAttribute("itensaguardando", itensaguardando);
		 model.addAttribute("itenscancelados", itenscancelados);
		 model.addAttribute("itensentregue", itensentregue);
		 model.addAttribute("itenspreparacao", itenspreparacao);
		 model.addAttribute("itensprontos", itensprontos);

	}
	
    @RequestMapping(value = "/cozinha", method = RequestMethod.GET)
    public ModelAndView ExibirCozinha(HttpServletRequest request) {

//        UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/private/pedidovenda/cozinha");

        pedidos = getservice().findAll();
        
        if(carrinho == null){
        	carrinho = new Carrinho();
        	UUID uuid = UUID.randomUUID();
 			carrinho.setId(uuid);
      
            }else {
            	
//            	UUID uuid = UUID.randomUUID();
//     			carrinho.setId(uuid);
            	
            	
            }
        
        
        exibircat.addObject("carrinho", carrinhobean.getCarrinho());
        exibircat.addObject("pedidos", pedidos);

        return exibircat;
    }
    
    
    @RequestMapping(value = "/item/pronto", method = RequestMethod.GET)
    public ModelAndView ExibirCozinhaPRONTO(HttpServletRequest request) {

//        UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/private/pedidovenda/itensPorStatus");

//        pedidos = getservice().findAllByStatusIsOrderByDataAsc(StatusPedido.PRONTO);
//
//        exibircat.addObject("pedidos", pedidos);
        
        exibircat.addObject("itens", itensprontos);

        
        

        return exibircat;
    }
    
    
    @RequestMapping(value = "/item/entregue", method = RequestMethod.GET)
    public ModelAndView ExibirCozinhaENTREGUE(HttpServletRequest request) {

//        UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/private/pedidovenda/itensPorStatus");

     //   pedidos = getservice().findAllByStatusIsOrderByDataAsc(StatusPedido.ENTREGUE);

     //   exibircat.addObject("pedidos", pedidos);
        
        exibircat.addObject("itens", itensentregue);


        return exibircat;
    }
    
    
    @RequestMapping(value = "/item/preparacao", method = RequestMethod.GET)
    public ModelAndView ExibirCozinhaPREPARACAO(HttpServletRequest request) {

//        UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/private/pedidovenda/itensPorStatus");

      //  pedidos = getservice().findAllByStatusIsOrderByDataAsc(StatusPedido.PENDENTE);

        exibircat.addObject("itens", itenspreparacao);

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
        
        exibircat.addObject("itens", itensaguardando);

        

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
        
        
        exibircat.addObject("itens", itenscancelados);


        return exibircat;
    }

    
    @RequestMapping(value = "/item/mudarpronto", method = RequestMethod.GET)
    public ModelAndView MudarItemPronto(HttpServletRequest request) {
    	
    	Map<Item,String> itensaux = new HashMap<Item,String>();


        UUID idf = UUID.fromString(request.getParameter("id"));
        
        UUID idfped = UUID.fromString(request.getParameter("idped"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/private/pedidovenda/cozinha");

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
        
        pedidos = getservice().findAll();

        exibircat.addObject("pedidos", pedidos);
        
        

      //  exibircat.addObject("pedidos", pedidos);

        return exibircat;
    }  
    
    
    
    @RequestMapping(value = "/item/mudarentregar", method = RequestMethod.GET)
    public ModelAndView MudarItemEntregar(HttpServletRequest request) {
    	
    	Map<Item,String> itensaux = new HashMap<Item,String>();


        UUID idf = UUID.fromString(request.getParameter("id"));
        
        UUID idfped = UUID.fromString(request.getParameter("idped"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/private/pedidovenda/cozinha");

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
        
        pedidos = getservice().findAll();

        exibircat.addObject("pedidos", pedidos);
        
        

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
        
        ModelAndView exibircat = new ModelAndView("/private/pedidovenda/cozinha");

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
        
        pedidos = getservice().findAll();

        exibircat.addObject("pedidos", pedidos);

        return exibircat;
    }  
    
    
    @RequestMapping(value = "/item/mudarcancelar", method = RequestMethod.GET)
    public ModelAndView MudarItemCancelar(HttpServletRequest request) {


//    	Map<Item,String> itensaux = null;


    	
    	UUID idf = UUID.fromString(request.getParameter("id"));
        
        UUID idfped = UUID.fromString(request.getParameter("idped"));
        
    	Map<Item,String> itensaux = new HashMap<Item,String>();
        
      

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/private/pedidovenda/cozinha");

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
        
        pedidos = getservice().findAll();

        exibircat.addObject("pedidos", pedidos);

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
	protected PedidoVendaServicoImpl getservice() {
		// TODO Auto-generated method stub
		return PedidoVendaService;
	}

}
