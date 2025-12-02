package controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.Usuario;
import model.UsuarioEmpresa;
import model.exception.ModelException;

public interface ICtrlIncluirUsuarioEmp {
	@POST
	@Path("/incluir")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario incluirUsuarioEmp(UsuarioEmpresa novo) throws ModelException, ControllerException;

}
