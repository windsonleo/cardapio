package com.tecsoluction.cardapio.controller;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletContext;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tecsoluction.cardapio.RestauranteBean;
import com.tecsoluction.cardapio.entidade.Produto;
import com.tecsoluction.cardapio.entidade.Promocao;
import com.tecsoluction.cardapio.framework.AbstractController;
import com.tecsoluction.cardapio.framework.AbstractEditor;
import com.tecsoluction.cardapio.servico.ProdutoServicoImpl;
import com.tecsoluction.cardapio.servico.PromocaoServicoImpl;


/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "promocao/")
public class PromocaoController extends AbstractController<Promocao> {

	private static final Logger logger = LoggerFactory.getLogger(PromocaoController.class);

	 @Autowired
	private final PromocaoServicoImpl promocaoService;
	 
		
		@Autowired
		private ServletContext context;
	 
	 
	 @Autowired
	private final ProdutoServicoImpl produtoService;
	 
	 
	 private Promocao promocao;
	 
//	 private Promocao promocaoProd = new Promocao();
	 
	 
	    private String filename="promo.jpg";
	    
	    private List<Produto> produtoss ;
	    
	    private Set<Produto> prodpromo;
	    
	    private List<Promocao> promocoes ;
	    
	    private List<Produto> produtosFiltro;


		 @Autowired
		 private RestauranteBean restaurantebean; 
	    
	    

	@Autowired
	public PromocaoController( PromocaoServicoImpl roleService,ProdutoServicoImpl prd) {
		super("promocao");
		this.promocaoService = roleService;
		this.produtoService = prd;
		
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {


    	binder.registerCustomEditor(Produto.class, new AbstractEditor<Produto>(this.produtoService) {
            
    });

	}

	@ModelAttribute
	public void addAttributes(Model model) {
		
		logger.info("Welcome add atribute Promocao Controller !" + model);

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
		
//		promocao = new Promocao();
		
		filename="promo.jpg";
		
		produtoss = produtoService.findAll();
		
		promocao = new Promocao();
		
		prodpromo = new HashSet<Produto>();
		 
		 if(!promocao.getProdutos().isEmpty()){
			 
			prodpromo = promocao.getProdutos(); 
			 
		 }else {
			 
			 
		 }
		
		 model.addAttribute("promocao", promocao);
		 model.addAttribute("filename", filename);
		 model.addAttribute("produtoss", produtoss);
		 model.addAttribute("prodpromo", prodpromo);
		 model.addAttribute("promocaoList", getservice().findAll());

		 
		 
		 
		 
		 

	}
	
	
	  @PostMapping(value = "salvarfotopromocao")
	    public ModelAndView SalvarFotoPromo(@RequestParam ("file") MultipartFile file, HttpSession session,
	                                    HttpServletRequest request, Model model) {

	        String sucesso = "Sucesso ao salvar foto";
	        
	        String erros = "Falha ao salvar foto";

//	        String path = session.getServletContext().getRealPath("/WEB-INF/classes/static/img/promocao/");
	        
	        ModelAndView cadastro = new ModelAndView("/private/promocao/cadastro/cadastropromocao");
	        
//	        String path = session.getServletContext().getRealPath("/");

	        String d = request.getContextPath();

	    //   String pathh = "resources/static/img/produto/";
	        // string pathh = file.get

//	        String filename = file.getOriginalFilename();
	        
	        String path = context.getRealPath("/WEB-INF/classes/static/img/promocao/");
	        
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
	        
            model.addAttribute("promocao", promocao);


	        return cadastro;

	    }
	  
	  
	  @RequestMapping(value = "addproduto",method=RequestMethod.GET)
	    public ModelAndView AddProduto( HttpSession session,
	                                    HttpServletRequest request, Model model) {

		
		  ModelAndView addproduto = new ModelAndView("/private/promocao/cadastro/cadastropromocao");
		  
		  prodpromo.clear();
//		  promocao.getProdutos().clear();
		  
		  UUID idfpromo = null;
		  
		  String idpromo = request.getParameter("id");
		  
		  
		  idfpromo = UUID.fromString(idpromo);
		  
		  this.promocao = getservice().findOne(idfpromo);

//		  promocao.getProdutos().clear();
		  
		  
		  String[] ids = null;
		  
		  int qtdparam = request.getParameterValues("produtoss").length;
		  
		  System.out.println("qtd produtos : " + qtdparam);
		  
		  ids = new String[qtdparam];
			
			ids = request.getParameterValues("produtoss");
			
			 System.out.println(" produtos : " + ids.toString());
			
			 Produto p = null;
			 
			 UUID idf = null;
			
			for(int i=0;i<ids.length;i++){
				
				 idf = UUID.fromString(ids[i]);
		  
				//Produto p = new Produto();
				
				p=  produtoService.findOne(idf);
				
				
				prodpromo.add(p);
				
//				p.addPromo(promocao);
		  
			//	this.promocao.addProduto(p);
				
			//	p.addPromo(promocao);
				
				
				
			}
			
			System.out.println("prodpromo : " + prodpromo);
			
			this.promocao.setProdutos(prodpromo);
			
			getservice().save( this.promocao);
			
			
//			produtoService.edit(p)
//	  		UUID idf = UUID.fromString(request.getParameter("produtos"));
//	       
//	  		Produto produto = produtoService.findOne(idf);
//	  		
//	  		this.promocao.addProduto(produto);
			
			model.addAttribute("prodpromo",prodpromo);
			model.addAttribute("promocao", this.promocao);
	  		
	  		return addproduto;

	    }
	  	
	  	
	  	@RequestMapping(value = "excluirproduto",method = RequestMethod.GET )
	    public ModelAndView ExcluirProduto( HttpSession session,
	                                    HttpServletRequest request, Model model) {

	  		
	  		UUID idf = UUID.fromString(request.getParameter("id"));
	       
	  		Produto produto = produtoService.findOne(idf);
	  		
	  		prodpromo.remove(produto);
	  		
//	  		promocao.removeProduto(produto);
	  		
//	  		produto.removePromo(promocao);
	  		
//	  		getservice().edit(promocao);
	  		
	  		 this.promocao.getProdutos().clear();
	  		
	  		 this.promocao.setProdutos(prodpromo);
	  		
	  		getservice().edit( this.promocao);
	  		
			model.addAttribute("prodpromo",prodpromo);
			model.addAttribute("promocao", this.promocao);
	  		
	        return new ModelAndView("forward:/promocao/cadastro/");

	    }
	  
	  
	    @RequestMapping(value = "/perfil", method = RequestMethod.GET)
	    public ModelAndView ExibirPerfilPromocao(HttpServletRequest request) {

	        UUID idf = UUID.fromString(request.getParameter("id"));

//	        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
	        
	        ModelAndView exibircat = new ModelAndView("/public/perfilpromo");

	        Promocao cat = getservice().findOne(idf);

	        exibircat.addObject("promocao", cat);

	        return exibircat;
	    }    
	    
	    
	    @RequestMapping(value = "filtroOfertasHoje", method = RequestMethod.GET)
	    public ModelAndView filtroOfertasHoje(HttpServletRequest request) {

	        // long idf = Long.parseLong(request.getParameter("idpedido"));
	        ModelAndView novosprodutos = new ModelAndView("/public/filtro/exibir");
	        
//	         Pageable primeiroResultado = new PageRequest(0, 10);
//
//	        produtosFiltro = produtoService.ListaProdutoMenorPreco(primeiroResultado);
	        
	        

	        novosprodutos.addObject("produtosFiltro", filtroOfertas());
	        novosprodutos.addObject("filtroNome", "Ofertas de Hoje");


	        return novosprodutos;
	    }
	    
	    
	    
	    public List<Produto> filtroOfertas(){
	     
	    	promocoes = getservice().findAll();
	    	
	    	produtosFiltro = new ArrayList<Produto>();
	    	
	    	for(Promocao promo : promocoes){
	    		
	    		if(promo.isAtivo()){
	    			
	    			produtosFiltro.addAll(promo.getProdutos());
	    			
	    		}else {
	    			
	    			
	    			
	    			
	    		}
	    		
	    		
	    		
	    	}
	        
	        
//	        novosprodutos.addObject("produtosFiltro", produtosFiltro);
//	        novosprodutos.addObject("filtroNome", "filtroOfertasHoje");


	        return produtosFiltro;
	    }
	    
		@RequestMapping(value = "ofertas", method = RequestMethod.GET)
		public ModelAndView CardapioPr(HttpServletRequest request,@RequestParam(value = "erro", required = false) String error, 
	    		@RequestParam(value = "id", required = false) String id,@RequestParam(value = "sucesso", required = false) String sucesso,
	    		Locale locale, Model model) {
	       
			
		
			
			ModelAndView cardapio = new ModelAndView("/public/ofertas");
			
			  String mensagem ="";
		        
		        if(error != null && error !=""){
		        	 mensagem = error + "erros";
		        	 cardapio.addObject("erro", mensagem);
		        	
		        }else if(sucesso != null && sucesso !=""){
		        	
		       	 mensagem = sucesso + "sucesso";
		       	cardapio.addObject("sucesso", mensagem);
		        	
		        }else if(id != null && id !=""){
		        	
		       	 mensagem =  "sucesso"+id;
		       	cardapio.addObject("sucesso", mensagem);
		        	
		        }
			
			
			return cardapio;
		
			
		
		
		
		}     

	@Override
	protected PromocaoServicoImpl getservice() {
		// TODO Auto-generated method stub
		return promocaoService;
	}

}
