package com.sistran.fastclaims.domain.fluxo;

import com.sistran.fastclaims.domain.UnitTest;
import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.natureza.NaturezaID;
import com.sistran.fastclaims.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FluxoTeste extends UnitTest {

    @Test
    public void informadoParametrosValidos_quandoChamadoOCadastrodeUmFluxo_deveInstanciarUmFluxo() {
        final var descricao = "Cobertura de cancelamento";
        final var naturezaId = NaturezaID.aPartirDe("123");

        final var fluxo = Fluxo.novoFluxo(descricao, naturezaId);

        Assertions.assertNotNull(fluxo.getDescricao());
        Assertions.assertNotNull(fluxo.getId());
        Assertions.assertNotNull(fluxo.getDataCadastro());
        Assertions.assertNotNull(fluxo.getNaturezaId());
        Assertions.assertEquals(descricao, fluxo.getDescricao());
    }

    @Test
    public void informadaUmaDescricaoNula_quandoChamadoOCadastrodeUmFluxo_deveRetornarUmErro() {
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'descrição' não pode ser nula!";
        final var naturezaId = NaturezaID.aPartirDe("123");

        final var fluxo = Fluxo.novoFluxo(null, naturezaId);

        final var excecao = Assertions.assertThrows(DomainException.class, () -> fluxo.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(errosEsperados, excecao.getErrors().size());
        Assertions.assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());
    }

    @Test
    public void informadaUmaNaturezaNula_quandoChamadoOCadastrodeUmFluxo_deveRetornarUmErro() {
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'naturezaId' não pode ser nulo!";
        final var descricao = "descricao";

        final var fluxo = Fluxo.novoFluxo(descricao, null);

        final var excecao = Assertions.assertThrows(DomainException.class, () -> fluxo.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(errosEsperados, excecao.getErrors().size());
        Assertions.assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());
    }
}

