package com.sistran.fastclaims.application.dominio.pesquisar.lista;

import com.sistran.fastclaims.application.UseCaseTest;
import com.sistran.fastclaims.application.dominio.pesquisar.nome.DefaultPesquisarDominioPorNomeUseCase;
import com.sistran.fastclaims.domain.dominio.Dominio;
import com.sistran.fastclaims.domain.dominio.DominioGateway;
import com.sistran.fastclaims.domain.dominiovalor.DominioValor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PesquisarDominioPorNomeUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultPesquisarDominioPorNomeUseCase pesquisarDominioPorNomeUseCase;

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
    void informadoUmNomeValido_quandoChamadoOMetodoPesquisarDominioPorNome_deveRetornarUmDominio() {
        final var dominio = Dominio.novoDominio("Dominio", "Descricao", listaDominioValor());

        final var nomeEsperado = dominio.getNome();

        when(dominioGateway.pesquisarPorNome(any()))
                .thenReturn(Optional.of(dominio));

        final var saida = pesquisarDominioPorNomeUseCase.executar(nomeEsperado);

        assertNotNull(saida);
        assertNotNull(saida.get().nome());
        assertEquals(nomeEsperado, saida.get().nome());

    }

    @Test
    void informadoUmNomeInvalido_quandoChamadoOMetodoPesquisarDominioPorNome_deveRetornarUmDominioNaoEncontrado() {
        final var nomeEsperado = "Cidade";

        when(dominioGateway.pesquisarPorNome(any()))
                .thenReturn(Optional.empty());

        final var excecao = Assertions.assertThrows(
                RuntimeException.class,
                () -> pesquisarDominioPorNomeUseCase.executar(nomeEsperado)
        );

        assertEquals("Dominio com nome Cidade inexistente", excecao.getMessage());

    }

    @Test
    void informadoUmNomeVazio_quandoChamadoOMetodoPesquisarDominioPorNome_deveRetornarUmDominioNaoEncontrado() {
        final var nomeEsperado = "";

        when(dominioGateway.pesquisarPorNome(any()))
                .thenReturn(Optional.empty());

        final var excecao = Assertions.assertThrows(
                RuntimeException.class,
                () -> pesquisarDominioPorNomeUseCase.executar(nomeEsperado)
        );

        assertEquals("Dominio com nome  inexistente", excecao.getMessage());
    }


}
