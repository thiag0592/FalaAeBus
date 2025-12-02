package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import model.exception.ModelException;

@Entity
public class UsuarioAdm extends Usuario {
	
	@Column(nullable = false)
	private boolean idAdm;
	
	public UsuarioAdm(String nomeUsuario, String dataNascimentoUsuario, String cpfUsuario, String enderecoUsuario,
			String senhaMD5, boolean idAdm) throws ModelException {
		super(nomeUsuario, dataNascimentoUsuario, cpfUsuario, enderecoUsuario, senhaMD5);
		this.idAdm = idAdm;
	}
	
	

}
