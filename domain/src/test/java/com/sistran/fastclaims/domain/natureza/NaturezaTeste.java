package com.sistran.fastclaims.domain.natureza;

import com.sistran.fastclaims.domain.UnitTest;
import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NaturezaTeste extends UnitTest {
    @Test
    public void informandoParametrosValidos_quandoChamadoNovaNatureza_deveInstanciarUmaNatureza() {
        final var nome = "algumaNatureza";
        final var codigoNatureza = "123";

        final var natureza = Natureza.novaNatureza(nome, codigoNatureza);

        Assertions.assertNotNull(natureza.getId());
        Assertions.assertNotNull(natureza.getNome());
        Assertions.assertNotNull(natureza.getCodigoNatureza());
        Assertions.assertEquals(natureza.getNome(), nome);
        Assertions.assertEquals(natureza.getCodigoNatureza(), codigoNatureza);
    }

    @Test
    public void informandoNomeECodigoNaturezaNulos_quandoChamadoNovaNatureza_deveRetornarUmErro() {
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'nome' e 'codigoNatureza' não podem ser nulos!";


        final var natureza = Natureza.novaNatureza(null, null);


        final var excecao = Assertions.assertThrows(DomainException.class, () -> natureza.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(errosEsperados, excecao.getErrors().size());
        Assertions.assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());
    }

    @Test
    public void informandoNomeNulo_quandoChamadoNovaNatureza_deveRetornarUmErro() {
        final var codigoNatureza = "123";
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'nome' não pode ser nulo!";


        final var natureza = Natureza.novaNatureza(null, codigoNatureza);


        final var excecao = Assertions.assertThrows(DomainException.class, () -> natureza.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(errosEsperados, excecao.getErrors().size());
        Assertions.assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());
    }

    @Test
    public void informandoCodigoNaturezaNulo_quandoChamadoNovaNatureza_deveRetornarUmErro() {
        final var nome = "algumaNatureza";
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'codigoNatureza' não pode ser nulo!";


        final var natureza = Natureza.novaNatureza(nome, null);


        final var excecao = Assertions.assertThrows(DomainException.class, () -> natureza.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(errosEsperados, excecao.getErrors().size());
        Assertions.assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());
    }

    @Test
    public void informandoNomeECodigoNaturezaVazios_quandoChamadoNovaNatureza_deveRetornarUmErro() {
        final var nome = "";
        final var codigoNatureza = "";
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'nome' e 'codigoNatureza' não podem ser vazios!";


        final var natureza = Natureza.novaNatureza(nome, codigoNatureza);


        final var excecao = Assertions.assertThrows(DomainException.class, () -> natureza.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(errosEsperados, excecao.getErrors().size());
        Assertions.assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());
    }

    @Test
    public void informandoNomeVazio_quandoChamadoNovaNatureza_deveRetornarUmErro() {
        final var nome = "";
        final var codigoNatureza = "123";
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'nome' não pode ser vazio!";


        final var natureza = Natureza.novaNatureza(nome, codigoNatureza);


        final var excecao = Assertions.assertThrows(DomainException.class, () -> natureza.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(errosEsperados, excecao.getErrors().size());
        Assertions.assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());
    }

    @Test
    public void informandoCodigoNaturezaVazio_quandoChamadoNovaNatureza_deveRetornarUmErro() {
        final var nome = "AlgumaNatureza";
        final var codigoNatureza = "";
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'codigoNatureza' não pode ser vazio!";


        final var natureza = Natureza.novaNatureza(nome, codigoNatureza);


        final var excecao = Assertions.assertThrows(DomainException.class, () -> natureza.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(errosEsperados, excecao.getErrors().size());
        Assertions.assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());
    }

    @Test
    public void informandoCodigoNaturezaNaoNumerico_quandoChamadoNovaNatureza_deveRetornarUmErro() {
        final var nome = "AlgumaNatureza";
        final var codigoNatureza = "NaoNumerico";
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'codigoNatureza' deve ser um número!";


        final var natureza = Natureza.novaNatureza(nome, codigoNatureza);


        final var excecao = Assertions.assertThrows(DomainException.class, () -> natureza.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(errosEsperados, excecao.getErrors().size());
        Assertions.assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());
    }

}
