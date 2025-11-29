package controller;

import java.util.List;

import jakarta.ws.rs.Path;
import model.AvaliaLinha;

@Path("\relatoriogeral")
public class CtrlRelatorioGeral {

	List<AvaliaLinha> avaliacoes;
	
	/*
	 * SELECT e.idEmpresa, e.nomeEmpresa, l.idLinha, l.nomeLinha,
	 * al.idAvaliacaoLinha, al.notaArCondicionado, al.notaManutencao,
	 * al.notaDistribuicaoHorarios, al.notaLotacaoPassageiros, al.horarioLinha,
	 * al.avaliacaoTextualLinha, al.idUsuario FROM Empresa e INNER JOIN Linha l ON
	 * l.idEmpresa = e.idEmpresa INNER JOIN AvaliaLinha al ON al.idLinha = l.idLinha
	 * WHERE e.idEmpresa = :idEmpresa;
	 */
	
	
}
