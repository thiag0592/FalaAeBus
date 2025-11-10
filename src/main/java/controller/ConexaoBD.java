package controller;
import jakarta.persistence.*;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import model.Departamento;
import model.ModelException;

import java.util.List;

@WebListener
public class ConexaoBD implements ServletContextListener{
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		 System.out.println("========================================");
	     System.out.println("=== INICIALIZANDO APLICAÇÃO ===");
	     System.out.println("========================================");
	     
	     this.conexao();
		
	}
	
    public void conexao() {
    	System.out.println("---banco de dadooooos");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("tde5PU");
        EntityManager em = emf.createEntityManager();
        System.out.println("---banco de dados ok :)");
        
        em.getTransaction().begin();

        try {
        	em.persist(new Departamento("TI", "Tecnologia da Informação"));
			em.persist(new Departamento("RH", "Recursos Humanos"));
			em.persist(new Departamento("FN", "Financeiro"));
			em.persist(new Departamento("MK", "Marketing"));
		} catch (ModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}

        em.getTransaction().commit();

        // Consulta e exibe os departamentos
        List<Departamento> lista = em.createQuery("FROM Departamento", Departamento.class).getResultList();
        lista.forEach(System.out::println);

        em.close();
        emf.close();
    }
}
