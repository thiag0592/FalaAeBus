package controller;

import java.util.List;

import dto.DtoRelatorio;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import model.AvaliaLinha;
import model.UsuarioEmpresa;
import model.dao.DaoAvaliaLinha;

import static controller.ApplicationConfig.CH_EMP_ATUAL;

@Path("/{tipo:(adm|repemp)}/relatoriolinha")
public class CtrlRelatorioLinha implements ICtrlRelatorioLinha{

	List<AvaliaLinha> avaliacoes;
	
	@Context
	private HttpServletRequest request;

	@Context
	private HttpServletResponse response;
	
	public CtrlRelatorioLinha() {
	}
	
	@Override
	public DtoRelatorio GeraRelatorio(String tipo, String cnpj, String numeroLinha) throws ControllerException {
		
		if(tipo.contentEquals("repemp")){
			HttpSession session = request.getSession();
			UsuarioEmpresa emp = (UsuarioEmpresa) session.getAttribute(CH_EMP_ATUAL);
			if(emp==null)
				throw new ControllerException("Acesso negado: empresa inválida");
			if(!cnpj.equals(emp.getCnpj()))
				throw new ControllerException("Acesso negado: você só pode ver o relatório da sua empresa.");
		}
		
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
