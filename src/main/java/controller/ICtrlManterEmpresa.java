package controller;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.Empresa;
import model.exception.ModelException;

public interface ICtrlManterEmpresa {

	@POST
	@Path("/criarEmpresa")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	void incluirEmpresa(Empresa empresa) throws ModelException;

	@GET
	@Path("/obterEmpresas")
	@Produces(MediaType.APPLICATION_JSON)
	List<Empresa> obterEmpresas();
	
	@GET
	@Path("/listarEmpresa")
	@Produces(MediaType.APPLICATION_JSON)
	Empresa listarEmpresa(String cnpj) throws ControllerException;

	@PUT
	@Path("/alterarEmpresa")
	@Consumes(MediaType.APPLICATION_JSON)
	void alterarEmpresa(Empresa e) throws ModelException;

}