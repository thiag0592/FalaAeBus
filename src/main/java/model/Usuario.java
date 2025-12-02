package model;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.xml.bind.DatatypeConverter;
import model.exception.ModelException;

@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario implements Serializable {

	//
	// ATRIBUTOS
	//
	@Id
	@GeneratedValue
	private int idUsuario;

	@Column(nullable = false)
	private String nomeUsuario;

	@Column(nullable = false)
	private String dataNascimentoUsuario;

	@Column(nullable = false, unique = true)
	private String cpfUsuario;

	@Column(nullable = false, unique = true)
	private String enderecoUsuario;

	@Column(nullable = false)
	private String senhaMD5;

	@OneToMany(mappedBy = "usuario")
	private List<AvaliaLinha> linhas;

	//
	// MÉTODOS
	//
	public Usuario() {
		System.out.println("Objeto Usuário Instanciado pelo construtor vazio !");
	}

	public Usuario(String nomeUsuario, String dataNascimentoUsuario, String cpfUsuario, String enderecoUsuario,
			String senhaMD5) throws ModelException {
		super();
		this.setNomeUsuario(nomeUsuario);
		this.setDataNascimentoUsuario(dataNascimentoUsuario);
		this.setCpfUsuario(cpfUsuario);
		this.setEnderecoUsuario(enderecoUsuario);
		this.setSenhaMD5(senhaMD5);
	}

	public Usuario(int idUsuario, String nomeUsuario, String dataNascimentoUsuario, String cpfUsuario,
			String enderecoUsuario, String senhaMD5) throws ModelException {
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
		if (!nomeUsuario.matches("^[A-Za-z ]+$"))
			throw new ModelException("Erro: apenas letras são permitidas");

	}

	public String getDataNascimentoUsuario() {
		return dataNascimentoUsuario;
	}

	public void setDataNascimentoUsuario(String dataNascimentoUsuario) throws ModelException {
		validarDataNascimentoUsuario(dataNascimentoUsuario);
		this.dataNascimentoUsuario = dataNascimentoUsuario;

	}

	public static void validarDataNascimentoUsuario(String data) throws ModelException {
		// Verifica formato ddMMyyyy
		if (!data.matches("^\\d{2}/\\d{2}/\\d{4}$"))
			throw new ModelException("Erro: fomatação inválida");

		int dia = Integer.parseInt(data.substring(0, 2));
		int mes = Integer.parseInt(data.substring(3, 5));
		int ano = Integer.parseInt(data.substring(6, 10));

		if (LocalDate.now().getYear() - ano < 18) {
			throw new ModelException("Erro: Usuário menor de idade!");
		}

		if (mes < 1 || mes > 12)
			throw new ModelException("Erro: Mês Inválido");

		int diasNoMes;
		switch (mes) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			diasNoMes = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
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

	public void setCpfUsuario(String cpfUsuario) throws ModelException {
		validarCPF(cpfUsuario);
		this.cpfUsuario = cpfUsuario;
	}

	public static void validarCPF(String cpf) throws ModelException {
		// Padrão: 000.000.000-00
		if (!cpf.matches("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$"))
			throw new ModelException("Erro: formato de CPF inválido. Use ###.###.###-##");
	}

	public String getEnderecoUsuario() {
		return enderecoUsuario;
	}

	// Endereço de email
	public void setEnderecoUsuario(String enderecoUsuario) throws ModelException {
		validarEnderecoEmail(enderecoUsuario);
		this.enderecoUsuario = enderecoUsuario;
	}

	public static void validarEnderecoEmail(String email) throws ModelException {
		// Regex simples e comum para formato de e-mail
		if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
			throw new ModelException("Erro: formato de e-mail inválido");
	}

	public String getSenhaMD5() {
		return senhaMD5;
	}

	public void setSenhaMD5(String senhaMD5) throws ModelException {
		senhaMD5 = senhaMD5.toUpperCase();
		validarMD5(senhaMD5);
		this.senhaMD5 = senhaMD5;
	}

	public static void validarMD5(String hash) throws ModelException {
		// Aceita somente MD5 em hexadecimal MAIÚSCULO (32 caracteres)
		System.out.println(hash);
		if (!hash.matches("^[A-F0-9]{32}$"))
			throw new ModelException(
					"Erro: o valor deve ser um hash MD5 válido em maiúsculas (32 caracteres hexadecimais)");
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
