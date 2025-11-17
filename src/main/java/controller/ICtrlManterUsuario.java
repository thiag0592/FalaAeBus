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

	@PUT
	@Path("/alterarUsuario")
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario alterarUsuario(Usuario modificacao);

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario removerUsuario(@PathParam("id") int id);
}
