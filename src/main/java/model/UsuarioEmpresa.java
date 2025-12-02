package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import model.exception.ModelException;

@Entity
@Table
public class UsuarioEmpresa extends Usuario {
	
	@Column(nullable = false)
	private boolean idEmpresa;

	private String cnpj;
	
	public UsuarioEmpresa(String nomeUsuario, String dataNascimentoUsuario, String cpfUsuario, String enderecoUsuario,
			String senhaMD5, boolean idEmpresa,String cnpj) throws ModelException {
		super(nomeUsuario, dataNascimentoUsuario, cpfUsuario, enderecoUsuario, senhaMD5);
		this.idEmpresa = idEmpresa;
		this.cnpj = cnpj;
	}

	public boolean isIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(boolean idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	
}
