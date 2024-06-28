package com.sistran.fastclaims.application.colecaocampo.cadastrar;

import com.sistran.fastclaims.domain.colecao.Colecao;
import com.sistran.fastclaims.domain.colecao.ColecaoGateway;
import com.sistran.fastclaims.domain.colecao.ColecaoID;
import com.sistran.fastclaims.domain.colecaocampo.*;
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
public class TipoDadoData {

    @InjectMocks
    private DefaultCadastrarColecaoCampoUseCase useCase;

    @Mock
    private ColecaoCampoGateway colecaoCampoGateway;

    @Mock
    private ColecaoGateway colecaoGateway;


    @Test
    void tipoFormatoDataTipoDadoDataTest() {
        final var comando = CadastrarColecaoCampoCommand.com(
                "Campo", "Alias", TipoDado.DATA,
                TipoFormato.DATA, "ColecaoId", null);

        when(colecaoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(Colecao.com(ColecaoID.from("ColecaoId"), "Nome", "Alias")));

        when(colecaoCampoGateway.cadastrar(any())).thenReturn(gerearColecaoCampo());

        final var result = useCase.executar(comando);

        Assertions.assertTrue(result.isRight());
    }

    @Test
    void tipoFormatoPercentTipoDadoDataTest() {
        final var comando = CadastrarColecaoCampoCommand.com(
                "Campo", "Alias", TipoDado.DATA,
                TipoFormato.PERCENT, "ColecaoId", null);

        when(colecaoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(Colecao.com(ColecaoID.from("ColecaoId"), "Nome", "Alias")));

        final var result = useCase.executar(comando);

        Assertions.assertTrue(result.isLeft());
    }

    @Test
    void tipoFormatoZeroTipoDadoDataTest() {
        final var comando = CadastrarColecaoCampoCommand.com(
                "Campo", "Alias", TipoDado.DATA,
                TipoFormato.ZERO, "ColecaoId", null);

        when(colecaoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(Colecao.com(ColecaoID.from("ColecaoId"), "Nome", "Alias")));

        final var result = useCase.executar(comando);

        Assertions.assertTrue(result.isLeft());
    }

    @Test
    void tipoFormatoDecimalTipoDadoDataTest() {
        final var comando = CadastrarColecaoCampoCommand.com(
                "Campo", "Alias", TipoDado.DATA,
                TipoFormato.DECIMAL, "ColecaoId", null);

        when(colecaoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(Colecao.com(ColecaoID.from("ColecaoId"), "Nome", "Alias")));

        final var result = useCase.executar(comando);

        Assertions.assertTrue(result.isLeft());
    }

    @Test
    void tipoFormatoAsteriscoTipoDadoDataTest() {
        final var comando = CadastrarColecaoCampoCommand.com(
                "Campo", "Alias", TipoDado.DATA,
                TipoFormato.ASTERISCO, "ColecaoId", null);

        when(colecaoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(Colecao.com(ColecaoID.from("ColecaoId"), "Nome", "Alias")));

        final var result = useCase.executar(comando);

        Assertions.assertTrue(result.isLeft());
    }

    private ColecaoCampo gerearColecaoCampo() {
        return new ColecaoCampo(ColecaoCampoID.from("ColecaoCampoID"), "Campo", "Alias",
                TipoDado.NUMERICO, TipoFormato.DECIMAL, "Rastro", ColecaoID.from("ColecaoId"), null);
    }

}
