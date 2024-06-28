package com.sistran.fastclaims.application.natureza.pesquisar.id;

import com.sistran.fastclaims.application.UseCaseTest;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.natureza.Natureza;
import com.sistran.fastclaims.domain.natureza.NaturezaGateway;
import com.sistran.fastclaims.domain.natureza.NaturezaID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class PesquisarNaturezaPorIdUseCaseTeste extends UseCaseTest {

    @InjectMocks
    private DefaultPesquisarNaturezaPorIdUseCase useCase;

    @Mock
    private NaturezaGateway naturezaGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(naturezaGateway);
    }

    @Test
    public void informandoComandoValido_quandoChamadoOMetodoPesquisarNaturezaPorId_deveRetornarUmaNatureza() {
        final var nomeEsperado = "AlgumaNatureza";
        final var codigoNaturezaEsperado = "123";
        final var natureza = Natureza.novaNatureza(nomeEsperado, codigoNaturezaEsperado);
        final var idEsperado = natureza.getId();

        when(naturezaGateway.pesquisarPorId(any())).thenReturn(Optional.of(natureza.clone()));

        final var naturezaAtual = useCase.executar(idEsperado.getValue());

        Assertions.assertEquals(idEsperado, naturezaAtual.id());
        Assertions.assertEquals(nomeEsperado, naturezaAtual.nome());
        Assertions.assertEquals(codigoNaturezaEsperado, naturezaAtual.codigoNatureza());

    }

    @Test
    public void informandoComandoInvalido_quandoChamadoOMetodoPesquisarNaturezaPorId_deveRetornarUmNotFound() {
        final var mensagemDeErroEsperada = "Natureza com o ID 123 nao foi encontrado (a)";
        final var idEsperado = NaturezaID.aPartirDe("123");

        when(naturezaGateway.pesquisarPorId(eq(idEsperado)))
                .thenReturn(Optional.empty());

        final var excecao = Assertions.assertThrows(
                NotFoundException.class,
                () -> useCase.executar(idEsperado.getValue())
        );

        Assertions.assertEquals(mensagemDeErroEsperada, excecao.getMessage());

    }

    @Test
    public void informandoComandoInvalido_quandoChamadoOMetodoPesquisarNaturezaPorId_deveRetornarUmaException() {

        final var mensagemDeErroEsperada = "Gateway error";
        final var idEsperado = NaturezaID.aPartirDe("123");

        when(naturezaGateway.pesquisarPorId(eq(idEsperado)))
                .thenThrow(new IllegalStateException(mensagemDeErroEsperada));

        final var excecao = Assertions.assertThrows(
                IllegalStateException.class,
                () -> useCase.executar(idEsperado.getValue())
        );

        Assertions.assertEquals(mensagemDeErroEsperada, excecao.getMessage());
    }
}
