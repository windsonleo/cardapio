package com.tecsoluction.cardapio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.tecsoluction.cardapio.exception.LoggingAccessDeniedHandler;
import com.tecsoluction.cardapio.util.CustomLogoutSuccessHandler;




@Configuration
@EnableWebSecurity
@ComponentScan(basePackages="com.tecsoluction.cardapio")
@Import({DataSourceConf.class})
public class MyWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
	
	@Value("${spring.queries.users-query}")
	private String usersQuery;
	
	@Value("${spring.queries.roles-query}")
	private String rolesQuery;	
	
	@Autowired
	private DataSourceConf dataSource;
	
	  @Autowired
	 private LoggingAccessDeniedHandler accessDeniedHandler;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.
			jdbcAuthentication()
				.usersByUsernameQuery(usersQuery)
				.authoritiesByUsernameQuery(rolesQuery)
				.dataSource(dataSource.dataSource());
//				.passwordEncoder(bCryptPasswordEncoder);
				
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
				
		http.
			authorizeRequests()				
				.antMatchers("/public/**").permitAll()
				.antMatchers("/resources/**").permitAll()
				.antMatchers("/static/**").permitAll()
				.antMatchers("/templates/**").permitAll()
				.antMatchers("/web/**").permitAll()
				.antMatchers("/build/**").permitAll()
				.antMatchers("/vendors/**").permitAll()
				.antMatchers("/img/**").permitAll()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/js/**").permitAll()
				.antMatchers("/esquecisenha**").permitAll()
				.antMatchers("*/accessdenied**").permitAll()
				.antMatchers("*/accessdenied").permitAll()
				.antMatchers("/accessdenied").permitAll()
				.antMatchers("/usuario/cadastro/**").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("*/error/**").permitAll()
				.antMatchers("/erro**").permitAll()
				.antMatchers("*/erro**").permitAll()
				.antMatchers("/erro").permitAll()
				.antMatchers("/fonts/**").permitAll()
				.antMatchers("/bootstrap/**").permitAll()
				.antMatchers("/categoria/exibir**").permitAll()
				.antMatchers("/usuario/perfil**").permitAll()
				.antMatchers("/usuario/perfil").permitAll()
				
				.antMatchers("/usuario/addmensagem**").permitAll()
				.antMatchers("/usuario/addmensagem").permitAll()
				.antMatchers("/produto/perfil**").permitAll()
				.antMatchers("/produto/filtroMenorPreco**").permitAll()
				.antMatchers("/promocao/filtroOfertasHoje**").permitAll()
				.antMatchers("/produto/filtroMaiorAvaliacao**").permitAll()
				.antMatchers("/cardapio/cardapio").permitAll()
				.antMatchers("/promocao/ofertas").permitAll()
				
				.antMatchers("/produto/filtroMenorPreco").permitAll()
				.antMatchers("/promocao/filtroOfertasHoje").permitAll()
				.antMatchers("/produto/filtroMaiorAvaliacao").permitAll()
				
				.antMatchers("/produto/all").permitAll()
				.antMatchers("/categoria/all").permitAll()
				.antMatchers("/categoria/pai").permitAll()
				.antMatchers("/promocao/perfil**").permitAll()
				.antMatchers("/usuario/autenticar**").permitAll()
				.antMatchers("/usuario/lock**").permitAll()
				.antMatchers("/usuario/add**").permitAll()
				.antMatchers("/usuario/all**").permitAll()
				.antMatchers("/usuario/usuarioSave").permitAll()
				.antMatchers("/usuario/salvarFacebook").permitAll()
				.antMatchers("/salvarfotoregistro**").permitAll()
				.antMatchers("/carrinho/adicionar**").permitAll()				
				.antMatchers("/esquecisenhaenv").permitAll()
				.antMatchers("/registro**").permitAll()
				.antMatchers("/registroenv").permitAll()
				.antMatchers("/promocao/addproduto**").permitAll()
				.antMatchers("/produtocomposto/additem**").permitAll()
				.antMatchers("/produtocomposto/all").permitAll()
				.antMatchers("/carrinho/visualizar**").permitAll()
				.antMatchers("/carrinho/finalizar**").permitAll()
				.antMatchers("/carrinho/finalizaCarrinho**").permitAll()
				.antMatchers("/carrinho/finalizaCarrinho").permitAll()

				.antMatchers("/carrinho/cancelar**").permitAll()
				.antMatchers("/carrinho/deleteitem**").permitAll()
				.antMatchers("/pedidovenda/id**").permitAll()
				.antMatchers("/pedidovenda/monitor**").permitAll()
				.antMatchers("/card").permitAll()
				.antMatchers("/pedidovenda/**").permitAll()
				.antMatchers("/home").permitAll()
				.antMatchers("/logout").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/logoutsucess").permitAll()				
				.antMatchers("https://connect.facebook.net").permitAll()	
				.antMatchers("https://apis.google.com/js/platform.js").permitAll()
				
				.antMatchers("/webjars/**").permitAll()
				.antMatchers("*/sass/**").permitAll()
				.antMatchers("/private/**").hasAnyRole("ADM","SUPER").anyRequest().authenticated()
				.and()
				.csrf().disable().formLogin()
				.loginPage("/login").failureUrl("/login?error=true")
				.defaultSuccessUrl("/home")
				.usernameParameter("email")
				.passwordParameter("senha").and()
			    .rememberMe()
			    .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(logoutSuccessHandler()).logoutSuccessUrl("/login")
//			    .and().logout()
//				.and().logout().logoutUrl("/cardapio_logout").permitAll()
//				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//				.logoutSuccessUrl("/home")
				.and().exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler);
//				.accessDeniedPage("/accessdenied");
				
//		http.sessionManagement().maximumSessions(sessaoMax).and().invalidSessionUrl("/sessaoinvalida").and()
//		.sessionManagement().sessionFixation().migrateSession();
		
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	       .ignoring()
	       .antMatchers("/resources/**");
       web
	       .ignoring()
	       .antMatchers("/static/**");
       
       web
       .ignoring()
       .antMatchers("/css/**");
       
       web
       .ignoring()
       .antMatchers("/js/**");
       
       web
       .ignoring()
       .antMatchers("/img/**");
       
       web
       .ignoring()
       .antMatchers("/web/**");
       
       web
       .ignoring()
       .antMatchers("/build/**");
       
       web
       .ignoring()
       .antMatchers("/vendors/**");
       
       web
       .ignoring()
       .antMatchers("/public/**");
       
       web
       .ignoring()
       .antMatchers("/templates/**");
       
       web
       .ignoring()
       .antMatchers("/webjars/**");
       
       web
       .ignoring()
       .antMatchers("*/sass/**");
       
       web
       .ignoring()
       .antMatchers("*/fonts/**");
       
       web
       .ignoring()
       .antMatchers("*/error/**");
       
       

	}
	

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("cliente").password("passwordcliente").roles("CLIENTE")
                .and()
                .withUser("admin").password("passwordadmin").roles("ADM")
                .and()
                .withUser("face").password("passwordface").roles("FACE")
                .and()
                .withUser("super").password("super").roles("SUPER");
    }
    
    
//    @Bean
//    public SpringTemplateEngine templateEngine() {
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        
//        templateEngine.addDialect(new SpringSecurityDialect());
//    return templateEngine;
//	
//    }
	
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }
    
    
    
}
