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
import model.UsuarioEmpresa;
import model.dao.DaoUsuarioEmp;
import model.exception.ModelException;

import static controller.ApplicationConfig.CH_EMP_ATUAL;

// 
// http://127.0.0.1:8080/prjEx02Rest/application.wadl?
//

@Path("/adm/ctrlAlterarEmp")
public class CtrlAlterarUsuarioEmp implements ICtrlAlterarUsuarioEmp {

	@Context
	private HttpServletRequest request;

	/**
	 * Bloco static é executado quando o bytecode da classe é carregado
	 * (Class.forName)
	 */
	static {
		System.out.println("Bytecode da classe CtrlManterUsuarioEMP foi carregado!");
	}

	//
	// MÉTODOS
	//
	public CtrlAlterarUsuarioEmp() {
		System.out.println("Um objeto CtrlManterUsuarioEMP foi instanciado!!!");
	}


	@Override
	public UsuarioEmpresa alterarUsuarioEmp(UsuarioEmpresa modificacao) throws ModelException {
		System.out.println("---ALTERAR USUARIOEMP---");
		DaoUsuarioEmp dao = new DaoUsuarioEmp();
		HttpSession session = request.getSession();
		UsuarioEmpresa usrSession = (UsuarioEmpresa) session.getAttribute(CH_EMP_ATUAL);
		modificacao.setIdUsuario(usrSession.getIdUsuario());
		dao.alterarUsuarioEmp(modificacao);
		return modificacao; 
	}

	@Override
	public UsuarioEmpresa consultarUsuarioEmp() {
		System.out.println("--CONSULTAR USUARIOEMP--");
		HttpSession session = request.getSession();
		return (UsuarioEmpresa) session.getAttribute(CH_EMP_ATUAL);

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
