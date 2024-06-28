package com.sistran.fastclaims.application.colecaocampo.cadastrar;

import com.sistran.fastclaims.domain.colecao.Colecao;
import com.sistran.fastclaims.domain.colecao.ColecaoGateway;
import com.sistran.fastclaims.domain.colecao.ColecaoID;
import com.sistran.fastclaims.domain.colecaocampo.TipoDado;
import com.sistran.fastclaims.domain.colecaocampo.TipoFormato;
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
public class TipoFormatoTest {

    @InjectMocks
    private DefaultCadastrarColecaoCampoUseCase useCase;

    @Mock
    private ColecaoGateway colecaoGateway;

    @Test
    void tipoFormatoNuloTest() {
        final var comando = CadastrarColecaoCampoCommand.com(
                "Campo", "Alias", TipoDado.NUMERICO,
                null, "ColecaoId", null);

        when(colecaoGateway.pesquisarPorId(any()))
                .thenReturn(Optional.of(Colecao.com(ColecaoID.from("ColecaoId"), "Nome", "Alias")));

        final var result = useCase.executar(comando);

        Assertions.assertTrue(result.isLeft());
    }
}

