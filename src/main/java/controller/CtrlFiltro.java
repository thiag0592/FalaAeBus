package controller;

import java.io.IOException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.ext.Provider;
import model.Usuario;

@Provider
@PreMatching
public class CtrlFiltro implements ContainerRequestFilter {
	@Context
	private HttpServletRequest request;

	@Context
	private HttpServletResponse response;

	public CtrlFiltro() {
		System.out.println("Filtro de Entrada: " + this);
	}

	@Override
	public void filter(ContainerRequestContext ctx) throws IOException {
		System.out.println("INÍCIO FILTRO ENTRADA ");
		String path = ctx.getUriInfo().getPath();
		System.out.println("Path da Requisição: " + path);
		System.out.println("Http Method da Requisição: " + ctx.getMethod());

		// Recupero a sessão vinculada à requisição
		HttpSession sessao = request.getSession();

		// TODO Falar na semana que vem
		if (path.contains("application.wadl")) 
			return;
		
		// Se o path não tem a String 'login', precisamos ver se o usuário efetuou previamente 
		// a ação de login 
		if (!path.contains("login")) {
			// Se o usuário fez o login, então na sessão temos um dado vinculado ao rótulo "contaLogada"
			Usuario conta = (Usuario)sessao.getAttribute("contaLogada");
			if(conta == null) 	
				throw new NotAuthorizedException("Não autorizado");
			// Por curiosidade, vamos mostrar o JSESSIONID do usuário
			for(Cookie biscoito : request.getCookies()) {
				if(biscoito.getName().equals("JSESSIONID")) {
					System.out.println("Requisição vinda de " + conta  + " " +
							  " com JSESSIONID = " + biscoito.getValue());
					break;
				}
			}
		} else {
			System.out.println("Requisição de login.");
		}
		// Indicando o content type do body
		System.out.println("->" + request.getContentType());
		System.out.println("FIM FILTER ENTRADA");
	}
}


// ctx.abortWith(Response.status(Response.Status.FORBIDDEN).entity("A sessão não foi iniciada!").build());
// return;
