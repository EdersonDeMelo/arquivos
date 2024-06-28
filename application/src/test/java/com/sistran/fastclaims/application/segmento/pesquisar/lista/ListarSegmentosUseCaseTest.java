package com.sistran.fastclaims.application.segmento.pesquisar.lista;

import com.sistran.fastclaims.application.UseCaseTest;
import com.sistran.fastclaims.domain.segmento.Segmento;
import com.sistran.fastclaims.domain.segmento.SegmentoGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.when;

public class ListarSegmentosUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultListarSegmentosUseCase useCase;

    @Mock
    private SegmentoGateway segmentoGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(segmentoGateway);
    }

    @Test
    public void informadaUmaConsultaValida_quandoChamadoOMetodoPesquisarSegmento_deveRetornarOsSegmentos() {
        final var itensEsperados = 2;

        final var segmentos = List.of(
                Segmento.novoSegmento("Segmento Teste 1"),
                Segmento.novoSegmento("Segmento Teste 2")
        );

        Mockito.when(segmentoGateway.listar()).thenReturn(segmentos);

        final var segmentosEsperados = useCase.execute();
        Assertions.assertEquals(segmentosEsperados.size(), itensEsperados);
        Assertions.assertEquals(segmentosEsperados.get(0).nome(), segmentos.get(0).getNome());
        Assertions.assertEquals(segmentosEsperados.get(1).nome(), segmentos.get(1).getNome());
    }

    @Test
    public void informadaUmaConsultaValidaOndeNaoExistemSegmentos_quandoChamadoOMetodoPesquisarSegmento_deveRetornarUmaListaVazia() {
        final var itensEsperados = 0;

        final var segmentos = List.<Segmento>of();

        Mockito.when(segmentoGateway.listar()).thenReturn(segmentos);

        final var segmentosEsperados = useCase.execute();
        Assertions.assertEquals(segmentosEsperados.size(), itensEsperados);
    }

    @Test
    public void informandoUmaConsultaInvalida_quandoOcorreErroNoGateway_deveRetornarUmaExcecao() {
        final var mensagemErroEsperada = "Gateway error";

        when(segmentoGateway.listar())
                .thenThrow(new IllegalStateException(mensagemErroEsperada));
        final var excecao = Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute());

        Assertions.assertEquals(mensagemErroEsperada, excecao.getMessage());
    }
}
