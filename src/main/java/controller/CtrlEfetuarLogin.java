package controller;

import static controller.ApplicationConfig.CH_USUARIO_ATUAL;
import static controller.ApplicationConfig.CH_ADM_ATUAL;
import static controller.ApplicationConfig.CH_EMP_ATUAL;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import model.Usuario;
import model.UsuarioAdm;
import model.UsuarioEmpresa;
import model.dao.DaoUsuario;
import model.dao.DaoUsuarioAdm;
import model.dao.DaoUsuarioEmp;

@Path("/ctrlLogin")
public class CtrlEfetuarLogin implements ICtrlEfetuarLogin {
	/**
	 * Bloco static é executado quando o bytecode da classe é carregado (Class.forName)
	 */
	static {
		System.out.println("Bytecode da classe CtrlEfetuarLogin foi carregado!");
	}

	//
	// ATRIBUTOS
	//
	/**
	 *  Toda vez que chegar uma requisição, o Jersey Servlet vai injetar no atributo
	 *  request (abaixo) a referência para o objeto HttpServletRequest. Isso só ocorre
	 *  porque colocamos a anotação @Context. Precisamos do request para ter acesso ao
	 *  objeto HttpSession
	 */ 
	@Context
	private HttpServletRequest request;
	
	@Context
	private HttpServletResponse response;

	//
	// MÉTODOS
	//
	public CtrlEfetuarLogin() {
		System.out.println("Um objeto CtrlEfetuarLogin foi instanciado!!!");
	}

	/**
	 * Envia uma indicação de erro na requisição usando o objeto HttpServletResponse
	 * @param codigo
	 * @param mensagem
	 */
	public static void enviarErro(int codigo, String mensagem) {
		System.out.println("---Ocorreu o erro #" + codigo + " - " + mensagem);
		throw new WebApplicationException(codigo); 
		//Response.status(codigo).entity(mensagem).build();		
	}
	 
	@Override
	public String loginUsuario(Usuario usr) {
		System.out.println("Usuário: " + usr);
		DaoUsuario dao = new DaoUsuario();
		Usuario usuario = dao.obterUsuarioPeloCpf(usr.getCpfUsuario());
		if(usuario == null) {
			enviarErro(HttpServletResponse.SC_FORBIDDEN, "Usuário Inválido!");
			return null;
		}
		
		if(!usuario.getSenhaMD5().toUpperCase().equals(usr.getSenhaMD5().toUpperCase())) {
			enviarErro(HttpServletResponse.SC_FORBIDDEN, "Senha Inválida!");
			return null;
		}
	
		HttpSession sessao = request.getSession(true);
		sessao.setAttribute(CH_USUARIO_ATUAL, usuario);
		
		// Retorno um texto dizendo que o login foi efetuado.
		return "Login da conta '" + usr.getCpfUsuario() + "' feito com sucesso!";
	}


	@Override
	public String loginAdm(UsuarioAdm usr) {
		System.out.println("Usuário Adm: " + usr);
		DaoUsuarioAdm dao = new DaoUsuarioAdm();
		UsuarioAdm adm = dao.obterUsuarioAdmPeloCpf(usr.getCpfUsuario());
		if (adm == null) {
			enviarErro(HttpServletResponse.SC_FORBIDDEN, "Usuário Inválido!");
			return null;
		}

		if (!adm.getSenhaMD5().toUpperCase().equals(usr.getSenhaMD5().toUpperCase())) {
			enviarErro(HttpServletResponse.SC_FORBIDDEN, "Senha Inválida!");
			return null;
		}

		HttpSession sessao = request.getSession(true);
		sessao.setAttribute(CH_ADM_ATUAL, adm);

		// Retorno um texto dizendo que o login foi efetuado.
		return "Login da conta '" + usr.getCpfUsuario() + "' feito com sucesso!";

	}
	
	@Override
	public String loginEmp(UsuarioEmpresa usr) {
		System.out.println("Usuário EMP: " + usr);
		DaoUsuarioEmp dao = new DaoUsuarioEmp();
		UsuarioEmpresa emp = dao.obterUsuarioEmpPeloCpf(usr.getCpfUsuario());
		if (emp == null) {
			enviarErro(HttpServletResponse.SC_FORBIDDEN, "Usuário Inválido!");
			return null;
		}

		if (!emp.getSenhaMD5().toUpperCase().equals(usr.getSenhaMD5().toUpperCase())) {
			enviarErro(HttpServletResponse.SC_FORBIDDEN, "Senha Inválida!");
			return null;
		}

		HttpSession sessao = request.getSession(true);
		sessao.setAttribute(CH_EMP_ATUAL, emp);

		// Retorno um texto dizendo que o login foi efetuado.
		return "Login da conta '" + usr.getCpfUsuario() + "' feito com sucesso!";
	}

	@Override
	public String logoff() {
		HttpSession sessao = request.getSession();
		Usuario atual = (Usuario)sessao.getAttribute(CH_USUARIO_ATUAL);
		request.getSession(true);
		return "Logoff da conta '" + atual.getCpfUsuario() + "' feito com sucesso!";
	}
	@Override
	public String logoffADM() {
		HttpSession sessao = request.getSession();
		Usuario atual = (Usuario)sessao.getAttribute(CH_ADM_ATUAL);
		request.getSession(true);
		return "Logoff da conta '" + atual.getCpfUsuario() + "' feito com sucesso!";
	}
	@Override
	public String logoffEmp() {
		HttpSession sessao = request.getSession();
		UsuarioEmpresa atual = (UsuarioEmpresa)sessao.getAttribute(CH_EMP_ATUAL);
		request.getSession(true);
		return "Logoff da conta '" + atual.getCpfUsuario() + "' feito com sucesso!";
	}

	// Desconsiderar o código abaixo. Será útil para as futuras aulas
	// Mesma coisa, medo de dar ruim de tirar
	private static String chaveCriptografia = "0123456789abcdef";
	private static byte[] decodedKey = Base64.getDecoder().decode(chaveCriptografia);
	private static byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private static IvParameterSpec ivspec = new IvParameterSpec(iv);

	public static String criptografar(String texto) {
		try {
			// Obtendo uma instância do cifrador
			// AES: Algoritmo de criptografia (Advanced Encryption Standard)
			// CBC: Modo de operação (Cipher Block Chaining)
			// PKCS5Padding: Esquema de preenchimento para blocos incompletos
			// SunJCE: Provedor de segurança específico da Oracle
			Cipher cifrador = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
			
			// Cria uma chave secreta a partir dos bytes. Vamos garantir que 
			// que a chave tenha exatamente 16 bytes (128 bits - tamanho requerido pelo AES)
			// AES - algoritmo para o qual a chave será usada
			SecretKey chave = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
			
			// Cipher.ENCRYPT_MODE: Configura o cipher para modo de criptografia
			// chave: A chave secreta criada anteriormente
			// ivspec: Vetor de inicialização (IV) para o modo CBC
			cifrador.init(Cipher.ENCRYPT_MODE, chave, ivspec);

			// Efetuando a Codificação 
			byte[] cipherText = cifrador.doFinal(texto.getBytes("UTF-8"));
			
			// Retornando uma String no formato Base64 da codificação realizada
			return Base64.getEncoder().encodeToString(cipherText);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | 
				 NoSuchPaddingException | InvalidAlgorithmParameterException | 
				 IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String descriptografar(String cripto) throws Exception {
		try { 
			Cipher decifrador = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
			SecretKey chave = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
			decifrador.init(Cipher.DECRYPT_MODE, chave, ivspec);
			byte[] cipherText = decifrador.doFinal(Base64.getDecoder().decode(cripto));
			return new String(cipherText);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | 
				 NoSuchPaddingException | InvalidAlgorithmParameterException | 
				 IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
