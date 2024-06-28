package com.sistran.fastclaims.application.dominio.pesquisar.id;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PesquisarDominioPorIdUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultPesquisarDominioPorIdUseCase pesquisarDominioPorIdUseCase;

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
    void informadoUmIdValido_quandoChamadoOMetodoPesquisarDominioPorId_deveRetornarUmDominio() {
        final var valoresEsperados = listaDominioValor();
        final var dominio = Dominio.novoDominio("Dominio", "Descricao", valoresEsperados);

        final var idEsperado = dominio.getId();

        when(dominioGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(dominio.clone()));

        final var saida = pesquisarDominioPorIdUseCase.executar(idEsperado.getValue());

        assertNotNull(saida);
        assertNotNull(saida.get().id());
        assertEquals(idEsperado, saida.get().id());

    }

    @Test
    void informadoUmIdInvalido_quandoChamadoOMetodoPesquisarDominioPorId_deveRetornarUmDominioNaoEncontrado() {
        final var idEsperado = "123";
        when(dominioGateway.pesquisarPorId(any()))
                .thenReturn(Optional.empty());

        final var excecao = Assertions.assertThrows(
                NotFoundException.class,
                () -> pesquisarDominioPorIdUseCase.executar(idEsperado)
        );

        assertEquals("Dominio de código 123 inexistente", excecao.getMessage());
    }

    @Test
    void informadoUmIdVazio_quandoChamadoOMetodoPesquisarDominioPorId_deveRetornarUmDominioNaoEncontrado() {
        final var idEsperado = "";
        when(dominioGateway.pesquisarPorId(any()))
                .thenReturn(Optional.empty());

        final var excecao = Assertions.assertThrows(
                NotFoundException.class,
                () -> pesquisarDominioPorIdUseCase.executar(idEsperado)
        );

        assertEquals("Dominio de código  inexistente", excecao.getMessage());
    }

}
