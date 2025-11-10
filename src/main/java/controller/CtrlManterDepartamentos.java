package controller;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import model.Departamento;
import model.ModelException;
import model.dao.DaoDepartamento;

// 
// http://127.0.0.1:8080/prjEx02Rest/application.wadl?
//

@Path("/departamento")
public class CtrlManterDepartamentos implements ICtrlManterDepartamentos {
	/**
	 * Bloco static é executado quando o bytecode da classe é carregado (Class.forName)
	 */
	static {
		System.out.println("Bytecode da classe CtrlManterDepartamentos foi carregado!");
	}

	//
	// MÉTODOS
	//
	public CtrlManterDepartamentos() {
		System.out.println("Um objeto CtrlManterDepartamentos foi instanciado!!!");
	}

	@Override
	public Departamento incluirDepartamento(Departamento novo) {
		DaoDepartamento dao = new DaoDepartamento();
		dao.incluirDepartamento(novo);
		return novo;
	}

	@Override
	public Collection<Departamento> listarDepartamentos() {
		DaoDepartamento dao = new DaoDepartamento();
		return dao.obterDepartamentos();
	}

	@Override
	public Departamento listarDepartamento(int id) {
		System.out.println("---LISTOU DEPARTAMENTO?---");
		DaoDepartamento dao = new DaoDepartamento();
		Departamento d = dao.obterDepartamento(id);
		if(d == null)
			CtrlEfetuarLogin.enviarErro(HttpServletResponse.SC_BAD_REQUEST, "Departamento não encontrado!");
		return d; 
	}

	@Override
	public Departamento alterarDepartamento(int id, String sigla, String nome) {
		DaoDepartamento dao = new DaoDepartamento();
		Departamento depto = dao.obterDepartamento(id);
		if (depto == null) {
			CtrlEfetuarLogin.enviarErro(HttpServletResponse.SC_BAD_REQUEST, "Departamento não encontrado");
			return null;
		}
		try {
			depto.setSigla(sigla);
			depto.setNome(nome);
		} catch (ModelException e) {
			CtrlEfetuarLogin.enviarErro(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}
		return dao.alterarDepartamento(depto);
	}

	@Override
	public Departamento removerDepartamento(int id) {
		DaoDepartamento dao = new DaoDepartamento();
		Departamento depto = dao.obterDepartamento(id);
		if (depto == null)
			CtrlEfetuarLogin.enviarErro(HttpServletResponse.SC_BAD_REQUEST, "Departamento não encontrado");
		return dao.removerDepartamento(depto);
	}

	// Desconsiderar o código abaixo. Será útil para as futuras aulas
	private static String chaveCriptografia = "0123456789abcdef";
	private static byte[] decodedKey = Base64.getDecoder().decode(chaveCriptografia);
	private static byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private static IvParameterSpec ivspec = new IvParameterSpec(iv);

	public static String criptografar(String texto) throws Exception {
		Cipher cifrador = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
		SecretKey chave = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
		cifrador.init(Cipher.ENCRYPT_MODE, chave, ivspec);
		byte[] cipherText = cifrador.doFinal(texto.getBytes("UTF-8"));
		return Base64.getEncoder().encodeToString(cipherText);
	}

	public static String descriptografar(String cripto) throws Exception {
		Cipher decifrador = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
		SecretKey chave = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
		decifrador.init(Cipher.DECRYPT_MODE, chave, ivspec);
		byte[] cipherText = decifrador.doFinal(Base64.getDecoder().decode(cripto));
		return new String(cipherText);
	}
}
