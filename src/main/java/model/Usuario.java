package model;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.xml.bind.DatatypeConverter;

public class Usuario implements Serializable  {

	//
	//  ATRIBUTOS
	//
	@Id
    @GeneratedValue
    private int idUsuario;

    @Column(nullable = false)
    private String nomeUsuario;
	
    @Column(nullable = false)
    private String dataNascimentoUsuario;

    @Column(nullable = false)
    private String cpfUsuario;
    
    @Column(nullable = false)
    private String enderecoUsuario;

    @Column(nullable = false)
    private String senhaMD5;
    
	//
	// MÉTODOS
	//
	public Usuario() {
		System.out.println("Objeto Usário Instanciado pelo construtor vazio !");
	}
	

	public Usuario(String nomeUsuario, String dataNascimentoUsuario, String cpfUsuario, String enderecoUsuario, String senhaMD5) throws ModelException {
		super();
		this.setNomeUsuario(nomeUsuario);
		this.setDataNascimentoUsuario(dataNascimentoUsuario);
		this.setCpfUsuario(cpfUsuario);
		this.setEnderecoUsuario(enderecoUsuario);
		this.setSenhaMD5(senhaMD5);
	}
	
	public Usuario(int idUsuario, String nomeUsuario, String dataNascimentoUsuario, String cpfUsuario, String enderecoUsuario, String senhaMD5) throws ModelException {
		super();
		this.setIdUsuario(idUsuario);
		this.setNomeUsuario(nomeUsuario);
		this.setDataNascimentoUsuario(dataNascimentoUsuario);
		this.setCpfUsuario(cpfUsuario);
		this.setEnderecoUsuario(enderecoUsuario);
		this.setSenhaMD5(senhaMD5);
	}
	
	
	
	
	public int getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}


	public String getNomeUsuario() {
		return nomeUsuario;
	}


	public void setNomeUsuario(String nomeUsuario) throws ModelException {
		validarNomeUsuario(nomeUsuario);
		this.nomeUsuario = nomeUsuario;
	}

	

	private void validarNomeUsuario(String nomeUsuario) throws ModelException {
		if (!nomeUsuario.matches("^[A-Za-z]+$"))
		    throw new ModelException("Erro: apenas letras são permitidas");
		
	}


	public String getDataNascimentoUsuario() {
		return dataNascimentoUsuario;
	}


	public void setDataNascimentoUsuario(String dataNascimentoUsuario) throws ModelException {
		validarDataNascimentoUsuario(dataNascimentoUsuario);
		this.dataNascimentoUsuario = dataNascimentoUsuario;
		
	}
	
	public void validarDataNascimentoUsuario(String data) throws ModelException {
		// Verifica formato ddMMyyyy
		if (!data.matches("^\\d{8}$"))
			throw new ModelException("Erro: fomatação inválida");

		int dia = Integer.parseInt(data.substring(0, 2));
		int mes = Integer.parseInt(data.substring(2, 4));
		int ano = Integer.parseInt(data.substring(4, 8));

		if (mes < 1 || mes > 12)
			throw new ModelException("Erro: Mês Inválido");


		int diasNoMes;
		switch (mes) {
		case 1:	case 3:	case 5:	case 7:	case 8:	case 10: case 12:
			diasNoMes = 31;
			break;
		case 4:	case 6:	case 9:	case 11:
			diasNoMes = 30;
			break;
		case 2:
			diasNoMes = ((ano % 4 == 0 && ano % 100 != 0) || (ano % 400 == 0)) ? 29 : 28;
			break;
		default:
			throw new ModelException("Erro: Dia Inválido");
		}
	}


	public String getCpfUsuario() {
		return cpfUsuario;
	}


	public void setCpfUsuario(String cpfUsuario) {
		this.cpfUsuario = cpfUsuario;
	}


	public String getEnderecoUsuario() {
		return enderecoUsuario;
	}


	public void setEnderecoUsuario(String enderecoUsuario) {
		this.enderecoUsuario = enderecoUsuario;
	}


	public String getSenhaMD5() {
		return senhaMD5;
	}


	public void setSenhaMD5(String senhaMD5) {
		this.senhaMD5 = senhaMD5;
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
