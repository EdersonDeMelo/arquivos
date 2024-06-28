package com.sistran.fastclaims.application.colecao.atualizar;

import com.sistran.fastclaims.domain.colecao.Colecao;
import com.sistran.fastclaims.domain.colecao.ColecaoGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AtualizarColecaoUseCaseTest {

    @InjectMocks
    private DefaultAtualizarColecaoUseCase useCase;

    @Mock
    private ColecaoGateway colecaoGateway;

    @Test
    void atualizarColecaoTest() {

        final var colecao = Colecao.novaColecao("Nome", "Alias");

        final var comando = AtualizarColecaoCommand.com(colecao.getId().getValue(), "Alias Atualizado");

        when(colecaoGateway.pesquisarPorId(any())).thenReturn(java.util.Optional.of(colecao));
        when(colecaoGateway.atualizar(any())).thenReturn(colecao.atualizar("Alias Atualizado"));

        final var result = useCase.executar(comando);

        assertTrue(result.isRight());
    }

    @Test
    void atualizarColecaoCampoAliasNuloTest() {

        final var colecao = Colecao.novaColecao("Nome", "Alias");

        final var comando = AtualizarColecaoCommand.com(colecao.getId().getValue(), null);

        when(colecaoGateway.pesquisarPorId(any())).thenReturn(java.util.Optional.of(colecao));

        final var result = useCase.executar(comando);

        assertTrue(result.isLeft());
    }

    @Test
    void atualizarColecaoCampoAliasVazioTest() {

        final var colecao = Colecao.novaColecao("Nome", "Alias");

        final var comando = AtualizarColecaoCommand.com(colecao.getId().getValue(), "");

        when(colecaoGateway.pesquisarPorId(any())).thenReturn(java.util.Optional.of(colecao));

        final var result = useCase.executar(comando);

        assertTrue(result.isLeft());
    }

    @Test
    void atualizarColecaoCampoAliasMenos3CaracteresTest() {

        final var colecao = Colecao.novaColecao("Nome", "Alias");

        final var comando = AtualizarColecaoCommand.com(colecao.getId().getValue(), "12");

        when(colecaoGateway.pesquisarPorId(any())).thenReturn(java.util.Optional.of(colecao));

        final var result = useCase.executar(comando);

        assertTrue(result.isLeft());
    }

    @Test
    void atualizarColecaoCampoAliasMaisDe40CaracteresTest() {

        final var colecao = Colecao.novaColecao("Nome", "Alias");

        final var comando = AtualizarColecaoCommand.com(colecao.getId().getValue(), gerar51Caracteres());

        when(colecaoGateway.pesquisarPorId(any())).thenReturn(java.util.Optional.of(colecao));

        final var result = useCase.executar(comando);

        assertTrue(result.isLeft());
    }

    private String gerar51Caracteres() {
        return "a".repeat(51);
    }

}
