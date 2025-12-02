package controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import model.Linha;
import model.Linha.Status;
import model.dao.DaoLinha;
import model.exception.ModelException;

@Path("/adm/linha")
public class CtrlManterLinha implements ICtrlManterLinha {

	@Context
	private HttpServletRequest request;
	
	@Context
	private HttpServletResponse response;

	public CtrlManterLinha() {
		System.out.println("Um objeto CtrlManterLinha foi instanciado!!!");
	}
	
	@Override
	public void incluirLinha(Linha linha) throws ModelException {
		linha.mudarStatus(Status.EM_FUNCIONAMENTO);
		DaoLinha dao = new DaoLinha();
		dao.incluirLinha(linha);
	}
	
	@Override
	public List<Linha> obterLinhas(){
		DaoLinha dao = new DaoLinha();
		return dao.obterLinhas();
		
	}
	
	@Override
	public Linha listarLinha(String id) throws ControllerException{
		DaoLinha dao = new DaoLinha();
		Linha l = dao.obterLinhaPeloNumero(id);
		if(l == null)
			throw new ControllerException("Linha não encontrada!");
		return l;
	}
	
	@Override
	public void alterarLinha(Linha l) {
		DaoLinha dao = new DaoLinha();
		dao.alterarLinha(l);		
	}
	
	
}
