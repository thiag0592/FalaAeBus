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
	@Path("login/adm")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String loginAdm(UsuarioAdm usr);

	@POST
	@Path("login/repemp")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String loginEmp(UsuarioEmpresa usr);

	@POST
	@Path("logoff")
	@Produces(MediaType.TEXT_PLAIN)
	public String logoff() throws Exception;

	@POST
	@Path("logoffAdm")
	@Produces(MediaType.TEXT_PLAIN)
	public String logoffADM();

	@POST
	@Path("logoffEmp")
	@Produces(MediaType.TEXT_PLAIN)
	public String logoffEmp();
}
