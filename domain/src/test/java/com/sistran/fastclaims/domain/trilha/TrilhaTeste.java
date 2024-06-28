package com.sistran.fastclaims.domain.trilha;

import com.sistran.fastclaims.domain.UnitTest;
import com.sistran.fastclaims.domain.fluxo.FluxoID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TrilhaTeste extends UnitTest {

    @Test
    public void informadoUmaTrilhaAtiva_quandoChamadoOMetodoDesativar_deveDesativarATrilha() {

        final var nomeEsperado = "Trilha Geral";
        final var descricaoEsperada = "Descriçao da Trilha";
        final var fluxoIdEsperado = FluxoID.unique();

        final var trilha = Trilha.novaTrilha(nomeEsperado, descricaoEsperada, fluxoIdEsperado);

        Assertions.assertNotNull(trilha);
        Assertions.assertTrue(trilha.isAtivo());
        Assertions.assertNotNull(trilha.getNome());
        Assertions.assertNotNull(trilha.getDescricao());
        Assertions.assertNotNull(trilha.getDataCadastro());
        Assertions.assertNotNull(trilha.getDataAlteracao());
        Assertions.assertNotNull(trilha.getFluxoId());

        final var dataCadastroAtual = trilha.getDataCadastro();
        final var dataAlteracaoAtual = trilha.getDataAlteracao();

        trilha.desativar();

        Assertions.assertEquals(nomeEsperado, trilha.getNome());
        Assertions.assertEquals(descricaoEsperada, trilha.getDescricao());
        Assertions.assertEquals(dataCadastroAtual, trilha.getDataCadastro());
        Assertions.assertFalse(trilha.isAtivo());
        Assertions.assertFalse(dataAlteracaoAtual.isBefore(trilha.getDataAlteracao()));
    }

    @Test
    public void informadoUmaTrilhaDesativada_quandoChamadoOMetodoAtivar_deveAtivarATrilha() {

        final var nomeEsperado = "Trilha Geral";
        final var descricaoEsperada = "Descriçao da Trilha";
        final var fluxoIdEsperado = FluxoID.unique();
        final var ativo = true;

        final var trilha = Trilha.novaTrilha(nomeEsperado, descricaoEsperada, false, fluxoIdEsperado);

        Assertions.assertNotNull(trilha);
        Assertions.assertFalse(trilha.isAtivo());
        Assertions.assertNotNull(trilha.getNome());
        Assertions.assertNotNull(trilha.getDescricao());
        Assertions.assertNotNull(trilha.getDataCadastro());
        Assertions.assertNotNull(trilha.getDataAlteracao());
        Assertions.assertNotNull(trilha.getFluxoId());

        final var dataCadastroAtual = trilha.getDataCadastro();
        final var dataAlteracaoAtual = trilha.getDataAlteracao();

        trilha.ativar();

        Assertions.assertEquals(ativo, trilha.isAtivo());
        Assertions.assertEquals(nomeEsperado, trilha.getNome());
        Assertions.assertEquals(descricaoEsperada, trilha.getDescricao());
        Assertions.assertEquals(dataCadastroAtual, trilha.getDataCadastro());
        Assertions.assertNotEquals(dataAlteracaoAtual, trilha.getDataAlteracao());
        Assertions.assertTrue(trilha.isAtivo());
        Assertions.assertTrue(dataAlteracaoAtual.isBefore(trilha.getDataAlteracao()));
    }
}
