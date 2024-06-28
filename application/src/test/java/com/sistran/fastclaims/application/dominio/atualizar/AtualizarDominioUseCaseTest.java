package com.sistran.fastclaims.application.dominio.atualizar;

import com.sistran.fastclaims.application.UseCaseTest;
import com.sistran.fastclaims.domain.dominio.Dominio;
import com.sistran.fastclaims.domain.dominio.DominioGateway;
import com.sistran.fastclaims.domain.dominiovalor.DominioValor;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AtualizarDominioUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultAtualizarDominioUseCase atualizarDominioUseCase;

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
    void informadoUmDominioValido_quandoChamadoOMetodoAtualizarDominio_deveAtualizarODominio() {
        final var dominio = Dominio.novoDominio("Dominio", "Descricao", listaDominioValor());
        final var atualizarDominioCommand = AtualizarDominioCommand.com(dominio.getId().toString(), "Dominio", "Descricao");

        when(dominioGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(dominio));
        when(dominioGateway.atualizar(any()))
                .thenReturn(dominio);


        final var saida = atualizarDominioUseCase.executar(atualizarDominioCommand);

        assertNotNull(saida);
    }

    @Test
    void informadoNomeDoDominioVazio_quandoChamadoOMetodoAtualizarDominio_deveRetornarUmaNotificacao() {
        final var dominio = Dominio.novoDominio(" ", "Descricao", listaDominioValor());
        final var atualizarDominioCommand = AtualizarDominioCommand.com(dominio.getId().toString(), dominio.getNome(), dominio.getDescricao());
        final var descricaoErroEsperada = "'nome' não pode ser vazio";
        final var errosEsperados = 1;

        when(dominioGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(dominio));

        when(dominioGateway.pesquisarPorNome(any()))
                .thenReturn(Optional.empty());

        final var saida = atualizarDominioUseCase.executar(atualizarDominioCommand).getLeft();

        assertEquals(errosEsperados, saida.getErrors().size());
        assertEquals(descricaoErroEsperada, saida.firstError().message());

        verify(dominioGateway, times(0)).atualizar(any());
    }

    @Test
    void informadoNomeDoDominioComMaisDe40Caracteres_quandoChamadoOMetodoAtualizarDominio_deveRetornarUmaNotificacao() {
        final var dominio = Dominio.novoDominio("a".repeat(41), "Descricao", listaDominioValor());
        final var atualizarDominioCommand = AtualizarDominioCommand.com(dominio.getId().toString(), dominio.getNome(), dominio.getDescricao());
        final var descricaoErroEsperada = "'nome' dever ter entre 2 e 40 caracteres";
        final var errosEsperados = 1;

        when(dominioGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(dominio));

        when(dominioGateway.pesquisarPorNome(any()))
                .thenReturn(Optional.empty());

        final var saida = atualizarDominioUseCase.executar(atualizarDominioCommand).getLeft();

        assertEquals(errosEsperados, saida.getErrors().size());
        assertEquals(descricaoErroEsperada, saida.firstError().message());

        verify(dominioGateway, times(0)).atualizar(any());
    }

    @Test
    void informadoNomeDoDominioComMenosDe2Caracteres_quandoChamadoOMetodoAtualizarDominio_deveRetornarUmaNotificacao() {
        final var dominio = Dominio.novoDominio("a", "Descricao", listaDominioValor());
        final var atualizarDominioCommand = AtualizarDominioCommand.com(dominio.getId().toString(), dominio.getNome(), dominio.getDescricao());
        final var descricaoErroEsperada = "'nome' dever ter entre 2 e 40 caracteres";
        final var errosEsperados = 1;

        when(dominioGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(dominio));

        when(dominioGateway.pesquisarPorNome(any()))
                .thenReturn(Optional.empty());

        final var saida = atualizarDominioUseCase.executar(atualizarDominioCommand).getLeft();

        assertEquals(errosEsperados, saida.getErrors().size());
        assertEquals(descricaoErroEsperada, saida.firstError().message());

        verify(dominioGateway, times(0)).atualizar(any());
    }

    @Test
    void informadoNomeDoDominioJaExistente_quandoChamadoOMetodoAtualizarDominio_deveRetornarUmaNotificacao() {
        final var dominio = Dominio.novoDominio("Dominio", "Descricao", listaDominioValor());
        final var atualizarDominioCommand = AtualizarDominioCommand.com(dominio.getId().toString(), dominio.getNome(), dominio.getDescricao());
        final var descricaoErroEsperada = "Nome do domínio já existe";
        final var errosEsperados = 1;

        when(dominioGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(dominio));

        when(dominioGateway.pesquisarPorNome(any()))
                .thenReturn(Optional.of(dominio));

        final var saida = atualizarDominioUseCase.executar(atualizarDominioCommand).getLeft();

        assertEquals(errosEsperados, saida.getErrors().size());
        assertEquals(descricaoErroEsperada, saida.firstError().message());

        verify(dominioGateway, times(0)).atualizar(any());
    }

    @Test
    void informadoDescricaoDoDominioVazia_quandoChamadoOMetodoAtualizarDominio_deveRetornarUmaNotificacao() {
        final var dominio = Dominio.novoDominio("Dominio", " ", listaDominioValor());
        final var atualizarDominioCommand = AtualizarDominioCommand.com(dominio.getId().toString(), dominio.getNome(), dominio.getDescricao());
        final var descricaoErroEsperada = "'descricao' não pode ser vazia";
        final var errosEsperados = 1;

        when(dominioGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(dominio));

        when(dominioGateway.pesquisarPorNome(any()))
                .thenReturn(Optional.empty());

        final var saida = atualizarDominioUseCase.executar(atualizarDominioCommand).getLeft();

        assertEquals(errosEsperados, saida.getErrors().size());
        assertEquals(descricaoErroEsperada, saida.firstError().message());

        verify(dominioGateway, times(0)).atualizar(any());
    }

    @Test
    void informadoDescricaoDoDominioComMaisDe40Caracteres_quandoChamadoOMetodoAtualizarDominio_deveRetornarUmaNotificacao() {
        final var dominio = Dominio.novoDominio("Dominio", "a".repeat(41), listaDominioValor());
        final var atualizarDominioCommand = AtualizarDominioCommand.com(dominio.getId().toString(), dominio.getNome(), dominio.getDescricao());
        final var descricaoErroEsperada = "'descricao' dever ter entre 2 e 40 caracteres";
        final var errosEsperados = 1;

        when(dominioGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(dominio));

        when(dominioGateway.pesquisarPorNome(any()))
                .thenReturn(Optional.empty());

        final var saida = atualizarDominioUseCase.executar(atualizarDominioCommand).getLeft();

        assertEquals(errosEsperados, saida.getErrors().size());
        assertEquals(descricaoErroEsperada, saida.firstError().message());

        verify(dominioGateway, times(0)).atualizar(any());
    }

    @Test
    void informadoDescricaoDoDominioComMenosDe2Caracteres_quandoChamadoOMetodoAtualizarDominio_deveRetornarUmaNotificacao() {
        final var dominio = Dominio.novoDominio("Dominio", "a", listaDominioValor());
        final var atualizarDominioCommand = AtualizarDominioCommand.com(dominio.getId().toString(), dominio.getNome(), dominio.getDescricao());
        final var descricaoErroEsperada = "'descricao' dever ter entre 2 e 40 caracteres";
        final var errosEsperados = 1;

        when(dominioGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(dominio));

        when(dominioGateway.pesquisarPorNome(any()))
                .thenReturn(Optional.empty());

        final var saida = atualizarDominioUseCase.executar(atualizarDominioCommand).getLeft();

        assertEquals(errosEsperados, saida.getErrors().size());
        assertEquals(descricaoErroEsperada, saida.firstError().message());

        verify(dominioGateway, times(0)).atualizar(any());
    }

    @Test
    void informadoDominioNaoEncontrado_quandoChamadoOMetodoAtualizarDominio_deveRetornarUmaNotificacao() {
        final var dominio = Dominio.novoDominio("Dominio", "Descricao", listaDominioValor());
        final var dominioId = dominio.getId().getValue();
        final var atualizarDominioCommand = AtualizarDominioCommand.com(dominioId, dominio.getNome(),
                dominio.getDescricao());
        final var mensagemErroEsperada = "Dominio de código " + dominioId + " inexistente";

        when(dominioGateway.pesquisarPorId(any()))
                .thenReturn(Optional.empty());

        final var excecao = assertThrows(NotFoundException.class,
                () -> atualizarDominioUseCase.executar(atualizarDominioCommand));

        Assertions.assertEquals(mensagemErroEsperada, excecao.getMessage());
        verify(dominioGateway, never()).atualizar(any());

    }
}
