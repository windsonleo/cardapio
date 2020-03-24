package com.tecsoluction.cardapio;


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.tecsoluction.cardapio.controller.HomeController;
import com.tecsoluction.cardapio.entidade.Carrinho;
import com.tecsoluction.cardapio.entidade.Categoria;
import com.tecsoluction.cardapio.entidade.Configuracao;
import com.tecsoluction.cardapio.entidade.Garcon;
import com.tecsoluction.cardapio.entidade.Item;
import com.tecsoluction.cardapio.entidade.Mesa;
import com.tecsoluction.cardapio.entidade.PedidoVenda;
import com.tecsoluction.cardapio.entidade.Produto;
import com.tecsoluction.cardapio.entidade.ProdutoComposto;
import com.tecsoluction.cardapio.entidade.Promocao;
import com.tecsoluction.cardapio.entidade.Restaurante;
import com.tecsoluction.cardapio.entidade.Usuario;
import com.tecsoluction.cardapio.exception.CustomGenericException;
import com.tecsoluction.cardapio.exception.LoggingAccessDeniedHandler;
import com.tecsoluction.cardapio.servico.CategoriaServicoImpl;
import com.tecsoluction.cardapio.servico.ConfiguracaoServicoImpl;
import com.tecsoluction.cardapio.servico.GarconServicoImpl;
import com.tecsoluction.cardapio.servico.MesaServicoImpl;
import com.tecsoluction.cardapio.servico.PedidoVendaServicoImpl;
import com.tecsoluction.cardapio.servico.ProdutoCompostoServicoImpl;
import com.tecsoluction.cardapio.servico.ProdutoServicoImpl;
import com.tecsoluction.cardapio.servico.PromocaoServicoImpl;
import com.tecsoluction.cardapio.servico.UsuarioServicoImpl;

import javassist.NotFoundException;





/**
 * Created by clebr on 01/09/2016.
 */
@ControllerAdvice
public class ContextoAplicacao {
	

	 
	 private static final Logger logger = LoggerFactory.getLogger(ContextoAplicacao.class);
	 
	 @Autowired
	 private UsuarioServicoImpl userService;
	 
	 @Autowired
	    private CategoriaServicoImpl categoriaService;
	 
	 @Autowired
	  private ConfiguracaoServicoImpl ConfiguracaoService;
	 
	 @Autowired
	  private ProdutoServicoImpl produtoService;
	 
	 @Autowired
	  private ProdutoCompostoServicoImpl produtoCompostoService;
	 
	 @Autowired
	  private PromocaoServicoImpl promocaoService;
	 
	 @Autowired
	private  PedidoVendaServicoImpl PedidoVendaService;
	 
	 @Autowired
	private  MesaServicoImpl mesaService;
	 
		
	 @Autowired
	private  GarconServicoImpl GarconService;
	 
	 
	 private List<Garcon> GarconLista ;
	 
	 private List<Mesa> MesaLista ;

	 private List<PedidoVenda> PedidoVendaLista ;

	 private List<Promocao> PromocaoLista ;

	 private List<ProdutoComposto> ProdutoCompostoLista ;
	 
	 private List<Produto> ProdutoLista ;

	 
	 
	 
	 private List<Categoria> categoriaLista ;
	 
	 private Configuracao configuracaoAtual; 
	 	 
	 private List<Configuracao> configuracaoLista ;
	 
	 


	 private Usuario usuario;
	 
	 private Date hoje;
	 	
	 private boolean estaaberto;
	 
	 private boolean nmostrar = true;
	 
	 private boolean acessoubanco = false;
	 
	 private boolean  primeiroacesso = true;
	 
	  private boolean esconderOver = true;
	 
	 private List<Usuario> usuarios ;
	
	 private Usuario usuarioIndica = new Usuario();
	 
	 private Produto produtoIndica = new Produto();
	 
	 private Carrinho  carrinho;
	 
	 private int totalmsg;

	private	List<Categoria> categorias = new ArrayList<Categoria>();

	 
	 @Autowired
	 private CarrinhoBean carrinhobean;
	 
	 @Autowired
	 private RestauranteBean restaurantebean;
	 
	 private Restaurante restaurante;

	 
	 
	 private Timer timer;
	 
	 int  index = 0;
	 	

	// @Autowired
	// public ContextoAplicacao(UsuarioServicoImpl sevice) {
	//
	// this.userservice = sevice;
	// }

	@Autowired
	public ContextoAplicacao() {

		// this.userservice = sevice;
	}

	@ModelAttribute
	public void addAttributes(Model model) {

		// Usuario usuarioAtt = dao.PegarPorId(100L);
		//
		// model.addAttribute("usuarioAtt", usuarioAtt);
		
        //inicio de ususario logado
		
		
//		categorias = categoriaService.findAll();
//		
//		categoriaLista = CategoriasComProduto(categorias);
		
		
//		Long qtdRegistroConfiuracao = ConfiguracaoService.count();
//		
//		if(qtdRegistroConfiuracao != null && qtdRegistroConfiuracao > 0 ){
//			
//			
//			configuracaoLista = ConfiguracaoService.PegarConfiguracaoAtualLista();
//			
//			configuracaoAtual = configuracaoLista.get(0);
//			
//		}else {
//			
//			
//			configuracaoAtual = ConfiguracaoService.PegarConfiguracaoAtual();
//			
//		}
//		
//		if(configuracaoAtual == null){
//			
//			Date dati = new Date();
//			dati.setHours(18);
//			dati.setMinutes(00);
//			
//			Date datf = new Date();
//			datf.setHours(23);
//			datf.setMinutes(00);
//			
//			configuracaoAtual = new Configuracao();
//			
//			configuracaoAtual.setAtivo(true);
//			configuracaoAtual.setBanner1("banner.png");
//			configuracaoAtual.setBanner2("banner.png");
//			configuracaoAtual.setBanner3("banner.png");
//			configuracaoAtual.setCorcard("blue");
//			configuracaoAtual.setCormenu("blue");
//			configuracaoAtual.setCortopo("blue");
//			configuracaoAtual.setLogo("logo.png");
//			configuracaoAtual.setNomeempresa("Teste");
//			configuracaoAtual.setUrlface("https://www.facebook.com");
//			configuracaoAtual.setUrlgmail("https://www.gmail.com");
//			configuracaoAtual.setUrlinsta("https://www.instagram.com");
//			configuracaoAtual.setHoraabertura(dati);
//			configuracaoAtual.setHorafechamento(datf);
//			configuracaoAtual.setEndereco("Av.Dois Irmão 547");
//			configuracaoAtual.setTelefone("81-9981-7766");
//
//			
//			
//			
//		}
		
		
//		if(timer == null && primeiroacesso){
//			timer = new Timer();
//			AgendadorTarefa();
//			primeiroacesso = false;
//			 PegarIndicacao();
//			
//		}else {
//			
//			 PegarIndicacao();
//			
//		}
        
//         usuario = new Usuario();
//        usuario.setEmail(SecurityContextHolder.getContext().getAuthentication().getName());
//        usuario = userService.findByEmail(usuario.getEmail());
//  
//        //verifica se há usuario cadastrado
//        if(usuario == null) {
//        	
//        model.addAttribute("mensagem", "Carregado Usuario Cliente");
//        
//        usuario = new Usuario();
//        UUID idf = UUID.fromString("4b71a569-c0bd-41a2-bffe-35e39e1a875a");
//        usuario = userService.findOne(idf);
//        usuario.setDataultimoAcesso(new Date());	
//        usuario.setOnline(true);
//        	
//        } else {
//        	
//        	usuario.setOnline(true);
//        	usuario.setDataultimoAcesso(new Date());
//    	
//	        model.addAttribute("mensagem", "Bem-Vindo " + usuario.getEmail());
//
//        
//        }
        
//         hoje = new Date();
//         
//         estaaberto = VerificaHorarioFechamento(configuracaoAtual);
         
//         if(carrinhobean.getCarrinho() == null){
//	        	carrinho = new Carrinho();
//	        	UUID uuid = UUID.randomUUID();
//	 			carrinho.setId(uuid);
//	 			//carrinhobean.SetarCarrinhoSessao(carrinho);
//	          //  model.addAttribute("carrinho", carrinho); 
//	            }else {
//	            	
////	            	UUID uuid = UUID.randomUUID();
////		 			carrinho.setId(uuid);
//	            	
//	            	carrinho=carrinhobean.getCarrinho();
//	            	
//	            }
         
//         if(carrinho == null){
//         	
//         	carrinho = new Carrinho();
//         	UUID uuid = UUID.randomUUID();
//  			carrinho.setId(uuid);
//  			
//  			carrinhobean.SetarCarrinhoSessao(carrinho);
//         	
//       
//         }else {
//        	 
//         //	carrinho = carrinhobean.getCarrinho();
//         // 	UUID uuid = UUID.randomUUID();
//   		//	carrinho.setId(uuid);
//        //	 carrinho = carrinhobean.getCarrinho();
//   			carrinhobean.SetarCarrinhoSessao(carrinho);
//   			
//   			
//         	
//         	
//         }
         
         
         
         
         if(restaurante == null){
          	
        	 restaurante = new Restaurante();
          	UUID uuid = UUID.randomUUID();
          	restaurante.setId(uuid);
          	
          	PegarListagemDasEntidades();
          	ValidarEntidades();
          	AdicionarAoRestaurante();
   			
          	restaurantebean.SetarRestauranteSessao(restaurante);
          	
        
          }else {
         	 
        	  	
        		ValidarEntidades();
            	restaurantebean.SetarRestauranteSessao(restaurante);
    			
    			
          	
          	
          }
         
         
         
         
//         if((carrinhobean.getCarrinho() == null)&&(carrinho == null)){
//	        	carrinho = new Carrinho();
//	        	UUID uuid = UUID.randomUUID();
//	 			carrinho.setId(uuid);
//	 			carrinhobean.SetarCarrinhoSessao(carrinho);
//	          //  model.addAttribute("carrinho", carrinho); 
//	            }else if((carrinhobean.getCarrinho() == null)&&(carrinho != null)) {
//	            	
////	            	UUID uuid = UUID.randomUUID();
////		 			carrinho.setId(uuid);
//	            	
//	            	//carrinho = carrinhobean.getCarrinho();
//	            	
//	            }
//         
//				else if((carrinhobean.getCarrinho() != null)&&(carrinho != null)) {
//					            	
//				//	            	UUID uuid = UUID.randomUUID();
//				//		 			carrinho.setId(uuid);
//					            	
//					            //	carrinho = carrinhobean.getCarrinho();
//					            	
//					            }
//         
//				else if((carrinhobean.getCarrinho() != null)&&(carrinho == null)) {
//	            	
//				//	            	UUID uuid = UUID.randomUUID();
//				//		 			carrinho.setId(uuid);
//					            	
//					            	carrinho = carrinhobean.getCarrinho();
//					            	
//					            }
         
       //  carrinhobean.SetarCarrinhoSessao(carrinho);
         
         
//         if(!nmostrar && acessoubanco){
//        	 
//        	usuarioIndica = PegarIndicacao();
//        	
////        	produtoIndica ;
//        	
//        	 Iterator<Produto> i= usuarioIndica.getIndicacoes().iterator();  
//             if(i.hasNext())  
//             {  
//            	 esconderOver = true;
//            	 
//            	 produtoIndica = i.next();  
//            	 model.addAttribute("usuarioIndica", usuarioIndica);
//            	 model.addAttribute("produtoIndica", produtoIndica);
//            	 model.addAttribute("mostrar", nmostrar);
//             	 model.addAttribute("esconderOver", esconderOver);
//            
//             }  else {
//            	 
//            	 
//            	 
//             }
//
//
//        	 
//        	 
//         }else {
//        	 
//        	 
//        	 
//         }
         
         
         
       //  carrinhobean.SetarCarrinhoSessao(carrinho);
         
      //   BigDecimal tot = carrinhobean.TotalItens();
         
     
        model.addAttribute("usuarioAtt", usuario);
        model.addAttribute("categoriaLista", categoriaLista);
        model.addAttribute("configuracaoAtual", configuracaoAtual);
        model.addAttribute("hoje", hoje);
        model.addAttribute("estaaberto", estaaberto);
        model.addAttribute("carrinho", carrinhobean.getCarrinho());
	   	 model.addAttribute("totalitens", carrinhobean.TotalItens());
	   	 model.addAttribute("totalmsg", usuario.getMensagens().size());

	   	 
	   	 model.addAttribute("categorias", categorias);
	   	 model.addAttribute("usuarios", userService.findAll());
//	   	 model.addAttribute("esconderOver", esconderOver);
	   	
     
        
	}

	private void ValidarEntidades() {

		ValidarConfiguracao();
		
		ValidaUsuario();
		
		ValidaCarrinho();
		
		
	}

	private void AdicionarAoRestaurante() {

		restaurante.setCategorias(categorias);
		restaurante.setGarcons(GarconLista);
		restaurante.setMesas(MesaLista);
		
		restaurante.setPedidosvendas(PedidoVendaLista);
		
		restaurante.setProdutos(ProdutoLista);
		restaurante.setProdutoscompostos(ProdutoCompostoLista);
		restaurante.setPromocoes(PromocaoLista);
		restaurante.setUsuarios(usuarios);
		
		
		
		
	}

	private void PegarListagemDasEntidades() {
		
		categorias = categoriaService.findAll();
		
		categoriaLista = CategoriasComProduto(categorias);

		ProdutoCompostoLista = produtoCompostoService.findAll();
		
		ProdutoLista = produtoService.findAll();
		
		PromocaoLista = promocaoService.findAll();
		
		MesaLista = mesaService.findAll();
		
		PedidoVendaLista = PedidoVendaService.findAll();
		
		GarconLista =GarconService.findAll();
		
		usuarios = userService.findAll();
		

		
		
	}

	private void ValidaCarrinho() {
		
		
         if(carrinho == null){
         	
         	carrinho = new Carrinho();
         	UUID uuid = UUID.randomUUID();
  			carrinho.setId(uuid);
  			
  			carrinhobean.SetarCarrinhoSessao(carrinho);
         	
       
         }else {
        	 
         //	carrinho = carrinhobean.getCarrinho();
         // 	UUID uuid = UUID.randomUUID();
   		//	carrinho.setId(uuid);
        //	 carrinho = carrinhobean.getCarrinho();
   			carrinhobean.SetarCarrinhoSessao(carrinho);
   			
   			
         	
         	
         }

		
		
	}

	private void ValidaUsuario() {

		
		
		usuario = new Usuario();
        usuario.setEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        usuario = userService.findByEmail(usuario.getEmail());
  
        //verifica se há usuario cadastrado
        if(usuario == null) {
        	
     //   model.addAttribute("mensagem", "Carregado Usuario Cliente");
        
        usuario = new Usuario();
        UUID idf = UUID.fromString("4b71a569-c0bd-41a2-bffe-35e39e1a875a");
        usuario = userService.findOne(idf);
        usuario.setDataultimoAcesso(new Date());	
        usuario.setOnline(true);
        	
        } else {
        	
        	usuario.setOnline(true);
        	usuario.setDataultimoAcesso(new Date());
    	
	   //     model.addAttribute("mensagem", "Bem-Vindo " + usuario.getEmail());

        
        }
	}

	private void ValidarConfiguracao() {
		
		Long qtdRegistroConfiuracao = ConfiguracaoService.count();
		
		if(qtdRegistroConfiuracao != null && qtdRegistroConfiuracao > 0 ){
			
			
			configuracaoLista = ConfiguracaoService.PegarConfiguracaoAtualLista();
			
			configuracaoAtual = configuracaoLista.get(0);
			
		}else {
			
			
			configuracaoAtual = ConfiguracaoService.PegarConfiguracaoAtual();
			
		}	
		
		
if(configuracaoAtual == null){
			
			Date dati = new Date();
			dati.setHours(18);
			dati.setMinutes(00);
			
			Date datf = new Date();
			datf.setHours(23);
			datf.setMinutes(00);
			
			configuracaoAtual = new Configuracao();
			
			configuracaoAtual.setAtivo(true);
			configuracaoAtual.setBanner1("banner.png");
			configuracaoAtual.setBanner2("banner.png");
			configuracaoAtual.setBanner3("banner.png");
			configuracaoAtual.setCorcard("blue");
			configuracaoAtual.setCormenu("blue");
			configuracaoAtual.setCortopo("blue");
			configuracaoAtual.setLogo("logo.png");
			configuracaoAtual.setNomeempresa("Teste");
			configuracaoAtual.setUrlface("https://www.facebook.com");
			configuracaoAtual.setUrlgmail("https://www.gmail.com");
			configuracaoAtual.setUrlinsta("https://www.instagram.com");
			configuracaoAtual.setHoraabertura(dati);
			configuracaoAtual.setHorafechamento(datf);
			configuracaoAtual.setEndereco("Av.Dois Irmão 547");
			configuracaoAtual.setTelefone("81-9981-7766");

			
			
			
		}


		hoje = new Date();
		
		estaaberto = VerificaHorarioFechamento(configuracaoAtual);
	
	
	}

	private Usuario PegarIndicacao() {
		// TODO Auto-generated method stub
		
	    Usuario usu = null;
        usuarios = userService.findAll();
        acessoubanco = true;
        
        usu = IndicacaoUsuario(usuarios);
        
		
		return usu;
	}
	
	
	

	private Usuario IndicacaoUsuario(List<Usuario> usuarios2) {

		for(Usuario us: usuarios2){
			
			if(!us.getIndicacoes().isEmpty()){
				
				
				return us;
			}
			
		}
		
		
		return null;
	}
	
	
	private BigDecimal TotalItens(Carrinho carrinho) {

		BigDecimal qtd = new BigDecimal(0);
		
		
		if(!carrinho.getItens().isEmpty()){
		
		for(Item us: carrinho.getItens()){
			
			BigDecimal qtdaux = new BigDecimal(us.getQtd());
			qtd = qtd.add(qtdaux);
			
		}
		
		}else {
			
			
			
			
		}
		
		
		return qtd;
	}
	
	
	  @ExceptionHandler(NotFoundException.class)
	  public ModelAndView errorPage() {
		  
			ModelAndView model = new ModelAndView("/public/error/erro");
			
			model.addObject("errCode", "55055");
			model.addObject("errMsg", "Pagina não encontrada");
	        model.addObject("usuarioAtt", usuario);
	        model.addObject("categoriaLista", categoriaLista);
	        model.addObject("configuracaoAtual", configuracaoAtual);
	        model.addObject("hoje", hoje);
	        model.addObject("estaaberto", estaaberto);
	        
	        if(carrinho == null){
	        	
	        	carrinho = new Carrinho();
	        	UUID uuid = UUID.randomUUID();
	 			carrinho.setId(uuid);
	  			carrinhobean.SetarCarrinhoSessao(carrinho);

	        	
	       
	        }else {
	        	
	        	
//	        	UUID uuid = UUID.randomUUID();
//	 			carrinho.setId(uuid);	
	        	
	        }
	        
	        model.addObject("carrinho", carrinhobean.getCarrinho()); 
		   	 model.addObject("totalitens", carrinhobean.TotalItens());


	    return model;
	  }
	  
	  
	  
	  @ExceptionHandler(DataIntegrityViolationException.class)
	  public ModelAndView conflict() {

			ModelAndView model = new ModelAndView("/public/error/erro");
			
			model.addObject("errCode", "6666");
			model.addObject("errMsg", "Foi lançada DataIntegrityViolationException!");
	        model.addObject("usuarioAtt", usuario);
	        model.addObject("categoriaLista", categoriaLista);
	        model.addObject("configuracaoAtual", configuracaoAtual);
	        model.addObject("hoje", hoje);
	        model.addObject("estaaberto", estaaberto);
	        
	        if(carrinho == null){
	        	
	        	carrinho = new Carrinho();
	        	UUID uuid = UUID.randomUUID();
	 			carrinho.setId(uuid);
	  			carrinhobean.SetarCarrinhoSessao(carrinho);

	        	
	       
	        }else {
	        	
	        	
//	        	UUID uuid = UUID.randomUUID();
//	 			carrinho.setId(uuid);	
	        	
	        }
	        
	        model.addObject("carrinho", carrinhobean.getCarrinho()); 
		   	 model.addObject("totalitens", carrinhobean.TotalItens());


	    return model;
	  }

	@ExceptionHandler(CustomGenericException.class)
	public ModelAndView handleCustomException(CustomGenericException ex) {

		ModelAndView model = new ModelAndView("/public/error/erro");
		model.addObject("errCode", ex.getErrCode());
		model.addObject("errMsg", ex.getErrMsg());
        model.addObject("usuarioAtt", usuario);
        model.addObject("categoriaLista", categoriaLista);
        model.addObject("configuracaoAtual", configuracaoAtual);
        model.addObject("hoje", hoje);
        model.addObject("estaaberto", estaaberto);
//        model.addObject("carrinho", carrinho); 
        
        if(carrinho == null){
        	
        	carrinho = new Carrinho();
        	UUID uuid = UUID.randomUUID();
 			carrinho.setId(uuid);
  			carrinhobean.SetarCarrinhoSessao(carrinho);

       
        }else {
        	
        	
//        	UUID uuid = UUID.randomUUID();
// 			carrinho.setId(uuid);	
        	
        }
        
        model.addObject("carrinho", carrinhobean.getCarrinho()); 
	   	 model.addObject("totalitens", carrinhobean.TotalItens());


		return model;

	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(Exception ex) {

		ModelAndView model = new ModelAndView("/public/error/erro");
		model.addObject("errMsg", ex.toString());
        model.addObject("usuarioAtt", usuario);
        model.addObject("categoriaLista", categoriaLista);
        model.addObject("configuracaoAtual", configuracaoAtual);
        model.addObject("hoje", hoje);
        model.addObject("estaaberto", estaaberto);
//        model.addObject("carrinho", carrinho); 
        
        if(carrinho == null){
        	
        	carrinho = new Carrinho();
        	UUID uuid = UUID.randomUUID();
 			carrinho.setId(uuid);
  			carrinhobean.SetarCarrinhoSessao(carrinho);
	
       
        }else {
        	
//        	
//        	UUID uuid = UUID.randomUUID();
// 			carrinho.setId(uuid);	
        	
        }
        
        model.addObject("carrinho", carrinhobean.getCarrinho()); 
	   	 model.addObject("totalitens", carrinhobean.TotalItens());


		return model;

	}
	
//	@ExceptionHandler(LoggingAccessDeniedHandler.class)
//	public ModelAndView handleLoggingAccessDeniedHandler(Exception ex) {
//
//		ModelAndView model = new ModelAndView("/public/accessdenied");
//		model.addObject("errMsg", ex.toString());
//        model.addObject("usuarioAtt", usuario);
//        model.addObject("categoriaLista", categoriaLista);
//        model.addObject("configuracaoAtual", configuracaoAtual);
//        model.addObject("hoje", hoje);
//        model.addObject("estaaberto", estaaberto);
//        
//        
////        model.addObject("carrinho", carrinho); 
//        
//        
//        if(carrinho == null){
//        	
//        	carrinho = new Carrinho();
//        	UUID uuid = UUID.randomUUID();
// 			carrinho.setId(uuid);
//        	
//       
//        }else {
//        	
//        	
//        	UUID uuid = UUID.randomUUID();
// 			carrinho.setId(uuid);	
//        	
//        }
//        
//        model.addObject("carrinho", carrinhobean.getCarrinho()); 
//
//		return model;
//
//	}
	
	
	public List<Categoria> CategoriasComProduto(List<Categoria> cats){
		
		List<Categoria> validas = new ArrayList<Categoria>();
		
		String insumo = "INSUMOS";
		
		for(Categoria cat : cats) {
			
			
			if((cat.getProdutos().size() > 0) && (!cat.getNome().equals(insumo))){
				
				
				cat.CalcularMenorMaiorPreco();
				validas.add(cat);
				
				
			}else {
				
				
				
				
			}
			
		}
		
		
		return validas;
		
	}
	
	public boolean VerificaHorarioFechamento(Configuracao configuracao) {
      
	
		boolean aberto = false;
		
		
		SimpleDateFormat sdfConvert = new SimpleDateFormat("HH:mm:ss");
        Date horaabertura = configuracao.getHoraabertura();
        Date horafechamento = configuracao.getHorafechamento();
        Date horaagora = new Date();
        
        Date datafor = null;
        
        Date horaabre = null;
        
        Date horafecha=null;
        
        
       try {
    	   datafor = sdfConvert.parse(sdfConvert.format(horaagora));
    	   horaabre = sdfConvert.parse(sdfConvert.format(horaabertura));
    	   horafecha = sdfConvert.parse(sdfConvert.format(horafechamento));
    	   
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
 	   logger.info("erro conversão data hora Contexto /Fechado! data.",e);

	}
       
       
       if( (datafor.after(horafecha)) && (datafor.before(horaabre))){
    	   
    	   System.out.print("Fechado! " + datafor);
    	   
    	   logger.info("Welcome Contexto /Fechado! data.", datafor);
    	   
       }else {
    	   
    	   if((datafor.before(horafecha)) && (datafor.after(horaabre))){
        	   
        	   aberto = true;
        	   
        	   System.out.print("Aberto! " + datafor);
        	   
        	   logger.info("Welcome Contexto /Aberto! data.", datafor);
        	   
           }else {
        	   
        	   
        	  
        	   
           }
    	   
    	   
    	   
       }
       
       
      
      

//        if ((datafor.getTime() > horaabertura.getTime()) && (datafor.getTime() < horafechamento.getTime())){
//           
//        	aberto = true;
//        	System.out.print("Aberto!");
//        	System.out.print("hora agora!" + datafor.getTime() );
//        	System.out.print("hora abre!" + horaabertura.getTime());
//        	System.out.print("hora fecha!  " + horafechamento.getTime());
//       
//        
//        }else if ((datafor.getTime() > horaabertura.getTime()) && (datafor.getTime() > horafechamento.getTime())){
//            
//        	System.out.print("Fechado!");
//        	System.out.print("hora agora!" + datafor.getTime() );
//        	System.out.print("hora abre!" + horaabertura.getTime());
//        	System.out.print("hora fecha!  " + horafechamento.getTime());
//        	
//        	aberto = false;
//        }else {
//        	
//        	
//        	aberto = true;
//        	
//        	System.out.print("hora agora elses!" + datafor.getTime() );
//        	System.out.print("hora abre elses!" + horaabertura.getTime());
//        	System.out.print("hora fecha elses!  " + horafechamento.getTime());
//        	
//        }
	
	
	return aberto;

}
	
	
//private void AgendadorTarefa(){
//			
////			timer = new Timer(); 
//			timer.scheduleAtFixedRate(new TimerTask() {
//			    @Override public void run() { 
//			    System.out.println("Executando a primeira vez em " +
//			     "1 segundo e as demais a cada 5 segundos!"); 
//			    
//
//			   if(nmostrar){
//				   
//				   nmostrar=false;
//				   esconderOver=false;
//				   
//			   }else {
//				   
//				   nmostrar=true;
//				   esconderOver=true;
//				   
//			   }
//			   // mostrar = true;
//			  
//
//			    
//			    
//			    }
//			    
//
//			    }, 1000, 10000);
//			
//			
//			
//		}


//		private boolean UsuarioPossuiIndicacao(Usuario usuario){
//			
//			boolean possui = false;
//			
//			if(!usuario.getIndicacoes().isEmpty()){
//				
//				possui = true;
//				
//			}else {
//				
//				
//				
//			}
//			
//			return possui;
//		}
	
}
