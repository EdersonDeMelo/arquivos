package com.sistran.fastclaims.application.trilha.atualizar.regra_trilha;

import com.sistran.fastclaims.application.UseCaseTest;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoID;
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
import static org.mockito.Mockito.when;

public class AtualizarRegraTrilhaUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultAtualizarRegraTrilhaUseCase useCase;

    @Mock
    private RegraGateway regraGateway;

    @Mock
    private TrilhaGateway trilhaGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(regraGateway, trilhaGateway);
    }

    @Test
    public void informadoUmComandoValido_quandoChamadoOMetodoAtualizarRegraTrilha_deveAtualizarRegraTrilha() {

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
        regra.adicionarTrilhaID(trilha.getId());

        final var regraTrilha = AtualizarRegraTrilhaCommand.aPartirDe(
                regra.getId().getValue(),
                trilha.getId().getValue(),
                false,
                TipoAcao.ENCERRAR.getNome(),
                false
        );

        when(regraGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(regra));

        when(trilhaGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(trilha));

        when(trilhaGateway.atualizarRegraTrilha(any(), any())).thenReturn(RegraTrilha.novaRegraTrilha(regra.getId(), false, TipoAcao.ENCERRAR, false));

        useCase.executar(regraTrilha);

        Trilha trilhaResult = trilhaGateway.pesquisarPorId(trilha.getId()).get();

        Assertions.assertFalse(trilhaResult.getRegras().get(0).ativa());
        Assertions.assertEquals(TipoAcao.ENCERRAR, trilhaResult.getRegras().get(0).tipoAcao());
        Assertions.assertFalse(trilhaResult.getRegras().get(0).resultadoEsperado());
        Assertions.assertEquals(regra.getId(), trilhaResult.getRegras().get(0).id());
    }
}
