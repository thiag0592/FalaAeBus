package controller;

import java.util.List;

import dto.DtoRelatorio;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import model.AvaliaLinha;
import model.dao.DaoAvaliaLinha;

@Path("/{tipo:(adm|emp)}/relatoriolinha")
public class CtrlRelatorioLinha implements ICtrlRelatorioLinha{

	List<AvaliaLinha> avaliacoes;
	
	@Context
	private HttpServletRequest request;

	@Context
	private HttpServletResponse response;
	
	public CtrlRelatorioLinha() {
	}
	
	@Override
	public DtoRelatorio GeraRelatorio(String cnpj, String numeroLinha) {
		DaoAvaliaLinha daoAvalia = new DaoAvaliaLinha();
		avaliacoes = daoAvalia.obterTodasDeUmaLinha(numeroLinha,cnpj);
		int contador = 0;
		int nAr=0;
		int nManut=0;
		int nDistHor=0;
		int nLotaPass=0;

		
		for (AvaliaLinha al : avaliacoes) {
			nAr+=al.getNotaArCondicionado();
			nManut+=al.getNotaManutencao();
			nDistHor+=al.getNotaDistribuicaoHorarios();
			nLotaPass+=al.getNotaLotacaoPassageiros();
			contador+=1;
		}
		nAr=nAr/contador;
		nManut=nManut/contador;
		nDistHor=nDistHor/contador;
		nLotaPass=nLotaPass/contador;
		
		int mediaAv=(nAr+nManut+nDistHor+nLotaPass)/4;
		
		DtoRelatorio relatorio = new DtoRelatorio(nAr,nManut,nLotaPass,nDistHor);
		
		relatorio.processarCalculoDeMedia(mediaAv);
		
		return relatorio;
	}
	
}
