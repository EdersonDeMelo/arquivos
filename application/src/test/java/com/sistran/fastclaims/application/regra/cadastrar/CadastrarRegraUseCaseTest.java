package com.sistran.fastclaims.application.regra.cadastrar;

import com.sistran.fastclaims.application.UseCaseTest;
import com.sistran.fastclaims.domain.colecao.Colecao;

import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampo;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoGateway;
import com.sistran.fastclaims.domain.colecaocampo.TipoDado;
import com.sistran.fastclaims.domain.colecaocampo.TipoFormato;

import com.sistran.fastclaims.domain.colecao.ColecaoID;
import com.sistran.fastclaims.domain.colecaocampo.*;
import com.sistran.fastclaims.domain.dominio.DominioID;

import com.sistran.fastclaims.domain.operador.Operador;
import com.sistran.fastclaims.domain.operador.OperadorGateway;
import com.sistran.fastclaims.domain.operador.TipoOperador;
import com.sistran.fastclaims.domain.regra.RegraGateway;
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

public class CadastrarRegraUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCadastrarRegraUseCase useCase;

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
    public void informadoUmComandoValidoComCamposObrigatorios_quandoChamadoOMetodoCadastrarRegra_deveRetornarUmaRegra() {

        final var nome = "Regra teste";
        final var descricao = "Descricao";
        final var campoTres = "CampoTres";

        final var colecao = Colecao.novaColecao("Colecao", "DescricaoColecao");

        final var colecaoIdEsperada = colecao.getId().getValue();

        final var campoUm = ColecaoCampo.novaColecaoCampo(
                "CampoUm",
                "DescricaoCampoUm",
                TipoDado.ALFA,
                TipoFormato.ASTERISCO,
                ColecaoID.from("Rastro"),
                DominioID.from(colecaoIdEsperada),
                null
        );

        when(colecaoCampoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(campoUm));

        final var campoUmIdEsperado = campoUm.getId();

        final var operadorDois = Operador.novoOperador("OperadorUm", "SimboloUm", TipoOperador.RELACIONAL);

        when(operadorGateway.pesquisarPorId(operadorDois.getId()))
                .thenReturn(Optional.of(operadorDois));

        final var operadorDoisIdEsperado = operadorDois.getId();

        final var comando = CadastrarRegraCommand.aPartirDe(
                nome,
                descricao,
                campoUmIdEsperado.getValue(),
                "",
                null,
                operadorDoisIdEsperado.getValue(),
                campoTres
        );

        when(regraGateway.cadastrar(any()))
                .thenAnswer(returnsFirstArg());

        final var saida = useCase.executar(comando).get();

        Assertions.assertNotNull(saida);
        Assertions.assertNotNull(saida.id());
        Assertions.assertNotNull(saida.nome());
        Assertions.assertNotNull(saida.descricao());
        Assertions.assertNotNull(saida.campoUm());
        Assertions.assertNull(saida.campoDois());
        Assertions.assertNotNull(saida.operadorDois());
        Assertions.assertNotNull(saida.campoTres());

        Mockito.verify(regraGateway, times(1)).cadastrar(argThat(regra ->
                Objects.equals(descricao, regra.getDescricao()) &&
                        Objects.equals(nome, regra.getNome()) &&
                        Objects.equals(campoUm.getId().getValue(), regra.getCampoUm().getValue())
        ));
    }

    @Test
    public void informadoUmComandoValidoComCamposNaoObrigatorios_quandoChamadoOMetodoCadastrarRegra_deveRetornarUmaRegra() {

        final var nome = "Regra teste";
        final var descricao = "Descricao";
        final var campoTres = "CampoTres";
        final var campoDois = "CampoDois";

        final var colecao = Colecao.novaColecao("Colecao", "DescricaoColecao");

        final var colecaoIdEsperada = colecao.getId().getValue();

        final var campoUm = ColecaoCampo.novaColecaoCampo(
                "CampoUm",
                "DescricaoCampoUm",
                TipoDado.ALFA,
                TipoFormato.ASTERISCO,
                ColecaoID.from("Rastro"),
                DominioID.from(colecaoIdEsperada),
                null
        );

        when(colecaoCampoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(campoUm));

        final var campoUmIdEsperado = campoUm.getId();

        final var operadorUm = Operador.novoOperador("OperadorUm", "SimboloUm", TipoOperador.ARITMETICO);

        when(operadorGateway.pesquisarPorId(operadorUm.getId()))
                .thenReturn(Optional.of(operadorUm));

        final var operadorDois = Operador.novoOperador("OperadorDois", "SimboloUm", TipoOperador.RELACIONAL);

        when(operadorGateway.pesquisarPorId(operadorDois.getId()))
                .thenReturn(Optional.of(operadorDois));

        final var operadorDoisIdEsperado = operadorDois.getId();

        final var comando = CadastrarRegraCommand.aPartirDe(
                nome,
                descricao,
                campoUmIdEsperado.getValue(),
                operadorUm.getId().getValue(),
                campoDois,
                operadorDoisIdEsperado.getValue(),
                campoTres
        );

        when(regraGateway.cadastrar(any()))
                .thenAnswer(returnsFirstArg());

        final var saida = useCase.executar(comando).get();

        Assertions.assertNotNull(saida);
        Assertions.assertNotNull(saida.id());
        Assertions.assertNotNull(saida.nome());
        Assertions.assertNotNull(saida.descricao());
        Assertions.assertNotNull(saida.campoUm());
        Assertions.assertNotNull(saida.campoDois());
        Assertions.assertNotNull(saida.operadorDois());
        Assertions.assertNotNull(saida.campoTres());
        Assertions.assertNotNull(saida.operadorUm());


        Mockito.verify(regraGateway, times(1)).cadastrar(argThat(regra ->
                Objects.equals(descricao, regra.getDescricao()) &&
                        Objects.equals(nome, regra.getNome()) &&
                        Objects.equals(campoUm.getId().getValue(), regra.getCampoUm().getValue()) &&
                        Objects.equals(operadorUm.getId().getValue(), regra.getOperadorUm().getValue()) &&
                        Objects.equals(campoDois, regra.getCampoDois()) &&
                        Objects.equals(operadorDois.getId().getValue(), regra.getOperadorDois().getValue())
        ));
    }

    @Test
    public void informadoUmComandoComOperadorDoisIncompativel_quandoChamadoOMetodoCadastrarRegra_deveRetornarUmaDomainException() {

        final var nome = "Regra teste";
        final var descricao = "Descricao";
        final var campoTres = "CampoTres";
        final var mensagemErroEsperada = "O Operador Dois deve ser do tipo RELACIONAL";
        final var errosEsperados = 1;

        final var colecao = Colecao.novaColecao("Colecao", "DescricaoColecao");

        final var colecaoIdEsperada = colecao.getId().getValue();

        final var campoUm = ColecaoCampo.novaColecaoCampo(
                "CampoUm",
                "DescricaoCampoUm",
                TipoDado.ALFA,
                TipoFormato.ASTERISCO,
                ColecaoID.from("Rastro"),
                DominioID.from(colecaoIdEsperada),
                null
        );

        when(colecaoCampoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(campoUm));

        final var campoUmIdEsperado = campoUm.getId();

        final var operadorDois = Operador.novoOperador("OperadorDois", "SimboloUm", TipoOperador.ARITMETICO);

        when(operadorGateway.pesquisarPorId(operadorDois.getId()))
                .thenReturn(Optional.of(operadorDois));

        final var operadorDoisIdEsperado = operadorDois.getId();

        final var comando = CadastrarRegraCommand.aPartirDe(
                nome,
                descricao,
                campoUmIdEsperado.getValue(),
                "",
                null,
                operadorDoisIdEsperado.getValue(),
                campoTres
        );

        final var notificacao = useCase.executar(comando).getLeft();

        Assertions.assertEquals(errosEsperados, notificacao.getErrors().size());
        Assertions.assertEquals(mensagemErroEsperada, notificacao.firstError().message());

        Mockito.verify(regraGateway, times(0)).cadastrar(any());
    }

    @Test
    public void informadoUmComandoComOperadorUmIncompativel_quandoChamadoOMetodoCadastrarRegra_deveRetornarUmaDomainException() {

        final var nome = "Regra teste";
        final var descricao = "Descricao";
        final var campoTres = "CampoTres";
        final var mensagemErroEsperada = "O Operador Um deve ser do tipo ARITMETICO, FUNCAO ou FUNCAOAGRUPADA";
        final var errosEsperados = 1;
        final var campoDois = "CampoDois";

        final var colecao = Colecao.novaColecao("Colecao", "DescricaoColecao");

        final var colecaoIdEsperada = colecao.getId().getValue();

        final var campoUm = ColecaoCampo.novaColecaoCampo(
                "CampoUm",
                "DescricaoCampoUm",
                TipoDado.ALFA,
                TipoFormato.ASTERISCO,
                ColecaoID.from("Rastro"),
                DominioID.from(colecaoIdEsperada),
                null
        );

        when(colecaoCampoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(campoUm));

        final var campoUmIdEsperado = campoUm.getId();

        final var operadorUm = Operador.novoOperador("OperadorUm", "SimboloUm", TipoOperador.RELACIONAL);

        when(operadorGateway.pesquisarPorId(operadorUm.getId()))
                .thenReturn(Optional.of(operadorUm));

        final var operadorUmIdEsperado = operadorUm.getId();

        final var operadorDois = Operador.novoOperador("OperadorDois", "SimboloDois", TipoOperador.RELACIONAL);

        when(operadorGateway.pesquisarPorId(operadorDois.getId()))
                .thenReturn(Optional.of(operadorDois));

        final var operadorDoisIdEsperado = operadorDois.getId();

        final var comando = CadastrarRegraCommand.aPartirDe(
                nome,
                descricao,
                campoUmIdEsperado.getValue(),
                operadorUmIdEsperado.getValue(),
                campoDois,
                operadorDoisIdEsperado.getValue(),
                campoTres
        );

        final var notificacao = useCase.executar(comando).getLeft();

        Assertions.assertEquals(errosEsperados, notificacao.getErrors().size());
        Assertions.assertEquals(mensagemErroEsperada, notificacao.firstError().message());

        Mockito.verify(regraGateway, times(0)).cadastrar(any());
    }

    @Test
    public void informadoUmComandoComOperadorUmPreenchidoECampoDoisNulo_quandoChamadoOMetodoCadastrarRegra_deveRetornarUmaDomainException() {

        final var nome = "Regra teste";
        final var descricao = "Descricao";
        final var campoTres = "CampoTres";
        final var mensagemErroEsperada = "'campoDois' não pode ser nulo se 'operadorUm' for preenchido";
        final var errosEsperados = 1;

        final var colecao = Colecao.novaColecao("Colecao", "DescricaoColecao");

        final var colecaoIdEsperada = colecao.getId().getValue();

        final var campoUm = ColecaoCampo.novaColecaoCampo(
                "CampoUm",
                "DescricaoCampoUm",
                TipoDado.ALFA,
                TipoFormato.ASTERISCO,
                ColecaoID.from("Rastro"),
                DominioID.from(colecaoIdEsperada),
                null
        );

        when(colecaoCampoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(campoUm));

        final var campoUmIdEsperado = campoUm.getId();

        final var operadorUm = Operador.novoOperador("OperadorUm", "Simbolo", TipoOperador.ARITMETICO);

        when(operadorGateway.pesquisarPorId(operadorUm.getId()))
                .thenReturn(Optional.of(operadorUm));

        final var operadorUmIdEsperado = operadorUm.getId();

        final var operadorDois = Operador.novoOperador("OperadorDois", "SimboloUm", TipoOperador.RELACIONAL);

        when(operadorGateway.pesquisarPorId(operadorDois.getId()))
                .thenReturn(Optional.of(operadorDois));

        final var operadorDoisIdEsperado = operadorDois.getId();

        final var comando = CadastrarRegraCommand.aPartirDe(
                nome,
                descricao,
                campoUmIdEsperado.getValue(),
                operadorUmIdEsperado.getValue(),
                null,
                operadorDoisIdEsperado.getValue(),
                campoTres
        );

        final var notificacao = useCase.executar(comando).getLeft();

        Assertions.assertEquals(errosEsperados, notificacao.getErrors().size());
        Assertions.assertEquals(mensagemErroEsperada, notificacao.firstError().message());

        Mockito.verify(regraGateway, times(0)).cadastrar(any());
    }

    @Test
    public void informadoUmComandoComOperadorUmNuloECampoDoisPreenchido_quandoChamadoOMetodoCadastrarRegra_deveRetornarUmaDomainException() {

        final var nome = "Regra teste";
        final var descricao = "Descricao";
        final var campoTres = "CampoTres";
        final var mensagemErroEsperada = "'operadorUm' não pode ser nulo se 'campoDois' estiver preenchido";
        final var errosEsperados = 1;

        final var colecao = Colecao.novaColecao("Colecao", "DescricaoColecao");

        final var colecaoIdEsperada = colecao.getId().getValue();

        final var campoUm = ColecaoCampo.novaColecaoCampo(
                "CampoUm",
                "DescricaoCampoUm",
                TipoDado.ALFA,
                TipoFormato.ASTERISCO,
                ColecaoID.from("Rastro"),
                DominioID.from(colecaoIdEsperada),
                null
        );

        when(colecaoCampoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(campoUm));

        final var campoUmIdEsperado = campoUm.getId();


        final var operadorDois = Operador.novoOperador("OperadorDois", "SimboloUm", TipoOperador.RELACIONAL);

        when(operadorGateway.pesquisarPorId(operadorDois.getId()))
                .thenReturn(Optional.of(operadorDois));

        final var operadorDoisIdEsperado = operadorDois.getId();

        final var comando = CadastrarRegraCommand.aPartirDe(
                nome,
                descricao,
                campoUmIdEsperado.getValue(),
                "",
                "Campo 2",
                operadorDoisIdEsperado.getValue(),
                campoTres
        );

        final var notificacao = useCase.executar(comando).getLeft();

        Assertions.assertEquals(errosEsperados, notificacao.getErrors().size());
        Assertions.assertEquals(mensagemErroEsperada, notificacao.firstError().message());

        Mockito.verify(regraGateway, times(0)).cadastrar(any());
    }

    @Test
    public void informadoUmComandoComOperadorUmDoTipoFuncaoECampoDoisPreenchido_quandoChamadoOMetodoCadastrarRegra_deveRetornarUmaDomainException() {

        final var nome = "Regra teste";
        final var descricao = "Descricao";
        final var campoTres = "CampoTres";
        final var mensagemErroEsperada = "'campoDois' não deve ser preenchido se 'operadorUm' for do tipo FUNCAO";
        final var errosEsperados = 1;

        final var colecao = Colecao.novaColecao("Colecao", "DescricaoColecao");

        final var colecaoIdEsperada = colecao.getId().getValue();

        final var campoUm = ColecaoCampo.novaColecaoCampo(
                "CampoUm",
                "DescricaoCampoUm",
                TipoDado.ALFA,
                TipoFormato.ASTERISCO,
                ColecaoID.from("Rastro"),
                DominioID.from(colecaoIdEsperada),
                null
        );

        when(colecaoCampoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(campoUm));

        final var campoUmIdEsperado = campoUm.getId();


        final var operadorUm = Operador.novoOperador("OperadorUm", "Simbolo", TipoOperador.FUNCAO);

        when(operadorGateway.pesquisarPorId(operadorUm.getId()))
                .thenReturn(Optional.of(operadorUm));

        final var operadorUmIdEsperado = operadorUm.getId();

        final var operadorDois = Operador.novoOperador("OperadorDois", "SimboloUm", TipoOperador.RELACIONAL);

        when(operadorGateway.pesquisarPorId(operadorDois.getId()))
                .thenReturn(Optional.of(operadorDois));

        final var operadorDoisIdEsperado = operadorDois.getId();

        final var comando = CadastrarRegraCommand.aPartirDe(
                nome,
                descricao,
                campoUmIdEsperado.getValue(),
                operadorUmIdEsperado.getValue(),
                "Campo 2",
                operadorDoisIdEsperado.getValue(),
                campoTres
        );

        final var notificacao = useCase.executar(comando).getLeft();

        Assertions.assertEquals(errosEsperados, notificacao.getErrors().size());
        Assertions.assertEquals(mensagemErroEsperada, notificacao.firstError().message());

        Mockito.verify(regraGateway, times(0)).cadastrar(any());
    }
}
