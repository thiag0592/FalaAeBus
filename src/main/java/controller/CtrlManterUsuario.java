package controller;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Path;
import model.Usuario;
import model.dao.DaoUsuario;

// 
// http://127.0.0.1:8080/prjEx02Rest/application.wadl?
//

@Path("/Usuario")
public class CtrlManterUsuario implements ICtrlManterUsuario {
	/**
	 * Bloco static é executado quando o bytecode da classe é carregado (Class.forName)
	 */
	static {
		System.out.println("Bytecode da classe CtrlManterUsuario foi carregado!");
	}

	//
	// MÉTODOS
	//
	public CtrlManterUsuario() {
		System.out.println("Um objeto CtrlManterUsuario foi instanciado!!!");
	}

	@Override
	public Usuario incluirUsuario(Usuario novo) {
		System.out.println("incluindo usuario");
		DaoUsuario dao = new DaoUsuario();
		dao.incluirUsuario(novo);
		return novo;
	}

	@Override
	public Collection<Usuario> listarUsuarios() {
		DaoUsuario dao = new DaoUsuario();
		return dao.obterUsuarios();
	}

	@Override
	public Usuario listarUsuario(int id) {
		System.out.println("---LISTOU USUARIO?---");
		DaoUsuario dao = new DaoUsuario();
		Usuario d = dao.obterUsuario(id);
		if(d == null)
			CtrlEfetuarLogin.enviarErro(HttpServletResponse.SC_BAD_REQUEST, "Usuario não encontrado!");
		return d; 
	}

	@Override
	public Usuario alterarUsuario(int id, String sigla, String nome) {
		return null;
		/*
		DaoUsuario dao = new DaoUsuario();
		Usuario depto = dao.obterUsuario(id);
		if (depto == null) {
			CtrlEfetuarLogin.enviarErro(HttpServletResponse.SC_BAD_REQUEST, "Usuario não encontrado");
			return null;
		}
		try {
			depto.setSigla(sigla);
			depto.setNome(nome);
		} catch (ModelException e) {
			CtrlEfetuarLogin.enviarErro(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}
		return dao.alterarUsuario(depto);
		*/
	}

	@Override
	public Usuario removerUsuario(int id) {
		return null;
		/*
		DaoUsuario dao = new DaoUsuario();
		Usuario depto = dao.obterUsuario(id);
		if (depto == null)
			CtrlEfetuarLogin.enviarErro(HttpServletResponse.SC_BAD_REQUEST, "Usuario não encontrado");
		return dao.removerUsuario(depto);
		*/
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
