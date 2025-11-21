package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import model.exception.ModelException;

@Entity
public class UsuarioAdm extends Usuario {
	
	public UsuarioAdm(String nomeUsuario, String dataNascimentoUsuario, String cpfUsuario, String enderecoUsuario,
			String senhaMD5) throws ModelException {
		super(nomeUsuario, dataNascimentoUsuario, cpfUsuario, enderecoUsuario, senhaMD5);
	}
	
	

}
