package com.sistran.fastclaims.infrastructure.api.v1.controllers;

import com.sistran.fastclaims.application.colecaocampo.cadastrar.CadastrarColecaoCampoCommand;
import com.sistran.fastclaims.application.colecaocampo.cadastrar.CadastrarColecaoCampoOutput;
import com.sistran.fastclaims.application.colecaocampo.cadastrar.CadastrarColecaoCampoUseCase;
import com.sistran.fastclaims.application.colecaocampo.excluir.ExcluirColecaoCampoUseCase;
import com.sistran.fastclaims.application.colecaocampo.pesquisar.id.PesquisarColecaoCampoPorIdUseCase;
import com.sistran.fastclaims.application.colecaocampo.pesquisar.listar.ListarColecaoCampoUseCase;
import com.sistran.fastclaims.domain.colecao.ColecaoID;
import com.sistran.fastclaims.domain.colecaocampo.TipoDado;
import com.sistran.fastclaims.domain.colecaocampo.TipoFormato;
import com.sistran.fastclaims.domain.dominio.DominioID;
import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.pagination.Pagination;
import com.sistran.fastclaims.domain.pagination.SearchQuery;
import com.sistran.fastclaims.domain.validation.Error;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import com.sistran.fastclaims.infrastructure.api.v1.openApi.ColecaoCampoControllerOpenApi;
import com.sistran.fastclaims.infrastructure.colecaocampo.models.CadastrarColecaoCampoRequest;
import com.sistran.fastclaims.infrastructure.colecaocampo.models.ColecaoCampoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.function.Function;

@RestController
@RequestMapping("/v1/colecaocampo")
public class ColecaoCampoController implements ColecaoCampoControllerOpenApi {

    private final CadastrarColecaoCampoUseCase cadastrarColecaoCampoUseCase;
    private final PesquisarColecaoCampoPorIdUseCase pesquisarColecaoCampoPorIdUseCase;
    private final ListarColecaoCampoUseCase listarColecaoCampoUseCase;
    private final ExcluirColecaoCampoUseCase excluirColecaoCampoUseCase;

    public ColecaoCampoController(
            final CadastrarColecaoCampoUseCase cadastrarColecaoCampoUseCase,
            final PesquisarColecaoCampoPorIdUseCase pesquisarColecaoCampoPorIdUseCase,
            final ListarColecaoCampoUseCase listarColecaoCampoUseCase,
            final ExcluirColecaoCampoUseCase excluirColecaoCampoUseCase
    ) {
        this.cadastrarColecaoCampoUseCase = cadastrarColecaoCampoUseCase;
        this.pesquisarColecaoCampoPorIdUseCase = pesquisarColecaoCampoPorIdUseCase;
        this.listarColecaoCampoUseCase = listarColecaoCampoUseCase;
        this.excluirColecaoCampoUseCase = excluirColecaoCampoUseCase;
    }

    @Override
    public ResponseEntity<?> cadastrarColecaoCampo(CadastrarColecaoCampoRequest request) {

        final CadastrarColecaoCampoCommand comando = CadastrarColecaoCampoCommand.com(
                request.campo(),
                request.alias(),
                TipoDado.valueOf(request.tipoDado()),
                TipoFormato.of(request.tipoFormato()).orElseThrow(() ->
                        DomainException.with(new Error("O tipo de formato informado não é válido."))),
                request.colecaoId(),
                request.dominioId());

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.badRequest().body(notification);

        final Function<CadastrarColecaoCampoOutput, ResponseEntity<?>> onSuccess = output -> {
            ColecaoCampoResponse response = ColecaoCampoResponse.aPartirDe(
                    output.id(), output.campo(), output.alias(), output.tipoDado().name(),
                    output.tipoFormato().name(), output.rastro(), output.colecaoId().getValue(),
                    output.dominioId().getValue());
            return ResponseEntity.created(URI.create("/v1/colecaocampo/" + output.id())).body(response);
        };

        return cadastrarColecaoCampoUseCase.executar(comando)
                .fold(onError, onSuccess);
    }

    @Override
    public ColecaoCampoResponse pesquisarPorId(String colecaoCampoId) {
        final var response = pesquisarColecaoCampoPorIdUseCase.executar(colecaoCampoId);
        return ColecaoCampoResponse.aPartirDe(
                response.id(), response.campo(), response.alias(), response.tipoDado().name(),
                response.tipoFormato().name(), response.rastro(), response.colecaoId().getValue(),
                response.dominioId().getValue());
    }

    @Override
    public Pagination<ColecaoCampoResponse> listarColecaoCampo(
            final String termo,
            final int pagina,
            final int porPagina,
            final String ordenarPor,
            final String ordem
    ) {
        return listarColecaoCampoUseCase.executar(new SearchQuery(pagina, porPagina, termo, ordenarPor, ordem))
                .map(ColecaoCampoResponse::aPartirDe);
    }

    @Override
    public void excluirColecaoPorId(String colecaoCampoId) {
        excluirColecaoCampoUseCase.executar(colecaoCampoId);
    }
}
