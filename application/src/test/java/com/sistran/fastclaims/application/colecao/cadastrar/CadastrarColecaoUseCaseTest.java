package com.sistran.fastclaims.application.colecao.cadastrar;

import com.sistran.fastclaims.domain.colecao.Colecao;
import com.sistran.fastclaims.domain.colecao.ColecaoGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CadastrarColecaoUseCaseTest {

    @InjectMocks
    private DefaultCadastrarColecaoUseCase useCase;

    @Mock
    private ColecaoGateway colecaoGateway;

    @Test
    public void cadastrarColecaoTest() {
        final var comando = CadastrarColecaoCommand.com("Nome", "Alias");

        when(colecaoGateway.cadastrar(any())).thenReturn(Colecao.novaColecao("Nome", "Alias"));

        final var result = useCase.executar(comando);

        Assertions.assertTrue(result.isRight());
        Assertions.assertEquals("Nome", result.get().nome());
        Assertions.assertEquals("Alias", result.get().alias());
    }

    @Test
    public void cadastrarColecaoNomeNuloTest() {
        final var comando = CadastrarColecaoCommand.com(null, "Alias");

        final var result = useCase.executar(comando);

        Assertions.assertTrue(result.isLeft());
    }

    @Test
    public void cadastrarColecaoNomeComEspacoTest() {
        final var comando = CadastrarColecaoCommand.com("Nome com espaco", "Alias");

        final var result = useCase.executar(comando);

        Assertions.assertTrue(result.isLeft());
    }

    @Test
    public void cadastrarColecaoMenos3CaracteresTest() {
        final var comando1 = CadastrarColecaoCommand.com("12", "Alias");
        final var comando2 = CadastrarColecaoCommand.com("Nome", "12");

        final var result1 = useCase.executar(comando1);
        final var result2 = useCase.executar(comando2);

        Assertions.assertTrue(result1.isLeft());
        Assertions.assertTrue(result2.isLeft());
    }

    @Test
    public void cadastrarColecaoMais40CaracteresTest() {
        final var comando1 = CadastrarColecaoCommand.com(gerar41Caracteres(), "Alias");
        final var comando2 = CadastrarColecaoCommand.com("Nome", gerar41Caracteres());

        final var result1 = useCase.executar(comando1);
        final var result2 = useCase.executar(comando2);

        Assertions.assertTrue(result1.isLeft());
        Assertions.assertTrue(result2.isLeft());
    }

    private String gerar41Caracteres() {
        return "a".repeat(41);
    }

}
