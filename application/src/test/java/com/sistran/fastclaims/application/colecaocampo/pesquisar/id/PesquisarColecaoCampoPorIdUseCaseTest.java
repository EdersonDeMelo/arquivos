package com.sistran.fastclaims.application.colecaocampo.pesquisar.id;

import com.sistran.fastclaims.domain.colecao.ColecaoID;
import com.sistran.fastclaims.domain.colecaocampo.*;
import com.sistran.fastclaims.domain.dominio.DominioID;
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
public class PesquisarColecaoCampoPorIdUseCaseTest {

    @InjectMocks
    private DefaultPesquisarColecaoCampoPorIdUseCase useCase;

    @Mock
    private ColecaoCampoGateway colecaoCampoGateway;

    @Test
    public void pesquisarColecaoCampoPorIdTest() {
        final var colecaoCampo = ColecaoCampo.novaColecaoCampo("Campo", "Alias", TipoDado.NUMERICO,
                TipoFormato.ASTERISCO, ColecaoID.from("colecaoID"), DominioID.from("dominioID"), "nomeColecao");
        final ColecaoCampoID id = colecaoCampo.getId();

        when(colecaoCampoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(colecaoCampo));

        final var result = useCase.executar(id.getValue());

        Assertions.assertEquals(id.getValue(), result.id());
    }

    @Test
    public void pesquisarColecaoCampoPorIdNaoEncontradaTest() {
        final ColecaoCampoID id = ColecaoCampoID.from("id");

        when(colecaoCampoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(Exception.class, () -> useCase.executar(id.getValue()));
    }

}
