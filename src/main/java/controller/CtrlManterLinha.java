package controller;

import java.util.List;

import jakarta.ws.rs.Path;
import model.Linha;
import model.Linha.Status;
import model.dao.DaoLinha;

@Path("/adm/linha")
public class CtrlManterLinha implements ICtrlManterLinha {

	@Override
	public void incluirLinha(Linha linha) {
		linha.mudarStatus(Status.EM_FUNCIONAMENTO);
		DaoLinha dao = new DaoLinha();
		dao.incluirUsuario(linha);
	}
	
	@Override
	public List<Linha> obterLinhas(){
		DaoLinha dao = new DaoLinha();
		return dao.obterLinhas();
		
	}
	
	@Override
	public void alterarLinha(Linha l) {
		DaoLinha dao = new DaoLinha();
		dao.alterarLinha(l);		
	}
	
	
}
