package controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.UsuarioAdm;
import model.exception.ModelException;

public interface ICtrlAlterarUsuarioAdm {
	/*
	 * @POST
	 * 
	 * @Path("/Registro")
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Usuario incluirUsuario(Usuario
	 * novo) throws ModelException, ControllerException;
	 */

	@PUT
	@Path("/alterarAdm")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UsuarioAdm alterarUsuarioAdm(UsuarioAdm modificacao) throws ModelException;

	@GET
	@Path("/consultarAdm")
	@Produces(MediaType.APPLICATION_JSON) 
	public UsuarioAdm consultarUsuarioAdm();
}
