package controller;

import org.glassfish.jersey.server.ResourceConfig;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.ApplicationPath;

//
// No web.xml há uma indicação para o Jersey Servlet carregar esta classe
// quando o servidor for iniciado. Esta classe serve para passarmos os 
// parâmetros de configuração da nossa aplicação.
//


//Estamos indicado que todos os webservices terão no path da URL a indicação '/ws'
@ApplicationPath("/ws") 
public class ApplicationConfig extends ResourceConfig {
	
	public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("falaaebus");
	
	
	// Construtor da classe
	public ApplicationConfig() {
	    super();
	    System.out.println("==== ApplicationConfig inicializando ====");
	    this.packages("controller");
	}
}