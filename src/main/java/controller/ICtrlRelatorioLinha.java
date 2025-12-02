package controller;

import dto.DtoRelatorio;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

public interface ICtrlRelatorioLinha {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	DtoRelatorio GeraRelatorio(@QueryParam("cnpj") String cnpj,@QueryParam("numeroLinha") String numeroLinha);

}