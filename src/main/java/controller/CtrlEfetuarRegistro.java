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
import model.Usuario;

@Path("/ctrlLogin")
public class CtrlEfetuarRegistro implements ICtrlEfetuarRegistro {
	/**
	 * Bloco static é executado quando o bytecode da classe é carregado (Class.forName)
	 */
	static {
		System.out.println("Bytecode da classe CtrlEfetuarLogin foi carregado!");
	}

	//
	// ATRIBUTOS
	//

	@Context
	private HttpServletRequest request;

	//
	// MÉTODOS
	//
	public CtrlEfetuarRegistro() {
		System.out.println("Um objeto CtrlEfetuarLogin foi instanciado!!!");
	}

	public static void enviarErro(int codigo, String mensagem) {
		System.out.println("---Ocorreu o erro #" + codigo + " - " + mensagem);
		throw new WebApplicationException(codigo); 
		//Response.status(codigo).entity(mensagem).build();		
	}
	
	/**
	 *  Toda vez que chegar uma requisição, o Jersey Servlet vai injetar no parâmetro
	 *  request (abaixo) a referência para o objeto HttpServletRequest. Isso só ocorre
	 *  porque colocamos a anotação @Context na interface. Precisamos do request para 
	 *  ter acesso ao objeto HttpSession
	 */ 
	@Override
	public String registro(Usuario usr) {
		//Usuario usr = new Usuario("alessandro","mala");
		// Crio uma nova sessão (pois estamos passando o parâmetro true
		// Com isso, a resposta enviará o Cookie JSESSIONID que indica o 
		// valor de referência ao HttpSession do usuário
		HttpSession sessao = request.getSession(true);
		System.out.println("Usuário: " + usr);
		
		
		// Vou colocar na sessão do usuário uma informação indexada pela chave 'conta'
		// que armazenará o nome da conta que efetuou o login. Isso vai marcar que a 
		// sessão teve uma autenticação. O método setAttribute vincula ao Map da sessão 
		// um dado indexado pela chave 'conta'
		sessao.setAttribute("contaLogada", usr);
		
		// System.out.println(criptografar(usr.getConta()));
		
		// Retorno um texto dizendo que o login foi efetuado.
		return "Login da conta '" + usr.getConta() + "' feito com sucesso!";
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
