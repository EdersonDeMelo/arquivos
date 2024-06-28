package com.sistran.fastclaims.application.fluxo.pesquisar.lista;

import com.sistran.fastclaims.application.UseCaseTest;
import com.sistran.fastclaims.domain.fluxo.Fluxo;
import com.sistran.fastclaims.domain.fluxo.FluxoGateway;
import com.sistran.fastclaims.domain.natureza.NaturezaID;
import com.sistran.fastclaims.domain.pagination.Pagination;
import com.sistran.fastclaims.domain.pagination.SearchQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class ListarFluxosUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultListarFluxosUseCase useCase;

    @Mock
    private FluxoGateway fluxoGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(fluxoGateway);
    }

    @Test
    public void informadaUmaConsultaValida_quandoChamadoOMetodoPesquisarFluxoPorTermo_deveRetornarOsFluxos() {

        final var fluxos = List.of(
                Fluxo.novoFluxo("Cobertura de cancelamento", NaturezaID.aPartirDe("123")),
                Fluxo.novoFluxo("Cobertura de morte", NaturezaID.aPartirDe("123")),
                Fluxo.novoFluxo("Assistencia médica", NaturezaID.aPartirDe("123"))
        );

        final var paginaEsperada = 0;
        final var porPagina = 10;
        final var termo = "";
        final var ordenarPor = "descricao";
        final var ordem = "asc";

        final var consulta = new SearchQuery(paginaEsperada, porPagina, termo, ordenarPor, ordem);

        final var paginacaoEsperada = new Pagination<>(paginaEsperada, porPagina, fluxos.size(), fluxos);

        final var itensEsperados = 3;
        final var resultadoEsperado = paginacaoEsperada.map(ListarFluxosOutput::aPartirDe);

        when(fluxoGateway.listar(eq(consulta)))
                .thenReturn(paginacaoEsperada);

        final var resultadoAtual = useCase.executar(consulta);

        Assertions.assertEquals(itensEsperados, resultadoAtual.items().size());
        Assertions.assertEquals(resultadoEsperado, resultadoAtual);
        Assertions.assertEquals(paginaEsperada, resultadoAtual.currentPage());
        Assertions.assertEquals(porPagina, resultadoAtual.perPage());
        Assertions.assertEquals(fluxos.size(), resultadoAtual.total());
    }

    @Test
    public void informadaUmaConsultaValida_quandoNaoExistemFluxosCadastrados_deveRetornarUmaListaVazia() {

        final var fluxos = List.<Fluxo>of();

        final var paginaEsperada = 0;
        final var porPagina = 10;
        final var termo = "";
        final var ordenarPor = "descricao";
        final var ordem = "asc";

        final var consulta = new SearchQuery(paginaEsperada, porPagina, termo, ordenarPor, ordem);

        final var paginacaoEsperada = new Pagination<>(paginaEsperada, porPagina, 0, fluxos);

        final var itensEsperados = 0;
        final var resultadoEsperado = paginacaoEsperada.map(ListarFluxosOutput::aPartirDe);

        when(fluxoGateway.listar(eq(consulta)))
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
        final var ordenarPor = "descricao";
        final var ordem = "asc";
        final var mensagemErroEsperada = "Gateway error";

        final var consulta = new SearchQuery(paginaEsperada, porPagina, termo, ordenarPor, ordem);

        when(fluxoGateway.listar(eq(consulta)))
                .thenThrow(new IllegalStateException(mensagemErroEsperada));

        final var excecao = Assertions.assertThrows(IllegalStateException.class, () -> useCase.executar(consulta));

        Assertions.assertEquals(mensagemErroEsperada, excecao.getMessage());
    }
}

