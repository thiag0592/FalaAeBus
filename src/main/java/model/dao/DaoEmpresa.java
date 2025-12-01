package model.dao;

import java.util.List;

import controller.ApplicationConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import model.Empresa;
import model.exception.ModelException;

public class DaoEmpresa {
	//
	// ATRIBUTOS
	//
	private static EntityManager em = ApplicationConfig.entityManager;

	//
	// MÉTODOS
	//
	public DaoEmpresa() {
		super();
	}
	
	public void incluir(Empresa nova) throws ModelException {
		em.getTransaction().begin();
		try {
			em.persist(nova);
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			// em.getTransaction().rollback();
			throw new ModelException(e.getMessage());
		}
	}
	
	public void alterar(Empresa alt) throws ModelException {
		em.getTransaction().begin();
		try {
			em.persist(alt);
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			// em.getTransaction().rollback();
			throw new ModelException(e.getMessage());
		}
	}
	
	/*
	 * public void remover(Empresa ex) throws ModelException {
	 * em.getTransaction().begin(); try { em.remove(ex);
	 * em.getTransaction().commit(); } catch (PersistenceException e) { //
	 * em.getTransaction().rollback(); throw new ModelException(e.getMessage()); } }
	 */
	
	public List<Empresa> obterTodos() {
		Query query = em.createQuery("SELECT a FROM Empresa a");
		List<Empresa> resultado  = query.getResultList();
		return resultado;
	}	
	
	public Empresa obterEmpresaPeloCnpj(String cnpj) {
		TypedQuery<Empresa> query = em.createQuery("SELECT a FROM Empresa a WHERE a.cnpj = :cnpj", Empresa.class);
        query.setParameter("cnpj", cnpj);
		return query.getSingleResult();
	}	
}