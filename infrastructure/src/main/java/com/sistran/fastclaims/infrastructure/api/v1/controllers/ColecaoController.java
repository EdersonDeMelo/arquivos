package com.sistran.fastclaims.infrastructure.api.v1.controllers;

import com.sistran.fastclaims.application.colecao.atualizar.AtualizarColecaoCommand;
import com.sistran.fastclaims.application.colecao.atualizar.AtualizarColecaoOutput;
import com.sistran.fastclaims.application.colecao.atualizar.AtualizarColecaoUseCase;
import com.sistran.fastclaims.application.colecao.cadastrar.CadastrarColecaoCommand;
import com.sistran.fastclaims.application.colecao.cadastrar.CadastrarColecaoOutput;
import com.sistran.fastclaims.application.colecao.cadastrar.CadastrarColecaoUseCase;
import com.sistran.fastclaims.application.colecao.excluir.ExcluirColecaoUseCase;
import com.sistran.fastclaims.application.colecao.pesquisar.id.PesquisarColecaoPorIdOutput;
import com.sistran.fastclaims.application.colecao.pesquisar.id.PesquisarColecaoPorIdUseCase;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import com.sistran.fastclaims.infrastructure.api.v1.openApi.ColecaoControllerOpenApi;
import com.sistran.fastclaims.infrastructure.colecao.models.AtualizarColecaoRequest;
import com.sistran.fastclaims.infrastructure.colecao.models.CadastrarColecaoRequest;
import com.sistran.fastclaims.infrastructure.colecao.models.ColecaoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.function.Function;

@RestController
@RequestMapping("/v1/colecao")
public class ColecaoController implements ColecaoControllerOpenApi {

    private final CadastrarColecaoUseCase cadastrarColecaoUseCase;
    private final PesquisarColecaoPorIdUseCase pesquisarColecaoPorIdUseCase;
    private final ExcluirColecaoUseCase excluirColecaoUseCase;
    private final AtualizarColecaoUseCase atualizarColecaoUseCase;

    public ColecaoController(final CadastrarColecaoUseCase cadastrarColecaoUseCase,
                             final PesquisarColecaoPorIdUseCase pesquisarColecaoPorIdUseCase,
                             final ExcluirColecaoUseCase excluirColecaoUseCase,
                             final AtualizarColecaoUseCase atualizarColecaoUseCase) {
        this.cadastrarColecaoUseCase = cadastrarColecaoUseCase;
        this.pesquisarColecaoPorIdUseCase = pesquisarColecaoPorIdUseCase;
        this.excluirColecaoUseCase = excluirColecaoUseCase;
        this.atualizarColecaoUseCase = atualizarColecaoUseCase;
    }

    @Override
    public ResponseEntity<?> cadastrarColecao(CadastrarColecaoRequest colecaoRequest) {
        final CadastrarColecaoCommand command = CadastrarColecaoCommand.com(colecaoRequest.nome(), colecaoRequest.alias());

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.badRequest().body(notification);

        final Function<CadastrarColecaoOutput, ResponseEntity<?>> onSuccess = output -> {
            ColecaoResponse response = ColecaoResponse.aPartirDe(
                    output.id().getValue(), output.nome(), output.alias());
            return ResponseEntity.created(URI.create("/v1/colecao/" + output.id().getValue())).body(response);
        };

        return cadastrarColecaoUseCase.executar(command)
                .fold(onError, onSuccess);
    }

    @Override
    public ColecaoResponse pesquisarPorId(String colecaoId) {
        final PesquisarColecaoPorIdOutput output = pesquisarColecaoPorIdUseCase.executar(colecaoId);
        return ColecaoResponse.aPartirDe(output.id(), output.nome(), output.alias());
    }

    @Override
    public void excluirColecaoPorId(String colecaoId) {
        excluirColecaoUseCase.executar(colecaoId);
    }

    @Override
    public ResponseEntity<?> cadastrarColecaoCampo(String colecaoId, AtualizarColecaoRequest request) {
        final AtualizarColecaoCommand command = AtualizarColecaoCommand.com(colecaoId, request.alias());

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.badRequest().body(notification);

        final Function<AtualizarColecaoOutput, ResponseEntity<?>> onSuccess = output -> {
            ColecaoResponse response = ColecaoResponse.aPartirDe(
                    output.id(), output.nome(), output.alias());
            return ResponseEntity.created(URI.create("/v1/colecao/" + output.id())).body(response);
        };

        return atualizarColecaoUseCase.executar(command)
                .fold(onError, onSuccess);
    }
}
