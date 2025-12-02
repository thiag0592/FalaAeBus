package dto;

public class DtoRelatorio {

	public enum Status {
		RUIM, REGULAR, BOM, OTIMO
	}

	private Status status;

	private int RelatorioAr;
	private int RelatorioManut;
	private int RelatorioLota;
	private int RelatorioHora;

	public DtoRelatorio() {
	}

	public DtoRelatorio(int relatorioAr, int relatorioManut, int relatorioLota, int relatorioHora) {
		RelatorioAr = relatorioAr;
		RelatorioManut = relatorioManut;
		RelatorioLota = relatorioLota;
		RelatorioHora = relatorioHora;
	}

	public void processarCalculoDeMedia(double media) {

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

	public Status getStatus() { return status; }
	public void setStatus(Status status) { this.status = status; }
	
	public int getRelatorioAr() {
		return RelatorioAr;
	}

	public void setRelatorioAr(int relatorioAr) {
		RelatorioAr = relatorioAr;
	}

	public int getRelatorioManut() {
		return RelatorioManut;
	}

	public void setRelatorioManut(int relatorioManut) {
		RelatorioManut = relatorioManut;
	}

	public int getRelatorioLota() {
		return RelatorioLota;
	}

	public void setRelatorioLota(int relatorioLota) {
		RelatorioLota = relatorioLota;
	}

	public int getRelatorioHora() {
		return RelatorioHora;
	}

	public void setRelatorioHora(int relatorioHora) {
		RelatorioHora = relatorioHora;
	}

}
