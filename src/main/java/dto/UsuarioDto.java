package dto;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import jakarta.xml.bind.DatatypeConverter;

public class UsuarioDto implements Serializable {
	
	//
	// ATRIBUTOS
	//
	private String endereco;
	private String senhaMD5;

	//
	// MÉTODOS
	//
	public UsuarioDto() {		
		super();
		System.out.println("Usuário Instanciado pelo construtor vazio!");
	}
	
	public UsuarioDto(String conta, String senhaMD5) {
		super();
		System.out.println("Usuário Instanciado!");
		this.endereco = conta;
		this.senhaMD5 = senhaMD5;
	}

	public String getConta() {
		return endereco;
	}

	public void setConta(String conta) {
		this.endereco = conta;
	}

	public String getSenhaMD5() {
		return senhaMD5;
	}

	public void setSenhaMD5(String senhaMD5) {
		this.senhaMD5 = senhaMD5;
	}
	
	@Override
	public String toString() {
		return "Usuário [conta=" + endereco + ", senhaMD5=" + senhaMD5 + "]";
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
