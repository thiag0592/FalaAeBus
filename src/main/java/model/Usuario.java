package model;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import jakarta.xml.bind.DatatypeConverter;

public class Usuario implements Serializable {
	//
	// ATRIBUTOS ESTÁTICOS
	//
	private static HashMap<String, Usuario> listaUsuarios = new HashMap<>();
	
	static {
		listaUsuarios.put("alessandro", new Usuario("alessandro",criptografarMD5("mala")));
		listaUsuarios.put("lasalle", new Usuario("lasalle",criptografarMD5("uni")));
	}
	
	//
	// ATRIBUTOS
	//
	private String conta;
	private String senhaMD5;

	//
	// MÉTODOS
	//
	public Usuario() {		
		super();
	}
	
	public Usuario(String conta, String senhaMD5) {
		super();
		System.out.println("Usuário Instanciado!");
		this.conta = conta;
		this.senhaMD5 = senhaMD5;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getSenhaMD5() {
		return senhaMD5;
	}

	public void setSenhaMD5(String senhaMD5) {
		this.senhaMD5 = senhaMD5;
	}
	
	@Override
	public String toString() {
		return "Usuário [conta=" + conta + ", senhaMD5=" + senhaMD5 + "]";
	}

	public static boolean verificarUsuario(String conta, String senhaMD5) {
		Usuario usr = listaUsuarios.get(conta);
		if(usr == null)
			return false;
		if(!usr.getSenhaMD5().equals(senhaMD5.toUpperCase()))
			return false;
		return true;
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
	
	
}
