package com.sistran.fastclaims.domain.dominiovalor;

import com.sistran.fastclaims.domain.UnitTest;
import com.sistran.fastclaims.domain.dominio.DominioID;
import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DominioValorTeste extends UnitTest {

    @Test
    void informadoNomeComParametrosValidos_quandoChamadoOCadastrodeUmDominioValor_deveInstanciarUmDominioValor() {
        final var nomeValor = "DominioValor";
        final var codigoValor = "DV";
        final var dominioID = DominioID.unique();

        final var dominioValor = DominioValor.novoDominioValor(codigoValor, nomeValor, dominioID);

        assertNotNull(dominioValor.getCodigoValor());
        assertNotNull(dominioValor.getId());
        assertNotNull(dominioValor.getDataCadastro());
        assertNotNull(dominioValor.getNomeValor());
        assertNotNull(dominioValor.getDominioID());

    }

    @Test
    void informadoUmNomeNulo_quandoChamadoOCadastrodeUmDominioValor_deveRetornarUmErro() {
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'nomeValor' não pode ser nulo!";
        final var codigoValor = "DV";
        final var dominioID = DominioID.unique();

        final var dominioValor = DominioValor.novoDominioValor(codigoValor, null, dominioID);

        final var excecao = assertThrows(DomainException.class, () -> dominioValor.validate(new ThrowsValidationHandler()));

        assertEquals(errosEsperados, excecao.getErrors().size());
        assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());

    }

    @Test
    void informadoUmNomeComMenosDe2Caracteres_quandoChamadoOCadastrodeUmDominioValor_deveRetornarUmErro() {
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'nomeValor' dever ter entre 2 e 40 caracteres";
        final var codigoValor = "DV";
        final var dominioID = DominioID.unique();

        final var dominioValor = DominioValor.novoDominioValor(codigoValor, "AB", dominioID);

        final var excecao = assertThrows(DomainException.class, () -> dominioValor.validate(new ThrowsValidationHandler()));

        assertEquals(errosEsperados, excecao.getErrors().size());
        assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());

    }

    @Test
    void informadoumNomeComMaisDe40Caracteres_quandoChamadoOCadastrodeUmDominioValor_deveRetornarUmErro() {
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'nomeValor' dever ter entre 2 e 40 caracteres";
        final var codigoValor = "DV";
        final var dominioID = DominioID.unique();

        final var dominioValor = DominioValor.novoDominioValor(codigoValor, "AB".repeat(21), dominioID);

        final var excecao = assertThrows(DomainException.class, () -> dominioValor.validate(new ThrowsValidationHandler()));

        assertEquals(errosEsperados, excecao.getErrors().size());
        assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());

    }

    @Test
    void informadoUmNomeVazio_quandoChamadoOCadastrodeUmDominioValor_deveRetornarUmErro() {
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'nomeValor' não pode ser vazio";
        final var codigoValor = "DV";
        final var dominioID = DominioID.unique();

        final var dominioValor = DominioValor.novoDominioValor(codigoValor, "", dominioID);

        final var excecao = assertThrows(DomainException.class, () -> dominioValor.validate(new ThrowsValidationHandler()));

        assertEquals(errosEsperados, excecao.getErrors().size());
        assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());

    }

    @Test
    void informadoUmCodigoComParametrosValidos_quandoChamadoOCadastrodeUmDominioValor_deveInstanciarUmDominioValor() {
        final var nomeValor = "DominioValor";
        final var codigoValor = "DV";
        final var dominioID = DominioID.unique();

        final var dominioValor = DominioValor.novoDominioValor(codigoValor, nomeValor, dominioID);

        assertNotNull(dominioValor.getCodigoValor());
        assertNotNull(dominioValor.getId());
        assertNotNull(dominioValor.getDataCadastro());
        assertNotNull(dominioValor.getNomeValor());
        assertNotNull(dominioValor.getDominioID());

    }

    @Test
    void informadoUmCodigoNulo_quandoChamadoOCadastrodeUmDominioValor_deveRetornarUmErro() {
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'codigoValor' não pode ser nulo!";
        final var nomeValor = "DominioValor";
        final var dominioID = DominioID.unique();

        final var dominioValor = DominioValor.novoDominioValor(null, nomeValor, dominioID);

        final var excecao = assertThrows(DomainException.class, () -> dominioValor.validate(new ThrowsValidationHandler()));

        assertEquals(errosEsperados, excecao.getErrors().size());
        assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());

    }

    @Test
    void informadoUmCodigoComMenosDe2Caracteres_quandoChamadoOCadastrodeUmDominioValor_deveRetornarUmErro() {
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'codigoValor' dever ter entre 2 e 40 caracteres";
        final var nomeValor = "DominioValor";
        final var dominioID = DominioID.unique();

        final var dominioValor = DominioValor.novoDominioValor("AB", nomeValor, dominioID);

        final var excecao = assertThrows(DomainException.class, () -> dominioValor.validate(new ThrowsValidationHandler()));

        assertEquals(errosEsperados, excecao.getErrors().size());
        assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());

    }

    @Test
    void informadoumCodigoComMaisDe40Caracteres_quandoChamadoOCadastrodeUmDominioValor_deveRetornarUmErro() {
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'codigoValor' dever ter entre 2 e 40 caracteres";
        final var nomeValor = "DominioValor";
        final var dominioID = DominioID.unique();

        final var dominioValor = DominioValor.novoDominioValor("AB".repeat(21), nomeValor, dominioID);

        final var excecao = assertThrows(DomainException.class, () -> dominioValor.validate(new ThrowsValidationHandler()));

        assertEquals(errosEsperados, excecao.getErrors().size());
        assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());

    }

    @Test
    void informadoUmCodigoVazio_quandoChamadoOCadastrodeUmDominioValor_deveRetornarUmErro() {
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'codigoValor' não pode ser vazio";
        final var nomeValor = "DominioValor";
        final var dominioID = DominioID.unique();

        final var dominioValor = DominioValor.novoDominioValor("", nomeValor, dominioID);

        final var excecao = assertThrows(DomainException.class, () -> dominioValor.validate(new ThrowsValidationHandler()));

        assertEquals(errosEsperados, excecao.getErrors().size());
        assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());

    }
}
