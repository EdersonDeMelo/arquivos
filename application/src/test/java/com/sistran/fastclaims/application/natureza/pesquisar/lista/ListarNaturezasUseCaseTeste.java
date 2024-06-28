package com.sistran.fastclaims.application.natureza.pesquisar.lista;

import com.sistran.fastclaims.application.UseCaseTest;
import com.sistran.fastclaims.domain.natureza.Natureza;
import com.sistran.fastclaims.domain.natureza.NaturezaGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class ListarNaturezasUseCaseTeste extends UseCaseTest {
    @InjectMocks
    private DefaultListarNaturezasUseCase useCase;

    @Mock
    private NaturezaGateway naturezaGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(naturezaGateway);
    }

    @Test
    public void informandoUmaConsultaValida_quandoChamadoOMetodoPesquisarNaturezaPorTermoSemParametros_deveRetornarTodasAsNaturezas() {
        final var itensEsperados = 2;

        final var naturezas = List.of(
                Natureza.novaNatureza("ASSISTÊNCIA", "5DDCEE83-486A-4C27-A371-719052358DF3"),
                Natureza.novaNatureza("AUXÍLIO FUNERAL - MORTE ACIDENTAL", "7635D119-9386-42F4-AC68-4D1FEE10E213")
        );

        Mockito.when(naturezaGateway.listar(eq(""))).thenReturn(naturezas);

        final var naturezaEsperada = useCase.executar("");
        Assertions.assertEquals(naturezaEsperada.size(), itensEsperados);
        Assertions.assertEquals(naturezaEsperada.get(0).nome(), naturezas.get(0).getNome());
        Assertions.assertEquals(naturezaEsperada.get(0).codigoNatureza(), naturezas.get(0).getCodigoNatureza());
        Assertions.assertEquals(naturezaEsperada.get(1).nome(), naturezas.get(1).getNome());
        Assertions.assertEquals(naturezaEsperada.get(1).codigoNatureza(), naturezas.get(1).getCodigoNatureza());
    }

    @Test
    public void informandoUmaConsultaValida_quandoChamadoOMetodoPesquisarNaturezaPorTermoComNome_deveRetornarANatureza() {
        final var termo = "assistencia";

        final var naturezas = List.of(
                Natureza.novaNatureza("ASSISTÊNCIA", "5DDCEE83-486A-4C27-A371-719052358DF3"));

        Mockito.when(naturezaGateway.listar(eq(termo))).thenReturn(naturezas);

        final var naturezaEsperada = useCase.executar(termo);

        Assertions.assertEquals(naturezaEsperada.size(), 1);
        Assertions.assertEquals(naturezaEsperada.get(0).nome(), naturezas.get(0).getNome());
        Assertions.assertEquals(naturezaEsperada.get(0).codigoNatureza(), naturezas.get(0).getCodigoNatureza());

    }

    @Test
    public void informandoUmaConsultaValida_quandoChamadoOMetodoPesquisarNaturezaPorTermoComCodigoNatureza_deveRetornarANatureza() {
        final var termo = "5DDCEE83-486A-4C27-A371-719052358DF3";

        final var naturezas = List.of(
                Natureza.novaNatureza("ASSISTÊNCIA", "5DDCEE83-486A-4C27-A371-719052358DF3"));
        when(naturezaGateway.listar(eq(termo))).thenReturn(naturezas);

        final var naturezaEsperada = useCase.executar(termo);

        Assertions.assertEquals(naturezaEsperada.size(), 1);
        Assertions.assertEquals(naturezaEsperada.get(0).nome(), naturezas.get(0).getNome());
        Assertions.assertEquals(naturezaEsperada.get(0).codigoNatureza(), naturezas.get(0).getCodigoNatureza());

    }

    @Test
    public void informandoUmaConsultaValida_quandoChamadoOMetodoPesquisarNaturezaPorTermoComNomeInvalido_deveRetornarListaVazia() {
        final var termo = "algumaNatureza";
        List<Natureza> naturezas = new ArrayList<>();

        when(naturezaGateway.listar(eq(termo))).thenReturn(naturezas);

        final var naturezaEsperada = useCase.executar(termo);

        Assertions.assertEquals(naturezaEsperada.size(), 0);
        ;

    }

    @Test
    public void informandoUmaConsultaValida_quandoChamadoOMetodoPesquisarNaturezaPorTermoComCodigoNaturezaInvalido_deveRetornarListaVazia() {
        final var termo = "123";
        List<Natureza> naturezas = new ArrayList<>();

        when(naturezaGateway.listar(eq(termo))).thenReturn(naturezas);

        final var naturezaEsperada = useCase.executar(termo);

        Assertions.assertEquals(naturezaEsperada.size(), 0);
        ;
    }

    @Test
    public void informandoUmaConsultaInvalida_quandoOcorreErroNoGateway_deveRetornarUmaExcecao() {
        final var mensagemErroEsperada = "Gateway error";

        when(naturezaGateway.listar(eq("")))
                .thenThrow(new IllegalStateException(mensagemErroEsperada));
        final var excecao = Assertions.assertThrows(IllegalStateException.class, () -> useCase.executar(""));

        Assertions.assertEquals(mensagemErroEsperada, excecao.getMessage());
    }


}
