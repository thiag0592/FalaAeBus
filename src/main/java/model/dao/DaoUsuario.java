package model.dao;

import java.util.List;

import controller.ApplicationConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Usuario;

public class DaoUsuario {

    public void incluirUsuario(Usuario usuario) {
    	System.out.println("Criando entityManager");
    	EntityManager em = ApplicationConfig.emf.createEntityManager();
    	System.out.println("Entity Manager criado, começando transação");
        try(em) {
        	System.out.println("begin");
            em.getTransaction().begin();
            System.out.println("persist");
            em.persist(usuario);
            System.out.println("commit");
            em.getTransaction().commit();
        }
    }

    public List<Usuario> obterUsuarios() {
        EntityManager em = ApplicationConfig.emf.createEntityManager();
        try(em) {
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
            return query.getResultList();
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

    public void alterarUsuario(Usuario usuario) {
        EntityManager em = ApplicationConfig.emf.createEntityManager();
        try(em) {
            em.getTransaction().begin();
            em.merge(usuario);
            em.getTransaction().commit();
        }
    }

    public void removerUsuario(int id) {
        EntityManager em = ApplicationConfig.emf.createEntityManager();
        try(em) {
            em.getTransaction().begin();
            Usuario usuario = em.find(Usuario.class, id);
            if (usuario != null) em.remove(usuario);
            em.getTransaction().commit();
        }
    }

    public Usuario buscarPorLoginESenha(String conta, String senhaMD5) {
        EntityManager em = ApplicationConfig.emf.createEntityManager();
        try(em) {
            TypedQuery<Usuario> query = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.conta = :conta AND u.senhaMD5 = :senhaMD5",
                Usuario.class
            );
            query.setParameter("conta", conta);
            query.setParameter("senhaMD5", senhaMD5);
            return query.getSingleResult();
        }
    }
}
