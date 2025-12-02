package model.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import controller.ApplicationConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import jakarta.xml.bind.DatatypeConverter;
import model.UsuarioEmpresa;
import model.exception.ModelException;

public class DaoUsuarioEmp {

	private static EntityManager em = ApplicationConfig.entityManager;
	
    public void incluirUsuario(UsuarioEmpresa usuario) throws ModelException {
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
    
    public UsuarioEmpresa obterUsuarioEmpPeloCpf(String cpf) {
		TypedQuery<UsuarioEmpresa> query = em.createQuery("SELECT u FROM Usuario u WHERE u.cps = :cpf", UsuarioEmpresa.class);
        query.setParameter("cpf", cpf);
		return query.getSingleResult();
	}	

	public boolean isUsuarioEmpAtivo(String cpf) {
		UsuarioEmpresa Emp = obterUsuarioEmpPeloCpf(cpf);
		return Emp.isIdEmpresa();
	}

	public boolean verificarUsuario(String cpf, String senhaMD5) {
		UsuarioEmpresa usr = this.obterUsuarioEmpPeloCpf(cpf);
		if(usr == null)
			return false;
		if(!usr.getSenhaMD5().equals(senhaMD5.toUpperCase()))
			return false;
		return true;
	}

	public void alterarUsuarioEmp(UsuarioEmpresa alt) throws ModelException {
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
    

    public UsuarioEmpresa buscarPorLoginESenha(String cpf, String senhaMD5) {
        EntityManager em = ApplicationConfig.emf.createEntityManager();
        try(em) {
            TypedQuery<UsuarioEmpresa> query = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.enderecoUsuario = :cpf AND u.senhaMD5 = :senhaMD5",
                UsuarioEmpresa.class
            );
            query.setParameter("cpf", cpf);
            query.setParameter("senhaMD5", senhaMD5);
            return query.getSingleResult();
        }
    }
}
