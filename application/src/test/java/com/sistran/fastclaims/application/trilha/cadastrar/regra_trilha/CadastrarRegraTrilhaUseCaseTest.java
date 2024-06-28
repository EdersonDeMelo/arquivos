package com.sistran.fastclaims.application.trilha.cadastrar.regra_trilha;

import com.sistran.fastclaims.application.UseCaseTest;
import com.sistran.fastclaims.domain.colecao.ColecaoID;
import com.sistran.fastclaims.domain.colecaocampo.*;
import com.sistran.fastclaims.domain.dominio.DominioID;
import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.operador.Operador;
import com.sistran.fastclaims.domain.operador.OperadorGateway;
import com.sistran.fastclaims.domain.operador.OperadorID;
import com.sistran.fastclaims.domain.operador.TipoOperador;
import com.sistran.fastclaims.domain.regra.Regra;
import com.sistran.fastclaims.domain.regra.RegraGateway;
import com.sistran.fastclaims.domain.regra.TipoAcao;
import com.sistran.fastclaims.domain.trilha.RegraTrilhaPreview;
import com.sistran.fastclaims.domain.trilha.Trilha;
import com.sistran.fastclaims.domain.trilha.TrilhaGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CadastrarRegraTrilhaUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCadastrarRegraTrilhaUseCase useCase;

    @Mock
    private TrilhaGateway trilhaGateway;

    @Mock
    private RegraGateway regraGateway;

    @Mock
    private ColecaoCampoGateway colecaoCampoGateway;

    @Mock
    private OperadorGateway operadorGateway;


    @Override
    protected List<Object> getMocks() {
        return List.of(trilhaGateway, regraGateway, colecaoCampoGateway, operadorGateway);
    }

    @Test
    public void informadoUmComandoValidoComUmaRegraExistente_quandoChamadoOMetodoCadastrarRegraTrilha_deveCadastrarRegraTrilha() {

        final var regra = Regra.novaRegra(
                "Regra Teste",
                "Teste",
                ColecaoCampoID.from("123"),
                OperadorID.from(""),
                null,
                OperadorID.from("123"),
                "Texto Livre"
        );

        when(regraGateway.cadastrar(any()))
                .thenReturn(regra);

        when(regraGateway.pesquisarPorId(regra.getId()))
                .thenReturn(Optional.of(regra));

        final var trilha = Trilha.novaTrilha("Trilha Geral", "Descriçao da Trilha", null);

        final var command = CadastrarRegraTrilhaCommand.aPartirDe(
                regra.getId().getValue(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                true,
                TipoAcao.ANALISAR.getNome(),
                true,
                trilha.getId().getValue()
        );

        when(trilhaGateway.pesquisarPorId(trilha.getId()))
                .thenReturn(Optional.of(Trilha.com(trilha)));

        when(trilhaGateway.cadastrarRegraTrilha(any(), any(), any()))
                .thenReturn(RegraTrilhaPreview.aPartirDe(regra.getId().getValue(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        true,
                        TipoAcao.ANALISAR.getNome(),
                        true));

        useCase.executar(command);

        final var regraAtualizada = regraGateway.pesquisarPorId(regra.getId()).get();
        final var trilhaAtualizada = trilhaGateway.pesquisarPorId(trilha.getId()).get();

        Assertions.assertEquals(regraAtualizada.getTrilhas().size(), 1);
        Assertions.assertEquals(regraAtualizada.getTrilhas().get(0), trilha.getId());
        Assertions.assertEquals(trilhaAtualizada.getRegras().size(), 1);
        Assertions.assertEquals(trilhaAtualizada.getRegras().get(0).id(), regra.getId());
        Assertions.assertTrue(trilhaAtualizada.getRegras().get(0).ativa());
    }

    @Test
    public void informadoUmTipoAcaoInvalidoComUmaRegraExistente_quandoChamadoOMetodoCadastrarRegraTrilha_deveRetornarUmaException() {

        final var regra = Regra.novaRegra(
                "Regra Teste",
                "Teste",
                ColecaoCampoID.from("123"),
                OperadorID.from(""),
                null,
                OperadorID.from("123"),
                "Texto Livre"
        );

        when(regraGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(regra));

        final var trilha = Trilha.novaTrilha("Trilha Geral", "Descriçao da Trilha", null);

        when(trilhaGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(trilha));

        final var command = CadastrarRegraTrilhaCommand.aPartirDe(
                regra.getId().getValue(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                true,
                "Continuar",
                true,
                trilha.getId().getValue()
        );

        final var erros = 1;
        final var mensagemErroEsperada = "Tipo de ação nulo ou inválido.";

        final var exception = Assertions.assertThrows(DomainException.class, () -> useCase.executar(command));

        Assertions.assertEquals(exception.getErrors().size(), erros);
        Assertions.assertEquals(exception.getErrors().get(0).message(), mensagemErroEsperada);
    }

    @Test
    public void informadoUmTipoAcaoNuloComUmaRegraExistente_quandoChamadoOMetodoCadastrarRegraTrilha_deveRetornarUmaException() {

        final var regra = Regra.novaRegra(
                "Regra Teste",
                "Teste",
                ColecaoCampoID.from("123"),
                OperadorID.from(""),
                null,
                OperadorID.from("123"),
                "Texto Livre"
        );

        when(regraGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(regra));

        final var trilha = Trilha.novaTrilha("Trilha Geral", "Descriçao da Trilha", null);

        when(trilhaGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(trilha));

        final var command = CadastrarRegraTrilhaCommand.aPartirDe(
                regra.getId().getValue(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                true,
                null,
                true,
                trilha.getId().getValue()
        );

        final var erros = 1;
        final var mensagemErroEsperada = "Tipo de ação nulo ou inválido.";

        final var exception = Assertions.assertThrows(DomainException.class, () -> useCase.executar(command));

        Assertions.assertEquals(exception.getErrors().size(), erros);
        Assertions.assertEquals(exception.getErrors().get(0).message(), mensagemErroEsperada);
    }

    @Test
    public void informandoUmComandoValidoCriandoUmaRegraNova_quandoChamadoOMetodoCadastrarRegraTrilha_deveCadastrarUmaRegraEUmaRegraTrilha() {

        final var trilha = Trilha.novaTrilha("Trilha Geral", "Descriçao da Trilha", null);

        when(trilhaGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(Trilha.com(trilha)));

        final var operadorDois = OperadorID.from("123");

        when(operadorGateway.pesquisarPorId(operadorDois))
                .thenReturn(Optional.of(Operador.novoOperador("Operador Teste", "Teste", TipoOperador.RELACIONAL)));

        final var campoUm = ColecaoCampoID.from("123");

        when(colecaoCampoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(novaColecaoCampo()));

        final var command = CadastrarRegraTrilhaCommand.aPartirDe(
                null,
                "Regra Teste",
                "Teste",
                campoUm.getValue(),
                "",
                null,
                operadorDois.getValue(),
                "Texto Livre",
                true,
                TipoAcao.ANALISAR.getNome(),
                true,
                trilha.getId().getValue()
        );

        when(regraGateway.cadastrar(any()))
                .thenReturn(Regra.novaRegra(
                        "Regra Teste",
                        "Teste",
                        campoUm,
                        operadorDois,
                        null,
                        operadorDois,
                        "Texto Livre"
                ));

        when(trilhaGateway.cadastrarRegraTrilha(any(), any(), any()))
                .thenReturn(RegraTrilhaPreview.aPartirDe(
                        "123",
                        "Regra Teste",
                        "Teste",
                        "campo",
                        "alias",
                        TipoDado.ALFA.name(),
                        TipoFormato.ASTERISCO.getValue(),
                        "rastro",
                        true,
                        TipoAcao.ANALISAR.getNome(),
                        true
                ));

        final var saida = useCase.executar(command);

        final var trilhaAtualizada = trilhaGateway.pesquisarPorId(trilha.getId()).get();

        Assertions.assertEquals(trilhaAtualizada.getRegras().size(), 1);
        Assertions.assertTrue(trilhaAtualizada.getRegras().get(0).ativa());
        Assertions.assertEquals(trilhaAtualizada.getRegras().get(0).tipoAcao(), TipoAcao.ANALISAR);
    }

    private static ColecaoCampo novaColecaoCampo() {
        return ColecaoCampo.novaColecaoCampo("campo", "alias", TipoDado.ALFA, TipoFormato.ASTERISCO,
                ColecaoID.from("rastro"), DominioID.from("123"), "123");
    }
}
