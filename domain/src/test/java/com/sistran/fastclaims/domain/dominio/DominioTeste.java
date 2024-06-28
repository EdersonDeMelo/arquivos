package com.sistran.fastclaims.domain.dominio;

import com.sistran.fastclaims.domain.UnitTest;
import com.sistran.fastclaims.domain.dominiovalor.DominioValor;
import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DominioTeste extends UnitTest {

    @Test
    void informadoNomeComParametrosValidos_quandoChamadoOCadastrodeUmDominio_deveInstanciarUmDominio() {
        final var nome = "Dominio";
        final var descricao = "Dominio de teste";

        final var dominio = Dominio.novoDominio(nome, descricao, null);

        assertNotNull(dominio.getNome());
        assertNotNull(dominio.getId());
        assertNotNull(dominio.getDataCadastro());
        assertNotNull(dominio.getDescricao());

    }

    @Test
    void informadoUmNomeNulo_quandoChamadoOCadastrodeUmDominio_deveRetornarUmErro() {
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'nome' não pode ser nulo!";
        final var descricao = "descricao";

        final var dominio = Dominio.novoDominio(null, descricao, null);

        final var excecao = assertThrows(DomainException.class, () -> dominio.validate(new ThrowsValidationHandler()));

        assertEquals(errosEsperados, excecao.getErrors().size());
        assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());

    }

    @Test
    void informadoUmNomeComMenosDe2Caracteres_quandoChamadoOCadastrodeUmDominio_deveRetornarUmErro() {
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'nome' dever ter entre 2 e 40 caracteres";
        final var descricao = "descricao";

        final var dominio = Dominio.novoDominio("A", descricao, null);

        final var excecao = assertThrows(DomainException.class, () -> dominio.validate(new ThrowsValidationHandler()));

        assertEquals(errosEsperados, excecao.getErrors().size());
        assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());

    }

    @Test
    void informadoumNomeComMaisDe40Caracteres_quandoChamadoOCadastrodeUmDominio_deveRetornarUmErro() {
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'nome' dever ter entre 2 e 40 caracteres";
        final var descricao = "descricao";

        final var dominio = Dominio.novoDominio("k3o6JuXruxGBPUaPSxXca#@a3P!Xj&VE3%z32Jr7m8jE&", descricao, null);

        final var excecao = assertThrows(DomainException.class, () -> dominio.validate(new ThrowsValidationHandler()));

        assertEquals(errosEsperados, excecao.getErrors().size());
        assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());
    }

    @Test
    void informadoUmNomeVazio_quandoChamadoOCadastrodeUmDominio_deveRetornarUmErro() {
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'nome' não pode ser vazio";
        final var descricao = "descricao";

        final var dominio = Dominio.novoDominio("", descricao, null);

        final var excecao = assertThrows(DomainException.class, () -> dominio.validate(new ThrowsValidationHandler()));

        assertEquals(errosEsperados, excecao.getErrors().size());
        assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());
    }

    @Test
    void informadoDescricaoComParametrosValidos_quandoChamadoOCadastrodeUmDominio_deveInstanciarUmDominio() {
        final var nome = "Dominio";
        final var descricao = "Dominio de teste";

        final var dominio = Dominio.novoDominio(nome, descricao, null);

        assertNotNull(dominio.getNome());
        assertNotNull(dominio.getId());
        assertNotNull(dominio.getDataCadastro());
        assertNotNull(dominio.getDescricao());
    }

    @Test
    void informadoUmaDescricaoNula_quandoChamadoOCadastrodeUmDominio_deveRetornarUmErro() {
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'descricao' não pode ser nula!";
        final var nome = "nome";

        final var dominio = Dominio.novoDominio(nome, null, null);

        final var excecao = assertThrows(DomainException.class, () -> dominio.validate(new ThrowsValidationHandler()));

        assertEquals(errosEsperados, excecao.getErrors().size());
        assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());
    }

    @Test
    void informadoUmaDescricaoComMenosDe2Caracteres_quandoChamadoOCadastrodeUmDominio_deveRetornarUmErro() {
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'descricao' dever ter entre 2 e 40 caracteres";
        final var nome = "nome";

        final var dominio = Dominio.novoDominio(nome, "A", null);

        final var excecao = assertThrows(DomainException.class, () -> dominio.validate(new ThrowsValidationHandler()));

        assertEquals(errosEsperados, excecao.getErrors().size());
        assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());
    }

    @Test
    void informadoUmaDescricaoComMaisDe40Caracteres_quandoChamadoOCadastrodeUmDominio_deveRetornarUmErro() {
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'descricao' dever ter entre 2 e 40 caracteres";
        final var nome = "nome";

        final var dominio = Dominio.novoDominio(nome, "k3o6JuXruxGBPUaPSxXca#@a3P!Xj&VE3%z32Jr7m8jE&", null);

        final var excecao = assertThrows(DomainException.class, () -> dominio.validate(new ThrowsValidationHandler()));

        assertEquals(errosEsperados, excecao.getErrors().size());
        assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());
    }

    @Test
    void informadoUmaDescricaoVazia_quandoChamadoOCadastrodeUmDominio_deveRetornarUmErro() {
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'descricao' não pode ser vazia";
        final var nome = "nome";

        final var dominio = Dominio.novoDominio(nome, "", null);

        final var excecao = assertThrows(DomainException.class, () -> dominio.validate(new ThrowsValidationHandler()));

        assertEquals(errosEsperados, excecao.getErrors().size());
        assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());
    }

    @Test
    void informadoValoresNulos_quandoChamadoOCadastrodeUmDominio_deveRetornarUmErro() {
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'valores' não pode ser vazio";
        final var nome = "nome";
        final var descricao = "descricao";

        final var dominio = Dominio.novoDominio(nome, descricao, null);

        final var excecao = assertThrows(DomainException.class, () -> dominio.validate(new ThrowsValidationHandler()));

        assertEquals(errosEsperados, excecao.getErrors().size());
        assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());
    }

    @Test
    void informadoValoresValidos_quandoChamadoOCadastrodeUmDominio_deveInstanciarUmDominio() {
        final var nome = "Dominio";
        final var descricao = "Dominio de teste";
        final var valores = listaDominioValor();

        final var dominio = Dominio.novoDominio(nome, descricao, valores);

        assertNotNull(dominio.getNome());
        assertNotNull(dominio.getId());
        assertNotNull(dominio.getDataCadastro());
        assertNotNull(dominio.getDescricao());
        assertNotNull(dominio.getValores());
    }

    private List<DominioValor> listaDominioValor() {
        List<DominioValor> listaDominioValor = new ArrayList<>();
        DominioValor dominioValor = DominioValor.novoDominioValor("codigo", "nomeValor", null);
        listaDominioValor.add(dominioValor);
        return listaDominioValor;
    }

}
