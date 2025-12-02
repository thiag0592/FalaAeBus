package controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.AvaliaLinha;
import model.exception.ModelException;

@Path("/avaliacao")
public interface ICtrlAvaliarLinha {

	String CH_LINHA_AVALIADA = "LinhaAvaliada";
	String CH_NOTA_AR = "NotaAr";
	String CH_NOTA_MANUT = "NotaManut";
	String CH_NOTA_HORARIO = "NotaHor";
	String CH_NOTA_LOTACAO = "NotaLot";

	
	@GET
    @Path("/iniciar")
    @Consumes(MediaType.APPLICATION_JSON)
	boolean iniciar() throws ModelException, ControllerException;

	@POST
    @Path("/informarlinha")
    @Consumes(MediaType.APPLICATION_JSON)
	boolean informarLinha(String numero, String cnpj) throws ModelException, ControllerException;

	@POST
    @Path("/informarnotas")
    @Consumes(MediaType.APPLICATION_JSON)
	boolean informarNotas(int ar, int manut, int horarios, int lotacao) throws ModelException, ControllerException;

	@POST
    @Path("/efetivaravaliacao")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	AvaliaLinha efetivarAvaliacao() throws ModelException, ControllerException;

}