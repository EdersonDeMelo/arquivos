package com.sistran.fastclaims.application.trilha.excluir;

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
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ExcluirTrilhaUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultExcluirTrilhaUseCase useCase;

    @Mock
    private TrilhaGateway trilhaGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(trilhaGateway);
    }

    @Test
    public void informadoUmIdDeTrilhaValido_quandoChamadoOMetodoExcluir_deveExcluirATrilha() {

        final var trilha = Trilha.novaTrilha("Trilha Geral", "Descriçao da Trilha", FluxoID.unique());

        final var idEsperado = trilha.getId();

        when(trilhaGateway.pesquisarPorId(trilha.getId()))
                .thenReturn(Optional.of(Trilha.com(trilha)));

        doNothing().when(trilhaGateway).excluir(any());

        Assertions.assertDoesNotThrow(() -> useCase.executar(idEsperado.getValue()));

        Mockito.verify(trilhaGateway, times(1)).excluir(idEsperado);
    }

    @Test
    public void informadoUmIdDeTrilhaInvalido_quandoChamadoOMetodoExcluir_deveRetornarUmaException() {

        final var idEsperado = TrilhaID.from("123");
        final var mensagemErroEsperada = "Trilha com o ID 123 nao foi encontrado (a)";

        final var excecao = Assertions.assertThrows(NotFoundException.class, () -> useCase.executar(idEsperado.getValue()));

        Assertions.assertEquals(mensagemErroEsperada, excecao.getMessage());
        verify(trilhaGateway, never()).excluir(idEsperado);
    }
}


