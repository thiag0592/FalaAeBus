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
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import model.Usuario;
import model.dao.DaoUsuario;

// 
// http://127.0.0.1:8080/prjEx02Rest/application.wadl?
//

@Path("/Usuario")
public class CtrlManterUsuario implements ICtrlManterUsuario {
	
	@Context
	private HttpServletRequest request;
	
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
		
		HttpSession sessao = request.getSession(true);
		sessao.setAttribute("contaLogada", novo);
		return novo;
	}


	@Override
	public Usuario alterarUsuario(Usuario modificacao) {
		System.out.println("---ALTERAR USUARIO---");
		DaoUsuario dao = new DaoUsuario();
		HttpSession session = request.getSession();
		Usuario usrSession = (Usuario) session.getAttribute("contaLogada");
		modificacao.setIdUsuario(usrSession.getIdUsuario());
		return dao.alterarUsuario(modificacao);
	}

	@Override
	public Usuario removerUsuario(int id) {
		//TODO O QUE ACONTECE COM AS RESPOSTAS DE USUARIO AO SEREM EXCLUIDAS?
		return null;
	}
	
	@Override
	public Usuario consultarUsuario() {
		System.out.println("--CONSULTAR USUARIO--");
		HttpSession session = request.getSession();
		return (Usuario) session.getAttribute("contaLogada");
		
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
