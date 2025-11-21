package model.dao;

import java.util.List;

import controller.ApplicationConfig;
import jakarta.persistence.EntityManager;
import model.Linha;
import model.ModelException;

public class DaoLinha {

    public Linha incluirUsuario(Linha l) {
    	System.out.println("Criando entityManager");
    	EntityManager em = ApplicationConfig.emf.createEntityManager();
    	System.out.println("Entity Manager criado, começando transação");
        try(em) {
            em.getTransaction().begin();
            em.persist(l);
            em.getTransaction().commit();
        }
        return l;
    }

	public List<Linha> obterLinhas() {
		EntityManager em = ApplicationConfig.emf.createEntityManager();
		try(em){
			return em.createQuery("SELECT l FROM Linha l", Linha.class).getResultList();
		}
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

	            if (novosDados.getNumeroLinha() != 0) {
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


}
