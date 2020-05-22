package com.tecsoluction.cardapio.controller;


import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.tecsoluction.cardapio.CarrinhoBean;
import com.tecsoluction.cardapio.entidade.Categoria;
import com.tecsoluction.cardapio.entidade.Produto;
import com.tecsoluction.cardapio.framework.AbstractController;
import com.tecsoluction.cardapio.framework.AbstractEditor;
import com.tecsoluction.cardapio.servico.CategoriaServicoImpl;
import com.tecsoluction.cardapio.servico.ProdutoServicoImpl;
import com.tecsoluction.cardapio.util.ModoPreparo;
import com.tecsoluction.cardapio.util.UnidadeMedida;

@Controller
@RequestMapping(value = "produto/")
public class ProdutoController extends AbstractController<Produto> {
	
	
	private static final Logger logger = LoggerFactory.getLogger(ProdutoController.class);


//    private final UsuarioServicoImpl userservice;
	 @Autowired
    private final ProdutoServicoImpl produtoService;

	 @Autowired
    private final CategoriaServicoImpl categoriaService;

    private List<Produto> produtoList;

//    @Autowired
//	 private CarrinhoBean carrinhobean;
//    
//	private Carrinho carrinho;



    private List<Categoria> categoriaList;
    
    private String filename="avatar2.png";
    
    private Produto produto;
    
    private List<Produto> produtosFiltro;
    
	 @Autowired
	 private CarrinhoBean carrinhobean;


    @Autowired
    public ProdutoController(ProdutoServicoImpl dao, CategoriaServicoImpl categoriaDao) {
        super("produto");
        this.produtoService = dao;
        this.categoriaService = categoriaDao;
//        this.userservice = usudao;
    }


    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {

        binder.registerCustomEditor(Categoria.class, new AbstractEditor<Categoria>(this.categoriaService) {
        });



    }

    @ModelAttribute
    public void addAttributes(Model model) {
    	
    	
		logger.info("Welcome add atribute Produto Controller !" + model);


        UnidadeMedida[] umList = UnidadeMedida.values();
        
        ModoPreparo[] umListModo = ModoPreparo.values();


//        Usuario usuario = new Usuario();
//        usuario.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//        usuario = userservice.findByUsername(usuario.getUsername());
        
        produto = new Produto();
        
//        filename="avatar2.png";

        categoriaList = categoriaService.findAll();


        produtoList = getservice().findAll();
        
//        if(carrinho == null){
//        	carrinho = new Carrinho();
//        	UUID uuid = UUID.randomUUID();
// 			carrinho.setId(uuid);
////            model.addAttribute("carrinho", carrinho); 
// 			carrinhobean.SetarCarrinhoSessao(carrinho);
//
//            }else {
//            	
//            	
////            	if(carrinho.getId()==null){
////            		
////            		carrinho = carrinhobean.getCarrinho();
////		        	UUID uuid = UUID.randomUUID();
////		 			carrinho.setId(uuid);	
////		 			carrinhobean.SetarCarrinhoSessao(carrinho);
////            		
////            	}else {
////            		
////            		
////            		
////            		
////            	}
//            	            	
//            	
//            }
        
        
        

//        model.addAttribute("usuarioAtt", usuario);
        model.addAttribute("produtosList", produtoList);
        model.addAttribute("categoriaListall", categoriaList);
        model.addAttribute("produto", produto);
        model.addAttribute("umList", umList);
        model.addAttribute("filename", filename);
        model.addAttribute("umListModo", umListModo);

        
        
        
//	     model.addAttribute("carrinho", carrinhobean.getCarrinho()); 
//        model.addAttribute("carrinho", carrinhobean.getCarrinho()); 
//	   	model.addAttribute("totalitens", carrinhobean.TotalItens());
        

    }

    @RequestMapping(value = "novosprodutos", method = RequestMethod.GET)
    public ModelAndView NovosProdutos(HttpServletRequest request) {

        // long idf = Long.parseLong(request.getParameter("idpedido"));
        ModelAndView novosprodutos = new ModelAndView("novosprodutos");

        List<Produto> produtos = produtoService.findAll();

        novosprodutos.addObject("produtosList", produtos);

        return novosprodutos;
    }
    
    
    
    
    @RequestMapping(value = "filtroMenorPreco", method = RequestMethod.GET)
    public ModelAndView filtroMenorPreco(HttpServletRequest request) {

        // long idf = Long.parseLong(request.getParameter("idpedido"));
        ModelAndView novosprodutos = new ModelAndView("/public/filtro/exibir");
        
         Pageable primeiroResultado = new PageRequest(0, 10);

        produtosFiltro = produtoService.ListaProdutoMenorPreco(primeiroResultado);

        novosprodutos.addObject("produtosFiltro", produtosFiltro);
        novosprodutos.addObject("filtroNome", "Menores Preços");


        return novosprodutos;
    }
    
   
    @RequestMapping(value = "filtroMaiorAvaliacao", method = RequestMethod.GET)
    public ModelAndView filtroMaiorAvaliacao(HttpServletRequest request) {

        // long idf = Long.parseLong(request.getParameter("idpedido"));
    	  ModelAndView novosprodutos = new ModelAndView("/public/filtro/exibir");        
         Pageable primeiroResultado = new PageRequest(0, 10);

        produtosFiltro = produtoService.ListaProdutoMaiorAvaliacao(primeiroResultado);

        
        
        novosprodutos.addObject("produtosFiltro", produtosFiltro);
        novosprodutos.addObject("filtroNome", "Maiores Avaliações");


        return novosprodutos;
    }
    
    
    

    @RequestMapping(value = "detalhes", method = RequestMethod.GET)
    public ModelAndView detalhesProduto(HttpServletRequest request) {

        UUID idf = UUID.fromString(request.getParameter("id"));

        ModelAndView detalhesproduto = new ModelAndView("detalhesproduto");

        Produto produto = produtoService.findOne(idf);

//        detalhesproduto.addObject("produto", produto);

        return detalhesproduto;
    }

    @PostMapping(value = "salvarfotoproduto")
    public ModelAndView SalvarFotoProduto(@RequestParam ("file") MultipartFile file, HttpSession session,
                                    HttpServletRequest request, Model model) {

        String sucesso = "Sucesso ao salvar foto";
        
        String erros = "Falha ao salvar foto";

        String path = session.getServletContext().getRealPath("/WEB-INF/classes/static/img/produto/");
        
//        String path = session.getServletContext().getRealPath("/");

        String d = request.getContextPath();

    //   String pathh = "resources/static/img/produto/";
        // string pathh = file.get

//        String filename = file.getOriginalFilename();
        
        this.filename = file.getOriginalFilename();
        
        
        String caminho = path +filename;

        System.out.println("Caminho" + path + " " + filename);

//        System.out.println("request end" + d + pathh + "/" + filename);

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

//            model.addAttribute("erros", erros + e);
//            model.addAttribute("produto", new Produto());
            
            model.addAttribute("erros", erros + e);
            model.addAttribute("acao", "add");
            
            System.out.println(" não salvou file : " + e);

        }
        
        this.produto.setFoto(filename);

        return new ModelAndView("redirect:/produto/cadastro");

    }

    @RequestMapping(value = "gerencia", method = RequestMethod.GET)
    public ModelAndView gerenciarProduto(HttpServletRequest request) {

        ModelAndView gerencia = new ModelAndView("gerenciaproduto");

        gerencia.addObject("produtoList", produtoList);

        return gerencia;
    }

    @RequestMapping(value = "LocalizarProdutoGerencia", method = RequestMethod.POST)
    public ModelAndView gerenciarProdutoLocalizarProduto(HttpServletRequest request) {

        UUID idf = UUID.fromString(request.getParameter("id"));

        ModelAndView gerencia = new ModelAndView("gerenciaproduto");

        Produto produto = produtoService.findOne(idf);

    //    DadosGerenciais dadosgerenciais = new DadosGerenciais(produto);

//        dadosgerenciais.setMargemlucro(new BigDecimal(40.00).setScale(4, RoundingMode.UP));
//        dadosgerenciais.setCusto(produto.getPrecocusto().setScale(4, RoundingMode.UP));
//        dadosgerenciais.setDespesafixa(new BigDecimal(5.00).setScale(4, RoundingMode.UP));
//        dadosgerenciais.setDespesavariavel(new BigDecimal(10.00).setScale(4, RoundingMode.UP));

//        BigDecimal precosugerido = dadosgerenciais.getPrecovenda();
//                
//        BigDecimal margemlucro = dadosgerenciais.getMargemlucro();


//        gerencia.addObject("produto", produto);
//        gerencia.addObject("dadosgerenciais", dadosgerenciais);
//        gerencia.addObject("precosugerido", precosugerido);

        return gerencia;
    }
    
    @RequestMapping(value = "/perfil", method = RequestMethod.GET)
    public ModelAndView ExibirCategoria(HttpServletRequest request, @RequestParam(value = "erro", required = false) String error, 
    		@RequestParam(value = "id", required = false) String id,@RequestParam(value = "sucesso", required = false) String sucesso,
    		Locale locale, Model model) {
        

        UUID idf = UUID.fromString(request.getParameter("id"));
        Produto cat = getservice().findOne(idf);

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/public/perfil");
        
		  String mensagem ="";
	        
	        if(error != null && error !=""){
	        	 mensagem = error + "erros";
	        	 exibircat.addObject("erro", mensagem);
	        	
	        }else if(sucesso != null && sucesso !=""){
	        	
	       	 mensagem = sucesso + "sucesso";
	       	exibircat.addObject("sucesso", mensagem);
	        	
	        }else if(id != null && id !=""){
	        	
//	       	 mensagem =  "sucesso"+id;
//	       	exibircat.addObject("sucesso", mensagem);
	        	
	        }

      

        exibircat.addObject("produto", cat);
        exibircat.addObject("carrinho", carrinhobean.getCarrinho());


//      model.addAttribut

        return exibircat;
    }  
    
    
    @RequestMapping(value = "/avaliar", method = RequestMethod.GET)
    public ModelAndView AvaliarProduto(HttpServletRequest request, Model model,@RequestParam(value = "erro", required = false) String error, 
    		@RequestParam(value = "id", required = false) String id,@RequestParam(value = "sucesso", required = false) String sucesso,
    		Locale locale) {

        ModelAndView exibircat = new ModelAndView("/private/produto/avaliar/avaliar");
     
        String msg = ";";
        
        if(sucesso!=null && sucesso != ""){
        	
        	exibircat.addObject("sucesso", sucesso);
        	
        }
        if(id !=null && id != ""){
        	
            UUID idf = UUID.fromString(request.getParameter("id"));
            Produto cat = getservice().findOne(idf);
            exibircat.addObject("produto", cat);

        	
        }

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        



        return exibircat;
    }  
    
    
    
    @RequestMapping(value = "/avaliarenv", method = RequestMethod.POST)
    public ModelAndView AvaliarProdutoPost(HttpServletRequest request, Model model) {
    	
    	
    	String mensagem = "Avaliado com Sucesso!";
    	
    	String sucesso = "Avaliado com Sucesso!";

      List<Integer> notas = new ArrayList<Integer>();
    	
    	UUID idf = UUID.fromString(request.getParameter("id"));
        
        String nota = request.getParameter("nota");
        
        Integer inteiro = Integer.parseInt(nota);

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
//        ModelAndView exibircat = new ModelAndView("/private/produto/avaliar/avaliar");

        Produto cat = getservice().findOne(idf);
        
        if(!cat.getNotas().isEmpty()){
        	
        	notas = cat.getNotas();
        	
        }else {
        	
        	
        	
        	
        }
        
     //   notas = cat.getNotas();
        
        notas.add(inteiro);
        
//        cat.addNota(inteiro);
        cat.setNotas(notas);
        
        Integer aux = cat.CalcularAvaliacao();
        
    	logger.debug("Calculo Avaliação Controller : " + aux);

      
        cat.setAvaliacao(aux);
        
        
        
        getservice().edit(cat);

        model.addAttribute("produto", cat);
        model.addAttribute("mensagem", mensagem);
        
        

        return new ModelAndView("redirect:/produto/avaliar?id=" + cat.getId()).addObject("sucesso", sucesso);
    }  
    

    @Override
    protected ProdutoServicoImpl getservice() {
        // TODO Auto-generated method stub
        return produtoService;
    }

}
