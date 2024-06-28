package com.sistran.fastclaims.application.regra.pesquisar.lista;

import com.sistran.fastclaims.application.UseCaseTest;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoID;
import com.sistran.fastclaims.domain.operador.OperadorID;
import com.sistran.fastclaims.domain.pagination.Pagination;
import com.sistran.fastclaims.domain.pagination.SearchQuery;
import com.sistran.fastclaims.domain.regra.Regra;
import com.sistran.fastclaims.domain.regra.RegraGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class ListarRegrasUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultListarRegrasUseCase useCase;

    @Mock
    private RegraGateway regraGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(regraGateway);
    }

    @Test
    public void informadaUmaConsultaValida_quandoChamadoOMetodoListarRegras_deveRetornarAsRegras() {

        final var regras = List.of(
                Regra.novaRegra(
                        "Teste1",
                        "Desc1",
                        ColecaoCampoID.from("123"),
                        OperadorID.from(""),
                        null,
                        OperadorID.from("124"),
                        "Texto livre1"
                ),
                Regra.novaRegra(
                        "Teste2",
                        "Desc2",
                        ColecaoCampoID.from("124"),
                        OperadorID.from(""),
                        null,
                        OperadorID.from("124"),
                        "Texto livre2"
                ),
                Regra.novaRegra(
                        "Teste3",
                        "Desc3",
                        ColecaoCampoID.from("125"),
                        OperadorID.from(""),
                        null,
                        OperadorID.from("124"),
                        "Texto livre3"
                )
        );


        final var paginaEsperada = 0;
        final var porPagina = 10;
        final var termo = "";
        final var ordenarPor = "nome";
        final var ordem = "asc";

        final var consulta = new SearchQuery(paginaEsperada, porPagina, termo, ordenarPor, ordem);

        final var paginacaoEsperada = new Pagination<>(paginaEsperada, porPagina, regras.size(), regras);

        final var itensEsperados = 3;
        final var resultadoEsperado = paginacaoEsperada.map(ListarRegrasOutput::aPartirDe);

        when(regraGateway.listar(eq(consulta)))
                .thenReturn(paginacaoEsperada);

        final var resultadoAtual = useCase.executar(consulta);

        Assertions.assertEquals(itensEsperados, resultadoAtual.items().size());
        Assertions.assertEquals(resultadoEsperado, resultadoAtual);
        Assertions.assertEquals(paginaEsperada, resultadoAtual.currentPage());
        Assertions.assertEquals(porPagina, resultadoAtual.perPage());
        Assertions.assertEquals(regras.size(), resultadoAtual.total());
        Assertions.assertEquals(regras.get(0).getId().getValue(), resultadoAtual.items().get(0).id());
        Assertions.assertEquals(regras.get(0).getNome(), resultadoAtual.items().get(0).nome());
        Assertions.assertEquals(regras.get(0).getDescricao(), resultadoAtual.items().get(0).descricao());
        Assertions.assertEquals(regras.get(0).getCampoUm().getValue(), resultadoAtual.items().get(0).campoUm());
        Assertions.assertEquals(regras.get(0).getOperadorDois().getValue(), resultadoAtual.items().get(0).operadorDois());
        Assertions.assertEquals(regras.get(0).getCampoTres(), resultadoAtual.items().get(0).campoTres());
        Assertions.assertEquals(regras.get(1).getId().getValue(), resultadoAtual.items().get(1).id());
        Assertions.assertEquals(regras.get(1).getNome(), resultadoAtual.items().get(1).nome());
        Assertions.assertEquals(regras.get(1).getDescricao(), resultadoAtual.items().get(1).descricao());
        Assertions.assertEquals(regras.get(1).getCampoUm().getValue(), resultadoAtual.items().get(1).campoUm());
        Assertions.assertEquals(regras.get(1).getOperadorDois().getValue(), resultadoAtual.items().get(1).operadorDois());
        Assertions.assertEquals(regras.get(1).getCampoTres(), resultadoAtual.items().get(1).campoTres());
        Assertions.assertEquals(regras.get(2).getId().getValue(), resultadoAtual.items().get(2).id());
        Assertions.assertEquals(regras.get(2).getNome(), resultadoAtual.items().get(2).nome());
        Assertions.assertEquals(regras.get(2).getDescricao(), resultadoAtual.items().get(2).descricao());
        Assertions.assertEquals(regras.get(2).getCampoUm().getValue(), resultadoAtual.items().get(2).campoUm());
        Assertions.assertEquals(regras.get(2).getOperadorDois().getValue(), resultadoAtual.items().get(2).operadorDois());
        Assertions.assertEquals(regras.get(2).getCampoTres(), resultadoAtual.items().get(2).campoTres());
    }

    @Test
    public void informadaUmaConsultaValidaFiltrandoPorNome_quandoChamadoOMetodoListarRegras_deveRetornarAsRegrasConformeNomeInformado() {

        final var regras = List.of(
                Regra.novaRegra(
                        "Geral",
                        "Desc1",
                        ColecaoCampoID.from("123"),
                        OperadorID.from(""),
                        null,
                        OperadorID.from("124"),
                        "Texto livre1"
                ),
                Regra.novaRegra(
                        "Customizada",
                        "Desc2",
                        ColecaoCampoID.from("124"),
                        OperadorID.from(""),
                        null,
                        OperadorID.from("124"),
                        "Texto livre2"
                ),
                Regra.novaRegra(
                        "Completa",
                        "Desc3",
                        ColecaoCampoID.from("125"),
                        OperadorID.from(""),
                        null,
                        OperadorID.from("124"),
                        "Texto livre3"
                )
        );


        final var paginaEsperada = 0;
        final var porPagina = 10;
        final var termo = "Completa";
        final var ordenarPor = "nome";
        final var ordem = "asc";

        final var consulta = new SearchQuery(paginaEsperada, porPagina, termo, ordenarPor, ordem);

        final var paginacaoEsperada = new Pagination<>(paginaEsperada, porPagina, regras.size(), regras);

        final var itensEsperados = 1;
        final var resultadoEsperado = paginacaoEsperada.map(ListarRegrasOutput::aPartirDe);

        when(regraGateway.listar(eq(consulta)))
                .thenReturn(paginacaoEsperada);

        final var resultadoAtual = useCase.executar(consulta);

        Assertions.assertEquals(itensEsperados, 1);
        Assertions.assertEquals(resultadoEsperado, resultadoAtual);
        Assertions.assertEquals(paginaEsperada, resultadoAtual.currentPage());
        Assertions.assertEquals(porPagina, resultadoAtual.perPage());
        Assertions.assertEquals(regras.size(), resultadoAtual.total());
        Assertions.assertEquals(regras.get(0).getId().getValue(), resultadoAtual.items().get(0).id());
        Assertions.assertEquals(regras.get(0).getNome(), resultadoAtual.items().get(0).nome());
        Assertions.assertEquals(regras.get(0).getDescricao(), resultadoAtual.items().get(0).descricao());
        Assertions.assertEquals(regras.get(0).getCampoUm().getValue(), resultadoAtual.items().get(0).campoUm());
        Assertions.assertEquals(regras.get(0).getOperadorDois().getValue(), resultadoAtual.items().get(0).operadorDois());
        Assertions.assertEquals(regras.get(0).getCampoTres(), resultadoAtual.items().get(0).campoTres());
    }

    @Test
    public void informadaUmaConsultaValida_quandoNaoExistemRegrasCadastradas_deveRetornarUmaListaVazia() {

        final var regras = List.<Regra>of();

        final var paginaEsperada = 0;
        final var porPagina = 10;
        final var termo = "";
        final var ordenarPor = "nome";
        final var ordem = "asc";

        final var consulta = new SearchQuery(paginaEsperada, porPagina, termo, ordenarPor, ordem);

        final var paginacaoEsperada = new Pagination<>(paginaEsperada, porPagina, 0, regras);

        final var itensEsperados = 0;
        final var resultadoEsperado = paginacaoEsperada.map(ListarRegrasOutput::aPartirDe);

        when(regraGateway.listar(eq(consulta)))
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

        when(regraGateway.listar(eq(consulta)))
                .thenThrow(new IllegalStateException(mensagemErroEsperada));

        final var excecao = Assertions.assertThrows(IllegalStateException.class, () -> useCase.executar(consulta));

        Assertions.assertEquals(mensagemErroEsperada, excecao.getMessage());
    }
}
