package com.sistran.fastclaims.application.trilha.desativar.regra_trilha;

import com.sistran.fastclaims.application.UseCaseTest;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoID;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.operador.OperadorID;
import com.sistran.fastclaims.domain.regra.Regra;
import com.sistran.fastclaims.domain.regra.RegraGateway;
import com.sistran.fastclaims.domain.regra.TipoAcao;
import com.sistran.fastclaims.domain.trilha.RegraTrilha;
import com.sistran.fastclaims.domain.trilha.Trilha;
import com.sistran.fastclaims.domain.trilha.TrilhaGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DesativarRegraTrilhaUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultDesativarRegraTrilhaUseCase useCase;

    @Mock
    private TrilhaGateway trilhaGateway;

    @Mock
    private RegraGateway regraGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(trilhaGateway, regraGateway);
    }

    @Test
    public void informadaUmaRegraDaTrilhaDesativada_quandoChamadoOMetodoDesativar_deveAtivarARegra() {

        final var regra = Regra.novaRegra(
                "Regra Teste",
                "Teste",
                ColecaoCampoID.from("123"),
                OperadorID.from(""),
                null,
                OperadorID.from("123"),
                "Texto Livre"
        );

        final var trilha = Trilha.novaTrilha("Trilha Geral", "Descriçao da Trilha", null);

        trilha.adicionarRegra(RegraTrilha.novaRegraTrilha(regra.getId(), true, TipoAcao.ANALISAR, true));

        when(trilhaGateway.pesquisarPorId(trilha.getId()))
                .thenReturn(Optional.of(Trilha.com(trilha)));

        regra.adicionarTrilhaID(trilha.getId());

        useCase.executar(trilha.getId().getValue(), regra.getId().getValue());

        Trilha trilhaResult = trilhaGateway.pesquisarPorId(trilha.getId()).get();

        verify(trilhaGateway, times(1)).desativarRegraTrilha(any(Trilha.class));

        Assertions.assertFalse(trilhaResult.getRegras().get(0).ativa());
    }

    @Test
    public void informadoUmIdTrilhaInexistente_quandoChamadoOMetodoDesativarRegraDaTrilha_deveLancarUmaNotFoundException() {

        final var mensagemDeErroEsperada = "Trilha com o ID 123 nao foi encontrado (a)";
        final var idTrilhaEsperado = "123";
        final var idRegraEsperado = "123";

        final var excecao = Assertions.assertThrows(NotFoundException.class, () -> useCase.executar(idTrilhaEsperado, idRegraEsperado));

        Assertions.assertEquals(mensagemDeErroEsperada, excecao.getMessage());
        verify(trilhaGateway, never()).desativarRegraTrilha(any(Trilha.class));
    }
}


