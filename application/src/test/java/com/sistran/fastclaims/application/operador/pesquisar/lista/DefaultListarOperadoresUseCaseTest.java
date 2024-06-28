package com.sistran.fastclaims.application.operador.pesquisar.lista;

import com.sistran.fastclaims.domain.operador.Operador;
import com.sistran.fastclaims.domain.operador.OperadorGateway;
import com.sistran.fastclaims.domain.operador.OperadorID;
import com.sistran.fastclaims.domain.operador.TipoOperador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DefaultListarOperadoresUseCaseTest {

    @Mock
    private OperadorGateway operadorGateway;
    @InjectMocks
    private DefaultListarOperadoresUseCase useCase;

    @BeforeEach
    public void setup() {
        operadorGateway = Mockito.mock(OperadorGateway.class);
        useCase = new DefaultListarOperadoresUseCase(operadorGateway);
    }

    @Test
    public void testExecute() {
        Operador operador1 = Operador.com(new OperadorID("id1"), "Nome1", "Simbolo1", TipoOperador.ARITMETICO); // Assuming TipoOperador.TIPO1 is a valid enum value
        Operador operador2 = Operador.com(new OperadorID("id2"), "Nome2", "Simbolo2", TipoOperador.VALOR); // Assuming TipoOperador.TIPO2 is a valid enum value
        List<Operador> operadores = Arrays.asList(operador1, operador2);

        when(operadorGateway.listar()).thenReturn(operadores);

        List<ListarOperadoresOutput> output = useCase.execute();

        assertEquals(2, output.size());
        assertEquals(operador1.getId().getValue(), output.get(0).id().getValue());
        assertEquals(operador1.getNome(), output.get(0).nome());
        assertEquals(operador1.getSimbolo(), output.get(0).simbolo());
        assertEquals(operador1.getTipoOperador(), output.get(0).tipoOperador());
        assertEquals(operador2.getId().getValue(), output.get(1).id().getValue());
        assertEquals(operador2.getNome(), output.get(1).nome());
        assertEquals(operador2.getSimbolo(), output.get(1).simbolo());
        assertEquals(operador2.getTipoOperador(), output.get(1).tipoOperador());
    }
}
