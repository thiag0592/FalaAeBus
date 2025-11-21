package model.dao;

import controller.ApplicationConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Usuario;
import model.exception.ModelException;

public class DaoUsuario {

    public void incluirUsuario(Usuario usuario) {
    	System.out.println("Criando entityManager");
    	EntityManager em = ApplicationConfig.emf.createEntityManager();
    	System.out.println("Entity Manager criado, começando transação");
        try(em) {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        }
    }


    public Usuario obterUsuario(int id) {
        EntityManager em = ApplicationConfig.emf.createEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public Usuario alterarUsuario(Usuario novosDados) {
        EntityManager em = ApplicationConfig.emf.createEntityManager();

        try (em) {
            em.getTransaction().begin();

            // 1. Buscar entidade existente pelo ID que está no objeto Detached
            Usuario existente = em.find(Usuario.class, novosDados.getIdUsuario());
            if (existente == null) {
                throw new IllegalArgumentException("Usuário não encontrado para o ID informado.");
            }

            // 2. Atualizar somente os campos que não forem null nos novos dados

            if (novosDados.getCpfUsuario() != null) {
                existente.setCpfUsuario(novosDados.getCpfUsuario());
            }

            if (novosDados.getSenhaMD5() != null) {
                existente.setSenhaMD5(novosDados.getSenhaMD5());
            }

            if (novosDados.getDataNascimentoUsuario() != null) {
                existente.setDataNascimentoUsuario(novosDados.getDataNascimentoUsuario());
            }

            if (novosDados.getNomeUsuario() != null) {
                existente.setNomeUsuario(novosDados.getNomeUsuario());
            }

            if (novosDados.getEnderecoUsuario() != null) {
                existente.setEnderecoUsuario(novosDados.getEnderecoUsuario());
            }

            em.getTransaction().commit();

            return existente;
        } catch (ModelException e) {
        	System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
    }


    public void removerUsuario(int id) {
        //TODO O QUE ACONTECE COM AS RESPOSTAS DE USUARIO AO SER DELETADO?
    }

    public Usuario buscarPorLoginESenha(String conta, String senhaMD5) {
        EntityManager em = ApplicationConfig.emf.createEntityManager();
        try(em) {
            TypedQuery<Usuario> query = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.enderecoUsuario = :conta AND u.senhaMD5 = :senhaMD5",
                Usuario.class
            );
            query.setParameter("conta", conta);
            query.setParameter("senhaMD5", senhaMD5);
            return query.getSingleResult();
        }
    }
}
