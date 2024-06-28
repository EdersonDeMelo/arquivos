package com.sistran.fastclaims.application.colecaocampo.pesquisar.lista;

import com.sistran.fastclaims.application.colecaocampo.pesquisar.listar.DefaultListarColecaoCampoUseCase;
import com.sistran.fastclaims.application.colecaocampo.pesquisar.listar.ListarColecaoCampoOutput;
import com.sistran.fastclaims.domain.colecao.ColecaoID;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampo;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoGateway;
import com.sistran.fastclaims.domain.colecaocampo.TipoDado;
import com.sistran.fastclaims.domain.colecaocampo.TipoFormato;
import com.sistran.fastclaims.domain.pagination.Pagination;
import com.sistran.fastclaims.domain.pagination.SearchQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListarColecaoCampoUseCaseTest {

    @InjectMocks
    private DefaultListarColecaoCampoUseCase useCase;

    @Mock
    private ColecaoCampoGateway colecaoCampoGateway;

    @Test
    public void listarColecaoCampoTest() {
        final var campos = List.of(
                ColecaoCampo.novaColecaoCampo("Campo", "Alias", TipoDado.NUMERICO,
                        TipoFormato.ASTERISCO, ColecaoID.from("colecaoID"), null, "nomeColecao"),

                ColecaoCampo.novaColecaoCampo("Campo", "Alias", TipoDado.NUMERICO,
                        TipoFormato.ASTERISCO, ColecaoID.from("colecaoID"), null, "nomeColecao")
        );

        final var paginaEsperada = 0;
        final var porPagina = 10;
        final var termo = "";
        final var ordenarPor = "descricao";
        final var ordem = "asc";

        final var consulta = new SearchQuery(paginaEsperada, porPagina, termo, ordenarPor, ordem);

        final var itensEsperados = 2;

        final var paginacaoEsperada = new Pagination<>(paginaEsperada, porPagina, campos.size(), campos);

        final var resultadoEsperado = paginacaoEsperada.map(ListarColecaoCampoOutput::aPartirDe);

        when(colecaoCampoGateway.listar(eq(consulta)))
                .thenReturn(paginacaoEsperada);

        final var resultadoAtual = useCase.executar(consulta);

        Assertions.assertEquals(itensEsperados, resultadoAtual.items().size());
        Assertions.assertEquals(resultadoEsperado, resultadoAtual);
        Assertions.assertEquals(paginaEsperada, resultadoAtual.currentPage());
        Assertions.assertEquals(porPagina, resultadoAtual.perPage());
        Assertions.assertEquals(campos.size(), resultadoAtual.total());

    }


}
