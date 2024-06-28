package com.sistran.fastclaims.infrastructure.api.v1.controllers;

import com.sistran.fastclaims.application.colecao.pesquisar.id.PesquisarColecaoPorIdOutput;
import com.sistran.fastclaims.application.colecao.pesquisar.id.PesquisarColecaoPorIdUseCase;
import com.sistran.fastclaims.application.grupousuario.cadastrar.CadastrarGrupoUsuarioCommand;
import com.sistran.fastclaims.application.grupousuario.cadastrar.CadastrarGrupoUsuarioOutput;
import com.sistran.fastclaims.application.grupousuario.cadastrar.CadastrarGrupoUsuarioUseCase;
import com.sistran.fastclaims.application.grupousuario.pesquisar.id.PesquisarGrupoUsuarioPorIdOutput;
import com.sistran.fastclaims.application.grupousuario.pesquisar.id.PesquisarGrupoUsuarioPorIdUseCase;
import com.sistran.fastclaims.application.grupousuario.pesquisar.nome.ListarGrupoUsuarioPorNomeOutput;
import com.sistran.fastclaims.application.grupousuario.pesquisar.nome.PesquisarGrupoUsuarioPorNomeUseCase;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import com.sistran.fastclaims.infrastructure.api.v1.openApi.GrupoUsuarioControllerOpenApi;
import com.sistran.fastclaims.infrastructure.grupousuario.models.CadastrarGrupoUsuarioRequest;
import com.sistran.fastclaims.infrastructure.grupousuario.models.GrupoUsuarioResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.function.Function;

@RestController
@RequestMapping("/v1/grupos-usuarios")
public class GrupoUsuarioController implements GrupoUsuarioControllerOpenApi {

    private final CadastrarGrupoUsuarioUseCase cadastrarGrupoUsuarioUseCase;
    private final PesquisarGrupoUsuarioPorIdUseCase pesquisarGrupoUsuarioPorIdUseCase;
    private final PesquisarGrupoUsuarioPorNomeUseCase pesquisarGrupoUsuarioPorNomeUseCase;

    public GrupoUsuarioController(CadastrarGrupoUsuarioUseCase cadastrarGrupoUsuarioUseCase, PesquisarGrupoUsuarioPorIdUseCase pesquisarGrupoUsuarioPorIdUseCase, PesquisarGrupoUsuarioPorNomeUseCase pesquisarGrupoUsuarioPorNomeUseCase) {
        this.cadastrarGrupoUsuarioUseCase = cadastrarGrupoUsuarioUseCase;
        this.pesquisarGrupoUsuarioPorIdUseCase = pesquisarGrupoUsuarioPorIdUseCase;
        this.pesquisarGrupoUsuarioPorNomeUseCase = pesquisarGrupoUsuarioPorNomeUseCase;
    }

    @Override
    public ResponseEntity<?> cadastrarGrupoUsuario(CadastrarGrupoUsuarioRequest request) {

        final CadastrarGrupoUsuarioCommand command = CadastrarGrupoUsuarioCommand.com(request.nome());

        final Function<Notification, ResponseEntity<?>> onError = notification -> ResponseEntity.badRequest().body(notification.getErrors());

        final Function<CadastrarGrupoUsuarioOutput, ResponseEntity<?>> onSuccess = output -> {
            GrupoUsuarioResponse response = GrupoUsuarioResponse.aPartirDe(
                    output.id().getValue(),
                    output.nome(),
                    output.dataCadastro(),
                    output.dataAlteracao(),
                    output.ativo());
            return ResponseEntity.created(URI.create("/v1/grupos-usuarios/" + output.id())).body(response);
        };
        return cadastrarGrupoUsuarioUseCase.executar(command).fold(onError, onSuccess);
    }

    @Override
    public ResponseEntity<?> pesquisarGrupoUsuarioPorId(String grupoUsuarioId) {

        final Function<Notification, ResponseEntity<?>> onError = notification -> ResponseEntity.badRequest().body(notification.getErrors());

        final Function<PesquisarGrupoUsuarioPorIdOutput, ResponseEntity<?>> onSuccess = output -> {
            GrupoUsuarioResponse response = GrupoUsuarioResponse.aPartirDe(
                    output.id().getValue(),
                    output.nome(),
                    output.dataCadastro(),
                    output.dataAtualizacao(),
                    output.ativo());
            return ResponseEntity.ok(response);
        };

        return pesquisarGrupoUsuarioPorIdUseCase.executar(grupoUsuarioId).fold(onError, onSuccess);
    }

    @Override
    public ResponseEntity<?> pesquisarGrupoUsuarioPorNome(String nome) {
        final Function<Notification, ResponseEntity<?>> onError = notification -> ResponseEntity.badRequest().body(notification.getErrors());

        final Function<ListarGrupoUsuarioPorNomeOutput, ResponseEntity<?>> onSuccess = output -> {
            GrupoUsuarioResponse response = GrupoUsuarioResponse.aPartirDe(
                    output.id().getValue(),
                    output.nome(),
                    output.dataCadastro(),
                    output.dataAtualizacao(),
                    output.ativo());
            return ResponseEntity.ok(response);
        };

        return pesquisarGrupoUsuarioPorNomeUseCase.executar(nome).fold(onError, onSuccess);
    }
}
