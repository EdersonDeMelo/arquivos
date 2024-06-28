package com.sistran.fastclaims.application.trilha.excluir.regra_trilha;

import com.sistran.fastclaims.application.UseCaseTest;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoID;
import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.operador.OperadorID;
import com.sistran.fastclaims.domain.regra.Regra;
import com.sistran.fastclaims.domain.regra.RegraGateway;
import com.sistran.fastclaims.domain.regra.RegraID;
import com.sistran.fastclaims.domain.regra.TipoAcao;
import com.sistran.fastclaims.domain.trilha.RegraTrilha;
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

public class ExcluirRegraTrilhaUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultExcluirRegraTrilhaUseCase useCase;

    @Mock
    private RegraGateway regraGateway;

    @Mock
    private TrilhaGateway trilhaGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(regraGateway, trilhaGateway);
    }

    @Test
    public void informadoUmComandoValido_quandoChamadoOMetodoExcluirRegraTrilha_deveExcluirARegraDaTrilha() {

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

        when(regraGateway.pesquisarPorId(regra.getId()))
                .thenReturn(Optional.of(regra));

        when(trilhaGateway.pesquisarPorId(trilha.getId()))
                .thenReturn(Optional.of(Trilha.com(trilha)));

        regra.adicionarTrilhaID(trilha.getId());

        useCase.executar(trilha.getId().getValue(), regra.getId().getValue());

        Trilha trilhaResult = trilhaGateway.pesquisarPorId(trilha.getId()).get();

        Regra regraResult = regraGateway.pesquisarPorId(regra.getId()).get();

        verify(trilhaGateway, times(1)).atualizar(any(Trilha.class));

        verify(regraGateway, times(1)).atualizar(any(Regra.class));

        Assertions.assertEquals(0, trilhaResult.getRegras().size());
        Assertions.assertEquals(0, regraResult.getTrilhas().size());
    }

    @Test
    public void informadoUmIdTrilhaInexistenteEmUmaRegra_quandoChamadoOMetodoExcluirRegraTrilha_deveRetornarUmaException() {

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

        when(regraGateway.pesquisarPorId(regra.getId()))
                .thenReturn(Optional.of(regra));

        when(trilhaGateway.pesquisarPorId(trilha.getId()))
                .thenReturn(Optional.of(Trilha.com(trilha)));

        regra.adicionarTrilhaID(TrilhaID.from("123"));

        final var mensagemDeErroEsperada = "Trilha não encontrada na regra!";

        final var excecao = Assertions.assertThrows(DomainException.class, () -> useCase.executar(trilha.getId().getValue(), regra.getId().getValue()));

        Assertions.assertEquals(mensagemDeErroEsperada, excecao.getMessage());
        verify(trilhaGateway, never()).atualizar(any(Trilha.class));
    }

    @Test
    public void informadoUmIdRegraInexistenteEmUmaTrilha_quandoChamadoOMetodoExcluirRegraTrilha_deveRetornarUmaException() {

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

        trilha.adicionarRegra(RegraTrilha.novaRegraTrilha(RegraID.from("123"), true, TipoAcao.ANALISAR, true));

        when(regraGateway.pesquisarPorId(regra.getId()))
                .thenReturn(Optional.of(regra));

        when(trilhaGateway.pesquisarPorId(trilha.getId()))
                .thenReturn(Optional.of(Trilha.com(trilha)));

        regra.adicionarTrilhaID(trilha.getId());

        final var mensagemDeErroEsperada = "Regra não encontrada na trilha!";

        final var excecao = Assertions.assertThrows(DomainException.class, () -> useCase.executar(trilha.getId().getValue(), regra.getId().getValue()));

        Assertions.assertEquals(mensagemDeErroEsperada, excecao.getMessage());
        verify(trilhaGateway, never()).atualizar(any(Trilha.class));
    }
}
