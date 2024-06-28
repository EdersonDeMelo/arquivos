package com.sistran.fastclaims.application.colecao.excluir;

import com.sistran.fastclaims.domain.colecao.ColecaoGateway;
import com.sistran.fastclaims.domain.colecao.ColecaoID;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampo;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoGateway;
import com.sistran.fastclaims.domain.colecaocampo.TipoDado;
import com.sistran.fastclaims.domain.colecaocampo.TipoFormato;
import com.sistran.fastclaims.domain.dominio.DominioID;
import com.sistran.fastclaims.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExcluirColecaoUseCaseTest {

    @InjectMocks
    private DefaultExcluirColecaoUseCase useCase;

    @Mock
    private ColecaoCampoGateway colecaoCampoGateway;

    @Mock
    private ColecaoGateway colecaoGateway;

    @Test
    public void excluirColecaoTest() {
        final var anIn = "ColecaoID";
        when(colecaoCampoGateway.pesquisarPorColecaoId(any())).thenReturn(Optional.empty());
        doNothing().when(colecaoGateway).excluirPorId(any());
        useCase.executar(anIn);
        verify(colecaoGateway, times(1)).excluirPorId(any());
    }

    @Test
    public void excluirColecaoComCampoAssociadoTest() {

        final var anIn = "ColecaoID";

        when(colecaoCampoGateway.pesquisarPorColecaoId(any()))
                .thenReturn(Optional.of(ColecaoCampo.com("id", "campo", "alias", TipoDado.NUMERICO,
                        TipoFormato.DECIMAL, "rastro", ColecaoID.from("ColecaoID"),
                        DominioID.from("DominioID"))));

        Assertions.assertThrows(DomainException.class, () -> useCase.executar(anIn));
    }

    @Test
    void excluirColecaoInexistenteTest() {
        final var anIn = "IDInexistente";

        when(colecaoCampoGateway.pesquisarPorColecaoId(any()))
                .thenReturn(Optional.of(ColecaoCampo.com("id", "campo", "alias", TipoDado.NUMERICO,
                        TipoFormato.DECIMAL, "rastro", ColecaoID.from("ColecaoID"), DominioID.from("DominioID"))));

        Assertions.assertThrows(DomainException.class, () -> useCase.executar(anIn));
    }

}
