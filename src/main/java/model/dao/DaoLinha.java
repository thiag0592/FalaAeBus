package model.dao;

import java.util.List;

import controller.ApplicationConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import model.Linha;
import model.exception.ModelException;

public class DaoLinha {

	private static EntityManager em = ApplicationConfig.entityManager;
	
	public DaoLinha() {
		super();
	}
	
    public void incluirLinha(Linha l) throws ModelException {
    	em.getTransaction().begin();
        try {
            em.persist(l);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
			// em.getTransaction().rollback();
			throw new ModelException(e.getMessage());
		}
    }

	public List<Linha> obterLinhas() {
		EntityManager em = ApplicationConfig.emf.createEntityManager();
		try(em){
			return em.createQuery("SELECT l FROM Linha l", Linha.class).getResultList();
		}
	}
	
	public Linha obterLinhaPeloNumero(String numero) {
		TypedQuery<Linha> query = em.createQuery("SELECT l FROM Linha a WHERE l.numero = :numero", Linha.class);
        query.setParameter("numero", numero);
		return query.getSingleResult();
	}

	public Linha alterarLinha(Linha novosDados) {
		 EntityManager em = ApplicationConfig.emf.createEntityManager();

	        try (em) {
	            em.getTransaction().begin();

	            // 1. Buscar entidade existente pelo ID que está no objeto Detached
	            Linha existente = em.find(Linha.class, novosDados.getIdLinha());
	            if (existente == null) {
	                throw new IllegalArgumentException("Usuário não encontrado para o ID informado.");
	            }

	            // 2. Atualizar somente os campos que não forem null nos novos dados

	            if (novosDados.getCaminho() != null) {
	                existente.setCaminho(novosDados.getCaminho());
	            }

	            if (novosDados.getNumeroLinha() != null) {
	                existente.setNumeroLinha(novosDados.getNumeroLinha());
	            }

	            if (novosDados.getEmpresa() != null) {
	                existente.setEmpresa(novosDados.getEmpresa());
	            }

	            if (novosDados.getStatus() != null) {
	            	existente.setStatus(novosDados.getStatus());
	            }

	            em.getTransaction().commit();

	            return existente;
	        } catch (ModelException e) {
	        	System.out.println(e.getMessage());
				e.printStackTrace();
				return null;
			}
		
	}

	public Linha obterPorNumeroECnpj(String numero, String cnpj) {
	    TypedQuery<Linha> query = em.createQuery(
	            "SELECT l FROM Linha l " +
	            "WHERE l.numeroLinha = :numero " +
	            "AND l.empresa.cnpj = :cnpj",
	            Linha.class
	    );

	    query.setParameter("numero", numero);
	    query.setParameter("cnpj", cnpj);

	    // Retorna a linha ou null caso não exista
	    List<Linha> resultado = query.getResultList();
	    return resultado.isEmpty() ? null : resultado.get(0);
	}


}
