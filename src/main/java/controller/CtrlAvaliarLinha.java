package controller;

import static controller.ApplicationConfig.CH_USUARIO_ATUAL;
import static controller.ApplicationConfig.CH_PROXIMO_PASSO;
import static controller.ApplicationConfig.CH_UC_EM_EXECUCAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import model.AvaliaLinha;
import model.Linha;
import model.Usuario;
import model.dao.DaoAvaliaLinha;
import model.dao.DaoLinha;
import model.exception.ModelException;

@Path("/avaliarlinha")
public class CtrlAvaliarLinha implements ICtrlAvaliarLinha {

	@Context
	private HttpServletRequest request;

	@Override
	public boolean iniciar() throws ModelException, ControllerException {
		HttpSession sessao = request.getSession();

		// Verifica se usuário está autenticado
		Usuario usuario = (Usuario) sessao.getAttribute(CH_USUARIO_ATUAL);
		if (usuario == null)
			throw new ControllerException("Sessão expirada. Faça login novamente.");

		// Configura case
		sessao.setAttribute(CH_UC_EM_EXECUCAO, "Avaliar Linha de Ônibus");
		sessao.setAttribute(CH_PROXIMO_PASSO, "InformarLinha");

		return true;
	}

	@Override
	public boolean informarLinha(String numero, String cnpj) throws ModelException, ControllerException {
		HttpSession sessao = request.getSession();

		DaoLinha daoLinha = new DaoLinha();
		Linha linha = daoLinha.obterPorNumeroECnpj(numero, cnpj);

		if (linha == null)
			throw new ControllerException("Linha não encontrada para a empresa informada.");

		sessao.setAttribute(CH_LINHA_AVALIADA, linha);
		sessao.setAttribute(CH_PROXIMO_PASSO, "InformarNotas");

		return true;
	}
	
	@Override
	public boolean informarNotas(int ar, int manut, int horarios, int lotacao) throws ModelException, ControllerException {

		HttpSession sessao = request.getSession();

		sessao.setAttribute(CH_NOTA_AR, ar);
		sessao.setAttribute(CH_NOTA_MANUT, manut);
		sessao.setAttribute(CH_NOTA_HORARIO, horarios);
		sessao.setAttribute(CH_NOTA_LOTACAO, lotacao);

		sessao.setAttribute(CH_PROXIMO_PASSO, "EfetivarAvaliacao");

		return true;
	}
	
	@Override
	public AvaliaLinha efetivarAvaliacao() throws ModelException, ControllerException {
		HttpSession sessao = request.getSession();

		Usuario usuario = (Usuario) sessao.getAttribute(CH_USUARIO_ATUAL);
		if (usuario == null)
			throw new ControllerException("Sessão expirada.");

		Linha linha = (Linha) sessao.getAttribute(CH_LINHA_AVALIADA);

		int ar = (int) sessao.getAttribute(CH_NOTA_AR);
		int manut = (int) sessao.getAttribute(CH_NOTA_MANUT);
		int horarios = (int) sessao.getAttribute(CH_NOTA_HORARIO);
		int lotacao = (int) sessao.getAttribute(CH_NOTA_LOTACAO);

		// Criar objeto AvaliaLinha
		AvaliaLinha avaliacao = new AvaliaLinha();
		avaliacao.setLinha(linha);
		avaliacao.setUsuario(usuario);

		avaliacao.setNotaArCondicionado(ar);
		avaliacao.setNotaManutencao(manut);
		avaliacao.setNotaDistribuicaoHorarios(horarios);
		avaliacao.setNotaLotacaoPassageiros(lotacao);

		// Calcular média e definir status
		double media = (ar + manut + horarios + lotacao) / 4.0;
		//avaliacao.processarCalculoDeMedia(media);

		// Persistência
		DaoAvaliaLinha dao = new DaoAvaliaLinha();
		dao.incluir(avaliacao);

		return avaliacao;
	}
	
}

