package com.sistran.fastclaims.infrastructure.api.v1.controllers;


import com.sistran.fastclaims.application.fluxo.atualizar.AtualizarFluxoCommand;
import com.sistran.fastclaims.application.fluxo.atualizar.AtualizarFluxoOutput;
import com.sistran.fastclaims.application.fluxo.atualizar.AtualizarFluxoUseCase;
import com.sistran.fastclaims.application.fluxo.cadastrar.CadastrarFluxoCommand;
import com.sistran.fastclaims.application.fluxo.cadastrar.CadastrarFluxoOutput;
import com.sistran.fastclaims.application.fluxo.cadastrar.CadastrarFluxoUseCase;
import com.sistran.fastclaims.application.fluxo.excluir.ExcluirFluxoUseCase;
import com.sistran.fastclaims.application.fluxo.pesquisar.id.PesquisarFluxoPorIdOutput;
import com.sistran.fastclaims.application.fluxo.pesquisar.id.PesquisarFluxoPorIdUseCase;
import com.sistran.fastclaims.application.fluxo.pesquisar.lista.ListarFluxosUseCase;
import com.sistran.fastclaims.domain.pagination.Pagination;
import com.sistran.fastclaims.domain.pagination.SearchQuery;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import com.sistran.fastclaims.infrastructure.api.v1.openApi.FluxoControllerOpenApi;
import com.sistran.fastclaims.infrastructure.fluxo.models.AtualizarFluxoRequest;
import com.sistran.fastclaims.infrastructure.fluxo.models.AtualizarFluxoResponse;
import com.sistran.fastclaims.infrastructure.fluxo.models.CadastrarFluxoRequest;
import com.sistran.fastclaims.infrastructure.fluxo.models.FluxoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.function.Function;

@RestController
@RequestMapping("/v1/fluxos")
public class FluxoController implements FluxoControllerOpenApi {

    private final CadastrarFluxoUseCase cadastrarFluxoUseCase;
    private final PesquisarFluxoPorIdUseCase pesquisarFluxoPorIdUseCase;
    private final ListarFluxosUseCase pesquisarFluxoPorTermoUseCase;
    private final AtualizarFluxoUseCase atualizarFluxoUseCase;
    private final ExcluirFluxoUseCase excluirFluxoUseCase;

    public FluxoController(final CadastrarFluxoUseCase cadastrarFluxoUseCase,
                           final PesquisarFluxoPorIdUseCase pesquisarFluxoPorIdUseCase,
                           final ListarFluxosUseCase pesquisarFluxoPorTermoUseCase,
                           final AtualizarFluxoUseCase atualizarFluxoUseCase,
                           final ExcluirFluxoUseCase excluirFluxoUseCase) {
        this.cadastrarFluxoUseCase = cadastrarFluxoUseCase;
        this.pesquisarFluxoPorIdUseCase = pesquisarFluxoPorIdUseCase;
        this.pesquisarFluxoPorTermoUseCase = pesquisarFluxoPorTermoUseCase;
        this.atualizarFluxoUseCase = atualizarFluxoUseCase;
        this.excluirFluxoUseCase = excluirFluxoUseCase;
    }

    @Override
    public ResponseEntity<?> cadastrarFluxo(final CadastrarFluxoRequest request) {
        final CadastrarFluxoCommand command = CadastrarFluxoCommand.com(request.descricao(), request.naturezaId());

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.badRequest().body(notification);

        final Function<CadastrarFluxoOutput, ResponseEntity<?>> onSuccess = output -> {
            FluxoResponse response = FluxoResponse.aPartirDe(output.id().getValue(), output.descricao(), output.naturezaID().getValue());
            return ResponseEntity.created(URI.create("/v1/fluxos/" + output.id().getValue())).body(response);
        };

        return cadastrarFluxoUseCase.executar(command).fold(onError, onSuccess);
    }

    @Override
    public Pagination<FluxoResponse> listarFluxos(
            final String termo,
            final int pagina,
            final int porPagina,
            final String ordenarPor,
            final String ordem
    ) {
        return pesquisarFluxoPorTermoUseCase.executar(new SearchQuery(pagina, porPagina, termo, ordenarPor, ordem))
                .map(FluxoResponse::aPartirDe);
    }

    @Override
    public FluxoResponse pesquisarFluxoPorId(final String fluxoId) {
        final PesquisarFluxoPorIdOutput fluxoOutput = pesquisarFluxoPorIdUseCase.executar(fluxoId);
        return FluxoResponse.aPartirDe(fluxoOutput.id().getValue(), fluxoOutput.descricao(), fluxoOutput.naturezaID().getValue());

    }

    @Override
    public ResponseEntity<?> atualizarFluxo(final String fluxoId, final AtualizarFluxoRequest request) {

        final AtualizarFluxoCommand comando = AtualizarFluxoCommand.com(fluxoId, request.descricao());

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.badRequest().body(notification);

        final Function<AtualizarFluxoOutput, ResponseEntity<?>> onSuccess = output -> {
            AtualizarFluxoResponse response = AtualizarFluxoResponse.aPartirDe(output.id().getValue(), output.descricao());
            return ResponseEntity.ok(response);
        };

        return atualizarFluxoUseCase.executar(comando).fold(onError, onSuccess);
    }

    public ResponseEntity<Void> excluirFluxo(final String fluxoId) {
        excluirFluxoUseCase.executar(fluxoId);
        return ResponseEntity.noContent().build();
    }
}

