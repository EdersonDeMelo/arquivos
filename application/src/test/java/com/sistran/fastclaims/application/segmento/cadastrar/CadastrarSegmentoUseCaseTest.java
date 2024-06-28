package com.sistran.fastclaims.application.segmento.cadastrar;

import com.sistran.fastclaims.application.UseCaseTest;
import com.sistran.fastclaims.domain.segmento.SegmentoGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class CadastrarSegmentoUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCadastrarSegmentoUseCase useCase;

    @Mock
    private SegmentoGateway segmentoGateway;


    @Override
    protected List<Object> getMocks() {
        return List.of(segmentoGateway);
    }

    @Test
    public void informadoUmComandoValido_quandoChamadoOMetodoCadastrarSegmento_deveRetornarUmSegmento() {

        final var nome = "Segmento teste";

        final var segmento = CadastrarSegmentoCommand.com(nome);

        when(segmentoGateway.cadastrar(any()))
                .thenAnswer(returnsFirstArg());

        final var segmentoCadastrado = useCase.executar(segmento);

        Assertions.assertNotNull(segmentoCadastrado);
        Assertions.assertNotNull(segmentoCadastrado.get().id());
        Assertions.assertEquals(nome, segmentoCadastrado.get().nome());
        Assertions.assertNotNull(segmentoCadastrado.get().dataCadastro());
    }

    @Test
    public void informadoUmNomeNulo_quandoChamadoOMetodoCadastrarSegmento_deveRetornarUmaException() {

        final var descricaoErroEsperada = "'nome' não pode ser nulo!";
        final var errosEsperados = 1;

        final var segmento = CadastrarSegmentoCommand.com(null);

        final var notificacao = useCase.executar(segmento).getLeft();

        Assertions.assertEquals(errosEsperados, notificacao.getErrors().size());
        Assertions.assertEquals(descricaoErroEsperada, notificacao.firstError().message());

        Mockito.verify(segmentoGateway, times(0)).cadastrar(any());
    }
}
