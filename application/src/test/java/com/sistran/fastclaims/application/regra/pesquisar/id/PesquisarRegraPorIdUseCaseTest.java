package com.sistran.fastclaims.application.regra.pesquisar.id;

import com.sistran.fastclaims.application.UseCaseTest;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampo;
import com.sistran.fastclaims.domain.colecaocampo.TipoDado;
import com.sistran.fastclaims.domain.colecaocampo.TipoFormato;
import com.sistran.fastclaims.domain.colecao.ColecaoID;
import com.sistran.fastclaims.domain.colecaocampo.*;
import com.sistran.fastclaims.domain.dominio.DominioID;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.operador.Operador;
import com.sistran.fastclaims.domain.operador.OperadorID;
import com.sistran.fastclaims.domain.operador.TipoOperador;
import com.sistran.fastclaims.domain.regra.Regra;
import com.sistran.fastclaims.domain.regra.RegraGateway;
import com.sistran.fastclaims.domain.regra.RegraID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class PesquisarRegraPorIdUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultPesquisarRegraPorIdUseCase useCase;

    @Mock
    private RegraGateway regraGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(regraGateway);
    }

    @Test
    public void informadoUmdValido_quandoChamadoOMetodoPesquisarRegraPotId_deveRetornarUmaRegra() {

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
                .thenReturn(Optional.of(regra.clone()));

        final var regraAtual = useCase.executar(idEsperado.getValue());

        Assertions.assertEquals(idEsperado.getValue(), regraAtual.id());
        Assertions.assertEquals(descricao, regraAtual.descricao());
        Assertions.assertEquals(nome, regraAtual.nome());
        Assertions.assertEquals(campoUm.getId().getValue(), regraAtual.campoUm());
        Assertions.assertEquals(operadorDois.getId().getValue(), regraAtual.operadorDois());
        Assertions.assertEquals(campoTres, regraAtual.campoTres());
        Assertions.assertNotNull(regra.getDataAlteracao());
        Assertions.assertNotNull(regra.getDataCadastro());
    }

    @Test
    public void informadoUmComandoInvalido_quandoChamadoOMetodoPesquisarRegraPorId_deveRetornarUmNotFound() {

        final var mensagemDeErroEsperada = "Regra com o ID 123 nao foi encontrado (a)";
        final var idEsperado = RegraID.from("123");

        when(regraGateway.pesquisarPorId(any()))
                .thenReturn(Optional.empty());

        final var excecao = Assertions.assertThrows(
                NotFoundException.class,
                () -> useCase.executar(idEsperado.getValue())
        );

        Assertions.assertEquals(mensagemDeErroEsperada, excecao.getMessage());
    }

    @Test
    public void informadoUmComandoValido_quandoChamadoOMetodoPesquisarRegraPorId_deveRetornarUmaException() {

        final var mensagemDeErroEsperada = "Gateway error";
        final var idEsperado = RegraID.from("123");

        when(regraGateway.pesquisarPorId(eq(idEsperado)))
                .thenThrow(new IllegalStateException(mensagemDeErroEsperada));

        final var excecao = Assertions.assertThrows(
                IllegalStateException.class,
                () -> useCase.executar(idEsperado.getValue())
        );
        Assertions.assertEquals(mensagemDeErroEsperada, excecao.getMessage());
    }
}

