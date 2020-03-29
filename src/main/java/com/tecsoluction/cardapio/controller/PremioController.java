package com.tecsoluction.cardapio.controller;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tecsoluction.cardapio.entidade.Premio;
import com.tecsoluction.cardapio.entidade.Produto;
import com.tecsoluction.cardapio.framework.AbstractController;
import com.tecsoluction.cardapio.servico.PremioServicoImpl;
import com.tecsoluction.cardapio.servico.ProdutoServicoImpl;



@Controller
@RequestMapping("premio/")
public class PremioController extends AbstractController<Premio> {
	
	
	private static final Logger logger = LoggerFactory.getLogger(PremioController.class);
	
	
	 private final PremioServicoImpl premioservice;
	 
	 
	 private Premio premio;
	 
	 private List<Premio> premios = new ArrayList<Premio>();
	 
	 
	 private final ProdutoServicoImpl produtoservice;
	 
	 
	 private List<Produto> produtos = new ArrayList<Produto>();
	
	 
	 private String filename="vazio.jpg";
	
    public PremioController(PremioServicoImpl usuimpl,ProdutoServicoImpl prodser) {
		super("premio");
	
		this.premioservice = usuimpl;
		this.produtoservice = prodser;

		
	}
    
    
    @ModelAttribute
    public void addAttributes(Model model) {

    	logger.info("Welcome add atribute Premio Controller !" + premio);

    	
    	
    	
    	
    
    		
    		premio = new Premio();
    		
    	premios = getservice().findAll();
    	
    	produtos = produtoservice.findAll();
    
    	
//    	Genero[] generos = Genero.values();

//        usuario = new Usuario();
//        usuario.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//        usuario = ususervice.findByUsername(usuario.getUsername());

                
//        model.addAttribute("usuarioAtt", usuario);
//        model.addAttribute("generos", generos);
        model.addAttribute("premio", premio);
        model.addAttribute("premios", premios);
        model.addAttribute("produtos", produtos);
        model.addAttribute("filename", filename);

        

    }
    
    
    @RequestMapping(value = "salvarfotopremio", method = RequestMethod.POST)
    public ModelAndView SalvarFotoCliente(@RequestParam ("file") MultipartFile file, HttpSession session, HttpServletRequest request,
                             Model model,@ModelAttribute Premio clienter) {
    	
    	
    	logger.info("Welcome salvar foto premio premio Controller !");
    	
//    	Cliente cliente = new Cliente();
    	
//    	cliente = clienter;

        String sucesso = "Sucesso ao salvar foto";
        
        String erros = "Falha ao salvar foto";
        
//        ModelAndView cadastro = new ModelAndView("/private/produto/cadastro/cadastroproduto");

        String path = session.getServletContext().getRealPath("/WEB-INF/classes/static/img/premio/");
        
        this.filename = file.getOriginalFilename();
        
//        String caminho = path + "\\" + filename;
        
        String caminho = path  + filename;
        


        System.out.println(" path = "  + path );

        System.out.println(" caminho" + caminho);
//        
//        System.out.println("request D" + d);

        try {

            byte barr[] = file.getBytes();

            BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(caminho));
            bout.write(barr);
            bout.flush();
            bout.close();

            model.addAttribute("sucesso", sucesso);
            model.addAttribute("filename", filename);
            model.addAttribute("acao", "add");
//            cliente.setFoto(filename);
            
            System.out.println(" salvou file : " + filename);

        } catch (Exception e) {

            System.out.println(e);

            model.addAttribute("erros", erros + e);
            model.addAttribute("acao", "add");
            
            System.out.println(" não salvou file : " + e);

        }

//     Cliente cliente = new Cliente();
        this.premio.setFoto(filename);
        
       return new ModelAndView("redirect:/premio/cadastro").addObject("premio", premio);

    }
    
    


	@Override
	protected PremioServicoImpl getservice() {
		// TODO Auto-generated method stub
		return premioservice;
	}

}
