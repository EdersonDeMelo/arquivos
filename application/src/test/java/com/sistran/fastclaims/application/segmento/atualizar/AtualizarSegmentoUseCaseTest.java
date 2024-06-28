package com.sistran.fastclaims.application.segmento.atualizar;

import com.sistran.fastclaims.application.UseCaseTest;
import com.sistran.fastclaims.domain.segmento.Segmento;
import com.sistran.fastclaims.domain.segmento.SegmentoGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AtualizarSegmentoUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultAtualizarSegmentoUseCase useCase;

    @Mock
    private SegmentoGateway segmentoGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(segmentoGateway);
    }

    @Test
    public void informadoUmComandoValido_quandoChamadoOMetodoAtualizarSegmento_deveRetornarUmSegmento() {

        final var segmento = Segmento.novoSegmento("Segmento Teste");

        when(segmentoGateway.pesquisarPorId(any())).thenReturn(Optional.of(segmento));
        when(segmentoGateway.atualizar(any())).then(returnsFirstArg());

        final var comando = AtualizarSegmentoCommand.aPartirDe(segmento.getId().toString(), "Novo Segmento");

        final var result = useCase.executar(comando);

        Assertions.assertTrue(result.isRight());
        Assertions.assertEquals("Novo Segmento", result.get().nome());
    }

    @Test
    public void informadoUmComandoComCampoNulo_quandoChamadoOMetodoAtualizarSegmento_deveRetornarUmaMensagemDeErro() {

        final var segmento = Segmento.novoSegmento("Segmento Teste");

        when(segmentoGateway.pesquisarPorId(any())).thenReturn(Optional.of(segmento));

        final var comando = AtualizarSegmentoCommand.aPartirDe(segmento.getId().toString(), null);

        final var result = useCase.executar(comando);

        Assertions.assertTrue(result.isLeft());
        Assertions.assertTrue(result.getLeft().hasErrors());
    }
}
