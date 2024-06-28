package com.sistran.fastclaims.application.trilha.pesquisar.lista;

import com.sistran.fastclaims.application.UseCaseTest;
import com.sistran.fastclaims.domain.fluxo.FluxoID;
import com.sistran.fastclaims.domain.pagination.Pagination;
import com.sistran.fastclaims.domain.pagination.SearchQuery;
import com.sistran.fastclaims.domain.trilha.Trilha;
import com.sistran.fastclaims.domain.trilha.TrilhaGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class ListarTrilhasUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultListarTrilhasUseCase useCase;

    @Mock
    private TrilhaGateway trilhaGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(trilhaGateway);
    }

    @Test
    public void informadaUmaConsultaValida_quandoChamadoOMetodoPesquisarTrilhaPorNome_deveRetornarAsTrilhas() {

        final var fluxos = List.of(
                Trilha.novaTrilha("Cobertura de cancelamento", "desc", FluxoID.unique()),
                Trilha.novaTrilha("Cobertura de morte", "desc", FluxoID.unique()),
                Trilha.novaTrilha("Assistencia médica", "desc", FluxoID.unique())
        );

        final var paginaEsperada = 0;
        final var porPagina = 10;
        final var termo = "";
        final var ordenarPor = "nome";
        final var ordem = "asc";

        final var consulta = new SearchQuery(paginaEsperada, porPagina, termo, ordenarPor, ordem);

        final var paginacaoEsperada = new Pagination<>(paginaEsperada, porPagina, fluxos.size(), fluxos);

        final var itensEsperados = 3;
        final var resultadoEsperado = paginacaoEsperada.map(ListarTrilhasOutput::aPartirDe);

        when(trilhaGateway.listar(eq(consulta)))
                .thenReturn(paginacaoEsperada);

        final var resultadoAtual = useCase.executar(consulta);

        Assertions.assertEquals(itensEsperados, resultadoAtual.items().size());
        Assertions.assertEquals(resultadoEsperado, resultadoAtual);
        Assertions.assertEquals(paginaEsperada, resultadoAtual.currentPage());
        Assertions.assertEquals(porPagina, resultadoAtual.perPage());
        Assertions.assertEquals(fluxos.size(), resultadoAtual.total());
    }

    @Test
    public void informadaUmaConsultaValida_quandoNaoExistemTrilhasCadastradas_deveRetornarUmaListaVazia() {

        final var fluxos = List.<Trilha>of();

        final var paginaEsperada = 0;
        final var porPagina = 10;
        final var termo = "";
        final var ordenarPor = "nome";
        final var ordem = "asc";

        final var consulta = new SearchQuery(paginaEsperada, porPagina, termo, ordenarPor, ordem);

        final var paginacaoEsperada = new Pagination<>(paginaEsperada, porPagina, 0, fluxos);

        final var itensEsperados = 0;
        final var resultadoEsperado = paginacaoEsperada.map(ListarTrilhasOutput::aPartirDe);

        when(trilhaGateway.listar(eq(consulta)))
                .thenReturn(paginacaoEsperada);

        final var resultadoAtual = useCase.executar(consulta);

        Assertions.assertEquals(itensEsperados, resultadoAtual.items().size());
        Assertions.assertEquals(resultadoEsperado, resultadoAtual);
        Assertions.assertEquals(paginaEsperada, resultadoAtual.currentPage());
        Assertions.assertEquals(porPagina, resultadoAtual.perPage());
        Assertions.assertEquals(0, resultadoAtual.total());
    }

    @Test
    public void informadaUmaConsultaValida_quandoOGatewayLancaUmaExcecao_deveRetornarUmaExcecao() {
        final var paginaEsperada = 0;
        final var porPagina = 10;
        final var termo = "";
        final var ordenarPor = "nome";
        final var ordem = "asc";
        final var mensagemErroEsperada = "Gateway error";

        final var consulta = new SearchQuery(paginaEsperada, porPagina, termo, ordenarPor, ordem);

        when(trilhaGateway.listar(eq(consulta)))
                .thenThrow(new IllegalStateException(mensagemErroEsperada));

        final var excecao = Assertions.assertThrows(IllegalStateException.class, () -> useCase.executar(consulta));

        Assertions.assertEquals(mensagemErroEsperada, excecao.getMessage());
    }
}
