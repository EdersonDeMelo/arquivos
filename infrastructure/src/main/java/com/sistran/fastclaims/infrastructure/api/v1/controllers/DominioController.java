package com.sistran.fastclaims.infrastructure.api.v1.controllers;

import com.sistran.fastclaims.application.dominio.atualizar.AtualizarDominioCommand;
import com.sistran.fastclaims.application.dominio.atualizar.AtualizarDominioOutput;
import com.sistran.fastclaims.application.dominio.atualizar.AtualizarDominioUseCase;
import com.sistran.fastclaims.application.dominio.cadastrar.CadastrarDominioCommand;
import com.sistran.fastclaims.application.dominio.cadastrar.CadastrarDominioOutput;
import com.sistran.fastclaims.application.dominio.cadastrar.CadastrarDominioUseCase;
import com.sistran.fastclaims.application.dominio.pesquisar.id.PesquisarDominioPorIdOutput;
import com.sistran.fastclaims.application.dominio.pesquisar.id.PesquisarDominioPorIdUseCase;
import com.sistran.fastclaims.application.dominio.pesquisar.lista.ListarDominiosUseCase;
import com.sistran.fastclaims.application.dominio.pesquisar.nome.ListarDominiosOutput;
import com.sistran.fastclaims.application.dominio.pesquisar.nome.PesquisarDominioPorNomeUseCase;
import com.sistran.fastclaims.application.dominiovalor.cadastrar.CadastrarDominioValorCommand;
import com.sistran.fastclaims.application.dominiovalor.cadastrar.CadastrarDominioValorOutput;
import com.sistran.fastclaims.application.dominiovalor.cadastrar.CadastrarDominioValorUseCase;
import com.sistran.fastclaims.application.dominiovalor.excluir.ExcluirDominioValorCommand;
import com.sistran.fastclaims.application.dominiovalor.excluir.ExcluirDominioValorUseCase;
import com.sistran.fastclaims.domain.dominio.DominioID;
import com.sistran.fastclaims.domain.dominiovalor.DominioValor;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import com.sistran.fastclaims.infrastructure.api.v1.openApi.DominioControllerOpenApi;
import com.sistran.fastclaims.infrastructure.dominio.models.*;
import com.sistran.fastclaims.infrastructure.dominiovalor.models.CadastrarDominioValorRequest;
import com.sistran.fastclaims.infrastructure.dominiovalor.models.DominioValorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/v1/dominios")
public class DominioController implements DominioControllerOpenApi {

    private final CadastrarDominioUseCase cadastrarDominioUseCase;
    private final PesquisarDominioPorIdUseCase pesquisarDominioPorIdUseCase;
    private final PesquisarDominioPorNomeUseCase pesquisarDominioPorNomeUseCase;
    private final ListarDominiosUseCase listarDominiosUseCase;
    private final AtualizarDominioUseCase atualizarDominioUseCase;
    private final CadastrarDominioValorUseCase cadastrarDominioValorUseCase;
    private final ExcluirDominioValorUseCase excluirDominioValorUseCase;

    public DominioController(final CadastrarDominioUseCase cadastrarDominioUseCase, PesquisarDominioPorIdUseCase pesquisarDominioPorIdUseCase, PesquisarDominioPorNomeUseCase pesquisarDominioPorNomeUseCase, ListarDominiosUseCase listarDominiosUseCase, AtualizarDominioUseCase atualizarDominioUseCase, CadastrarDominioValorUseCase cadastrarDominioValorUseCase, ExcluirDominioValorUseCase excluirDominioValorUseCase) {
        this.cadastrarDominioUseCase = cadastrarDominioUseCase;
        this.pesquisarDominioPorIdUseCase = pesquisarDominioPorIdUseCase;
        this.pesquisarDominioPorNomeUseCase = pesquisarDominioPorNomeUseCase;
        this.listarDominiosUseCase = listarDominiosUseCase;
        this.atualizarDominioUseCase = atualizarDominioUseCase;
        this.cadastrarDominioValorUseCase = cadastrarDominioValorUseCase;
        this.excluirDominioValorUseCase = excluirDominioValorUseCase;
    }

    @Override
    public ResponseEntity<?> cadastrarDominio(CadastrarDominioRequest dominioRequest) {

        final CadastrarDominioCommand command = CadastrarDominioCommand.com(dominioRequest.nome(),
                dominioRequest.descricao(),
                dominioRequest.valores().stream().map(v -> DominioValor.novoDominioValor(v.codigoValor(), v.nomeValor(),
                        DominioID.from(""))).toList());

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.badRequest().body(notification);

        final Function<CadastrarDominioOutput, ResponseEntity<?>> onSuccess = output -> {
            DominioResponse response = DominioResponse.aPartirDe(
                    output.id().getValue(),
                    output.nome(),
                    output.descricao(),
                    output.dataCadastro(),
                    output.valores().stream().map(v -> DominioValorResponse.aPartirDe(
                            v.getId().getValue(),
                            v.getCodigoValor(),
                            v.getNomeValor())).toList());
            return ResponseEntity.created(URI.create("/v1/dominios/" + output.id().getValue())).body(response);
        };

        return cadastrarDominioUseCase.executar(command).fold(onError, onSuccess);
    }

    @Override
    public ResponseEntity<?> pesquisarDominioPorId(final String dominioId) {

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.notFound().build();

        final Function<PesquisarDominioPorIdOutput, ResponseEntity<?>> onSuccess = output -> {
            DominioResponse response = DominioResponse.aPartirDe(
                    output.id().getValue(),
                    output.nome(),
                    output.descricao(),
                    output.dataCadastro(),
                    output.valores().stream().map(v -> DominioValorResponse.aPartirDe(
                            v.getId().getValue(),
                            v.getCodigoValor(),
                            v.getNomeValor()
                    )).toList());
            return ResponseEntity.ok(response);
        };
        return pesquisarDominioPorIdUseCase.executar(dominioId).fold(onError, onSuccess);
    }

    @Override
    public ResponseEntity<?> pesquisarDominioPorNome(String nome) {
        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.notFound().build();

        final Function<ListarDominiosOutput, ResponseEntity<?>> onSuccess = output -> {
            ListarDominiosResponse response = ListarDominiosResponse.aPartirDe(
                    output.id().getValue(),
                    output.nome(),
                    output.descricao());
            return ResponseEntity.ok(response);
        };
        if (nome.isEmpty()) {
            final List<ListarDominiosResponse> listarDominios = listarDominiosUseCase.execute().stream()
                    .map(output -> ListarDominiosResponse.aPartirDe(
                            output.id().getValue(),
                            output.nome(),
                            output.descricao()))
                    .toList();
            return ResponseEntity.ok(listarDominios);
        }
        return pesquisarDominioPorNomeUseCase.executar(nome).fold(onError, onSuccess);
    }

    @Override
    public ResponseEntity<?> atualizarDominio(String dominioId, AtualizarDominioRequest request) {
        final AtualizarDominioCommand command = AtualizarDominioCommand.com(dominioId, request.nome(), request.descricao());

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.badRequest().body(notification);

        final Function<AtualizarDominioOutput, ResponseEntity<?>> onSuccess = output -> {
            AtualizarDominioResponse response = AtualizarDominioResponse.aPartirDe(
                    output.id().getValue(),
                    output.nome(),
                    output.descricao(),
                    output.dataCadastro(),
                    output.dataAlteracao());
            return ResponseEntity.ok(response);
        };

        return atualizarDominioUseCase.executar(command).fold(onError, onSuccess);

    }

    @Override
    public ResponseEntity<?> cadastrarDominioValores(String dominioId, CadastrarDominioValorRequest request) {

        final CadastrarDominioValorCommand command = CadastrarDominioValorCommand.com(
                DominioID.from(dominioId),
                request.codigoValor(),
                request.nomeValor());

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.badRequest().body(notification);

        final Function<CadastrarDominioValorOutput, ResponseEntity<?>> onSuccess = output -> {
            DominioValorResponse response = DominioValorResponse.aPartirDe(
                    output.id().getValue(),
                    output.valores().get(output.valores().size() - 1).getCodigoValor(),
                    output.valores().get(output.valores().size() - 1).getNomeValor());
            return ResponseEntity.ok(response);
        };

        return cadastrarDominioValorUseCase.executar(command).fold(onError, onSuccess);
    }

    @Override
    public ResponseEntity<?> excluirDominioValor(String dominioId, String dominioValorId) {

        final ExcluirDominioValorCommand command = ExcluirDominioValorCommand.com(
                dominioId,
                dominioValorId);

        excluirDominioValorUseCase.executar(command);
        return ResponseEntity.noContent().build();
    }
}
