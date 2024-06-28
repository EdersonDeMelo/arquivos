package com.sistran.fastclaims.application.trilha.cadastrar;

import com.sistran.fastclaims.application.UseCaseTest;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.fluxo.Fluxo;
import com.sistran.fastclaims.domain.fluxo.FluxoGateway;
import com.sistran.fastclaims.domain.fluxo.FluxoID;
import com.sistran.fastclaims.domain.natureza.NaturezaID;
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
import static org.mockito.Mockito.*;

public class CadastrarTrilhaUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCadastrarTrilhaUseCase useCase;

    @Mock
    private TrilhaGateway trilhaGateway;

    @Mock
    private FluxoGateway fluxoGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(trilhaGateway, fluxoGateway);
    }

    @Test
    public void informadoUmComandoValidoComFluxoIDNulo_quandoChamadoOMetodoCadastrarTrilha_deveRetornarUmaTrilha() {

        final var nomeEsperado = "Trilha teste";
        final var descricaoEsperada = "Descricao";

        final var comando = CadastrarTrilhaCommand.com(nomeEsperado, descricaoEsperada, null);

        when(trilhaGateway.cadastrar(any()))
                .thenAnswer(returnsFirstArg());

        final var saida = useCase.executar(comando).get();

        Assertions.assertNotNull(saida);
        Assertions.assertNotNull(saida.id());
        Assertions.assertNotNull(saida.nome());
        Assertions.assertNotNull(saida.descricao());
        Assertions.assertNotNull(saida.fluxoId());
        Assertions.assertEquals("", saida.fluxoId());

        Mockito.verify(trilhaGateway, times(1)).cadastrar(argThat(trilha ->
                Objects.equals(descricaoEsperada, trilha.getDescricao()) &&
                        Objects.equals(nomeEsperado, trilha.getNome()) &&
                        Objects.equals("", trilha.getFluxoId().getValue()) &&
                        Objects.equals(true, trilha.isAtivo())
        ));
    }

    @Test
    public void informadoUmComandoValidoComFluxoIDVazio_quandoChamadoOMetodoCadastrarTrilha_deveRetornarUmaTrilha() {

        final var nomeEsperado = "Trilha teste";
        final var descricaoEsperada = "Descricao";
        final var fluxoIdEsperado = "";

        final var comando = CadastrarTrilhaCommand.com(nomeEsperado, descricaoEsperada, fluxoIdEsperado);

        when(trilhaGateway.cadastrar(any()))
                .thenAnswer(returnsFirstArg());

        final var saida = useCase.executar(comando).get();

        Assertions.assertNotNull(saida);
        Assertions.assertNotNull(saida.id());
        Assertions.assertNotNull(saida.nome());
        Assertions.assertNotNull(saida.descricao());
        Assertions.assertNotNull(saida.fluxoId());
        Assertions.assertEquals("", saida.fluxoId());

        Mockito.verify(trilhaGateway, times(1)).cadastrar(argThat(trilha ->
                Objects.equals(descricaoEsperada, trilha.getDescricao()) &&
                        Objects.equals(nomeEsperado, trilha.getNome()) &&
                        Objects.equals("", trilha.getFluxoId().getValue()) &&
                        Objects.equals(true, trilha.isAtivo())
        ));
    }

    @Test
    public void informadoUmComandoValidoComFluxoIDCorreto_quandoChamadoOMetodoCadastrarTrilha_deveRetornarUmaTrilha() {

        final var nomeEsperado = "Trilha teste";
        final var descricaoEsperada = "Descricao";

        final var fluxo = Fluxo.novoFluxo("Fluxo teste", NaturezaID.aPartirDe("123"));

        when(fluxoGateway.pesquisarPorId(FluxoID.from(fluxo.getId().getValue())))
                .thenReturn(Optional.of(Fluxo.com(fluxo)));

        final var fluxoIdEsperado = fluxo.getId().getValue();

        final var comando = CadastrarTrilhaCommand.com(nomeEsperado, descricaoEsperada, fluxoIdEsperado);

        when(trilhaGateway.cadastrar(any()))
                .thenAnswer(returnsFirstArg());

        final var saida = useCase.executar(comando).get();

        Assertions.assertNotNull(saida);
        Assertions.assertNotNull(saida.id());
        Assertions.assertNotNull(saida.nome());
        Assertions.assertNotNull(saida.descricao());
        Assertions.assertNotNull(saida.fluxoId());
        Assertions.assertEquals(fluxoIdEsperado, saida.fluxoId());

        Mockito.verify(trilhaGateway, times(1)).cadastrar(argThat(trilha ->
                Objects.equals(descricaoEsperada, trilha.getDescricao()) &&
                        Objects.equals(nomeEsperado, trilha.getNome()) &&
                        Objects.equals(fluxoIdEsperado, trilha.getFluxoId().getValue()) &&
                        Objects.equals(true, trilha.isAtivo())
        ));
    }

    @Test
    public void informadoUmComandoInvalidoComFluxoIDInexistente_quandoChamadoOMetodoCadastrarTrilha_deveRetornarUmaException() {

        final var nomeEsperado = "Trilha teste";
        final var descricaoEsperada = "Descricao";
        final var fluxoIdEsperado = FluxoID.from("123");
        final var mensagemDeErroEsperada = "Fluxo com o ID 123 nao foi encontrado (a)";

        final var comando = CadastrarTrilhaCommand.com(nomeEsperado, descricaoEsperada, fluxoIdEsperado.getValue());

        final var excecao = Assertions.assertThrows(NotFoundException.class, () -> useCase.executar(comando));

        Assertions.assertEquals(mensagemDeErroEsperada, excecao.getMessage());
        verify(trilhaGateway, never()).cadastrar(any(Trilha.class));
    }

    @Test
    public void informadoUmComandoInvalidoComNomeDaTrilhaNulo_quandoChamadoOMetodoCadastrarTrilha_deveRetornarUmaException() {

        final String descricaoErroEsperada = "'nome' não pode ser nulo!";
        final var errosEsperados = 1;

        final var comando = CadastrarTrilhaCommand.com(null, "Descricao", null);

        final var notificacao = useCase.executar(comando).getLeft();

        Assertions.assertEquals(errosEsperados, notificacao.getErrors().size());
        Assertions.assertEquals(descricaoErroEsperada, notificacao.firstError().message());

        Mockito.verify(trilhaGateway, times(0)).cadastrar(any());

    }
}
