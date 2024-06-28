package com.sistran.fastclaims.application.trilha.desativar;

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
import static org.mockito.Mockito.*;

public class DesativarTrilhaUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultDesativarTrilhaUseCase useCase;

    @Mock
    private TrilhaGateway trilhaGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(trilhaGateway);
    }

    @Test
    public void informadoUmaTrilhaAtiva_quandoChamadoOMetodoDesativar_deveDesativarATrilha() {

        final var trilha = Trilha.novaTrilha("Trilha Geral", "Descriçao da Trilha", true, FluxoID.unique());

        when(trilhaGateway.pesquisarPorId(trilha.getId()))
                .thenReturn(Optional.of(Trilha.com(trilha)));

        useCase.executar(trilha.getId().getValue());

        verify(trilhaGateway, times(1)).desativar(any(Trilha.class));

        Trilha trilhaResult = trilhaGateway.pesquisarPorId(trilha.getId()).get();
        Assertions.assertFalse(trilhaResult.isAtivo());
    }

    @Test
    public void informadoUmaTrilhaInexistente_quandoChamadoOMetodoDesativar_deveLancarUmaDomainException() {

        final var mensagemDeErroEsperada = "Trilha com o ID 123 nao foi encontrado (a)";
        final var idEsperado = TrilhaID.from("123");

        final var excecao = Assertions.assertThrows(NotFoundException.class, () -> useCase.executar(idEsperado.getValue()));

        Assertions.assertEquals(mensagemDeErroEsperada, excecao.getMessage());
        verify(trilhaGateway, never()).ativar(any(Trilha.class));
    }
}

