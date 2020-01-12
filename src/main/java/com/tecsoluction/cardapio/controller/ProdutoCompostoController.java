package com.tecsoluction.cardapio.controller;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

import com.tecsoluction.cardapio.entidade.Categoria;
import com.tecsoluction.cardapio.entidade.Item;
import com.tecsoluction.cardapio.entidade.Produto;
import com.tecsoluction.cardapio.entidade.ProdutoComposto;
import com.tecsoluction.cardapio.framework.AbstractController;
import com.tecsoluction.cardapio.framework.AbstractEditor;
import com.tecsoluction.cardapio.servico.CategoriaServicoImpl;
import com.tecsoluction.cardapio.servico.ProdutoCompostoServicoImpl;
import com.tecsoluction.cardapio.servico.ProdutoServicoImpl;
import com.tecsoluction.cardapio.util.UnidadeMedida;


@Controller
@RequestMapping(value = "produtocomposto/")
public class ProdutoCompostoController extends AbstractController<ProdutoComposto> {

    private static final Logger logger = LoggerFactory.getLogger(ProdutoCompostoController.class);

	
//	private final  UsuarioServicoImpl userservice;
    @Autowired
	private final  ProdutoServicoImpl produtoService;

    @Autowired
	private final  CategoriaServicoImpl categoriaService;
    @Autowired
	private final  ProdutoCompostoServicoImpl produtocompostoService;

	private List<ProdutoComposto> produtoList;

	private List<Produto> prodList;
	
	private Produto produto;

    private Map<Item, String> items = new HashMap<Item, String>();
	private ProdutoComposto produtocomposto = null;

	private BigDecimal totalitem = new BigDecimal(0.00).setScale(2, RoundingMode.UP);
	
    private BigDecimal totalpedido = new BigDecimal(0.00).setScale(2, RoundingMode.UP);
    
    private String filename="vazio.jpg";
    
    


	@Autowired
	public ProdutoCompostoController(ProdutoCompostoServicoImpl dao, CategoriaServicoImpl categoriaDao,ProdutoServicoImpl daoprod) {
		super("produtocomposto");
		
		this.produtocompostoService = dao;
		this.categoriaService = categoriaDao;
//		this.userservice = usudao;
		this.produtoService = daoprod;
//		this.itemService = it;
//		this.items.clear();

	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {

		binder.registerCustomEditor(Categoria.class, new AbstractEditor<Categoria>(this.categoriaService) {
		});

		
		binder.registerCustomEditor(Produto.class, new AbstractEditor<Produto>(this.produtoService) {
		});


	}

	@ModelAttribute
	public void addAttributes(Model model) {

		List<Categoria> categoriaList = categoriaService.findAll();
		produtoList = getservice().findAll();
		prodList = produtoService.findAll();
		
//		if(produtocomposto == null) {
//			
//			produtocomposto = new ProdutoComposto();
////			items.clear();
//
//			
//		}

		UnidadeMedida[] umList = UnidadeMedida.values();

//		Usuario usuario = new Usuario();
//		usuario.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//		usuario = userservice.findByUsername(usuario.getUsername());
//
//		model.addAttribute("usuarioAtt", usuario);
		
//		filename="vazio.jpg";
		
		produto = new Produto();

//		items  = new HashMap<Item, String>();
		
		produtocomposto = new ProdutoComposto();
		
		if(!produtocomposto.getItens_prodcomp().isEmpty()){
			
			items = produtocomposto.getItens_prodcomp();
			
		} else {
			
			
			
			
		}
		
		
		model.addAttribute("produtocompostosList", produtoList);
		model.addAttribute("prodList", prodList);
		model.addAttribute("categoriaListall", categoriaList);
		model.addAttribute("umList", umList);
		model.addAttribute("produtocomposto", produtocomposto);
		model.addAttribute("filename", filename);
		model.addAttribute("produto", produto);
		model.addAttribute("items", items);
		

		

	}

	@RequestMapping(value = "novosprodutos", method = RequestMethod.GET)
	public ModelAndView NovosProdutos(HttpServletRequest request) {

		ModelAndView novosprodutos = new ModelAndView("novosprodutos");

		List<ProdutoComposto> produtos = produtocompostoService.findAll();

		novosprodutos.addObject("produtosList", produtos);
		
        logger.info("Novos do Produtos Compostos!", produtos);


		return novosprodutos;
	}



	@RequestMapping(value = "additem", method = RequestMethod.GET)
	public ModelAndView additemProdutoCompostoForm(HttpSession session,
            										HttpServletRequest request, Model model) {
				
		
		
		ModelAndView additemprodutocomposto = new ModelAndView("/private/produtocomposto/cadastro/cadastroprodutocomposto");
		
		
//		String[] ids = null;
		
		 String idpromo = request.getParameter("id");
		 
		 String idprod = request.getParameter("itens_prodcompp");
		 
		 String qtd = request.getParameter("qtd");
		  
		 UUID idfpromo = null;
		  
		  idfpromo = UUID.fromString(idpromo);
		  
		  this.produtocomposto = getservice().findOne(idfpromo);
		  
		
//		int qtdparam = request.getParameterValues("itens_prodcompp").length;
		
//		ids = new String[qtdparam];
		
//		ids = request.getParameterValues("itens_prodcompp");
		
		UUID idf =null;
		
		Produto p =null;
		
		Item it =null;
		
//		for(int i=0;i<ids.length;i++){
			
//			idf = UUID.fromString(ids[i]);
			
			
//			Produp = new Produto();
		
		idf = UUID.fromString(idprod);
			
			p=  produtoService.findOne(idf);
			
			it = new Item(p);
			
			items.put(it, qtd);
			
//		}
		
//		UnidadeMedida[] umList = UnidadeMedida.values();
	//	UUID idf = UUID.fromString(request.getParameterValues("itens_prodcomp"));
		
//		ModelAndView additemprodutocomposto = new ModelAndView("/private/produtocomposto/cadastro/cadastroprodutocomposto");
		
		this.produtocomposto.setItens_prodcomp(items);
		
		
		
//		this.produto = produtoService.findOne(idf);

//		prodList = produtoService.findAll();
		
//		Item it = new Item(produto);
//		
//		items.put(it, "0");
		
		
	
		
		

//		  totalpedido = produtocomposto.getPrecocusto();
//		  totalitem ;

		produtocomposto.setPrecocusto(produtocomposto.CalcularTotalCusto());
		produtocomposto.setPrecovenda(produtocomposto.CalcularTotalVenda());

//	        DecimalFormat df = new DecimalFormat("0.##");
//	        String totalformatado = df.format(totalpedido);
		
		
		 BigDecimal total =  new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP);
		 
		 BigDecimal qtdd =  new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP);


	        // mudar para trazer pelo id da mesa e pelo status da mesa

	        for (Item item : produtocomposto.getItens_prodcomp().keySet()) {
	        	
	        	 qtdd =  new BigDecimal(produtocomposto.getItens_prodcomp().get(item));
	        

	            total = total.add(item.getPrecoUnitario().multiply(qtdd));
	            
	            item.setTotalItem(total);

	        }

//		additemprodutocomposto.addObject("produtocomposto", produtocomposto);
//		additemprodutocomposto.addObject("prodList", prodList);
//		additemprodutocomposto.addObject("items", items);
//		additemprodutocomposto.addObject("produto", produto);
//		additemprodutocomposto.addObject("filename", filename);
	        


	        
		getservice().save(produtocomposto);
		
		
        model.addAttribute("produtocomposto", produtocomposto);
        model.addAttribute("items", items);

		
        logger.info("Add Item ao Produto Composto Form!", produtocomposto);


        
        
        return additemprodutocomposto;
        
//        return new ModelAndView("forward:/produtocomposto/cadastro")
//        		.addObject("produtocomposto",produtocomposto).addObject("items", items);
//				.addObject(items).addObject(produto).addObject(filename).addObject(umList);
	}
	
	
	@RequestMapping(value = "deleteitem",method = RequestMethod.GET )
    public ModelAndView ExcluirProduto( HttpSession session,
                                    HttpServletRequest request, Model model) {

  		
  		UUID idf = UUID.fromString(request.getParameter("id"));
       
  		Produto produto = produtoService.findOne(idf);
  		
  		items.remove(produto);
  		
//  		promocao.removeProduto(produto);
  		
//  		produto.removePromo(promocao);
  		
//  		getservice().edit(promocao);
  		
//  		promocao.getProdutos().clear();
  		
  		produtocomposto.setItens_prodcomp(items);
  		
  		getservice().edit(produtocomposto);
  		
		model.addAttribute("items",items);
		model.addAttribute("produtocomposto",produtocomposto);
  		
        return new ModelAndView("forward:/produtocomposto/cadastro/");

    }
  

	@RequestMapping(value = "salvaritemprodutocomposto", method = RequestMethod.POST)
	public ModelAndView salvaritemproduto(HttpServletRequest request) {
	
		UUID idf = (UUID.fromString(request.getParameter("id")));
		
        logger.info("IDF ID!", idf);

		
		UUID idfprodcomp = (UUID.fromString(request.getParameter("idprocomp")));
		logger.info("IDFPRODCOMP ID!", idfprodcomp);
		
		
		
		Double prodqtd = Double.parseDouble(request.getParameter("qtd"));

		BigDecimal qtdbc= BigDecimal.valueOf(prodqtd);
	
		
		ModelAndView additemprodutocomposto = new ModelAndView("additemprodutocomposto");

		Produto produto;

		produto = produtoService.findOne(idf);

		if (produto == null) {

			String erros = "Nao Existe esse Produto";

			additemprodutocomposto.addObject("erros", erros);
			additemprodutocomposto.addObject("produtocomposto",
					produtocomposto = produtocompostoService.findOne(idfprodcomp));
			additemprodutocomposto.addObject("prodList", prodList);
			additemprodutocomposto.addObject("totalitem", produtocomposto.getPrecocusto());

			return additemprodutocomposto;
		}

		this.produtocomposto = getservice().findOne(idfprodcomp);

//		Item item = new Item(produto);
//		
//		item.setId(produto.getId());
//		item.setNome(produto.getNome()); 
//		 item.setCodigo(produto.getCodebar()); 
////		 item.setQtd(qtdbc); 
//		 item.setPrecoUnitario(produto.getPrecovenda()); 
//
//		 item.setDescricao(produto.getDescricao()); 
////		 item.setTotalItem(produto.getPrecovenda().multiply(qtdbc)); 
//		 item.setSituacao(SituacaoItem.AGUARDANDO);
//		 item.setUn_medida(produto.getUn_medida());

//			
			items = new HashMap<>();

//			produtocomposto.addItem(item, qtdbc.toString());
//			BigDecimal dec , dicvenda; 
//			dec = produtocomposto.getPrecocusto();
//			dicvenda = produtocomposto.getPrecovenda();
			
			produtocomposto.setPrecocusto(produtocomposto.getPrecocusto());
			produtocomposto.setPrecovenda(produtocomposto.CalcularTotalVenda());
			
		getservice().edit(produtocomposto);

		additemprodutocomposto.addObject("produtocomposto", produtocomposto);
		additemprodutocomposto.addObject("prodList", prodList); 
//		additemprodutocomposto.addObject("totalitem",  produtocomposto.getPrecocusto()); 

        logger.info("Salvar Item ao Produto Composto BD!", produtocomposto);


		return additemprodutocomposto;

	}

      

    @RequestMapping(value = "salvarfotocomposto", method = RequestMethod.POST)
    public ModelAndView SalvarFoto(@RequestParam ("file") MultipartFile file, HttpSession session, HttpServletRequest request, Model model) {


    //    ModelAndView cadastro = new ModelAndView("cadastroprodutocomposto");

        String sucesso = "Sucesso ao salvar foto";
        
        String erros = "Falha ao salvar foto";


        String path = session.getServletContext().getRealPath("/WEB-INF/classes/static/img/produtocomposto/");
        
        
        String path2 = session.getServletContext().getRealPath("/WEB-INF/classes/static/img/produto/");

        String d = request.getContextPath();


        String pathh = "/src/main/resources/static/img/produtocomposto";
        //string pathh = file.get
        
        this. filename = file.getOriginalFilename();

        System.out.println("Caminho" + path + " " + filename);

        System.out.println("request end" + d + pathh + "/" + filename);
        
        System.out.println("request end3" + path + filename);
        
       String caminho = d + pathh + "/" + filename;
       
      String caminhon = caminho.substring(1);
      
      String caminhof1 = path + filename;
      
      String caminhof2 = path2 + filename;
      
      System.out.println("request end 2" + caminhon);
      
      
      System.out.println("request end pathf" + caminhof1);


        try {

            byte barr[] = file.getBytes();

            BufferedOutputStream bout = new BufferedOutputStream(
                    new FileOutputStream(caminhof1));
            bout.write(barr);
            bout.flush();
            bout.close();
            
            BufferedOutputStream bout2 = new BufferedOutputStream(
                    new FileOutputStream(caminhof2));
            bout2.write(barr);
            bout2.flush();
            bout2.close();
            
            

            model.addAttribute("mensagem", sucesso);
            model.addAttribute("filename", filename);
//            model.addAttribute("produtocomposto", produtocomposto);
            model.addAttribute("acao", "add");


        } catch (Exception e) {

            System.out.println(e);

            model.addAttribute("erros", erros + e);

        }
        
        logger.info("Salvar Foto o Produto Composto !", filename);

        this.produtocomposto.setFoto(filename);

        return new ModelAndView("redirect:/produtocomposto/cadastro");

    }


    @RequestMapping(value = "gerencia", method = RequestMethod.GET)
    public ModelAndView gerenciarProduto(HttpServletRequest request) {


        UUID idf = (UUID.fromString(request.getParameter("id")));

        ModelAndView detalhesproduto = new ModelAndView("gerenciaproduto");


        ProdutoComposto produto = produtocompostoService.findOne(idf);

        detalhesproduto.addObject("produto", produto);

        logger.info(""
        		+ "Gerencia  Produto Composto !", produto);


        return detalhesproduto;
    }
    
    @RequestMapping(value = "produzirprodutocomposto", method = RequestMethod.GET)
    public ModelAndView ProduzirProdutoCompsotoForm(HttpServletRequest request) {


        UUID idf = (UUID.fromString(request.getParameter("idprod")));
        
        String qtd= (request.getParameter("qtd"));

       ProdutoComposto produtocomposto = getservice().findOne(idf);
       
       ProduzirComposto(produtocomposto, qtd);
       
        	


        return new ModelAndView("redirect:/cozinha");
    }
    
    
    @RequestMapping(value = "retirar", method = RequestMethod.GET)
    public ModelAndView RetirarProdutoCompsotoForm(HttpServletRequest request) {


        UUID idf = (UUID.fromString(request.getParameter("idprod")));
        
        String qtd= (request.getParameter("qtd"));

        ProdutoComposto produtocomposto = getservice().findOne(idf);

        	Retirar(produtocomposto, qtd);

        return new ModelAndView("redirect:/cozinha");
    }
    
    
    
    public void ProduzirComposto(ProdutoComposto produtocomposto, String qtd){
    	
    	Map<com.tecsoluction.cardapio.entidade.Item, String> itens = produtocomposto.getItens_prodcomp();
    	Map<Item,String> itensaux = new HashMap<>();
    	BigDecimal qtdb = new BigDecimal(qtd);
    	
    	for(com.tecsoluction.cardapio.entidade.Item it : itens.keySet()){
    		
    		String qtd_aux = itens.get(it);
    		BigDecimal qtd_auxb = new BigDecimal(qtd_aux);
    		BigDecimal qtd_aux_final = new BigDecimal("0.00");
    		qtd_aux_final = qtd_auxb.multiply(qtdb);
    		itensaux.put(it, qtd_aux_final.toString());
    		
    		
    	}
    	
    	//retirar essa quantidade do estoque e inserir na Bandeja(tipo outro estoque)
    	
    	System.out.println("Produzir Composto:" + itensaux.toString());
        logger.debug("Produzir Composto:" , itensaux.toString());

    	
    }
    
    public void Retirar(ProdutoComposto produtocomposto, String qtd){
    	
    	Map<com.tecsoluction.cardapio.entidade.Item, String> itens = produtocomposto.getItens_prodcomp();
    	Map<Item,String> itensaux = new HashMap<Item,String>();
    	BigDecimal qtdb = new BigDecimal(qtd);
    	
    	for(com.tecsoluction.cardapio.entidade.Item it : itens.keySet()){
    		
    		String qtd_aux = itens.get(it);
    		BigDecimal qtd_auxb = new BigDecimal(qtd_aux);
    		BigDecimal qtd_aux_final = new BigDecimal("0.00");
    		qtd_aux_final = qtd_auxb.multiply(qtdb);
    		itensaux.put(it, qtd_aux_final.toString());
    		
    		
    	}
    	
    	//retirar essa quantidade do estoque
    	
    	System.out.println("Retirar Composto:" + itensaux.toString());
    	
    }
    

    @Override
    protected ProdutoCompostoServicoImpl getservice() {

    	return produtocompostoService;
    }

   
    
}
