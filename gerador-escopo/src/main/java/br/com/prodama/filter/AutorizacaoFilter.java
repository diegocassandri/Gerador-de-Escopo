package br.com.prodama.filter;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.prodama.controller.UsuarioLogin;
import br.com.prodama.model.cadastro.geral.Grupo;
import br.com.prodama.model.cadastro.geral.Tela;

@WebFilter("*.xhtml")
public class AutorizacaoFilter implements Filter {

	@Inject
	private UsuarioLogin autenticacao;

	private List<Grupo> gruposDoUsuario;

	private String telaAtual = null;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;

		if (!autenticacao.isLogado() && !request.getRequestURI().endsWith("/Login.xhtml")
				&& !request.getRequestURI().contains("/javax.faces.resource/")) {
			response.sendRedirect(request.getContextPath() + "/Login.xhtml");
		} else {
			/* Verifica permissão de tela */

			if (!request.getRequestURI().contains("/gerador-escopo/javax.faces.resource/")) {
				gruposDoUsuario = autenticacao.getUsuarioLogin().getGrupos();
				telaAtual = request.getRequestURI().replace("/gerador-escopo", "");
				int achou = 0;
				if ((telaAtual.equals("/Login.xhtml"))
						|| (telaAtual.equals("/Home.xhtml") || (telaAtual.equals("/PermissaoNegada.xhtml")
								|| (telaAtual.equals("/cadastros/SelecaoEmpresa.xhtml")
										|| (telaAtual.equals("/cadastros/SelecaoFilial.xhtml")
												|| (telaAtual.equals("/AlteracaoSenha.xhtml"))))))) {
					chain.doFilter(req, res);
				} else {

					for (Grupo grupo : gruposDoUsuario) {
						for (Tela tela : grupo.getTelas()) {
							/* System.out.println(tela.getUrl()); */
							if (telaAtual.equals(tela.getUrl())) {
								achou = 1;
								chain.doFilter(req, res);
							}
						}
					}
					if (achou == 0) {
						response.sendRedirect(request.getContextPath() + "/PermissaoNegada.xhtml");
					}

				}

			} else {
				chain.doFilter(req, res);
			}

		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
