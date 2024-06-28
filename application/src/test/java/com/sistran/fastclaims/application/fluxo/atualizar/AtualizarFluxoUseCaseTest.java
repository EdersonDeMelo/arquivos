package com.sistran.fastclaims.application.fluxo.atualizar;

import com.sistran.fastclaims.domain.fluxo.Fluxo;
import com.sistran.fastclaims.domain.fluxo.FluxoGateway;
import com.sistran.fastclaims.domain.natureza.NaturezaID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AtualizarFluxoUseCaseTest {

    @InjectMocks
    private DefaultAtualizarFluxoUseCase useCase;

    @Mock
    private FluxoGateway fluxoGateway;

    @Test
    public void alterarDescricaoTest() {
        final var fluxo = Fluxo.novoFluxo("Descrição original", NaturezaID.aPartirDe("123"));
        final var comando = AtualizarFluxoCommand.com(fluxo.getId().toString(), "Nova descrição");

        when(fluxoGateway.pesquisarPorId(any())).thenReturn(Optional.of(fluxo));
        when(fluxoGateway.atualizar(any())).thenReturn(fluxo);

        final var result = useCase.executar(comando);

        Assertions.assertTrue(result.isRight());
        Assertions.assertEquals("Nova descrição", result.get().descricao());
    }

    @Test
    public void alterarDescricaoCampoNuloTest() {
        final var fluxo = Fluxo.novoFluxo("Descrição original", NaturezaID.aPartirDe("123"));
        final String id = fluxo.getId().getValue();
        final AtualizarFluxoCommand comando = AtualizarFluxoCommand.com(id, null);

        when(fluxoGateway.pesquisarPorId(any())).thenReturn(Optional.of(fluxo));

        final var result = useCase.executar(comando);

        Assertions.assertTrue(result.isLeft());
        Assertions.assertTrue(result.getLeft().hasErrors());
    }

    @Test
    public void alterarDescricaoCampoMenosDe3CaracteresTest() {
        final var fluxo = Fluxo.novoFluxo("Descrição original", NaturezaID.aPartirDe("123"));
        final var comando = AtualizarFluxoCommand.com(fluxo.getId().toString(), "12");

        when(fluxoGateway.pesquisarPorId(any())).thenReturn(Optional.of(fluxo));

        final var result = useCase.executar(comando);

        Assertions.assertTrue(result.isLeft());
        Assertions.assertTrue(result.getLeft().hasErrors());
    }

    @Test
    public void alterarDescricaoCampoMais50CaracteresTest() {
        final var fluxo = Fluxo.novoFluxo("Descrição original", NaturezaID.aPartirDe("123"));
        final var comando = AtualizarFluxoCommand.com(fluxo.getId().toString(), gerar51Caracteres());

        when(fluxoGateway.pesquisarPorId(any())).thenReturn(Optional.of(fluxo));

        final var result = useCase.executar(comando);

        Assertions.assertTrue(result.isLeft());
        Assertions.assertTrue(result.getLeft().hasErrors());
    }

    private String gerar51Caracteres() {
        return "a".repeat(51);
    }
}
