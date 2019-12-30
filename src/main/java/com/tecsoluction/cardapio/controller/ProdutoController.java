package com.tecsoluction.cardapio.controller;


import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import com.tecsoluction.cardapio.entidade.Categoria;
import com.tecsoluction.cardapio.entidade.Produto;
import com.tecsoluction.cardapio.framework.AbstractController;
import com.tecsoluction.cardapio.framework.AbstractEditor;
import com.tecsoluction.cardapio.servico.CategoriaServicoImpl;
import com.tecsoluction.cardapio.servico.ProdutoServicoImpl;
import com.tecsoluction.cardapio.util.UnidadeMedida;

@Controller
@RequestMapping(value = "produto/")
public class ProdutoController extends AbstractController<Produto> {

//    private final UsuarioServicoImpl userservice;
	 @Autowired
    private final ProdutoServicoImpl produtoService;

	 @Autowired
    private final CategoriaServicoImpl categoriaService;

    private List<Produto> produtoList;



    private List<Categoria> categoriaList;
    
    private String filename="avatar2.png";
    
    private Produto produto;


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

        UnidadeMedida[] umList = UnidadeMedida.values();

//        Usuario usuario = new Usuario();
//        usuario.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//        usuario = userservice.findByUsername(usuario.getUsername());
        
        produto = new Produto();

        categoriaList = categoriaService.findAll();


        produtoList = getservice().findAll();

//        model.addAttribute("usuarioAtt", usuario);
        model.addAttribute("produtosList", produtoList);
        model.addAttribute("categoriaListall", categoriaList);
        model.addAttribute("produto", produto);
        model.addAttribute("umList", umList);
        model.addAttribute("filename", filename);
        
        

    }

    @RequestMapping(value = "novosprodutos", method = RequestMethod.GET)
    public ModelAndView NovosProdutos(HttpServletRequest request) {

        // long idf = Long.parseLong(request.getParameter("idpedido"));
        ModelAndView novosprodutos = new ModelAndView("novosprodutos");

        List<Produto> produtos = produtoService.findAll();

        novosprodutos.addObject("produtosList", produtos);

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

        return new ModelAndView("redirect:/produto/cadastro").addObject("produto", produto);

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
    public ModelAndView ExibirCategoria(HttpServletRequest request) {

        UUID idf = UUID.fromString(request.getParameter("id"));

//        ModelAndView exibircat = new ModelAndView("/private/categoria/exibir");
        
        ModelAndView exibircat = new ModelAndView("/public/perfil");

        Produto cat = getservice().findOne(idf);

        exibircat.addObject("produto", cat);

        return exibircat;
    }  
    

    @Override
    protected ProdutoServicoImpl getservice() {
        // TODO Auto-generated method stub
        return produtoService;
    }

}
