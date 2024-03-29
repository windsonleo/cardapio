package com.tecsoluction.cardapio;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;




@Configuration
public class DataSourceConf {
	
	
	/**
	 * @return
	 */
	@Bean
    public DataSource dataSource() {
           
//        local
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setUrl("jdbc:postgresql://localhost:5432/cardapio");
//        dataSource.setUsername("postgres");        
//        dataSource.setPassword("");
		
		//heroku
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://ec2-35-172-36-137.compute-1.amazonaws.com:5432/da8ifs3uq7sqmt");
        dataSource.setUsername("dxoflrckminqcl");        
        dataSource.setPassword("2609fef61fb77136f0e4c12ad71efd4e4395867b6a337dcf97dfb3f9ecda1ef0");

    	return dataSource;
    }
	
	
	
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){


        LocalContainerEntityManagerFactoryBean lcemfb
                = new LocalContainerEntityManagerFactoryBean();

        lcemfb.setDataSource(dataSource());
        lcemfb.setPackagesToScan(new String[] {"com.tecsoluction.cardapio.entidade"});

        lcemfb.setPersistenceUnitName("PU-CARDAPIO");

        HibernateJpaVendorAdapter va = new HibernateJpaVendorAdapter();
        lcemfb.setJpaVendorAdapter(va);
        va.setDatabase(Database.POSTGRESQL);

        va.setGenerateDdl(true);
        va.setShowSql(true);
        va.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
        Properties ps = new Properties();
        ps.put("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
        ps.put("spring.jpa.hibernate.ddl-auto", "update");
		ps.put("useSSL","false");
		ps.put("spring.thymeleaf.encoding","UTF-8");
		ps.put("spring.jpa.properties.hibernate.format_sql","true");
		ps.put("spring.datasource.validationQuery","SELECT 1");
		ps.put("spring.thymeleaf.cache","false");
		ps.put("security.basic.enabled","false");
		ps.put("spring.thymeleaf.mode","LEGACYHTML5");

		ps.put("spring.social.facebook.appId","1047331868960513");
		ps.put("spring.social.facebook.appSecret","88b9a5181187e858176ae7f09a4bb6b4");
		

		
        lcemfb.setJpaProperties(ps);

        lcemfb.afterPropertiesSet();

        return lcemfb;

    }
    
    @Bean
    public PlatformTransactionManager transactionManager(){

        JpaTransactionManager tm = new JpaTransactionManager();

        tm.setEntityManagerFactory(
                this.entityManagerFactory().getObject() );

        return tm;

    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }
    
    
    


}
