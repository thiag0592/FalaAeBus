package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import model.exception.ModelException;

@Entity
@Table
public class UsuarioAdm extends Usuario {
	
	@Column(nullable = false)
	private boolean idAdm;
	
	public UsuarioAdm(String nomeUsuario, String dataNascimentoUsuario, String cpfUsuario, String enderecoUsuario,
			String senhaMD5, boolean idAdm) throws ModelException {
		super(nomeUsuario, dataNascimentoUsuario, cpfUsuario, enderecoUsuario, senhaMD5);
		this.idAdm = idAdm;
	}

	public boolean isIdAdm() {
		return idAdm;
	}

	public void setIdAdm(boolean idAdm) {
		this.idAdm = idAdm;
	}
	
	

}
