package controller;

import org.glassfish.jersey.server.ResourceConfig;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.ApplicationPath;
import model.UsuarioAdm;
import model.exception.ModelException;

//
// No web.xml há uma indicação para o Jersey Servlet carregar esta classe
// quando o servidor for iniciado. Esta classe serve para passarmos os 
// parâmetros de configuração da nossa aplicação.
//


//Estamos indicado que todos os webservices terão no path da URL a indicação '/ws'
@ApplicationPath("/ws") 
public class ApplicationConfig extends ResourceConfig {
	
	public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("tde5PU");
	
	final public static EntityManager entityManager = emf.createEntityManager();
	
	
	// Construtor da classe
	public ApplicationConfig() {
	    super();
	    System.out.println("==== ApplicationConfig inicializando ====");
	    this.packages("controller");
	    
	    
	    UsuarioAdm usuarioAdm = null;
		try {
			usuarioAdm = new UsuarioAdm("Adm Supremo","06/06/2005","123.456.789-01","emaildousuariosupremo@ummail.com","0cc175b9c0f1b6a831c399e269772661");
		} catch (ModelException e) {
			System.out.println("===erro! - erro ao criar usario adm");
			e.printStackTrace();
		}
	    EntityManager em = emf.createEntityManager();
    	System.out.println("Entity Manager criado, começando transação");
        try(em) {
            em.getTransaction().begin();
            em.persist(usuarioAdm);
            em.getTransaction().commit();
        }
	}
}