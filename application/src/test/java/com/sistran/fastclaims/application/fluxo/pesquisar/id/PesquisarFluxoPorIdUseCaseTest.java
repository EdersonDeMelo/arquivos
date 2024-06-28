package com.sistran.fastclaims.application.fluxo.pesquisar.id;

import com.sistran.fastclaims.application.UseCaseTest;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.fluxo.Fluxo;
import com.sistran.fastclaims.domain.fluxo.FluxoGateway;
import com.sistran.fastclaims.domain.fluxo.FluxoID;
import com.sistran.fastclaims.domain.natureza.NaturezaID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class PesquisarFluxoPorIdUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultPesquisarFluxoPorIdUseCase useCase;

    @Mock
    private FluxoGateway fluxoGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(fluxoGateway);
    }

    @Test
    public void informadoUmComandoValido_quandoChamadoOMetodoPesquisarFluxoPorId_deveRetornarUmFluxo() {
        final var descricaoEsperada = "Cobertura de cancelamento";

        final var fluxo = Fluxo.novoFluxo(descricaoEsperada, NaturezaID.aPartirDe("123"));

        final var idEsperado = fluxo.getId();

        when(fluxoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(fluxo.clone()));

        final var fluxoAtual = useCase.executar(idEsperado.getValue());

        Assertions.assertEquals(idEsperado, fluxoAtual.id());
        Assertions.assertEquals(descricaoEsperada, fluxoAtual.descricao());
    }

    @Test
    public void informadoUmComandoInvalido_quandoChamadoOMetodoPesquisarFluxoPorId_deveRetornarUmNotFound() {

        final var mensagemDeErroEsperada = "Fluxo com o ID 123 nao foi encontrado (a)";
        final var idEsperado = FluxoID.from("123");

        when(fluxoGateway.pesquisarPorId(eq(idEsperado)))
                .thenReturn(Optional.empty());

        final var excecao = Assertions.assertThrows(
                NotFoundException.class,
                () -> useCase.executar(idEsperado.getValue())
        );

        Assertions.assertEquals(mensagemDeErroEsperada, excecao.getMessage());
    }

    @Test
    public void informadoUmComandoValido_quandoChamadoOMetodoPesquisarFluxoPorId_deveRetornarUmaException() {

        final var mensagemDeErroEsperada = "Gateway error";
        final var idEsperado = FluxoID.from("123");

        when(fluxoGateway.pesquisarPorId(eq(idEsperado)))
                .thenThrow(new IllegalStateException(mensagemDeErroEsperada));

        final var excecao = Assertions.assertThrows(
                IllegalStateException.class,
                () -> useCase.executar(idEsperado.getValue())
        );

        Assertions.assertEquals(mensagemDeErroEsperada, excecao.getMessage());
    }
}
