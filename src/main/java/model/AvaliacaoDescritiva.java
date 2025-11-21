package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import model.exception.FluxoDeEstadoException;

@Entity
@Table
public class AvaliacaoDescritiva {
	public enum Status{
		EM_ANALISE,APROVADO,REMOVIDO
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Lob
    private String comentario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.EM_ANALISE;

    @OneToOne
    @JoinColumn(name = "id_avaliacao", nullable = false)
    private AvaliaLinha avaliacao;


    // ======================
    // Getters e Setters
    // ======================

    public int getId() {
        return id;
    }

    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Status getStatus() {
        return status;
    }

    private void setStatus(Status status) {
        this.status = status;
    }

    public AvaliaLinha getAvaliacao() {
        return avaliacao;
    }
    public void setAvaliacao(AvaliaLinha avaliacao) {
        this.avaliacao = avaliacao;
    }


    // ======================
    //  Regras do diagrama
    // ======================

    /**  
     * Moderar comentário → vai para EM_ANALISE.  
     * (Início → Em Análise)
     */
    public void iniciarAnalise() throws FluxoDeEstadoException {
        if (status != Status.EM_ANALISE) {
            throw new FluxoDeEstadoException("A avaliação já foi processada. Não é possível reiniciar análise.");
        }
    }

    /**  
     * Em Análise → Aprovado  
     */
    public void aprovar() throws FluxoDeEstadoException {
        if (status != Status.EM_ANALISE) {
            throw new FluxoDeEstadoException("Só é possível aprovar uma avaliação que está EM_ANALISE.");
        }
        setStatus(Status.APROVADO);
    }

    /**  
     * Em Análise → Removido  
     */
    public void remover() throws FluxoDeEstadoException {
        if (status != Status.EM_ANALISE) {
            throw new FluxoDeEstadoException("Só é possível remover uma avaliação que está EM_ANALISE.");
        }
        setStatus(Status.REMOVIDO);
    }

    /**  
     * Retorna se está no estado final  
     */
    public boolean isFinalizado() {
        return status == Status.APROVADO ||
               status == Status.REMOVIDO;
    }
}
