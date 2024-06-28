package com.sistran.fastclaims.application.dominiovalor.cadastrar;

import com.sistran.fastclaims.application.UseCaseTest;
import com.sistran.fastclaims.domain.dominio.Dominio;
import com.sistran.fastclaims.domain.dominio.DominioGateway;
import com.sistran.fastclaims.domain.dominiovalor.DominioValor;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CadastrarDominioValorUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCadastrarDominioValorUseCase cadastrarDominioValorUseCase;

    @Mock
    private DominioGateway dominioGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(dominioGateway);
    }

    private List<DominioValor> listaDominioValor() {
        List<DominioValor> listaDominioValor = new ArrayList<>();
        DominioValor dominioValor = DominioValor.novoDominioValor("codigo", "nomeValor", null);
        listaDominioValor.add(dominioValor);
        return listaDominioValor;
    }

    @Test
    void informadoDominioValorValido_quandoChamdoMetodoCadastrarDominioValor_deveRetornarDominioValor() {
        final var dominio = Dominio.novoDominio("nome", "descricao", listaDominioValor());
        final var command = CadastrarDominioValorCommand.com(dominio.getId(), "codigo2", "nomeValor2");

        when(dominioGateway.pesquisarPorId(any())).thenReturn(Optional.of(dominio));
        when(dominioGateway.adicionarValor(dominio)).thenReturn(dominio);

        final var saida = cadastrarDominioValorUseCase.executar(command);

        assertNotNull(saida);
    }

    @Test
    void informadoDominioValorSemCodigoValor_quandoChamdoMetodoCadastrarDominioValor_deveRetornarUmaNotificacao() {
        final var dominio = Dominio.novoDominio("nome", "descricao", listaDominioValor());
        final var command = CadastrarDominioValorCommand.com(dominio.getId(), "", "nomeValor2");
        final var errosEsperados = 1;
        final var descricaoErroEsperada = "'codigoValor' não pode ser vazio";

        final var saida = cadastrarDominioValorUseCase.executar(command).getLeft();

        assertEquals(errosEsperados, saida.getErrors().size());
        assertEquals(descricaoErroEsperada, saida.firstError().message());

        verify(dominioGateway, Mockito.never()).adicionarValor(any());

    }

    @Test
    void informadoDominioValorSemNomeValor_quandoChamdoMetodoCadastrarDominioValor_deveRetornarUmaNotificacao() {
        final var dominio = Dominio.novoDominio("nome", "descricao", listaDominioValor());
        final var command = CadastrarDominioValorCommand.com(dominio.getId(), "codigo2", "");
        final var errosEsperados = 1;
        final var descricaoErroEsperada = "'nomeValor' não pode ser vazio";

        final var saida = cadastrarDominioValorUseCase.executar(command).getLeft();

        assertEquals(errosEsperados, saida.getErrors().size());
        assertEquals(descricaoErroEsperada, saida.firstError().message());

        verify(dominioGateway, Mockito.never()).adicionarValor(any());

    }

    @Test
    void informadoDominioValorComCodigoValorComMaisDe40Caracteres_quandoChamdoMetodoCadastrarDominioValor_deveRetornarUmaNotificacao() {
        final var dominio = Dominio.novoDominio("nome", "descricao", listaDominioValor());
        final var command = CadastrarDominioValorCommand.com(dominio.getId(), "a".repeat(41), "nomeValor2");
        final var errosEsperados = 1;
        final var descricaoErroEsperada = "'codigoValor' dever ter entre 2 e 40 caracteres";

        final var saida = cadastrarDominioValorUseCase.executar(command).getLeft();

        assertEquals(errosEsperados, saida.getErrors().size());
        assertEquals(descricaoErroEsperada, saida.firstError().message());

        verify(dominioGateway, Mockito.never()).adicionarValor(any());

    }

    @Test
    void informadoDominioValorComCodigoValorComMenosDe2Caracteres_quandoChamdoMetodoCadastrarDominioValor_deveRetornarUmaNotificacao() {
        final var dominio = Dominio.novoDominio("nome", "descricao", listaDominioValor());
        final var command = CadastrarDominioValorCommand.com(dominio.getId(), "a", "nomeValor2");
        final var errosEsperados = 1;
        final var descricaoErroEsperada = "'codigoValor' dever ter entre 2 e 40 caracteres";

        final var saida = cadastrarDominioValorUseCase.executar(command).getLeft();

        assertEquals(errosEsperados, saida.getErrors().size());
        assertEquals(descricaoErroEsperada, saida.firstError().message());

        verify(dominioGateway, Mockito.never()).adicionarValor(any());

    }

    @Test
    void informadoDominioValorComNomeValorComMaisDe40Caracteres_quandoChamdoMetodoCadastrarDominioValor_deveRetornarUmaNotificacao() {
        final var dominio = Dominio.novoDominio("nome", "descricao", listaDominioValor());
        final var command = CadastrarDominioValorCommand.com(dominio.getId(), "codigo2", "a".repeat(41));
        final var errosEsperados = 1;
        final var descricaoErroEsperada = "'nomeValor' dever ter entre 2 e 40 caracteres";

        final var saida = cadastrarDominioValorUseCase.executar(command).getLeft();

        assertEquals(errosEsperados, saida.getErrors().size());
        assertEquals(descricaoErroEsperada, saida.firstError().message());

        verify(dominioGateway, Mockito.never()).adicionarValor(any());

    }

    @Test
    void informadoDominioValorComNomeValorComMenosDe2Caracteres_quandoChamdoMetodoCadastrarDominioValor_deveRetornarUmaNotificacao() {
        final var dominio = Dominio.novoDominio("nome", "descricao", listaDominioValor());
        final var command = CadastrarDominioValorCommand.com(dominio.getId(), "codigo2", "a");
        final var errosEsperados = 1;
        final var descricaoErroEsperada = "'nomeValor' dever ter entre 2 e 40 caracteres";

        final var saida = cadastrarDominioValorUseCase.executar(command).getLeft();

        assertEquals(errosEsperados, saida.getErrors().size());
        assertEquals(descricaoErroEsperada, saida.firstError().message());

        verify(dominioGateway, Mockito.never()).adicionarValor(any());

    }

    @Test
    void informadoDominioNaoEncontrado_quandoChamdoMetodoCadastrarDominioValor_deveRetornarUmaNotificacao() {
        final var dominio = Dominio.novoDominio("nome", "descricao", listaDominioValor());
        final var command = CadastrarDominioValorCommand.com(dominio.getId(), "codigo2", "nomeValor2");
        final var descricaoErroEsperada = "Dominio de código " + dominio.getId().getValue() + " inexistente";

        when(dominioGateway.pesquisarPorId(any())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> cadastrarDominioValorUseCase.executar(command).getLeft());

        assertEquals(descricaoErroEsperada, exception.getMessage());

        verify(dominioGateway, Mockito.never()).adicionarValor(any());

    }

    @Test
    void informadoUmDominioValorComCodigoJaExistente_quandoChamdoMetodoCadastrarDominioValor_deveRetornarUmaNotificacao() {
        final var dominio = Dominio.novoDominio("nome", "descricao", listaDominioValor());
        final var command = CadastrarDominioValorCommand.com(dominio.getId(), "codigo", "nomeValor2");
        final var descricaoErroEsperada = "'codigoValor' codigo já existente";

        when(dominioGateway.pesquisarPorId(any())).thenReturn(Optional.of(dominio));

        final var saida = cadastrarDominioValorUseCase.executar(command).getLeft();

        assertEquals(descricaoErroEsperada, saida.firstError().message());

        verify(dominioGateway, Mockito.never()).adicionarValor(any());

    }
}
