package com.sistran.fastclaims.application.colecaocampo.cadastrar;

import com.sistran.fastclaims.domain.colecao.Colecao;
import com.sistran.fastclaims.domain.colecao.ColecaoGateway;
import com.sistran.fastclaims.domain.colecao.ColecaoID;
import com.sistran.fastclaims.domain.colecaocampo.TipoDado;
import com.sistran.fastclaims.domain.colecaocampo.TipoFormato;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AliasTest {

    @InjectMocks
    private DefaultCadastrarColecaoCampoUseCase useCase;

    @Mock
    private ColecaoGateway colecaoGateway;

    @Test
    void aliasVazioTest() {
        final var comando = CadastrarColecaoCampoCommand.com(
                "Campo", "", TipoDado.NUMERICO,
                TipoFormato.DECIMAL, "ColecaoId", null);

        when(colecaoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(Colecao.com(ColecaoID.from("ColecaoId"), "Nome", "Alias")));

        final var result = useCase.executar(comando);

        Assertions.assertTrue(result.isLeft());
    }

    @Test
    void aliasComMenosDe3CaracteresTest() {
        final var comando = CadastrarColecaoCampoCommand.com(
                "Campo", "12", TipoDado.NUMERICO,
                TipoFormato.DECIMAL, "ColecaoId", null);

        when(colecaoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(Colecao.com(ColecaoID.from("ColecaoId"), "Nome", "Alias")));

        final var result = useCase.executar(comando);

        Assertions.assertTrue(result.isLeft());
    }

    @Test
    void aliasComMaisDe30CaracteresTest() {
        final var comando = CadastrarColecaoCampoCommand.com(
                "Campo", "1234567890123456789012345678901", TipoDado.NUMERICO,
                TipoFormato.DECIMAL, "ColecaoId", null);

        when(colecaoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(Colecao.com(ColecaoID.from("ColecaoId"), "Nome", "Alias")));

        final var result = useCase.executar(comando);

        Assertions.assertTrue(result.isLeft());
    }

}
