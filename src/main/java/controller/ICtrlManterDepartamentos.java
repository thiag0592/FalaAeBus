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
import model.Departamento;

public interface ICtrlManterDepartamentos {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Departamento incluirDepartamento(Departamento novo);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Departamento> listarDepartamentos();

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Departamento listarDepartamento(@PathParam("id") int id);

	@PUT
	@Path("/{id}/{sigla}/{nome}")
	@Produces(MediaType.APPLICATION_JSON)
	public Departamento alterarDepartamento(@PathParam("id") int id, @PathParam("sigla") String sigla,
			@PathParam("nome") String nome);

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Departamento removerDepartamento(@PathParam("id") int id);
}
