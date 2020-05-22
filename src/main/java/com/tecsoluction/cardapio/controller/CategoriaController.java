package com.tecsoluction.cardapio.controller;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tecsoluction.cardapio.CarrinhoBean;
import com.tecsoluction.cardapio.entidade.Categoria;
import com.tecsoluction.cardapio.entidade.Promocao;
import com.tecsoluction.cardapio.framework.AbstractController;
import com.tecsoluction.cardapio.framework.AbstractEditor;
import com.tecsoluction.cardapio.servico.CategoriaServicoImpl;
import com.tecsoluction.cardapio.servico.PromocaoServicoImpl;

@Controller
@RequestMapping(value = "categoria/")
public class CategoriaController extends AbstractController<Categoria> {
	
	
	private static final Logger logger = LoggerFactory.getLogger(CategoriaController.class);
	
	
	 @Autowired
    private CategoriaServicoImpl categoriaService;
	 
	 
		@Autowired
		private PromocaoServicoImpl PromocaoService = new PromocaoServicoImpl();
		
	    
	    private List<Promocao> promocoes = new ArrayList<Promocao>();
	    
	    private List<Categoria> categorias = new ArrayList<Categoria>();
	    
	    
	    private Categoria categoria;
	    
	    
//	    private Carrinho carrinho;
	    
//	    
		 @Autowired
		 private CarrinhoBean carrinhobean;
		 
		 
		 private String filename;
		 
			@Autowired
			private ServletContext context;


    @Autowired
    public CategoriaController(CategoriaServicoImpl dao,PromocaoServicoImpl pro) {
        super("categoria");
        this.categoriaService = dao;
        this.PromocaoService = pro;
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Categoria.class, new AbstractEditor<Categoria>(this.categoriaService) {

        });
    }

    @ModelAttribute
    public void addAttributes(Model model) {

//		Usuario usuario = new Usuario();
//		usuario.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//		usuario = userservice.findByUsername(usuario.getUsername());
		
		
      //  List<Categoria> categoriaList = getservice().getCategoriaPai();
    	
    	
		logger.info("Welcome add atribute Categoria Controller !" + model);
		
		filename="avatar_usu.jpg";
        
        categoria = new Categoria();
        
        
        promocoes = PromocaoService.findAll();
        
        categorias = getservice().findAll();  
        
		
//		if(carrinho == null){
//        	
//        	carrinho = new Carrinho();
//        	UUID uuid = UUID.randomUUID();
// 			carrinho.setId(uuid);
// 			carrinhobean.SetarCarrinhoSessao(carrinho);
//
//            }else {
//            	
////            	UUID uuid = UUID.randomUUID();
////     			carrinho.setId(uuid);	
//            	  	
//            }
        
        model.addAttribute("promocoesList", promocoes);
        model.addAttribute("categoriaListall", categorias);
		model.addAttribute("categoria", categoria);
        model.addAttribute("carrinho", carrinhobean.getCarrinho()); 
	   	model.addAttribute("totalitens", carrinhobean.TotalItens());
	    model.addAttribute("filename", filename);

    }
    
    
    
    @RequestMapping(value = "/exibir", method = RequestMethod.GET)
    public ModelAndView ExibirCategoria(HttpServletRequest request,@RequestParam(value = "erro", required = false) String error, 
    		@RequestParam(value = "id", required = false) String id,@RequestParam(value = "sucesso", required = false) String sucesso,
    		Locale locale, Model model) {
        

        UUID idf = UUID.fromString(request.getParameter("id"));
              
        Categoria cat = getservice().findOne(idf);
//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/public/exibir");
        
        
		  String mensagem ="";
	        
	        if(error != null && error !=""){
	        	 mensagem = error + "erros";
	        	 exibircat.addObject("erro", mensagem);
	        	
	        }else if(sucesso != null && sucesso !=""){
	        	
	       	 mensagem = sucesso + "sucesso";
	       	exibircat.addObject("sucesso", mensagem);
	        	
	        }else if(id != null && id !=""){
	        	
	       	 mensagem =  "sucesso"+id;
//	       	cardapio.addObject("sucesso", mensagem);
	        	
	        }

       

        exibircat.addObject("categoria", cat);
     
        
//        if(carrinho == null){
//        	carrinho = new Carrinho();
//        	UUID uuid = UUID.randomUUID();
// 			carrinho.setId(uuid);
// 			 carrinhobean.SetarCarrinhoSessao(carrinho);
//      
//            }else {
//            	
////            	carrinho = carrinhobean.getCarrinho();
//            	
////            	UUID uuid = UUID.randomUUID();
////     			carrinho.setId(uuid);
//            	
//            	
//            }
//        
        exibircat.addObject("carrinho", carrinhobean.getCarrinho());
//        
//        model.addAttribute("carrinho", carrinhobean.getCarrinho());

        return exibircat;
    }
    
    
    @RequestMapping(value = "salvarfotocategoria", method = RequestMethod.POST)
    public ModelAndView SalvarFotoProduto2(@RequestParam ("file") MultipartFile file, HttpSession session, HttpServletRequest request,
                             Model model, @ModelAttribute("categoria")  Categoria usuarior) {
    	
//    	Usuario usuario = new Usuario();

        String sucesso = "Sucesso ao salvar foto";
        
        String erros = "Falha ao salvar foto";
        
        ModelAndView cadastro = new ModelAndView("/private/categoria/cadastro/cadastrocategoria");

//        String path = session.getServletContext().getRealPath("/WEB-INF/classes/static/img/usuario/");
        
        String path = context.getRealPath("/WEB-INF/classes/static/img/categoria/");
        
        this.filename = file.getOriginalFilename();
        
        
//        heroku não funfa com essas barras
//        String caminho = path + "\\" + filename;
        
        String caminho = path + filename;
        


        System.out.println(" path = "  + path );

//        System.out.println(" caminho" + caminho);
//        
//        System.out.println("request D" + d);

        try {

            byte barr[] = file.getBytes();

            BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(caminho));
            bout.write(barr);
            bout.flush();
            bout.close();

            usuarior.setFoto(filename);
            
            model.addAttribute("sucesso", sucesso);
            model.addAttribute("filename", filename);
            model.addAttribute("acao", "add");
            model.addAttribute("categoria", categoria);
            System.out.println(" salvou file : " + filename);
            
           
//           usuario.setFoto(filename);

        } catch (Exception e) {

            System.out.println(e);

            model.addAttribute("erros", erros + e);
            model.addAttribute("acao", "add");
            model.addAttribute("categoria", categoria);
            System.out.println(" não salvou file : " + e);

        }

//       Usuario usuario =  new Usuario();
     
        //usuario.setFoto(filename);
        
       return cadastro;

    }

    @Override
    protected CategoriaServicoImpl getservice() {
        return categoriaService;
    }

}
