package com.sistran.fastclaims.infrastructure.api.v1.controllers;

import com.sistran.fastclaims.application.trilha.ativar.AtivarTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.ativar.regra_trilha.AtivarRegraTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.atualizar.AtualizarTrilhaCommand;
import com.sistran.fastclaims.application.trilha.atualizar.AtualizarTrilhaOutput;
import com.sistran.fastclaims.application.trilha.atualizar.AtualizarTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.atualizar.regra_trilha.AtualizarRegraTrilhaCommand;
import com.sistran.fastclaims.application.trilha.atualizar.regra_trilha.AtualizarRegraTrilhaOutput;
import com.sistran.fastclaims.application.trilha.atualizar.regra_trilha.AtualizarRegraTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.cadastrar.CadastrarTrilhaCommand;
import com.sistran.fastclaims.application.trilha.cadastrar.CadastrarTrilhaOutput;
import com.sistran.fastclaims.application.trilha.cadastrar.CadastrarTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.cadastrar.regra_trilha.CadastrarRegraTrilhaCommand;
import com.sistran.fastclaims.application.trilha.cadastrar.regra_trilha.CadastrarRegraTrilhaOutput;
import com.sistran.fastclaims.application.trilha.cadastrar.regra_trilha.CadastrarRegraTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.desativar.DesativarTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.desativar.regra_trilha.DesativarRegraTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.excluir.ExcluirTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.excluir.regra_trilha.ExcluirRegraTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.pesquisar.id.PesquisarTrilhaPorIdOutput;
import com.sistran.fastclaims.application.trilha.pesquisar.id.PesquisarTrilhaPorIdUseCase;
import com.sistran.fastclaims.application.trilha.pesquisar.lista.ListarTrilhasUseCase;
import com.sistran.fastclaims.domain.pagination.Pagination;
import com.sistran.fastclaims.domain.pagination.SearchQuery;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import com.sistran.fastclaims.infrastructure.api.v1.openApi.TrilhaControllerOpenApi;
import com.sistran.fastclaims.infrastructure.trilha.models.*;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.function.Function;

@RestController
@RequestMapping("/v1/trilhas")
public class TrilhaController implements TrilhaControllerOpenApi {

    private final PesquisarTrilhaPorIdUseCase pesquisarTrilhaPorIdUseCase;
    private final ListarTrilhasUseCase pesquisarTrilhaPorNomeUseCase;
    private final AtivarTrilhaUseCase ativarTrilhaUseCase;
    private final DesativarTrilhaUseCase desativarTrilhaUseCase;
    private final ExcluirTrilhaUseCase excluirTrilhaUseCase;
    private final CadastrarTrilhaUseCase cadastrarTrilhaUseCase;
    private final AtualizarTrilhaUseCase atualizarTrilhaUseCase;
    private final CadastrarRegraTrilhaUseCase cadastrarRegraTrilhaUseCase;
    private final AtualizarRegraTrilhaUseCase atualizarRegraTrilhaUseCase;
    private final ExcluirRegraTrilhaUseCase excluirRegraTrilhaUseCase;
    private final AtivarRegraTrilhaUseCase ativarRegraTrilhaUseCase;
    private final DesativarRegraTrilhaUseCase desativarRegraTrilhaUseCase;

    public TrilhaController(final PesquisarTrilhaPorIdUseCase pesquisarTrilhaPorIdUseCase,
                            final ListarTrilhasUseCase pesquisarTrilhaPorNomeUseCase,
                            final AtivarTrilhaUseCase ativarTrilhaUseCase,
                            final DesativarTrilhaUseCase desativarTrilhaUseCase,
                            final ExcluirTrilhaUseCase excluirTrilhaUseCase,
                            final CadastrarTrilhaUseCase cadastrarTrilhaUseCase,
                            final AtualizarTrilhaUseCase atualizarTrilhaUseCase,
                            final CadastrarRegraTrilhaUseCase cadastrarRegraTrilhaUseCase,
                            final AtualizarRegraTrilhaUseCase atualizarRegraTrilhaUseCase,
                            final ExcluirRegraTrilhaUseCase excluirRegraTrilhaUseCase,
                            final AtivarRegraTrilhaUseCase ativarRegraTrilhaUseCase,
                            final DesativarRegraTrilhaUseCase desativarRegraTrilhaUseCase
    ) {
        this.pesquisarTrilhaPorIdUseCase = pesquisarTrilhaPorIdUseCase;
        this.pesquisarTrilhaPorNomeUseCase = pesquisarTrilhaPorNomeUseCase;
        this.ativarTrilhaUseCase = ativarTrilhaUseCase;
        this.desativarTrilhaUseCase = desativarTrilhaUseCase;
        this.excluirTrilhaUseCase = excluirTrilhaUseCase;
        this.cadastrarTrilhaUseCase = cadastrarTrilhaUseCase;
        this.atualizarTrilhaUseCase = atualizarTrilhaUseCase;
        this.cadastrarRegraTrilhaUseCase = cadastrarRegraTrilhaUseCase;
        this.atualizarRegraTrilhaUseCase = atualizarRegraTrilhaUseCase;
        this.excluirRegraTrilhaUseCase = excluirRegraTrilhaUseCase;
        this.ativarRegraTrilhaUseCase = ativarRegraTrilhaUseCase;
        this.desativarRegraTrilhaUseCase = desativarRegraTrilhaUseCase;
    }

    @Override
    public TrilhaResponse pesquisarTrilhaPorId(final String trilhaId) {
        final PesquisarTrilhaPorIdOutput output = pesquisarTrilhaPorIdUseCase.executar(trilhaId);
        return TrilhaResponse.aPartirDe(output.id().getValue(), output.nome(), output.descricao(), output.ativo(), output.fluxoId());
    }

    @Override
    public Pagination<TrilhaResponse> listarTrilhas(final String termo, final int pagina, final int porPagina, final String ordenarPor, final String ordem) {
        return pesquisarTrilhaPorNomeUseCase.executar(new SearchQuery(pagina, porPagina, termo, ordenarPor, ordem))
                .map(TrilhaResponse::aPartirDe);
    }

    @Transactional
    @Override
    public ResponseEntity<Void> ativarTrilha(final String trilhaId) {
        ativarTrilhaUseCase.executar(trilhaId);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @Override
    public ResponseEntity<Void> desativarTrilha(final String trilhaId) {
        desativarTrilhaUseCase.executar(trilhaId);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @Override
    public ResponseEntity<Void> excluirTrilha(final String trilhaId) {
        excluirTrilhaUseCase.executar(trilhaId);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @Override
    public ResponseEntity<?> cadastrarTrilha(final CadastrarTrilhaRequest trilhaRequest) {
        final CadastrarTrilhaCommand command = CadastrarTrilhaCommand.com(trilhaRequest.nome(), trilhaRequest.descricao(), trilhaRequest.fluxoId());

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.badRequest().body(notification);

        final Function<CadastrarTrilhaOutput, ResponseEntity<?>> onSuccess = output -> {
            TrilhaResponse response = TrilhaResponse.aPartirDe(output);
            return ResponseEntity.created(URI.create("/v1/trilhas/" + output.id())).body(response);
        };

        return cadastrarTrilhaUseCase.executar(command).fold(onError, onSuccess);
    }

    @Transactional
    @Override
    public ResponseEntity<?> cadastrarRegraTrilha(CadastrarRegraTrilhaRequest cadastrarRegraTrilhaRequest) {
        final CadastrarRegraTrilhaCommand cadastrarRegraTrilhaCommand = CadastrarRegraTrilhaCommand.aPartirDe(
                cadastrarRegraTrilhaRequest.regraId(),
                cadastrarRegraTrilhaRequest.nome(),
                cadastrarRegraTrilhaRequest.descricao(),
                cadastrarRegraTrilhaRequest.campoUm(),
                cadastrarRegraTrilhaRequest.operadorUm() == null ? "" : cadastrarRegraTrilhaRequest.operadorUm(),
                cadastrarRegraTrilhaRequest.campoDois() == null ? "" : cadastrarRegraTrilhaRequest.campoDois(),
                cadastrarRegraTrilhaRequest.operadorDois(),
                cadastrarRegraTrilhaRequest.campoTres(),
                cadastrarRegraTrilhaRequest.resultadoEsperado(),
                cadastrarRegraTrilhaRequest.tipoAcao(),
                cadastrarRegraTrilhaRequest.ativa(),
                cadastrarRegraTrilhaRequest.trilhaId()
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.badRequest().body(notification);

        final Function<CadastrarRegraTrilhaOutput, ResponseEntity<?>> onSuccess = output -> {
            CadastrarRegraTrilhaResponse response = CadastrarRegraTrilhaResponse.aPartirDe(
                    output.id(),
                    output.nome(),
                    output.descricao(),
                    output.campoUm(),
                    output.operadorUm(),
                    output.campoDois(),
                    output.operadorDois(),
                    output.campoTres(),
                    output.resultadoEsperado(),
                    output.tipoAcao(),
                    output.ativa(),
                    cadastrarRegraTrilhaRequest.trilhaId()
            );
            return ResponseEntity.created(URI.create("/v1/trilhas/regra/" + output.id())).body(response);
        };

        return cadastrarRegraTrilhaUseCase.executar(cadastrarRegraTrilhaCommand).fold(onError, onSuccess);
    }

    @Transactional
    @Override
    public ResponseEntity<?> atualizarRegraTrilha(AtualizarRegraTrilhaRequest atualizarRegraTrilhaRequest, String trilhaId, String regraId) {
        final AtualizarRegraTrilhaCommand command = AtualizarRegraTrilhaCommand.aPartirDe(
                regraId,
                trilhaId,
                atualizarRegraTrilhaRequest.resultadoEsperado(),
                atualizarRegraTrilhaRequest.tipoAcao(),
                atualizarRegraTrilhaRequest.ativa()
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.badRequest().body(notification);

        final Function<AtualizarRegraTrilhaOutput, ResponseEntity<?>> onSuccess = output -> {
            AtualizarRegraTrilhaResponse response = AtualizarRegraTrilhaResponse.aPartirDe(
                    output.regraId(),
                    trilhaId,
                    output.resultadoEsperado(),
                    output.tipoAcao(),
                    output.ativa()
            );
            return ResponseEntity.created(URI.create("/v1/trilhas/" + trilhaId + "/regra/" + output.regraId())).body(response);
        };

        return atualizarRegraTrilhaUseCase.executar(command).fold(onError, onSuccess);
    }

    @Transactional
    public ResponseEntity<?> atualizarTrilha(AtualizarTrilhaRequest atualizarTrilhaRequest, final String trilhaId) {
        final AtualizarTrilhaCommand command = AtualizarTrilhaCommand.com(trilhaId, atualizarTrilhaRequest.nome(), atualizarTrilhaRequest.descricao());

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.badRequest().body(notification);

        final Function<AtualizarTrilhaOutput, ResponseEntity<?>> onSuccess = output -> {
            TrilhaResponse response = TrilhaResponse.aPartirDe(output);
            return ResponseEntity.created(URI.create("/v1/trilhas/" + output.id())).body(response);
        };

        return atualizarTrilhaUseCase.executar(command).fold(onError, onSuccess);
    }

    @Transactional
    @Override
    public ResponseEntity<Void> excluirRegraTrilha(String trilhaId, String regraId) {
        excluirRegraTrilhaUseCase.executar(trilhaId, regraId);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @Override
    public ResponseEntity<Void> ativarRegraTrilha(String trilhaId, String regraId) {
        ativarRegraTrilhaUseCase.executar(trilhaId, regraId);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @Override
    public ResponseEntity<Void> desativarRegraTrilha(String trilhaId, String regraId) {
        desativarRegraTrilhaUseCase.executar(trilhaId, regraId);
        return ResponseEntity.noContent().build();
    }
}

