package model.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import model.Departamento;

//
// DAO - Data Access Object
//
public class DaoDepartamento {

	private static Map<Integer, Departamento> conjDeptos = new HashMap<Integer, Departamento>();

	public void incluirDepartamento(Departamento d) {
		conjDeptos.put(d.getId(), d);
	}
	
	public Collection<Departamento> obterDepartamentos() {
		return conjDeptos.values();
	}
	
	public Departamento obterDepartamento(int id) {
		return conjDeptos.get(id);
	}
	
	public Departamento alterarDepartamento(Departamento d) {
		return d;
	}
	
	public Departamento removerDepartamento(Departamento d) {
		return conjDeptos.remove(d.getId());
	}
}
