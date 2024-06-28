package com.sistran.fastclaims.application.fluxo.cadastrar;

import com.sistran.fastclaims.application.UseCaseTest;
import com.sistran.fastclaims.domain.fluxo.FluxoGateway;
import com.sistran.fastclaims.domain.natureza.Natureza;
import com.sistran.fastclaims.domain.natureza.NaturezaGateway;
import com.sistran.fastclaims.domain.natureza.NaturezaID;
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

public class CadastrarFluxoUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCadastrarFluxoUseCase useCase;

    @Mock
    private FluxoGateway fluxoGateway;

    @Mock
    private NaturezaGateway naturezaGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(fluxoGateway, naturezaGateway);
    }

    @Test
    public void informadoUmComandoValidoComNaturezaIDCorreto_quandoChamadoOMetodoCadastrarFluxo_deveRetornarUmFluxo() {

        final var descricaoEsperada = "Cobertura de cancelamento";

        final var natureza = Natureza.novaNatureza("Natureza teste", "Descrição teste");

        when(naturezaGateway.pesquisarPorId(NaturezaID.aPartirDe(natureza.getId().getValue())))
                .thenReturn(Optional.of(Natureza.com(natureza)));

        final var naturezaIdEsperada = natureza.getId().getValue();

        final var comando = CadastrarFluxoCommand.com(descricaoEsperada, naturezaIdEsperada);

        when(fluxoGateway.cadastrar(any()))
                .thenAnswer(returnsFirstArg());

        final var saida = useCase.executar(comando).get();

        Assertions.assertNotNull(saida);
        Assertions.assertNotNull(saida.id());
        Assertions.assertNotNull(saida.descricao());

        Mockito.verify(fluxoGateway, times(1)).cadastrar(argThat(fluxo ->
                Objects.equals(descricaoEsperada, fluxo.getDescricao()) &&
                        Objects.equals(naturezaIdEsperada, fluxo.getNaturezaId().getValue())
        ));
    }

    @Test
    public void informadoUmaDescricaoInvalida_quandoChamadoOMetodoCadastrarFluxo_deveRetornarUmaDomainException() {

        final String descricaoErroEsperada = "'descrição' não pode ser nula!";
        final var errosEsperados = 1;

        final var natureza = Natureza.novaNatureza("Natureza teste", "Descrição teste");

        when(naturezaGateway.pesquisarPorId(NaturezaID.aPartirDe(natureza.getId().getValue())))
                .thenReturn(Optional.of(Natureza.com(natureza)));

        final var naturezaIdEsperada = natureza.getId().getValue();

        final var comando = CadastrarFluxoCommand.com(null, naturezaIdEsperada);

        final var notificacao = useCase.executar(comando).getLeft();

        Assertions.assertEquals(errosEsperados, notificacao.getErrors().size());
        Assertions.assertEquals(descricaoErroEsperada, notificacao.firstError().message());

        Mockito.verify(fluxoGateway, times(0)).cadastrar(any());
    }

    @Test
    public void informadoUmComandoValido_quandoOGatewayLancaUmThrowsRandomException_deveRetornarUmaException() {

        final var descricaoEsperada = "Cobertura de cancelamento";
        final var errosEsperados = 1;
        final var mensagemErroEsperada = "Gateway error";

        final var natureza = Natureza.novaNatureza("Natureza teste", "Descrição teste");

        when(naturezaGateway.pesquisarPorId(NaturezaID.aPartirDe(natureza.getId().getValue())))
                .thenReturn(Optional.of(Natureza.com(natureza)));

        final var naturezaIdEsperada = natureza.getId().getValue();

        final var comando = CadastrarFluxoCommand.com(descricaoEsperada, naturezaIdEsperada);

        when(fluxoGateway.cadastrar(any()))
                .thenThrow(new IllegalStateException(mensagemErroEsperada));

        final var notificacao = useCase.executar(comando).getLeft();

        Assertions.assertEquals(errosEsperados, notificacao.getErrors().size());
        Assertions.assertEquals(mensagemErroEsperada, notificacao.firstError().message());

        Mockito.verify(fluxoGateway, times(1)).cadastrar(argThat(fluxo ->
                Objects.equals(descricaoEsperada, fluxo.getDescricao())
        ));
    }
}


