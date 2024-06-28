package com.sistran.fastclaims.application.colecao.pesquisar.id;

import com.sistran.fastclaims.domain.colecao.Colecao;
import com.sistran.fastclaims.domain.colecao.ColecaoGateway;
import com.sistran.fastclaims.domain.colecao.ColecaoID;
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
public class PesquisarColecaoPorIdUseCaseTest {

    @InjectMocks
    private DefaultPesquisarColecaoPorIdUseCase useCase;

    @Mock
    private ColecaoGateway colecaoGateway;

    @Test
    public void pesquisarColecaoPorIdTest() {
        final ColecaoID id = ColecaoID.from("id");

        when(colecaoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(Colecao.novaColecao("Nome", "Alias")));

        final var result = useCase.executar(id.getValue());

        Assertions.assertEquals("Nome", result.nome());
        Assertions.assertEquals("Alias", result.alias());
    }

    @Test
    public void pesquisarColecaoPorIdNaoEncontradaTest() {
        final ColecaoID id = ColecaoID.from("id");

        when(colecaoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(Exception.class, () -> useCase.executar(id.getValue()));
    }
}
