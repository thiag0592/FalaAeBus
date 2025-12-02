package controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.Usuario;
import model.UsuarioAdm;
import model.UsuarioEmpresa;

public interface ICtrlEfetuarLogin {
	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String loginUsuario(Usuario usr);

	@POST
	@Path("login/Adm")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String loginAdm(UsuarioAdm usr);

	@POST
	@Path("login/Emp")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String loginEmp(UsuarioEmpresa usr);

	@POST
	@Path("logoff")
	@Produces(MediaType.TEXT_PLAIN)
	public String logoff() throws Exception;
}
