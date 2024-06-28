package com.sistran.fastclaims.domain.grupousuario;

import com.sistran.fastclaims.domain.UnitTest;
import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GrupoUsuarioTeste extends UnitTest {

    @Test
    void informadoNomeValido_quandoChamadoOCadastrodeUmGrupoUsuario_deveInstanciarUmGrupoUsuario() {
        final var nome = "GrupoUsuario";

        final var grupoUsuario = GrupoUsuario.novoGrupoUsuario(nome);

        assertNotNull(grupoUsuario.getNome());
        assertNotNull(grupoUsuario.getId());
        assertNotNull(grupoUsuario.getDataCadastro());

    }

    @Test
    void informadoUmNomeNulo_quandoChamadoOCadastrodeUmGrupoUsuario_deveRetornarUmErro() {
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'nome' não pode ser nulo!";

        final var grupoUsuario = GrupoUsuario.novoGrupoUsuario(null);

        final var excecao = assertThrows(DomainException.class, () -> grupoUsuario.validate(new ThrowsValidationHandler()));

        assertEquals(errosEsperados, excecao.getErrors().size());
        assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());

    }

    @Test
    void informadoUmNomeComMenosDe2Caracteres_quandoChamadoOCadastrodeUmGrupoUsuario_deveRetornarUmErro() {
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'nome' dever ter entre 2 e 40 caracteres";

        final var grupoUsuario = GrupoUsuario.novoGrupoUsuario("A");

        final var excecao = assertThrows(DomainException.class, () -> grupoUsuario.validate(new ThrowsValidationHandler()));

        assertEquals(errosEsperados, excecao.getErrors().size());
        assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());

    }

    @Test
    void informadoUmNomeComMaisDe40Caracteres_quandoChamadoOCadastrodeUmGrupoUsuario_deveRetornarUmErro() {
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'nome' dever ter entre 2 e 40 caracteres";

        final var grupoUsuario = GrupoUsuario.novoGrupoUsuario("GrupoUsuarioGrupoUsuarioGrupoUsuarioGrupoUsuarioGrupoUsuario");

        final var excecao = assertThrows(DomainException.class, () -> grupoUsuario.validate(new ThrowsValidationHandler()));

        assertEquals(errosEsperados, excecao.getErrors().size());
        assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());

    }

    @Test
    void informadoNomeVazio_quandoChamadoOCadastrodeUmGrupoUsuario_deveRetornarUmErro() {
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "'nome' não pode ser vazio";

        final var grupoUsuario = GrupoUsuario.novoGrupoUsuario("");

        final var excecao = assertThrows(DomainException.class, () -> grupoUsuario.validate(new ThrowsValidationHandler()));

        assertEquals(errosEsperados, excecao.getErrors().size());
        assertEquals(mensagemErroEsperada, excecao.getErrors().get(0).message());

    }
}
