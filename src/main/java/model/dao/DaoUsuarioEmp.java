package model.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import controller.ApplicationConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import jakarta.xml.bind.DatatypeConverter;
import model.UsuarioAdm;
import model.exception.ModelException;

public class DaoUsuarioEmp {

	private static EntityManager em = ApplicationConfig.entityManager;
	
    public void incluirUsuario(UsuarioAdm usuario) throws ModelException {
    	em.getTransaction().begin();
    	System.out.println("Entity Manager criado, começando transação");
        try{
            em.persist(usuario);
            em.getTransaction().commit();
        }
        catch (PersistenceException e) {
			throw new ModelException(e.getMessage());
		}
    }
    
    public UsuarioAdm obterUsuarioPeloCpf(String cpf) {
		TypedQuery<UsuarioAdm> query = em.createQuery("SELECT u FROM Usuario u WHERE u.cps = :cpf", UsuarioAdm.class);
        query.setParameter("cpf", cpf);
		return query.getSingleResult();
	}	

	public boolean verificarUsuario(String cpf, String senhaMD5) {
		UsuarioAdm usr = this.obterUsuarioPeloCpf(cpf);
		if(usr == null)
			return false;
		if(!usr.getSenhaMD5().equals(senhaMD5.toUpperCase()))
			return false;
		return true;
	}

	public void alterarUsuario(UsuarioAdm alt) throws ModelException {
		em.getTransaction().begin();
		try {
			em.persist(alt);
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			throw new ModelException(e.getMessage());
		}
	}
    
	public static String criptografarMD5(String texto) {
	    try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(texto.getBytes());
			byte[] digest = md.digest();
			return DatatypeConverter.printHexBinary(digest).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	    return null;
	}
    

    public UsuarioAdm buscarPorLoginESenha(String cpf, String senhaMD5) {
        EntityManager em = ApplicationConfig.emf.createEntityManager();
        try(em) {
            TypedQuery<UsuarioAdm> query = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.enderecoUsuario = :cpf AND u.senhaMD5 = :senhaMD5",
                UsuarioAdm.class
            );
            query.setParameter("cpf", cpf);
            query.setParameter("senhaMD5", senhaMD5);
            return query.getSingleResult();
        }
    }
}
