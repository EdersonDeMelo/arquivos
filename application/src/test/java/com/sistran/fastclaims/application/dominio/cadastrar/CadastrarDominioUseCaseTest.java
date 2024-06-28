package com.sistran.fastclaims.application.dominio.cadastrar;

import com.sistran.fastclaims.application.UseCaseTest;
import com.sistran.fastclaims.domain.dominio.Dominio;
import com.sistran.fastclaims.domain.dominio.DominioGateway;
import com.sistran.fastclaims.domain.dominiovalor.DominioValor;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CadastrarDominioUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCadastrarDominioUseCase useCase;

    @Mock
    private DominioGateway dominioGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(dominioGateway);
    }

    private List<DominioValor> listaDominioValor() {
        List<DominioValor> listaDominioValor = new ArrayList<>();
        DominioValor dominioValor = DominioValor.novoDominioValor("codigo", "nomeValor", null);
        listaDominioValor.add(dominioValor);
        return listaDominioValor;
    }

    @Test
    public void informadoUmDominioValido_quandoChamadoOMetodoCadastrarDominio_deveRetornarUmDominio() {

        final var nomeEsperado = "Dominioteste";
        final var descricaoEsperada = "Descricao";
        final var valoresEsperados = listaDominioValor();

        final var comando = CadastrarDominioCommand.com(nomeEsperado, descricaoEsperada, valoresEsperados);

        when(dominioGateway.cadastrar(any()))
                .thenAnswer(returnsFirstArg());

        final var saida = useCase.executar(comando).get();

        assertNotNull(saida);
        assertNotNull(saida.id());
        assertNotNull(saida.nome());
        assertNotNull(saida.descricao());
        assertNotNull(saida.dataCadastro());
        assertNotNull(saida.valores());

        verify(dominioGateway, times(1)).cadastrar(argThat(dominio ->
                dominio.getNome().equals(nomeEsperado) &&
                        dominio.getDescricao().equals(descricaoEsperada) &&
                        dominio.getValores().equals(valoresEsperados)
        ));
    }

    @Test
    public void informadoUmDominioValidoComValoresNulos_quandoChamadoOMetodoCadastrarDominio_deveRetornarUmDominio() {

        final var nomeEsperado = "Dominioteste";
        final var descricaoEsperada = "Descricao";
        final var descricaoErroEsperada = "'valores' não pode ser vazio";
        final var errosEsperados = 1;

        final var comando = CadastrarDominioCommand.com(nomeEsperado, descricaoEsperada, null);

        final var notificacao = useCase.executar(comando).getLeft();

        assertEquals(errosEsperados, notificacao.getErrors().size());
        assertEquals(descricaoErroEsperada, notificacao.getErrors().get(0).message());

        verify(dominioGateway, times(0)).cadastrar(any());
    }

    @Test
    public void informadoUmDominioValidoComValoresVazios_quandoChamadoOMetodoCadastrarDominio_deveRetornarUmDominio() {

        final var nomeEsperado = "Dominioteste";
        final var descricaoEsperada = "Descricao";
        final var valoresEsperados = new ArrayList<DominioValor>();
        final var descricaoErroEsperada = "'valores' não pode ser vazio";
        final var errosEsperados = 1;

        final var comando = CadastrarDominioCommand.com(nomeEsperado, descricaoEsperada, valoresEsperados);

        final var notificacao = useCase.executar(comando).getLeft();

        assertEquals(errosEsperados, notificacao.getErrors().size());
        assertEquals(descricaoErroEsperada, notificacao.getErrors().get(0).message());

        verify(dominioGateway, times(0)).cadastrar(any());
    }

    @Test
    public void informadoUmDominioValidoComNomeJaExistente_quandoChamadoOMetodoCadastrarDominio_deveRetornarUmaException() {

        final var nomeEsperado = "Dominioteste";
        final var descricaoEsperada = "Descricao";
        final var valoresEsperados = listaDominioValor();
        final var mensagemDeErroEsperada = "Nome do domínio já existe";
        final var errosEsperados = 1;

        final var comando = CadastrarDominioCommand.com(nomeEsperado, descricaoEsperada, valoresEsperados);

        when(dominioGateway.pesquisarPorNome(any()))
                .thenReturn(Optional.of(Dominio.novoDominio(nomeEsperado, descricaoEsperada, valoresEsperados)));

        final var actualOutput = useCase.executar(comando).getLeft();

        assertEquals(mensagemDeErroEsperada, actualOutput.firstError().message());
        assertEquals(errosEsperados, actualOutput.getErrors().size());

        verify(dominioGateway, times(0)).cadastrar(any());
    }

    @Test
    public void informadoUmDominioValidoComNomeNulo_quandoChamadoOMetodoCadastrarDominio_deveRetornarUmaException() {
        final String nomeEsperado = null;
        final var descricaoEsperada = "Descricao";
        final var valoresEsperados = listaDominioValor();
        final var descricaoErroEsperada = "'nome' não pode ser nulo!";
        final var errosEsperados = 1;

        final var comando = CadastrarDominioCommand.com(nomeEsperado, descricaoEsperada, valoresEsperados);

        final var notificacao = useCase.executar(comando).getLeft();

        assertEquals(errosEsperados, notificacao.getErrors().size());
        assertEquals(descricaoErroEsperada, notificacao.firstError().message());

        verify(dominioGateway, times(0)).cadastrar(any());

    }

    @Test
    public void informadoUmDominioValidoComNomeVazio_quandoChamadoOMetodoCadastrarDominio_deveRetornarUmaException(){
        final String nomeEsperado = "";
        final var descricaoEsperada = "Descricao";
        final var valoresEsperados = listaDominioValor();
        final var descricaoErroEsperada = "'nome' não pode ser vazio";
        final var errosEsperados = 1;

        final var comando = CadastrarDominioCommand.com(nomeEsperado, descricaoEsperada, valoresEsperados);

        final var notificacao = useCase.executar(comando).getLeft();

        assertEquals(errosEsperados, notificacao.getErrors().size());
        assertEquals(descricaoErroEsperada, notificacao.firstError().message());

        verify(dominioGateway, times(0)).cadastrar(any());
    }

    @Test
    public void informadoUmDominioValidoComDescricaoNula_quandoChamadoOMetodoCadastrarDominio_deveRetornarUmaException(){
        final var nomeEsperado = "Dominioteste";
        final String descricaoEsperada = null;
        final var valoresEsperados = listaDominioValor();
        final var descricaoErroEsperada = "'descricao' não pode ser nula!";
        final var errosEsperados = 1;

        final var comando = CadastrarDominioCommand.com(nomeEsperado, descricaoEsperada, valoresEsperados);

        final var notificacao = useCase.executar(comando).getLeft();

        assertEquals(errosEsperados, notificacao.getErrors().size());
        assertEquals(descricaoErroEsperada, notificacao.firstError().message());

        verify(dominioGateway, times(0)).cadastrar(any());
    }

    @Test
    public void informadoUmDominioValidoComDescricaoVazia_quandoChamadoOMetodoCadastrarDominio_deveRetornarUmaException(){
        final var nomeEsperado = "Dominioteste";
        final String descricaoEsperada = "";
        final var valoresEsperados = listaDominioValor();
        final var descricaoErroEsperada = "'descricao' não pode ser vazia";
        final var errosEsperados = 1;

        final var comando = CadastrarDominioCommand.com(nomeEsperado, descricaoEsperada, valoresEsperados);

        final var notificacao = useCase.executar(comando).getLeft();

        assertEquals(errosEsperados, notificacao.getErrors().size());
        assertEquals(descricaoErroEsperada, notificacao.firstError().message());

        verify(dominioGateway, times(0)).cadastrar(any());
    }


}


