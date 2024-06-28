package com.sistran.fastclaims.application.colecaocampo.cadastrar;

import com.sistran.fastclaims.domain.colecao.Colecao;
import com.sistran.fastclaims.domain.colecao.ColecaoGateway;
import com.sistran.fastclaims.domain.colecao.ColecaoID;
import com.sistran.fastclaims.domain.colecaocampo.*;
import com.sistran.fastclaims.domain.dominio.DominioGateway;
import com.sistran.fastclaims.domain.dominio.DominioID;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
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
class CadastrarColecaoCampoUseCaseTest {

    @InjectMocks
    private DefaultCadastrarColecaoCampoUseCase useCase;

    @Mock
    private ColecaoCampoGateway colecaoCampoGateway;

    @Mock
    private ColecaoGateway colecaoGateway;

    @Mock
    private DominioGateway dominioGateway;

    @Test
    void cadastrarColecaoCampoTest() {
        final var comando = CadastrarColecaoCampoCommand.com(
                "Campo", "Alias", TipoDado.NUMERICO,
                TipoFormato.DECIMAL, "ColecaoId", "DominioId");

        when(dominioGateway.exists(any())).thenReturn(true);

        when(colecaoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(Colecao.com(ColecaoID.from("ColecaoId"), "Nome", "Alias")));

        when(colecaoCampoGateway.cadastrar(any())).thenReturn(gerearColecaoCampo());

        final var result = useCase.executar(comando);

        Assertions.assertTrue(result.isRight());
    }

    @Test
    void cadastrarColecaoCampoColecaoNaoEncontradaTest() {
        final var colecaoID = "ColecaoId";
        final var comando = CadastrarColecaoCampoCommand.com(
                "Campo", "Alias", TipoDado.NUMERICO,
                TipoFormato.DECIMAL, colecaoID, null);

        when(colecaoGateway.pesquisarPorId(any()))
                .thenThrow(NotFoundException.with(Colecao.class, ColecaoID.from(colecaoID)));

        Assertions.assertThrows(NotFoundException.class, () -> useCase.executar(comando));
    }

    @Test
    void cadastrarColecaoCampoDominioNaoEncontradoTest() {
        final var comando = CadastrarColecaoCampoCommand.com(
                "Campo", "Alias", TipoDado.NUMERICO,
                TipoFormato.DECIMAL, "colecaoId", "dominioId");

        when(dominioGateway.exists(any())).thenReturn(false);

        when(colecaoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(Colecao.com(ColecaoID.from("ColecaoId"), "Nome", "Alias")));

        Assertions.assertThrows(NotFoundException.class, () -> useCase.executar(comando));
    }

    @Test
    void cadastrarColecaoCampoCampoNuloTest() {
        final var comando = CadastrarColecaoCampoCommand.com(
                null, "Alias", TipoDado.NUMERICO,
                TipoFormato.DECIMAL, "ColecaoId", null);

        when(colecaoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(Colecao.com(ColecaoID.from("ColecaoId"), "Nome", "Alias")));

        final var result = useCase.executar(comando);

        Assertions.assertTrue(result.isLeft());
    }

    @Test
    void cadastrarColecaoCampoCampoVazioTest() {
        final var comando = CadastrarColecaoCampoCommand.com(
                " ", "Alias", TipoDado.NUMERICO,
                TipoFormato.DECIMAL, "ColecaoId", null);

        when(colecaoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(Colecao.com(ColecaoID.from("ColecaoId"), "Nome", "Alias")));

        final var result = useCase.executar(comando);

        Assertions.assertTrue(result.isLeft());
    }

    @Test
    void cadastrarColecaoCampoCampoComMaisDe30CaracteresTest() {
        final var comando = CadastrarColecaoCampoCommand.com(
                "CampoComMaisDe30CaracteresCampoComMaisDe30Caracteres", "Alias", TipoDado.NUMERICO,
                TipoFormato.DECIMAL, "ColecaoId", null);

        when(colecaoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(Colecao.com(ColecaoID.from("ColecaoId"), "Nome", "Alias")));

        final var result = useCase.executar(comando);

        Assertions.assertTrue(result.isLeft());
    }

    @Test
    void cadastrarColecaoCampoCampoMenos3Caracteres() {
        final var comando = CadastrarColecaoCampoCommand.com(
                "12", "Alias", TipoDado.NUMERICO,
                TipoFormato.DECIMAL, "ColecaoId", null);

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
