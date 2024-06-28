package com.sistran.fastclaims.application.colecaocampo.excluir;

import com.sistran.fastclaims.domain.colecao.ColecaoID;
import com.sistran.fastclaims.domain.colecaocampo.*;
import com.sistran.fastclaims.domain.dominio.DominioID;
import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.operador.Operador;
import com.sistran.fastclaims.domain.operador.OperadorID;
import com.sistran.fastclaims.domain.operador.TipoOperador;
import com.sistran.fastclaims.domain.regra.Regra;
import com.sistran.fastclaims.domain.regra.RegraGateway;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExcluirColecaoCampoUseCaseTest {

    @InjectMocks
    private DefaultExcluirColecaoCampoUseCase useCase;

    @Mock
    private ColecaoCampoGateway colecaoCampoGateway;

    @Mock
    private RegraGateway regraGateway;

    @Test
    public void excluirColecaoCampoTest() {
        final var anIn = "ColecaoCampoID";

        Mockito.when(regraGateway.pesquisarCampoUm(ArgumentMatchers.any())).thenReturn(Optional.empty());
        Mockito.when(regraGateway.pesquisarCampoDois(ArgumentMatchers.any())).thenReturn(Optional.empty());
        Mockito.when(regraGateway.pesquisarCampoTres(ArgumentMatchers.any())).thenReturn(Optional.empty());

        Mockito.doNothing().when(colecaoCampoGateway).excluir(ArgumentMatchers.any());

        useCase.executar(anIn);

        verify(colecaoCampoGateway, times(1)).excluir(any());
        verify(regraGateway, times(1)).pesquisarCampoUm(any());
        verify(regraGateway, times(1)).pesquisarCampoDois(any());
        verify(regraGateway, times(1)).pesquisarCampoTres(any());
    }

    @Test
    public void excluirColecaoCampoCampoUmRegraAssossiadaTest() {
        final var anIn = "ColecaoCampoID";

        Mockito.when(regraGateway.pesquisarCampoUm(ArgumentMatchers.any())).thenReturn(Optional.of(gerarRegra()));

        Assertions.assertThrows(DomainException.class, () -> useCase.executar(anIn));

        verify(colecaoCampoGateway, times(0)).excluir(any());

    }

    @Test
    public void excluirColecaoCampoCampoDoisRegraAssossiadaTest() {
        final var anIn = "ColecaoCampoID";

        Mockito.when(regraGateway.pesquisarCampoDois(ArgumentMatchers.any())).thenReturn(Optional.of(gerarRegra()));

        Assertions.assertThrows(DomainException.class, () -> useCase.executar(anIn));

        verify(colecaoCampoGateway, times(0)).excluir(any());

    }

    @Test
    public void excluirColecaoCampoCampoTresRegraAssossiadaTest() {
        final var anIn = "ColecaoCampoID";

        Mockito.when(regraGateway.pesquisarCampoTres(ArgumentMatchers.any())).thenReturn(Optional.of(gerarRegra()));

        Assertions.assertThrows(DomainException.class, () -> useCase.executar(anIn));

        verify(colecaoCampoGateway, times(0)).excluir(any());

    }

    @Test
    public void excluirColecaoCampoInexistenteTest() {
        final var anIn = "IDInexistente";

        Mockito.when(regraGateway.pesquisarCampoUm(ArgumentMatchers.any())).thenReturn(Optional.empty());
        Mockito.when(regraGateway.pesquisarCampoDois(ArgumentMatchers.any())).thenReturn(Optional.empty());
        Mockito.when(regraGateway.pesquisarCampoTres(ArgumentMatchers.any())).thenReturn(Optional.empty());

        doThrow(DomainException.class).when(colecaoCampoGateway).excluir(any());

        Assertions.assertThrows(DomainException.class, () -> useCase.executar(anIn));
    }

    private Regra gerarRegra() {

        final var nome = "Regra teste";
        final var descricao = "Descricao";
        final var campoTres = "CampoTres";

        final var campoUm = ColecaoCampo.novaColecaoCampo(
                "CampoUm",
                "DescricaoCampoUm",
                TipoDado.ALFA,
                TipoFormato.ASTERISCO,
                ColecaoID.from("Rastro"),
                DominioID.from("123"),
                null
        );

        final var operadorDois = Operador.novoOperador(
                "OperadorUm",
                "SimboloUm",
                TipoOperador.RELACIONAL
        );

        final var regra = Regra.novaRegra(
                nome,
                descricao,
                campoUm.getId(),
                OperadorID.from(""),
                null,
                operadorDois.getId(),
                campoTres
        );

        return regra;
    }

}
