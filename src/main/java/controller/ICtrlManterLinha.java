package controller;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.Linha;
import model.exception.ModelException;

public interface ICtrlManterLinha {

	@POST
	@Path("/criarLinha")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void incluirLinha(Linha l) throws ModelException;

	@GET
	@Path("/obterLinhas")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Linha> obterLinhas();

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	void alterarLinha(Linha l);
}
