package controller;

import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import model.UsuarioAdm;
import model.dao.DaoUsuarioAdm;
import model.exception.ModelException;

import static controller.ApplicationConfig.CH_ADM_ATUAL;

// 
// http://127.0.0.1:8080/prjEx02Rest/application.wadl?
//

@Path("/adm/ctrlAlterarAdm")
public class CtrlAlterarUsuarioAdm implements ICtrlAlterarUsuarioAdm {

	@Context
	private HttpServletRequest request;

	/**
	 * Bloco static é executado quando o bytecode da classe é carregado
	 * (Class.forName)
	 */
	static {
		System.out.println("Bytecode da classe CtrlManterUsuarioADM foi carregado!");
	}

	//
	// MÉTODOS
	//
	public CtrlAlterarUsuarioAdm() {
		System.out.println("Um objeto CtrlManterUsuarioADM foi instanciado!!!");
	}


	@Override
	public UsuarioAdm alterarUsuarioAdm(UsuarioAdm modificacao) throws ModelException {
		System.out.println("---ALTERAR USUARIOADM---");
		DaoUsuarioAdm dao = new DaoUsuarioAdm();
		HttpSession session = request.getSession();
		UsuarioAdm usrSession = (UsuarioAdm) session.getAttribute(CH_ADM_ATUAL);
		modificacao.setIdUsuario(usrSession.getIdUsuario());
		dao.alterarUsuarioAdm(modificacao);
		return modificacao; 
	}

	@Override
	public UsuarioAdm consultarUsuarioAdm() {
		System.out.println("--CONSULTAR USUARIOADM--");
		HttpSession session = request.getSession();
		return (UsuarioAdm) session.getAttribute(CH_ADM_ATUAL);

	}

	// Desconsiderar o código abaixo. Será útil para as futuras aulas private
	static String chaveCriptografia = "0123456789abcdef";
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
