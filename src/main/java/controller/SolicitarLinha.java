package controller;

import jakarta.ws.rs.Path;
import model.Linha;
import model.dao.DaoLinha;

@Path("solicitarLinha")
public class SolicitarLinha implements ICtrlSolicitarLinha {

	@Override
	public Linha solicitarLinha(Linha linha) {
		DaoLinha dao = new DaoLinha();
		dao.incluirUsuario(linha);
		return linha;
	}

}
