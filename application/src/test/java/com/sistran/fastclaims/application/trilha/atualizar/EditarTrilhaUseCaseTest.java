package com.sistran.fastclaims.application.trilha.atualizar;

import com.sistran.fastclaims.application.UseCaseTest;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.fluxo.FluxoID;
import com.sistran.fastclaims.domain.trilha.Trilha;
import com.sistran.fastclaims.domain.trilha.TrilhaGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class EditarTrilhaUseCaseTest extends UseCaseTest {
    @InjectMocks
    private DefaultAtualizarTrilhaUseCase useCase;


    @Mock
    private TrilhaGateway trilhaGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(trilhaGateway);
    }

    @Test
    public void informandoUmaTrilhaValida_QuandoChamadoOMetodoEditar_deveRetornarUmaTrilhaEditada() {
        final var idEsperado = "123";
        final var nomeEsperado = "nomeAlterado";
        final var descricaoEsperada = "descricaoAlterada";

        final var trilhaCadastro = Trilha.novaTrilha("Algum nome", "Alguma descricao", FluxoID.unique());
        final var comando = AtualizarTrilhaCommand.com(idEsperado, nomeEsperado, descricaoEsperada);

        when(trilhaGateway.pesquisarPorId(any())).thenReturn(Optional.of(trilhaCadastro.clone()));

        when(trilhaGateway.atualizar(any())).thenAnswer(returnsFirstArg());

        final var saida = useCase.executar(comando).get();

        Mockito.verify(trilhaGateway, times(1)).atualizar(argThat(trilha ->
                Objects.equals(saida.descricao(), trilha.getDescricao()) &&
                        Objects.equals(saida.nome(), trilha.getNome())
        ));

    }

    @Test
    public void informandoUmaTrilhaComIdInvalido_QuandoChamadoOMetodoEditar_deveRetornarUmaException() {
        final var idEsperado = "123";
        final var nomeEsperado = "nomeAlterado";
        final var descricaoEsperada = "descricaoAlterada";
        final var errosEsperados = 1;
        final String mensagemDeErroEsperada = "Trilha com o ID 123 nao foi encontrado (a)";
        final var comando = AtualizarTrilhaCommand.com(idEsperado, nomeEsperado, descricaoEsperada);

        when(trilhaGateway.pesquisarPorId(any())).thenReturn(Optional.empty());

        final var excecao = Assertions.assertThrows(
                NotFoundException.class,
                () -> useCase.executar(comando)
        );

        Assertions.assertEquals(mensagemDeErroEsperada, excecao.getMessage());

    }

    @Test
    public void informandoUmaTrilhaComNomeNulo_quandoChamadoOMetodoEditar_deveRetornarUmaException() {

        final var idEsperado = "123";
        final var descricaoEsperada = "descricaoAlterada";

        final var trilhaCadastro = Trilha.novaTrilha("Algum nome", "Alguma descricao", FluxoID.unique());
        final var comando = AtualizarTrilhaCommand.com(idEsperado, null, descricaoEsperada);

        when(trilhaGateway.pesquisarPorId(any())).thenReturn(Optional.of(trilhaCadastro.clone()));

        final String descricaoErroEsperada = "'nome' não pode ser nulo!";
        final var errosEsperados = 1;

        final var notificacao = useCase.executar(comando).getLeft();

        Assertions.assertEquals(errosEsperados, notificacao.getErrors().size());
        Assertions.assertEquals(descricaoErroEsperada, notificacao.firstError().message());
    }

    @Test
    public void informandoUmaTrilhaComNomeEmBranco_quandoChamadoOMetodoEditar_deveRetornarUmaException() {

        final var idEsperado = "123";
        final var descricaoEsperada = "descricaoAlterada";

        final var trilhaCadastro = Trilha.novaTrilha("Algum nome", "Alguma descricao", FluxoID.unique());
        final var comando = AtualizarTrilhaCommand.com(idEsperado, "", descricaoEsperada);

        when(trilhaGateway.pesquisarPorId(any())).thenReturn(Optional.of(trilhaCadastro.clone()));

        final String descricaoErroEsperada = "'nome' não pode ser vazio!";
        final var errosEsperados = 1;

        final var notificacao = useCase.executar(comando).getLeft();

        Assertions.assertEquals(errosEsperados, notificacao.getErrors().size());
        Assertions.assertEquals(descricaoErroEsperada, notificacao.firstError().message());
    }
}
