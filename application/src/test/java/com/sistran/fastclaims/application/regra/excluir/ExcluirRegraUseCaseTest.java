package com.sistran.fastclaims.application.regra.excluir;

import com.sistran.fastclaims.application.UseCaseTest;

import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampo;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoID;
import com.sistran.fastclaims.domain.colecaocampo.TipoDado;
import com.sistran.fastclaims.domain.colecaocampo.TipoFormato;

import com.sistran.fastclaims.domain.colecao.ColecaoID;
import com.sistran.fastclaims.domain.colecaocampo.*;
import com.sistran.fastclaims.domain.dominio.DominioID;

import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.operador.Operador;
import com.sistran.fastclaims.domain.operador.OperadorID;
import com.sistran.fastclaims.domain.operador.TipoOperador;
import com.sistran.fastclaims.domain.regra.Regra;
import com.sistran.fastclaims.domain.regra.RegraGateway;
import com.sistran.fastclaims.domain.regra.RegraID;
import com.sistran.fastclaims.domain.trilha.TrilhaID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ExcluirRegraUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultExcluirRegraUseCase useCase;

    @Mock
    private RegraGateway regraGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(regraGateway);
    }

    @Test
    public void informadoUmComandoValido_quandoChamadoOMetodoExcluirRegra_deveExcluirUmaRegra() {

        final var nome = "Regra teste";
        final var descricao = "Descricao";
        final var campoTres = "CampoTres";

        final var campoUm = ColecaoCampo.novaColecaoCampo(
                "CampoUm",
                "DescricaoCampoUm",
                TipoDado.ALFA,
                TipoFormato.ASTERISCO,
                ColecaoID.from("Rastro"),
                DominioID.from("123"),
                null
        );

        final var operadorDois = Operador.novoOperador(
                "OperadorUm",
                "SimboloUm",
                TipoOperador.RELACIONAL
        );

        final var regra = Regra.novaRegra(
                nome,
                descricao,
                campoUm.getId(),
                OperadorID.from(""),
                null,
                operadorDois.getId(),
                campoTres
        );

        final var idEsperado = regra.getId();

        when(regraGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(regra));

        doNothing().when(regraGateway).excluir(any());

        Assertions.assertDoesNotThrow(() -> useCase.executar(idEsperado.getValue()));

        Mockito.verify(regraGateway, times(1)).excluir(idEsperado);
    }

    @Test
    public void informadoUmIdInexistente_quandoChamadoOMetodoExcluirRegra_deveRetornarUmNotFound() {

        final var idEsperado = RegraID.from("123");
        final var mensagemErroEsperada = "Regra com o ID 123 nao foi encontrado (a)";

        final var excecao = Assertions.assertThrows(NotFoundException.class, () -> useCase.executar(idEsperado.getValue()));

        Assertions.assertEquals(mensagemErroEsperada, excecao.getMessage());
        verify(regraGateway, never()).excluir(idEsperado);
    }

    @Test
    public void informadoUmaRegraComTrilhasVinculadas_quandoChamadoOMetodoExcluirRegra_deveRetornarUmaDomainException() {

        final var mensagemErroEsperada = "Regra não pode ser excluída, pois está associada a uma ou mais trilhas!";

        final var regra = Regra.novaRegra(
                "Regra Teste",
                "Teste",
                ColecaoCampoID.from("123"),
                OperadorID.from(""),
                null,
                OperadorID.from("123"),
                "Texto Livre"
        );

        regra.adicionarTrilhaID(TrilhaID.from("123"));

        final var idEsperado = regra.getId();

        when(regraGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(regra));

        final var excecao = Assertions.assertThrows(DomainException.class, () -> useCase.executar(idEsperado.getValue()));

        Assertions.assertEquals(mensagemErroEsperada, excecao.getMessage());

        verify(regraGateway, never()).excluir(idEsperado);
    }
}
