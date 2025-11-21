package model;

import jakarta.persistence.*;
import model.exception.ModelException;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Empresa {

    @Id @GeneratedValue
    private int id;
    @Column(length = 18, nullable = false)
    private String cnpj;

    @Column(length = 80, nullable = false)
    private String nomeEmpresa;

    @OneToMany(mappedBy = "empresa")
    private List<Linha> linhas;


    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) throws ModelException 
    { validarCnpj(cnpj); this.cnpj = cnpj; }
    private void validarCnpj(String cnpj) throws ModelException {
        if (cnpj == null || !cnpj.matches("\\d{14}"))
            throw new ModelException("CNPJ inválido. Deve conter exatamente 14 dígitos.");
    }
    
    
    public String getNomeEmpresa() { return nomeEmpresa; }
    public void setNomeEmpresa(String nomeEmpresa) throws ModelException 
    { validarNomeEmpresa(nomeEmpresa); this.nomeEmpresa = nomeEmpresa; }
    private void validarNomeEmpresa(String nomeEmpresa) throws ModelException {
        if (nomeEmpresa == null || nomeEmpresa.trim().isEmpty())
            throw new ModelException("Nome da empresa não pode ser vazio.");
        if (!nomeEmpresa.matches("^[A-Za-zÀ-ÿ0-9 ]+$"))
            throw new ModelException("Nome da empresa contém caracteres inválidos.");
    }
    
    
    public List<Linha> getLinhas() { return linhas; }
    public void setLinhas(List<Linha> linhas) { this.linhas = linhas; }
   
}
