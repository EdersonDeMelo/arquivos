package com.sistran.fastclaims.application.dominiovalor.excluir;

import com.sistran.fastclaims.application.UseCaseTest;
import com.sistran.fastclaims.domain.dominio.Dominio;
import com.sistran.fastclaims.domain.dominio.DominioGateway;
import com.sistran.fastclaims.domain.dominio.DominioID;
import com.sistran.fastclaims.domain.dominiovalor.DominioValor;
import com.sistran.fastclaims.domain.dominiovalor.DominioValorGateway;
import com.sistran.fastclaims.domain.dominiovalor.DominioValorID;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ExcluirDominioValorUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultExcluirDominioValorUseCase excluirDominioValorUseCase;

    @Mock
    private DominioGateway dominioGateway;

    @Mock
    private DominioValorGateway dominioValorGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(dominioGateway, dominioValorGateway);
    }

    private List<DominioValor> listaDominioValor() {
        List<DominioValor> listaDominioValor = new ArrayList<>();
        DominioValor dominioValor = DominioValor.novoDominioValor("codigo", "nomeValor", null);
        listaDominioValor.add(dominioValor);
        return listaDominioValor;
    }

    @Test
    void informarUmIdValido_quandoChamarExcluirDominioValor_deveRetornarOK() {
        final var dominio = Dominio.novoDominio("Dominioteste", "Descricao", listaDominioValor());
        final var expectedDominioValorId = dominio.getValores().get(0).getId();
        final var expectedDominioId = dominio.getId();
        final var excluirDominioValorCommand = ExcluirDominioValorCommand.com(expectedDominioId.getValue(),
                expectedDominioValorId.getValue());

        when(dominioGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(dominio));

        assertDoesNotThrow(() -> excluirDominioValorUseCase.executar(excluirDominioValorCommand));
        verify(dominioValorGateway, times(1)).excluirDominioValorPorId(eq(dominio.getValores().get(0)));
    }

    @Test
    void informarUmIdInvalido_quandoChamarExcluirDominioValor_deveRetornarExcecao() {
        final var dominio = Dominio.novoDominio("Dominioteste", "Descricao", listaDominioValor());
        final var expectedDominioId = dominio.getId();
        final var idInvalido = DominioValorID.from("123");
        final var excluirDominioValorCommand = ExcluirDominioValorCommand.com(expectedDominioId.getValue(),
                idInvalido.getValue());
        final var mensagemEsperada = "DominioValor com id 123 inexistente";

        when(dominioGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(dominio));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> excluirDominioValorUseCase.executar(excluirDominioValorCommand));

        assertEquals(mensagemEsperada, exception.getMessage());
        verify(dominioValorGateway, never()).excluirDominioValorPorId(any());

    }

    @Test
    void informarUmIdValido_quandoGatewayLancaExcecao_deveRetornarExcecao() {
        final var dominio = Dominio.novoDominio("Dominioteste", "Descricao", listaDominioValor());
        final var expectedDominioValorId = dominio.getValores().get(0).getId();
        final var expectedDominioId = dominio.getId();
        final var excluirDominioValorCommand = ExcluirDominioValorCommand.com(expectedDominioId.getValue(),
                expectedDominioValorId.getValue());
        final var mensagemEsperada = "Erro ao excluir dominio valor";

        when(dominioGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(dominio));

        doThrow(new RuntimeException("Erro ao excluir dominio valor"))
                .when(dominioValorGateway).excluirDominioValorPorId(any());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> excluirDominioValorUseCase.executar(excluirDominioValorCommand));

        assertEquals(mensagemEsperada, exception.getMessage());
        verify(dominioValorGateway, times(1)).excluirDominioValorPorId(eq(dominio.getValores().get(0)));
    }

    @Test
    void informarUmDominioNaoEncontrado_quandoChamarExcluirDominioValor_deveRetornarExcecao() {
        final var dominioId = DominioID.from("123");
        final var dominioValorId = DominioValorID.from("123");
        final var excluirDominioValorCommand = ExcluirDominioValorCommand.com(dominioId.getValue(), dominioValorId.getValue());
        final var mensagemEsperada = "Dominio de código 123 inexistente";

        when(dominioGateway.pesquisarPorId(any()))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> excluirDominioValorUseCase.executar(excluirDominioValorCommand));

        assertEquals(mensagemEsperada, exception.getMessage());
        verify(dominioValorGateway, never()).excluirDominioValorPorId(any());
    }

}
