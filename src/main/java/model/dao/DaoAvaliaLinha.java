package model.dao;

import java.util.List;

import controller.ApplicationConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import model.AvaliaLinha;
import model.exception.ModelException;
import model.Empresa;

public class DaoAvaliaLinha {

	private static EntityManager em = ApplicationConfig.entityManager;
	
	
	public void incluir(AvaliaLinha nova) throws ModelException {
		em.getTransaction().begin();
		try {
			em.persist(nova);
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			// em.getTransaction().rollback();
			throw new ModelException(e.getMessage());
		}
	}
	
	public List<AvaliaLinha> obterTodasDeEmpresa(String cnpj) {
		
		TypedQuery<AvaliaLinha> query = em.createQuery(
			    "SELECT al FROM AvaliaLinha al " +
			    "JOIN al.linha l " +
			    "JOIN l.empresa e " +
			    "WHERE e.cnpj = :cnpj", 
			    AvaliaLinha.class
			);

			query.setParameter("cnpj", cnpj);
			List<AvaliaLinha> resultado = query.getResultList();
		return resultado;
	}
	
	
	
}
