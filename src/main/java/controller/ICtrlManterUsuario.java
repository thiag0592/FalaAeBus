package controller;

import java.util.Collection;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.Usuario;

public interface ICtrlManterUsuario {
	@POST
	@Path("/Registro")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario incluirUsuario(Usuario novo);

	//TODO MÉTODO PARA TESTES, APAGAR AO FINAL DO DESENVOLVIMENTO
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Usuario> listarUsuarios();

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario listarUsuario(@PathParam("id") int id);

	@PUT
	@Path("/{id}/{sigla}/{nome}")
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario alterarUsuario(@PathParam("id") int id, @PathParam("sigla") String sigla,
			@PathParam("nome") String nome);

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario removerUsuario(@PathParam("id") int id);
}
