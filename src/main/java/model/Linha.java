package model;

import java.util.List;

import jakarta.persistence.*;
import model.exception.FluxoDeEstadoException;
import model.exception.ModelException;

@Entity
@Table
public class Linha {
	
// ===============================
//            ENUM
// ===============================
	
	public enum Status {
		
		EM_ANALISE {
			@Override
			public boolean podeIrPara(Status destino) {
				return destino == MODIFICADO || destino == REPROVADO;
			}
		},
		
		MODIFICADO {
			@Override
			public boolean podeIrPara(Status destino) {
				return destino == EM_FUNCIONAMENTO;
			}
		},
		
		EM_FUNCIONAMENTO {
			@Override
			public boolean podeIrPara(Status destino) {
				return destino == INATIVO;
			}
		},
		
		INATIVO {
			@Override
			public boolean podeIrPara(Status destino) {
				return destino == EM_ANALISE || destino == MODIFICADO;
			}
		},
		
		REPROVADO {
			@Override
			public boolean podeIrPara(Status destino) {
				return false; // estado final absoluto
			}
		};
		
		public abstract boolean podeIrPara(Status destino);
	}

    @Id
    @GeneratedValue
    private int idLinha;

    @Column(nullable = false)
    private int numeroLinha; 
    @Column(nullable = false)
    private String caminho;

    
    @ManyToOne(fetch = FetchType.LAZY)
    private Empresa empresa;
    
    @OneToMany(mappedBy = "linha",fetch = FetchType.LAZY)
    private List<AvaliaLinha> avaliaLinha; 
    
 
	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.EM_ANALISE;

    public Linha() {}


    // ===============================
    //   MÉTODO DE TROCA DE ESTADO
    // ===============================

    /**
     * Tenta mudar o estado respeitando as regras do diagrama.
     * Se a transição não for permitida, lança FluxoDeEstadoException.
     */
    public void mudarStatus(Status novo) {
        if (!this.status.podeIrPara(novo)) {
            throw new FluxoDeEstadoException(
                "Transição inválida: " + this.status + " → " + novo
            );
        }
        this.status = novo;
    }

 

    public int getIdLinha() { return idLinha; }
    public void setIdLinha(int idLinha) { this.idLinha = idLinha; }

    public int getNumeroLinha() { return numeroLinha; }
    public void setNumeroLinha(int numeroLinha) throws ModelException 
    { validarNumero(numeroLinha); this.numeroLinha = numeroLinha; }
    
    private void validarNumero(int numero) throws ModelException {
        if (numero <= 0) {
            throw new ModelException("O número da linha deve ser positivo.");
        }
    }

    

    public String getCaminho() { return caminho; }
    public void setCaminho(String caminho) throws ModelException 
    { validarCaminho(caminho); this.caminho = caminho; }
    
    private void validarCaminho(String caminho) throws ModelException {
    	if (caminho == null || caminho.isBlank()) {
    		throw new ModelException("O caminho não pode ser vazio.");
    	}
    	if (!caminho.contains("-")) {
    		throw new ModelException("Use o formato: \"BairroInicial - BairroFinal\".");
    	}
    }

    
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    
    
    public Empresa getEmpresa() { return empresa; }
 	public void setEmpresa(Empresa empresa) { this.empresa = empresa;}

}
