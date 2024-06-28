package com.sistran.fastclaims.application.trilha.pesquisar.id;

import com.sistran.fastclaims.application.UseCaseTest;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.fluxo.FluxoID;
import com.sistran.fastclaims.domain.trilha.Trilha;
import com.sistran.fastclaims.domain.trilha.TrilhaGateway;
import com.sistran.fastclaims.domain.trilha.TrilhaID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class PesquisarTrilhaPorIdUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultPesquisarTrilhaPorIdUseCase useCase;

    @Mock
    private TrilhaGateway trilhaGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(trilhaGateway);
    }

    @Test
    public void informadoUmComandoValido_quandoChamadoOMetodoPesquisarTrilhaPorId_deveRetornarUmaTrilha() {
        final var nomeEsperado = "Trilha Geral";
        final var descricaoEsperada = "Descricao";
        final var fluxoId = FluxoID.unique();

        final var trilha = Trilha.novaTrilha(nomeEsperado, descricaoEsperada, fluxoId);

        final var idEsperado = trilha.getId();

        when(trilhaGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(trilha.clone()));

        final var trilhaAtual = useCase.executar(idEsperado.getValue());

        Assertions.assertEquals(idEsperado, trilhaAtual.id());
        Assertions.assertEquals(descricaoEsperada, trilhaAtual.descricao());
        Assertions.assertEquals(nomeEsperado, trilhaAtual.nome());
        Assertions.assertEquals(fluxoId.getValue(), trilhaAtual.fluxoId());
        Assertions.assertNotNull(trilha.getDataAlteracao());
        Assertions.assertNotNull(trilha.getDataCadastro());
    }

    @Test
    public void informadoUmComandoInvalido_quandoChamadoOMetodoPesquisarTrilhaPorId_deveRetornarUmNotFound() {

        final var mensagemDeErroEsperada = "Trilha com o ID 123 nao foi encontrado (a)";
        final var idEsperado = FluxoID.from("123");

        when(trilhaGateway.pesquisarPorId(any()))
                .thenReturn(Optional.empty());

        final var excecao = Assertions.assertThrows(
                NotFoundException.class,
                () -> useCase.executar(idEsperado.getValue())
        );

        Assertions.assertEquals(mensagemDeErroEsperada, excecao.getMessage());
    }

    @Test
    public void informadoUmComandoValido_quandoChamadoOMetodoPesquisarTrilhaPorId_deveRetornarUmaException() {

        final var mensagemDeErroEsperada = "Gateway error";
        final var idEsperado = TrilhaID.from("123");

        when(trilhaGateway.pesquisarPorId(eq(idEsperado)))
                .thenThrow(new IllegalStateException(mensagemDeErroEsperada));

        final var excecao = Assertions.assertThrows(
                IllegalStateException.class,
                () -> useCase.executar(idEsperado.getValue())
        );

        Assertions.assertEquals(mensagemDeErroEsperada, excecao.getMessage());
    }
}
