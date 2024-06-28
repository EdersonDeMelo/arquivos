package com.sistran.fastclaims.application.fluxo.excluir;

import com.sistran.fastclaims.application.UseCaseTest;
import com.sistran.fastclaims.domain.fluxo.Fluxo;
import com.sistran.fastclaims.domain.fluxo.FluxoGateway;
import com.sistran.fastclaims.domain.fluxo.FluxoID;
import com.sistran.fastclaims.domain.natureza.NaturezaID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

public class ExcluirFluxoUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultExcluirFluxoUseCase useCase;

    @Mock
    private FluxoGateway fluxoGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(fluxoGateway);
    }

    @Test
    public void informarUmIdValido_quandoChamarExcluirFluxo_deveRetornarOK() {
        final var aFluxo = Fluxo.novoFluxo("Fluxo teste", NaturezaID.aPartirDe("123"));
        final var expectedId = aFluxo.getId();

        Mockito.doNothing()
                .when(fluxoGateway).excluirPorId(Mockito.eq(expectedId));

        Assertions.assertDoesNotThrow(() -> useCase.executar(expectedId.getValue()));
        Mockito.verify(fluxoGateway, Mockito.times(1)).excluirPorId(Mockito.eq(expectedId));
    }

    @Test
    public void informarUmIdValido_quandoGatewayLancaExcecao_deveRetornarExcecao() {
        final var aFluxo = Fluxo.novoFluxo("Fluxo teste", NaturezaID.aPartirDe("123"));
        final var expectedId = aFluxo.getId();

        Mockito.doThrow(new RuntimeException("Erro ao excluir fluxo"))
                .when(fluxoGateway).excluirPorId(Mockito.eq(expectedId));

        Assertions.assertThrows(RuntimeException.class, () -> useCase.executar(expectedId.getValue()));

        Mockito.verify(fluxoGateway, Mockito.times(1)).excluirPorId(Mockito.eq(expectedId));
    }

    @Test
    void informarUmIdInvalido_quandoChamarExcluirFluxo_deveRetornarExcecao() {
        final var expectedId = FluxoID.from("123");

        Mockito.doThrow(new RuntimeException("Erro ao excluir fluxo"))
                .when(fluxoGateway).excluirPorId(Mockito.eq(expectedId));

        Assertions.assertThrows(RuntimeException.class, () -> useCase.executar(expectedId.getValue()));
        Mockito.verify(fluxoGateway, Mockito.times(1)).excluirPorId(Mockito.eq(expectedId));
    }

}
