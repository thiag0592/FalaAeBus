package controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.Usuario;
import model.UsuarioAdm;
import model.exception.ModelException;

public interface ICtrlIncluirUsuarioAdm {
	@POST
	@Path("/incluir")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario incluirUsuarioAdm(UsuarioAdm novo) throws ModelException, ControllerException;

}
