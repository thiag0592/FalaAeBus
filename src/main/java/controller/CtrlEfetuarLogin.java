package controller;

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
import jakarta.ws.rs.core.Response;
import model.Usuario;
import model.dao.DaoUsuario;

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
	
	/**
	 * Verifica se o usuário efetuou previamente um login
	 * @return
	 */
	public static boolean sessaoValida(HttpServletRequest request) {
		// Recupero o HttpSession vinculado à requisição
		HttpSession sessao = request.getSession();
		// Verifico no HttpSession se há algo indexado com a chave 'contaLogada'
		String conta = (String) sessao.getAttribute("contaLogada");
		// Se não houver algo indexado, é porque ele não efetuou previamente o login
		if (conta == null) {
			// Envio erro com código FORBIDDEN (403) do http.
			enviarErro(HttpServletResponse.SC_FORBIDDEN, "Login não efetuado");
			return false;
		}
		// O usuário efetuou previamente o login.
		return true;
	}

	/**
	 *  Toda vez que chegar uma requisição, o Jersey Servlet vai injetar no parâmetro
	 *  request (abaixo) a referência para o objeto HttpServletRequest. Isso só ocorre
	 *  porque colocamos a anotação @Context na interface. Precisamos do request para 
	 *  ter acesso ao objeto HttpSession
	 */ 
	@Override
	public String login(Usuario usr) {
		//Usuario usr = new Usuario("alessandro","mala");
		// Crio uma nova sessão (pois estamos passando o parâmetro true
		// Com isso, a resposta enviará o Cookie JSESSIONID que indica o 
		// valor de referência ao HttpSession do usuário
		HttpSession sessao = request.getSession(true);
		System.out.println("Usuário: " + usr);
		DaoUsuario dao = new DaoUsuario();
		Usuario busca = dao.buscarPorLoginESenha(usr.getEnderecoUsuario(), usr.getSenhaMD5());
		if(busca == null)
			enviarErro(HttpServletResponse.SC_FORBIDDEN, "Email ou senha Senha Inválida!");
		
		sessao.setAttribute("contaLogada", busca);
		
		// System.out.println(criptografar(usr.getConta()));
		
		// Retorno um texto dizendo que o login foi efetuado.
	return "Login de \"" + busca.getNomeUsuario() + "\" feito com sucesso!";
	}

	@Override
	public String logoff() {
		// recuperamos a sessão do usuário 
		HttpSession sessao = request.getSession();
		sessao.setMaxInactiveInterval(0);
		// Verifico se o usuário, de fato, estava logado
		Usuario usr = (Usuario)sessao.getAttribute("contaLogada");
		sessao.removeAttribute("contaLogada");
		// Retorno um texto dizendo que o login foi efetuado.
		//return "Logoff da conta '" + usr.getConta() + "' feito com sucesso!";
		return null;
	}

	// Desconsiderar o código abaixo. Será útil para as futuras aulas
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
