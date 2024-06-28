package com.sistran.fastclaims.application.operador.pesquisar.id;

import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.operador.Operador;
import com.sistran.fastclaims.domain.operador.OperadorGateway;
import com.sistran.fastclaims.domain.operador.OperadorID;
import com.sistran.fastclaims.domain.operador.TipoOperador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class DefaultPesquisarOperadorPorIdUseCaseTest {

    @Mock
    private OperadorGateway operadorGateway;
    @InjectMocks
    private DefaultPesquisarOperadorPorIdUseCase useCase;

    @BeforeEach
    public void setup() {
        operadorGateway = Mockito.mock(OperadorGateway.class);
        useCase = new DefaultPesquisarOperadorPorIdUseCase(operadorGateway);
    }

    @Test
    public void testExecutarSuccess() {
        String id = "testId";
        Operador operador = Operador.com(new OperadorID(id), "Nome", "Simbolo", TipoOperador.ARITMETICO); // Assuming TipoOperador.TIPO1 is a valid enum value
        when(operadorGateway.pesquisarPorId(any())).thenReturn(Optional.of(operador));

        PesquisarOperadorPorIdOutput output = useCase.executar(id);

        assertEquals(operador.getId().getValue(), output.id().getValue());
        assertEquals(operador.getNome(), output.nome());
        assertEquals(operador.getSimbolo(), output.simbolo());
        assertEquals(operador.getTipoOperador(), output.tipoOperador());
    }

    @Test
    public void testExecutarFailure() {
        String id = "testId";
        when(operadorGateway.pesquisarPorId(OperadorID.from(id))).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> useCase.executar(id));
    }
}
