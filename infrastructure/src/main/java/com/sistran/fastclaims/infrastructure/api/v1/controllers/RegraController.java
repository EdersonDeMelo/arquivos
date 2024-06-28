package com.sistran.fastclaims.infrastructure.api.v1.controllers;

import com.sistran.fastclaims.application.regra.atualizar.AtualizarRegraCommand;
import com.sistran.fastclaims.application.regra.atualizar.AtualizarRegraOutput;
import com.sistran.fastclaims.application.regra.atualizar.AtualizarRegraUseCase;
import com.sistran.fastclaims.application.regra.cadastrar.CadastrarRegraCommand;
import com.sistran.fastclaims.application.regra.cadastrar.CadastrarRegraOutput;
import com.sistran.fastclaims.application.regra.cadastrar.CadastrarRegraUseCase;
import com.sistran.fastclaims.application.regra.excluir.ExcluirRegraUseCase;
import com.sistran.fastclaims.application.regra.pesquisar.id.PesquisarRegraPorIdOutput;
import com.sistran.fastclaims.application.regra.pesquisar.id.PesquisarRegraPorIdUseCase;
import com.sistran.fastclaims.application.regra.pesquisar.lista.ListarRegrasUseCase;
import com.sistran.fastclaims.domain.pagination.Pagination;
import com.sistran.fastclaims.domain.pagination.SearchQuery;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import com.sistran.fastclaims.infrastructure.api.v1.openApi.RegraControllerOpenApi;
import com.sistran.fastclaims.infrastructure.regra.models.*;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.function.Function;

@RestController
@RequestMapping("/v1/regras")
public class RegraController implements RegraControllerOpenApi {

    private final CadastrarRegraUseCase cadastrarRegraUseCase;
    private final PesquisarRegraPorIdUseCase pesquisarRegraPorIdUseCase;
    private final AtualizarRegraUseCase atualizarRegraUseCase;
    private final ExcluirRegraUseCase excluirRegraUseCase;
    private final ListarRegrasUseCase listarRegrasUseCase;

    public RegraController(final CadastrarRegraUseCase cadastrarRegraUseCase,
                           final PesquisarRegraPorIdUseCase pesquisarRegraPorIdUseCase,
                           final AtualizarRegraUseCase atualizarRegraUseCase,
                           final ExcluirRegraUseCase excluirRegraUseCase,
                           final ListarRegrasUseCase listarRegrasUseCase
    ) {
        this.cadastrarRegraUseCase = cadastrarRegraUseCase;
        this.pesquisarRegraPorIdUseCase = pesquisarRegraPorIdUseCase;
        this.atualizarRegraUseCase = atualizarRegraUseCase;
        this.excluirRegraUseCase = excluirRegraUseCase;
        this.listarRegrasUseCase = listarRegrasUseCase;
    }

    @Transactional
    @Override
    public ResponseEntity<?> cadastrarRegra(CadastrarRegraRequest cadastrarRegraRequest) {
        final CadastrarRegraCommand cadastrarRegraCommand = CadastrarRegraCommand.aPartirDe(
                cadastrarRegraRequest.nome(),
                cadastrarRegraRequest.descricao(),
                cadastrarRegraRequest.campoUm(),
                cadastrarRegraRequest.operadorUm() == null ? "" : cadastrarRegraRequest.operadorUm(),
                cadastrarRegraRequest.campoDois() == null ? "" : cadastrarRegraRequest.campoDois(),
                cadastrarRegraRequest.operadorDois(),
                cadastrarRegraRequest.campoTres()
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.badRequest().body(notification);

        final Function<CadastrarRegraOutput, ResponseEntity<?>> onSuccess = output -> {
            CadastrarRegraResponse response = CadastrarRegraResponse.aPartirDe(
                    output.id().getValue(),
                    output.nome(),
                    output.descricao(),
                    output.campoUm(),
                    output.operadorUm(),
                    output.campoDois(),
                    output.operadorDois(),
                    output.campoTres()
            );
            return ResponseEntity.created(URI.create("/v1/regras/" + output.id())).body(response);
        };

        return cadastrarRegraUseCase.executar(cadastrarRegraCommand).fold(onError, onSuccess);
    }

    @Override
    public RegraResponse pesquisarRegraPorId(String regraId) {
        final PesquisarRegraPorIdOutput output = pesquisarRegraPorIdUseCase.executar(regraId);
        return RegraResponse.aPartirDe(
                output.id(),
                output.nome(),
                output.descricao(),
                output.campoUm(),
                output.operadorUm(),
                output.campoDois(),
                output.operadorDois(),
                output.campoTres()
        );
    }

    @Transactional
    @Override
    public ResponseEntity<?> atualizarRegra(AtualizarRegraRequest atualizarRegraRequest, String regraId) {
        final AtualizarRegraCommand cadastrarRegraCommand = AtualizarRegraCommand.aPartirDe(
                regraId,
                atualizarRegraRequest.nome(),
                atualizarRegraRequest.descricao(),
                atualizarRegraRequest.campoUm(),
                atualizarRegraRequest.operadorUm() == null ? "" : atualizarRegraRequest.operadorUm(),
                atualizarRegraRequest.campoDois() == null ? "" : atualizarRegraRequest.campoDois(),
                atualizarRegraRequest.operadorDois(),
                atualizarRegraRequest.campoTres()
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.badRequest().body(notification);

        final Function<AtualizarRegraOutput, ResponseEntity<?>> onSuccess = output -> {
            AtualizarRegraResponse response = AtualizarRegraResponse.aPartirDe(
                    output.id(),
                    output.nome(),
                    output.descricao(),
                    output.campoUm(),
                    output.operadorUm(),
                    output.campoDois(),
                    output.operadorDois(),
                    output.campoTres()
            );
            return ResponseEntity.created(URI.create("/v1/regras/" + output.id())).body(response);
        };

        return atualizarRegraUseCase.executar(cadastrarRegraCommand).fold(onError, onSuccess);
    }

    @Transactional
    @Override
    public ResponseEntity<Void> excluirRegra(String regraId) {
        excluirRegraUseCase.executar(regraId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public Pagination<RegraResponse> listarRegras(String nome, int pagina, int porPagina, String ordenarPor, String ordem) {
        return listarRegrasUseCase.executar(new SearchQuery(pagina, porPagina, nome, ordenarPor, ordem))
                .map(RegraResponse::aPartirDe);
    }
}

