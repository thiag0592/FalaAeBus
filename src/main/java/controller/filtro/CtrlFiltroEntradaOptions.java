package controller.filtro;

import java.io.IOException;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;


//
// Sugiro visitar a página https://requestly.com/blog/what-is-cors-and-how-to-bypass-it/
//
@Provider
@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class CtrlFiltroEntradaOptions implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        System.out.println(">>> CtrlFiltroEntradaOptions : Início");
       // Trata requisições pré-flight (OPTIONS)
        if ("OPTIONS".equalsIgnoreCase(requestContext.getMethod())) {
            System.out.println(">>> CtrlFiltroEntradaOptions : interceptando OPTIONS");

            Response resposta = Response.ok()
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                    .header("Access-Control-Max-Age", "1209600")
                    .build();
            // Ele aborta a requisição (pré-flight), pois, com a autorização permitida, ele irá novamente
            // fazer a requisição com o HTTP METHOD original, juntamente com os dados a serem enviados
            requestContext.abortWith(resposta);
        }
    }
}