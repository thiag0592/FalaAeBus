package controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import model.Empresa;
import model.dao.DaoEmpresa;
import model.exception.ModelException;


@Path("/adm/empresa")
public class CtrlManterEmpresa implements ICtrlManterEmpresa {

	@Context
	private HttpServletRequest request;
	
	@Context
	private HttpServletResponse response;
	
	public CtrlManterEmpresa() {
		System.out.println("Um objeto CtrlManterEmpresa foi instanciado!!!");
	}
	
	
	@Override
	public void incluirEmpresa(Empresa empresa) throws ModelException {
		DaoEmpresa dao = new DaoEmpresa();
		dao.incluir(empresa);
	}
	
	
	@Override
	public List<Empresa> obterEmpresas(){
		DaoEmpresa dao = new DaoEmpresa();
		return dao.obterTodos();
		
	}
	
	@Override
	public Empresa listarEmpresa(String cnpj) throws ControllerException{
		DaoEmpresa dao = new DaoEmpresa();
		Empresa e = dao.obterEmpresaPeloCnpj(cnpj);
		if(e == null)
			throw new ControllerException("Empresa não encontrada!");
		return e;
	}
	
	
	@Override
	public void alterarEmpresa(Empresa e) throws ModelException {
		DaoEmpresa dao = new DaoEmpresa();
		dao.alterar(e);		
	}
	
}
