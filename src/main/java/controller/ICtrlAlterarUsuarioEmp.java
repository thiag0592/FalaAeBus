package controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.UsuarioEmpresa;
import model.exception.ModelException;

public interface ICtrlAlterarUsuarioEmp {
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
	  @Path("/alterarUsuario")
	  @Consumes(MediaType.APPLICATION_JSON)
	  @Produces(MediaType.APPLICATION_JSON) 
	  public UsuarioEmpresa alterarUsuarioEmp(UsuarioEmpresa modificacao) throws ModelException;
	  
	  
	 

	@GET
	@Path("/consultarUsuario")	
	@Produces(MediaType.APPLICATION_JSON)
	public UsuarioEmpresa consultarUsuarioEmp();
}
