package model;

import jakarta.persistence.*;

@Entity
public class AvaliaLinha {
	public enum Status{
		AVALIADO,MEDIADO,RUIM,REGULAR,BOM,OTIMO
	}
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private int notaArCondicionado;

    @Column(nullable = false)
    private int notaManutencao;

    @Column(nullable = false)
    private int notaDistribuicaoHorarios;

    @Column(nullable = false)
    private int notaLotacaoPassageiros;

    // ========================
    //     RELACIONAMENTOS
    // ========================

    // Uma avaliação pertence a UMA linha
    @ManyToOne(optional = false)
    private Linha linha;

    // Uma avaliação pertence a UM usuário
    @ManyToOne(optional = false)
    private Usuario usuario;

    // Uma avaliação PODE ter uma avaliação descritiva
    @OneToOne(optional = true)
    private AvaliacaoDescritiva avaliacaoDescritiva;

    
    
    
    private Status status;
    public void processarCalculoDeMedia(double media) {

        // Estado: Avaliado → Mediado
        setStatus(Status.MEDIADO);

        // Estado: Mediated → Classificação final
        if (media >= 1.0 && media <= 1.9) {
            setStatus(Status.RUIM);
        }
        else if (media >= 2.0 && media <= 2.9) {
            setStatus(Status.REGULAR);
        }
        else if (media >= 3.0 && media <= 3.9) {
            setStatus(Status.BOM);
        }
        else if (media >= 4.0 && media <= 5.0) {
            setStatus(Status.OTIMO);
        }
        else {
            throw new IllegalArgumentException("Média fora do intervalo 1 a 5.");
        }
    }
    // ========================
    //   GETTERS / SETTERS / VALIDAR
    // ========================

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    // --- Ar Condicionado ---
    public int getNotaArCondicionado() { return notaArCondicionado; }
    public void setNotaArCondicionado(int nota) throws ModelException {
        validarNota(nota);
        this.notaArCondicionado = nota;
    }

    private void validarNota(int nota) throws ModelException {
        if (nota < 1 || nota > 5)
            throw new ModelException("Nota deve ser de 1 a 5.");
    }

    // --- Manutenção ---
    public int getNotaManutencao() { return notaManutencao; }
    public void setNotaManutencao(int nota) throws ModelException 
    { validarNota(nota); this.notaManutencao = nota; }

    // --- Distribuição de Horários ---
    public int getNotaDistribuicaoHorarios() { return notaDistribuicaoHorarios; }
    public void setNotaDistribuicaoHorarios(int nota) throws ModelException 
    { validarNota(nota); this.notaDistribuicaoHorarios = nota; }

    // --- Lotação de Passageiros ---
    public int getNotaLotacaoPassageiros() { return notaLotacaoPassageiros; }
    public void setNotaLotacaoPassageiros(int nota) throws ModelException 
    { validarNota(nota); this.notaLotacaoPassageiros = nota; }

    
    
    public Status getStatus() { return status; }
	public void setStatus(Status status) { this.status = status; }


	// --- Linha ---
    public Linha getLinha() { return linha; }
    public void setLinha(Linha linha) { this.linha = linha; }

    // --- Usuário ---
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    // --- Avaliação Descritiva ---
    public AvaliacaoDescritiva getAvaliacaoDescritiva() { return avaliacaoDescritiva; }
    public void setAvaliacaoDescritiva(AvaliacaoDescritiva avaliacaoDescritiva) 
    { this.avaliacaoDescritiva = avaliacaoDescritiva;}
}
