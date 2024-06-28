package com.sistran.fastclaims.application.segmento.pesquisar.id;

import com.sistran.fastclaims.application.UseCaseTest;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.segmento.Segmento;
import com.sistran.fastclaims.domain.segmento.SegmentoGateway;
import com.sistran.fastclaims.domain.segmento.SegmentoID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PesquisarSegmentoPorIdUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultPesquisarSegmentoPorIdUseCase useCase;

    @Mock
    private SegmentoGateway segmentoGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(segmentoGateway);
    }

    @Test
    public void informadoUmSegmentoIdValido_quandoChamadoOMetodoPesquisarSegmentoPorId_deveRetornarUmSegmento() {

        final var nomeEsperado = "Segmento Teste";

        final var segmento = Segmento.novoSegmento(nomeEsperado);

        when(segmentoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(segmento.clone()));

        final var segmentoAtual = useCase.executar(segmento.getId().getValue());

        Assertions.assertEquals(nomeEsperado, segmentoAtual.nome());
        Assertions.assertNotNull(segmentoAtual.id());
        Assertions.assertNotNull(segmento.getDataCadastro());
        Assertions.assertNotNull(segmento.getDataAlteracao());
    }

    @Test
    public void informadoUmSegmentoIdInexistente_quandoChamadoOMetodoPesquisarSegmentoPorId_deveRetornarUmNotFound() {

        final var mensagemDeErroEsperada = "Segmento com o ID 123 nao foi encontrado (a)";
        final var idEsperado = SegmentoID.aPartirDe("123");

        when(segmentoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.empty());

        final var excecao = Assertions.assertThrows(
                NotFoundException.class,
                () -> useCase.executar(idEsperado.getValue())
        );

        Assertions.assertEquals(mensagemDeErroEsperada, excecao.getMessage());
    }
}
