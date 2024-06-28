package com.sistran.fastclaims.application.grupousuario.cadastrar;

import com.sistran.fastclaims.application.UseCaseTest;
import com.sistran.fastclaims.domain.grupousuario.GrupoUsuarioGateway;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CadastrarGrupoUsuarioUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCadastrarGrupoUsuarioUseCase useCase;

    @Mock
    private GrupoUsuarioGateway grupoUsuarioGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(grupoUsuarioGateway);
    }

    @Test
    void informadoUmGrupoUsuarioValido_quandoChamadoOMetodoCadastrarGrupoUsuario_deveRetornarUmGrupoUsuario() {
        final var nomeEsperado = "GrupoUsuarioTeste";

        final var comando = CadastrarGrupoUsuarioCommand.com(nomeEsperado);

        when(grupoUsuarioGateway.cadastrar(any()))
                .thenAnswer(returnsFirstArg());

        final var saida = useCase.executar(comando).get();

        assertNotNull(saida);
        assertNotNull(saida.id());
        assertNotNull(saida.nome());
        assertNotNull(saida.dataCadastro());
        assertNotNull(saida.ativo());

        verify(grupoUsuarioGateway, times(1)).cadastrar(any());
    }

    @Test
    void informadoUmGrupoUsuarioVazio_quandoChamadoOMetodoCadastrarGrupoUsuario_deveRetornarUmaMensagem() {
        final var comando = CadastrarGrupoUsuarioCommand.com("");
        final var mensagemEsperada = "'nome' não pode ser vazio";
        final var errosEsperados = 1;


        final var notificacao = useCase.executar(comando).getLeft();

        assertEquals(errosEsperados, notificacao.getErrors().size());
        assertEquals(mensagemEsperada, notificacao.getErrors().get(0).message());

        verify(grupoUsuarioGateway, never()).cadastrar(any());
    }

    @Test
    void informadoUmGrupoUsuarioNulo_quandoChamadoOMetodoCadastrarGrupoUsuario_deveRetornarUmaMensagem() {
        final var comando = CadastrarGrupoUsuarioCommand.com(null);
        final var mensagemEsperada = "'nome' não pode ser nulo!";
        final var errosEsperados = 1;

        final var notificacao = useCase.executar(comando).getLeft();

        assertEquals(errosEsperados, notificacao.getErrors().size());
        assertEquals(mensagemEsperada, notificacao.getErrors().get(0).message());

        verify(grupoUsuarioGateway, never()).cadastrar(any());
    }

    @Test
    void informadoUmGrupoUsuarioComMenosDeDoisCaracteres_quandoChamadoOMetodoCadastrarGrupoUsuario_deveRetornarUmaMensagem() {
        final var comando = CadastrarGrupoUsuarioCommand.com("a");
        final var mensagemEsperada = "'nome' dever ter entre 2 e 40 caracteres";
        final var errosEsperados = 1;

        final var notificacao = useCase.executar(comando).getLeft();

        assertEquals(errosEsperados, notificacao.getErrors().size());
        assertEquals(mensagemEsperada, notificacao.getErrors().get(0).message());

        verify(grupoUsuarioGateway, never()).cadastrar(any());
    }

    @Test
    void informadoUmGrupoUsuarioComMaisDeQuarentaCaracteres_quandoChamadoOMetodoCadastrarGrupoUsuario_deveRetornarUmaMensagem() {
        final var comando = CadastrarGrupoUsuarioCommand.com("GrupoUsuarioTesteGrupoUsuarioTesteGrupoUsuarioTesteGrupoUsuarioTeste");
        final var mensagemEsperada = "'nome' dever ter entre 2 e 40 caracteres";
        final var errosEsperados = 1;

        final var notificacao = useCase.executar(comando).getLeft();

        assertEquals(errosEsperados, notificacao.getErrors().size());
        assertEquals(mensagemEsperada, notificacao.getErrors().get(0).message());

        verify(grupoUsuarioGateway, never()).cadastrar(any());
    }
}
