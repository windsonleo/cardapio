package com.tecsoluction.cardapio.controller;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import com.tecsoluction.cardapio.CarrinhoBean;
import com.tecsoluction.cardapio.entidade.Configuracao;
import com.tecsoluction.cardapio.framework.AbstractController;
import com.tecsoluction.cardapio.servico.ConfiguracaoServicoImpl;


@Controller
@RequestMapping(value = "configuracao/")
public class ConfiguracaoController extends AbstractController<Configuracao>    {
	 @Autowired
	private 
	ConfiguracaoServicoImpl confService;
	 
	 
	 private Configuracao configuracao;
	 
	 private String filename="vazio.jpg";
	 
	 private String filenamebanner1="vazio.jpg";
	 private String filenamebanner2="vazio.jpg";
	 
	 
	 private String filenamebanner3="vazio.jpg";
	 
//	    private Carrinho carrinho = new Carrinho();
	    
	    
		 @Autowired
		 private CarrinhoBean carrinhobean;
	 
//	       
//	 @Autowired
//    private
//	ProdutoServicoImpl produtoService;
//   
//	 @Autowired
//    private 
//    ProdutoCompostoServicoImpl produtocompostoService;

	
//    private List<Produto> produtos;

    
    @Autowired
    public ConfiguracaoController(ConfiguracaoServicoImpl dao) {

    	  super("configuracao");
    	this.confService =dao;
//        this.categoriaService = dao;
//        this.produtoService = proddao;
//        this.produtocompostoService = comp;
    }



    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {

//        binder.registerCustomEditor(Categoria.class, new AbstractEditor<Categoria>(this.categoriaService) {
//        });
//        	
//        	binder.registerCustomEditor(Produto.class, new AbstractEditor<Produto>(this.produtoService) {
//        
//        });
//        	
//        	binder.registerCustomEditor(ProdutoComposto.class, new AbstractEditor<ProdutoComposto>(this.produtocompostoService) {
//                
//            });
        

    }

    @ModelAttribute
    public void addAttributes(Model model) {

    	
//    	List<Produto> sugestaoList = produtoService.findAll();
//        
//        List<Categoria> categoriaList = categoriaService.findAll();
//        
//        
    	
    	configuracao = new Configuracao();
    	
//if(carrinho == null){
//        	
//        	carrinho = new Carrinho();
//        	UUID uuid = UUID.randomUUID();
// 			carrinho.setId(uuid);
//            }else {
//            	
////            	UUID uuid = UUID.randomUUID();
////     			carrinho.setId(uuid);	
//            	  	
//            }
    	
        model.addAttribute("filename", filename);
        model.addAttribute("filenamebanner1", filenamebanner1);
        model.addAttribute("filenamebanner2", filenamebanner2);
        model.addAttribute("filenamebanner3", filenamebanner3);
        model.addAttribute("configuracao", configuracao);
        model.addAttribute("carrinho", carrinhobean.getCarrinho()); 


    }



	@Override
	protected ConfiguracaoServicoImpl getservice() {

		return confService;
	}
    
    
//	@RequestMapping(value = "inicio", method = RequestMethod.GET)
//	public ModelAndView Cardapio(Locale locale, Model model,HttpServletRequest request) {
//		
//		
//		ModelAndView cardapio = new ModelAndView("cardapio");
//				
//		return cardapio;
//	}
//	
//	
//	public List<Produto> pegarProdutoPorCategoria(Categoria categoria){
//		
//		
//		
//		
//		return null;
//	}
//
//	@RequestMapping(value = "produtoporcategoria", method = RequestMethod.GET)
//	public ModelAndView CardapioProdutoPorCatgeeoria(Locale locale, Model model,HttpServletRequest request) {
//		
//		ModelAndView cardapio = new ModelAndView("produtoporcategoria");
//		
//		return cardapio;
//	}
//	
//	@RequestMapping(value = "new", method = RequestMethod.GET)
//	public ModelAndView CardapioP(Locale locale, Model model,HttpServletRequest request) {
//		
//		ModelAndView cardapio = new ModelAndView("cardapionew");
//		
//		return cardapio;
//	}
	
	  @RequestMapping(value = "salvarfotoempresa", method = RequestMethod.POST)
	    public ModelAndView SalvarFotoEmpresa(@RequestParam ("file") MultipartFile file, HttpSession session, HttpServletRequest request,
	                             Model model, @ModelAttribute Configuracao usuarior) {
	    	
//	    	Usuario usuario = new Usuario();

	        String sucesso = "Sucesso ao salvar foto";
	        
	        String erros = "Falha ao salvar foto";
	        
//	        ModelAndView cadastro = new ModelAndView("/private/produto/cadastro/cadastroproduto");

	        String path = session.getServletContext().getRealPath("/WEB-INF/classes/static/img/configuracao/");
	        
	        this.filename = file.getOriginalFilename();
	        
	        
//	        heroku não funfa com essas barras
//	        String caminho = path + "\\" + filename;
	        
	        String caminho = path + filename;
	        


	        System.out.println(" path = "  + path );

//	        System.out.println(" caminho" + caminho);
//	        
//	        System.out.println("request D" + d);

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
	            
	           
//	           usuario.setFoto(filename);

	        } catch (Exception e) {

	            System.out.println(e);

	            model.addAttribute("erros", erros + e);
	            model.addAttribute("acao", "add");
	            
	            System.out.println(" não salvou file : " + e);

	        }

//	       Usuario usuario =  new Usuario();
	        
	        this.configuracao = usuarior;
	     
	        this.configuracao.setLogo(filename);
	        
	        
	        model.addAttribute("configuracao", configuracao);
	        
	       return new ModelAndView("redirect:/configuracao/cadastro");

	    }	
	  
	  
	  
	  
	  @RequestMapping(value = "salvarfotobanner1", method = RequestMethod.POST)
	    public ModelAndView SalvarFotobanner1(@RequestParam ("file") MultipartFile file, HttpSession session, HttpServletRequest request,
	                             Model model, @ModelAttribute Configuracao usuarior) {
	    	
//	    	Usuario usuario = new Usuario();

	        String sucesso = "Sucesso ao salvar foto";
	        
	        String erros = "Falha ao salvar foto";
	        
//	        ModelAndView cadastro = new ModelAndView("/private/produto/cadastro/cadastroproduto");

	        String path = session.getServletContext().getRealPath("/WEB-INF/classes/static/img/configuracao/");
	        
	        this.filenamebanner1 = file.getOriginalFilename();
	        
	        
//	        heroku não funfa com essas barras
//	        String caminho = path + "\\" + filename;
	        
	        String caminho = path + filenamebanner1;
	        


	        System.out.println(" path = "  + path );

//	        System.out.println(" caminho" + caminho);
//	        
//	        System.out.println("request D" + d);

	        try {

	            byte barr[] = file.getBytes();

	            BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(caminho));
	            bout.write(barr);
	            bout.flush();
	            bout.close();

	            model.addAttribute("sucesso", sucesso);
	            model.addAttribute("filenamebanner1", filenamebanner1);
	            model.addAttribute("acao", "add");
	            
	            System.out.println(" salvou file : " + filenamebanner1);
	            
	           
//	           usuario.setFoto(filename);

	        } catch (Exception e) {

	            System.out.println(e);

	            model.addAttribute("erros", erros + e);
	            model.addAttribute("acao", "add");
	            
	            System.out.println(" não salvou file : " + e);

	        }

//	       Usuario usuario =  new Usuario();
	        
	        this.configuracao = usuarior;
	     
	        this.configuracao.setBanner1(filenamebanner1);
	        
	        
	        model.addAttribute("configuracao", configuracao);
	        
	       return new ModelAndView("redirect:/configuracao/cadastro");

	    }	
	  
	  
	  
	  @RequestMapping(value = "salvarfotobanner2", method = RequestMethod.POST)
	    public ModelAndView SalvarFotoBanner2(@RequestParam ("file") MultipartFile file, HttpSession session, HttpServletRequest request,
	                             Model model, @ModelAttribute Configuracao usuarior) {
	    	
//	    	Usuario usuario = new Usuario();

	        String sucesso = "Sucesso ao salvar foto";
	        
	        String erros = "Falha ao salvar foto";
	        
//	        ModelAndView cadastro = new ModelAndView("/private/produto/cadastro/cadastroproduto");

	        String path = session.getServletContext().getRealPath("/WEB-INF/classes/static/img/configuracao/");
	        
	        this.filenamebanner2 = file.getOriginalFilename();
	        
	        
//	        heroku não funfa com essas barras
//	        String caminho = path + "\\" + filename;
	        
	        String caminho = path + filenamebanner2;
	        


	        System.out.println(" path = "  + path );

//	        System.out.println(" caminho" + caminho);
//	        
//	        System.out.println("request D" + d);

	        try {

	            byte barr[] = file.getBytes();

	            BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(caminho));
	            bout.write(barr);
	            bout.flush();
	            bout.close();

	            model.addAttribute("sucesso", sucesso);
	            model.addAttribute("filenamebanner2", filenamebanner2);
	            model.addAttribute("acao", "add");
	            
	            System.out.println(" salvou file : " + filenamebanner2);
	            
	           
//	           usuario.setFoto(filename);

	        } catch (Exception e) {

	            System.out.println(e);

	            model.addAttribute("erros", erros + e);
	            model.addAttribute("acao", "add");
	            
	            System.out.println(" não salvou file : " + e);

	        }

//	       Usuario usuario =  new Usuario();
	     
	        this.configuracao.setBanner2(filenamebanner2);
	        
	        model.addAttribute("configuracao", configuracao);
	        
	       return new ModelAndView("redirect:/configuracao/cadastro");

	    }	
	  
	  
	  
	  @RequestMapping(value = "salvarfotobanner3", method = RequestMethod.POST)
	    public ModelAndView SalvarFotoEmpresa3(@RequestParam ("file") MultipartFile file, HttpSession session, HttpServletRequest request,
	                             Model model, @ModelAttribute Configuracao usuarior) {
	    	
//	    	Usuario usuario = new Usuario();

	        String sucesso = "Sucesso ao salvar foto";
	        
	        String erros = "Falha ao salvar foto";
	        
//	        ModelAndView cadastro = new ModelAndView("/private/produto/cadastro/cadastroproduto");

	        String path = session.getServletContext().getRealPath("/WEB-INF/classes/static/img/configuracao/");
	        
	        this.filenamebanner3 = file.getOriginalFilename();
	        
	        
//	        heroku não funfa com essas barras
//	        String caminho = path + "\\" + filename;
	        
	        String caminho = path + filenamebanner3;
	        


	        System.out.println(" path = "  + path );

//	        System.out.println(" caminho" + caminho);
//	        
//	        System.out.println("request D" + d);

	        try {

	            byte barr[] = file.getBytes();

	            BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(caminho));
	            bout.write(barr);
	            bout.flush();
	            bout.close();

	            model.addAttribute("sucesso", sucesso);
	            model.addAttribute("filenamebanner3", filenamebanner3);
	            model.addAttribute("acao", "add");
	            
	            System.out.println(" salvou file : " + filenamebanner3);
	            
	           
//	           usuario.setFoto(filename);

	        } catch (Exception e) {

	            System.out.println(e);

	            model.addAttribute("erros", erros + e);
	            model.addAttribute("acao", "add");
	            
	            System.out.println(" não salvou file : " + e);

	        }

//	       Usuario usuario =  new Usuario();
	     
	        this.configuracao.setBanner3(filenamebanner3);
	        
	        model.addAttribute("configuracao", configuracao);
	        
	       return new ModelAndView("redirect:/configuracao/cadastro");

	    }	
	  
	  
	
}

