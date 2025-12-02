package controller;

import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.core.util.JacksonFeature;

import jakarta.persistence.EntityManager;
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
	
	final public static EntityManager entityManager = emf.createEntityManager();
	
	
	final public static String CH_USUARIO_ATUAL = "Usuário da Sessão";	
	final public static String CH_ADM_ATUAL = "Adm da Sessão";	
	final public static String CH_EMP_ATUAL = "Emp da Sessão";	
	
	// Precisei adicionar, usando como base o código de alessandro, se vc tiver outra ideia, tô aberto a sugestões
	// Pela falta de resposta, não teve sugestões
	final public static String CH_UC_EM_EXECUCAO = "Caso de Uso em Execução";
	final public static String CH_PROXIMO_PASSO = "Próximo Passo";

	
	// Construtor da classe
	public ApplicationConfig() {
	    super();
	    System.out.println("==== ApplicationConfig inicializando ====");
	    this.packages("controller");
	    this.packages("controller.filtro");
	    this.register(JacksonFeature.class);
	    
	    
		/*
		 * UsuarioAdm usuarioAdm = null; try { usuarioAdm = new
		 * UsuarioAdm("Adm Supremo","06/06/2005","123.456.789-01",
		 * "emaildousuariosupremo@ummail.com","0cc175b9c0f1b6a831c399e269772661"); }
		 * catch (ModelException e) {
		 * System.out.println("===erro! - erro ao criar usario adm");
		 * e.printStackTrace(); } EntityManager em = emf.createEntityManager();
		 * System.out.println("Entity Manager criado, começando transação"); try(em) {
		 * em.getTransaction().begin(); em.persist(usuarioAdm);
		 * em.getTransaction().commit(); }
		 */
	}
}