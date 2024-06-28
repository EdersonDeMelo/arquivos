package com.sistran.fastclaims.application.regra.atualizar;

import com.sistran.fastclaims.application.UseCaseTest;
import com.sistran.fastclaims.domain.colecao.ColecaoID;
import com.sistran.fastclaims.domain.colecaocampo.*;

import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampo;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoGateway;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoID;
import com.sistran.fastclaims.domain.dominio.DominioID;

import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.operador.Operador;
import com.sistran.fastclaims.domain.operador.OperadorGateway;
import com.sistran.fastclaims.domain.operador.OperadorID;
import com.sistran.fastclaims.domain.operador.TipoOperador;
import com.sistran.fastclaims.domain.regra.Regra;
import com.sistran.fastclaims.domain.regra.RegraGateway;
import com.sistran.fastclaims.domain.regra.RegraID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class AtualizarRegraUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultAtualizarRegraUseCase useCase;

    @Mock
    private RegraGateway regraGateway;

    @Mock
    private ColecaoCampoGateway colecaoCampoGateway;

    @Mock
    private OperadorGateway operadorGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(regraGateway, colecaoCampoGateway, operadorGateway);
    }

    @Test
    public void informadoUmComandoValido_quandoChamadoOMetodoAtualizarRegra_deveAtualizarUmaRegra() {

        final var nomeEsperado = "Regra atualizada";
        final var descricaoEsperada = "Descricao atualizada";
        final var campoTresEsperado = "CampoTres atualizado";

        final var regra = Regra.novaRegra(
                "Teste",
                "Desc",
                ColecaoCampoID.from("123"),
                OperadorID.from(""),
                null,
                OperadorID.from("124"),
                "Texto livre"
        );

        when(regraGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(regra.clone()));

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

        when(colecaoCampoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(campoUm));

        when(operadorGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(operadorDois));

        final var command = AtualizarRegraCommand.aPartirDe(
                regra.getId().getValue(),
                nomeEsperado,
                descricaoEsperada,
                campoUm.getId().getValue(),
                "",
                null,
                operadorDois.getId().getValue(),
                campoTresEsperado
        );

        when(regraGateway.atualizar(any())).thenAnswer(returnsFirstArg());

        final var regraAtualizada = useCase.executar(command).get();

        Assertions.assertEquals(nomeEsperado, regraAtualizada.nome());
        Assertions.assertEquals(descricaoEsperada, regraAtualizada.descricao());
        Assertions.assertEquals(campoUm.getId().getValue(), regraAtualizada.campoUm());
        Assertions.assertEquals(operadorDois.getId().getValue(), regraAtualizada.operadorDois());
        Assertions.assertEquals(campoTresEsperado, regraAtualizada.campoTres());
        Assertions.assertNotNull(regraAtualizada.dataAlteracao());
        Assertions.assertNotNull(regraAtualizada.dataAlteracao());

        Mockito.verify(regraGateway, times(1)).atualizar(argThat(regraAtt ->
                Objects.equals(regraAtualizada.descricao(), regraAtt.getDescricao()) &&
                        Objects.equals(regraAtualizada.nome(), regraAtt.getNome())
        ));
    }

    @Test
    public void informadoOCampoTresNulo_quandoChamadoOMetodoAtualizarRegra_deveRetornarUmaException() {

        final var regra = Regra.novaRegra(
                "Teste",
                "Desc",
                ColecaoCampoID.from("123"),
                OperadorID.from(""),
                null,
                OperadorID.from("124"),
                "Texto livre"
        );

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

        when(colecaoCampoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(campoUm));

        when(operadorGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(operadorDois));

        final var command = AtualizarRegraCommand.aPartirDe(
                regra.getId().getValue(),
                "Regra atualizada",
                "Descricao atualizada",
                campoUm.getId().getValue(),
                "",
                null,
                operadorDois.getId().getValue(),
                null
        );

        when(regraGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(regra.clone()));

        final String descricaoErroEsperada = "'campoTres' não pode ser nulo!";
        final var errosEsperados = 1;

        final var notificacao = useCase.executar(command).getLeft();

        Assertions.assertEquals(errosEsperados, notificacao.getErrors().size());
        Assertions.assertEquals(descricaoErroEsperada, notificacao.firstError().message());
    }

    @Test
    public void informadoUmIdRegraInexistente_quandoChamadoOMetodoAtualizarRegra_deveRetornarUmaNotFoundException() {

        final var idEsperado = RegraID.from("123");
        final var mensagemErroEsperada = "Regra com o ID 123 nao foi encontrado (a)";

        final var command = AtualizarRegraCommand.aPartirDe(
                idEsperado.getValue(),
                "Regra atualizada",
                "Descricao atualizada",
                ColecaoCampoID.from("123").getValue(),
                "",
                null,
                OperadorID.from("124").getValue(),
                null
        );

        final var excecao = Assertions.assertThrows(
                NotFoundException.class,
                () -> useCase.executar(command)
        );

        Assertions.assertEquals(mensagemErroEsperada, excecao.getMessage());
    }
}
