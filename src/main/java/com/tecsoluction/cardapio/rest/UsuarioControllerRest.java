package com.tecsoluction.cardapio.rest;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tecsoluction.cardapio.entidade.Role;
import com.tecsoluction.cardapio.entidade.Usuario;
import com.tecsoluction.cardapio.framework.AbstractRestController;
import com.tecsoluction.cardapio.servico.RoleServicoImpl;
import com.tecsoluction.cardapio.servico.UsuarioServicoImpl;

@RestController
@RequestMapping(value = "usuario")
public class UsuarioControllerRest extends AbstractRestController<Usuario> {
    
	
    private final UsuarioServicoImpl userService;
	
    private final RoleServicoImpl roleService;
	
	private Image image;

	private File file;
	
	
	private File file2;
	
	private String path;
	
	private String pathf;
	
	private String nome;
	
    @Autowired
    public UsuarioControllerRest(UsuarioServicoImpl dao,RoleServicoImpl rol) {
        this.userService = dao;
        this.roleService = rol;
    }
    
    
    @RequestMapping(value = "/usuarioSave", method =  RequestMethod.POST)
    public Usuario Post(@Valid @RequestBody Usuario pessoa,final HttpSession session,String caminho)
    {
    	
    	Usuario usuario = null;
    	
    	path = session.getServletContext().getRealPath("/WEB-INF/classes/static/img/usuario/");
    	
    	pathf = path + pessoa.getNome() + ".jpg";
    	
    	
    	if(UsuarioExiste(pessoa.getEmail())){
    		
    		System.out.println("usuario já existe" + pessoa);
    		
    		usuario = pessoa;
    		
    	}else {
    		
    		System.out.println("usuario não existe" + pessoa);
    		
    		PegarFotoArmazenar(caminho);
    		
    		pessoa.setFoto(nome);
    		
    		usuario =  getservice().save(pessoa);

    		
    	}
    	
        return usuario;
    }
    
    
    
    @RequestMapping(value = "/salvarFacebook", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Usuario salvarUsu(@RequestBody @Valid Usuario usu,final HttpSession session) {
    	
//    	System.out.println(eventos.toString());
    	
//    	for (Evento evento : eventos) {
    	Usuario usuario = null;
    	
    	UUID idf = UUID.fromString("9e5438c6-6749-43c0-8ecb-59e46e74c155");
    	
    	Role role = roleService.findOne(idf);
    	
    	path = session.getServletContext().getRealPath("/WEB-INF/classes/static/img/usuario/");
    	
    	pathf = path + usu.getNome() + ".jpg";
    	
    	
    	if(UsuarioExiste(usu.getEmail())){
    		
    		System.out.println("usuario já existe" + usu);
    		
    		usuario = usu;
    		
    	}else {
    		
    		System.out.println("usuario não existe" + usu);
    		
    		PegarFotoArmazenar(usu.getFoto());
    		
    		usu.setFoto(nome);
    		usu.setDatacadastro(new Date());
    		usu.getRoles().add(role);
    		role.getUsers().add(usu);
    		usu.setAtivo(true);
    	
    		
    		
    		getservice().save(usu);
    		roleService.edit(role);
			
		}
    	
    	
        return usu;

    }
    
    
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Usuario> ListAllUsuario() {

        return getservice().findAll();

    }
    
    
    
    public boolean UsuarioExiste(String email){
    	
    	boolean existe = false;
    
    	
    	Usuario usuario= getservice().findByEmail(email);
    	
    	if(usuario == null){
    		
    		
    	}else {
    		
    		existe=true;
    		
    	}
    	
    	
    	return existe;
    }
    
    
    
    public boolean PegarFotoArmazenar(String url){
    	
    	boolean capturou = false;
    	
    	boolean leu = false;
    	
    	boolean sucesso = false;
    	
//        URL urll=null;
//        BufferedImage img =null;
//        URL urlll=null;
        
        
        
//        try {
//			urlll=new URL(url);
//		} catch (MalformedURLException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
//        
//		try {
//			file2 = Paths.get(urlll.toURI()).toFile();
//		} catch (URISyntaxException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}

        
        
//		try {
//			 urll = new URL(url);
//			 try {
//				 img = ImageIO.read(urll);
//				 leu=true;
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			 file = new File(pathf);
//			 try {
//				ImageIO.write(img, "jpg", file);
//				capturou = true;
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	
    	
    	file = new File(pathf);
    	
    	
    			URL urlObj = null;
				try {
					urlObj = new URL(url);
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}                                    
    			HttpURLConnection httpConnection = null;
				try {
					httpConnection = (HttpURLConnection)urlObj.openConnection();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			try {
					httpConnection.setRequestMethod("GET");
				} catch (ProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			InputStream inputStream = null;
				try {
					inputStream = httpConnection.getInputStream();
					
					leu=true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			OutputStream outputStream = null;
    			try {
    			    int read = 0;
    			    byte[] bytes = new byte[1024];
    			    outputStream = new FileOutputStream(file);
    			    while ((read = inputStream.read(bytes)) != -1) {
    			        outputStream.write(bytes, 0, read);
    			    }
    			    
    			  capturou=true;
    			} catch (FileNotFoundException ex) {
    			    ex.getMessage();
    			} catch (IOException ex) {
    			    ex.getMessage();
    			} finally {
    			    try {
    			        if (outputStream != null) {
    			            outputStream.close();
    			        }
    			    } catch (IOException ex) {
    			        ex.getMessage();
    			    }
    			}
    	
    	
    	
    	
      
        
        if(leu && capturou){
        	
        	nome = SalvarDiretorio(file);
        	
        	sucesso = true;
        	
        } else {
        	
        	
        	
        	
        }
        

    	
    	
    	return sucesso;
    }
    

    private String SalvarDiretorio(File image2) {

    	
    	 
    	return image2.getName();
    	
	}


	@Override
    protected UsuarioServicoImpl getservice() {
        // TODO Auto-generated method stub
        return userService;
    }


}
