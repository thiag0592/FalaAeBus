package model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Departamento implements Serializable, Comparable<Departamento> {
	//
	//  ATRIBUTOS
	//
	  @Id
    @GeneratedValue
    private int id;

    @Column(length = 2, nullable = false)
    private String sigla;

    @Column(length = 40, nullable = false)
    private String nome;
	
	//
	// MÉTODOS
	//
	public Departamento() {
	}
	

	public Departamento(String s, String n) throws ModelException {
		super();
		this.setSigla(s);
		this.setNome(n);
	}
	
	public Departamento(int id, String s, String n) throws ModelException {
		super();
		this.setId(id);
		this.setSigla(s);
		this.setNome(n);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) throws ModelException {
		Departamento.validarId(id);
		this.id = id;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String s) throws ModelException {
		Departamento.validarSigla(s);
		this.sigla = s;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String n) throws ModelException {
		Departamento.validarNome(n);			
		this.nome = n;
	}

	@Override
	public String toString() {
		return nome;
	}
	
	public int compareTo(Departamento outro) {
		return this.nome.compareTo(outro.nome); 
	}
	
	public static void validarId(int id) throws ModelException {
		if(id <= 0)
			throw new ModelException("O ID do Departamento é inválido: " + id);
	}

	public static void validarSigla(String sigla) throws ModelException {
		if(sigla == null || sigla.length() != 2)
			throw new ModelException("A sigla passada é inválida: " + sigla);
	}

	public static void validarNome(String nome) throws ModelException {
		if(nome == null || nome.length() > 40)
			throw new ModelException("O nome passado é inválido: " + nome);
	}
}
