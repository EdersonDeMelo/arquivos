package com.sistran.fastclaims.infrastructure.api.v1.controllers;


import com.sistran.fastclaims.application.segmento.atualizar.AtualizarSegmentoCommand;
import com.sistran.fastclaims.application.segmento.atualizar.AtualizarSegmentoOutput;
import com.sistran.fastclaims.application.segmento.atualizar.AtualizarSegmentoUseCase;
import com.sistran.fastclaims.application.segmento.cadastrar.CadastrarSegmentoCommand;
import com.sistran.fastclaims.application.segmento.cadastrar.CadastrarSegmentoOutput;
import com.sistran.fastclaims.application.segmento.cadastrar.CadastrarSegmentoUseCase;
import com.sistran.fastclaims.application.segmento.pesquisar.id.PesquisarSegmentoPorIdOutput;
import com.sistran.fastclaims.application.segmento.pesquisar.id.PesquisarSegmentoPorIdUseCase;
import com.sistran.fastclaims.application.segmento.pesquisar.lista.ListarSegmentosUseCase;
import com.sistran.fastclaims.domain.segmento.SegmentoID;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import com.sistran.fastclaims.infrastructure.api.v1.openApi.SegmentoControllerOpenApi;
import com.sistran.fastclaims.infrastructure.segmento.models.AtualizarSegmentoRequest;
import com.sistran.fastclaims.infrastructure.segmento.models.CadastrarSegmentoRequest;
import com.sistran.fastclaims.infrastructure.segmento.models.SegmentoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/v1/segmentos")
public class SegmentoController implements SegmentoControllerOpenApi {

    private final CadastrarSegmentoUseCase cadastrarSegmentoUseCase;
    private final AtualizarSegmentoUseCase atualizarSegmentoUseCase;
    private final PesquisarSegmentoPorIdUseCase pesquisarSegmentoPorIdUseCase;
    private final ListarSegmentosUseCase listarSegmentosUseCase;

    public SegmentoController(
            final CadastrarSegmentoUseCase cadastrarSegmentoUseCase,
            final AtualizarSegmentoUseCase atualizarSegmentoUseCase,
            final PesquisarSegmentoPorIdUseCase pesquisarSegmentoPorIdUseCase,
            final ListarSegmentosUseCase listarSegmentosUseCase) {
        this.cadastrarSegmentoUseCase = cadastrarSegmentoUseCase;
        this.atualizarSegmentoUseCase = atualizarSegmentoUseCase;
        this.pesquisarSegmentoPorIdUseCase = pesquisarSegmentoPorIdUseCase;
        this.listarSegmentosUseCase = listarSegmentosUseCase;
    }

    @Transactional
    @Override
    public ResponseEntity<?> cadastrarSegmento(CadastrarSegmentoRequest cadastrarSegmentoRequest) {
        final CadastrarSegmentoCommand command = CadastrarSegmentoCommand.com(cadastrarSegmentoRequest.nome());

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.badRequest().body(notification);

        final Function<CadastrarSegmentoOutput, ResponseEntity<?>> onSuccess = output -> {
            SegmentoResponse response = SegmentoResponse.aPartirDe(output.id(), output.nome());
            return ResponseEntity.created(URI.create("/v1/segmentos/" + output.id())).body(response);
        };

        return cadastrarSegmentoUseCase.executar(command).fold(onError, onSuccess);
    }

    @Transactional
    @Override
    public ResponseEntity<?> atualizarSegmento(String segmentoId, AtualizarSegmentoRequest atualizarSegmentoRequest) {
        final AtualizarSegmentoCommand command = AtualizarSegmentoCommand.aPartirDe(SegmentoID.aPartirDe(segmentoId).getValue(), atualizarSegmentoRequest.nome());

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.badRequest().body(notification);

        final Function<AtualizarSegmentoOutput, ResponseEntity<?>> onSuccess = output -> {
            SegmentoResponse response = SegmentoResponse.aPartirDe(output.id(), output.nome());
            return ResponseEntity.ok(response);
        };

        return atualizarSegmentoUseCase.executar(command).fold(onError, onSuccess);
    }

    @Override
    public SegmentoResponse pesquisarSegmentoPorId(String segmentoId) {
        final PesquisarSegmentoPorIdOutput segmentoOutput = pesquisarSegmentoPorIdUseCase.executar(segmentoId);
        return SegmentoResponse.aPartirDe(segmentoOutput.id(), segmentoOutput.nome());
    }

    @Override
    public List<SegmentoResponse> listarSegmentos() {
        return listarSegmentosUseCase.execute()
                .stream()
                .map(segmento -> SegmentoResponse.aPartirDe(segmento.id(), segmento.nome())).toList();
    }
}


