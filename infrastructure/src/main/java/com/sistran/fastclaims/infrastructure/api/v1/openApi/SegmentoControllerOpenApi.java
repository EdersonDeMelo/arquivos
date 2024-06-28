package com.sistran.fastclaims.infrastructure.api.v1.openApi;

import com.sistran.fastclaims.infrastructure.segmento.models.AtualizarSegmentoRequest;
import com.sistran.fastclaims.infrastructure.segmento.models.CadastrarSegmentoRequest;
import com.sistran.fastclaims.infrastructure.segmento.models.SegmentoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Segmentos", description = "Gerencia os segmentos")
public interface SegmentoControllerOpenApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra um seguimento")
    @ApiResponse(responseCode = "201", description = "Segmento cadastrado")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<?> cadastrarSegmento(@RequestBody @Valid final CadastrarSegmentoRequest cadastrarSegmentoRequest);

    @PutMapping("/{segmentoId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualiza um segmento")
    @ApiResponse(responseCode = "200", description = "Segmento atualizado")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<?> atualizarSegmento(@PathVariable final String segmentoId, @RequestBody @Valid final AtualizarSegmentoRequest atualizarSegmentoRequest);

    @GetMapping("/{segmentoId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Busca um segmento por ID")
    @ApiResponse(responseCode = "200", description = "Segmento encontrado")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "404", description = "Segmento não encontrado")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    SegmentoResponse pesquisarSegmentoPorId(@PathVariable final String segmentoId);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista todos os segmentos")
    @ApiResponse(responseCode = "200", description = "Segmentos listados")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    List<SegmentoResponse> listarSegmentos();
}
