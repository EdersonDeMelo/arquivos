package com.sistran.fastclaims.infrastructure.api.v1.openApi;

import com.sistran.fastclaims.domain.pagination.Pagination;
import com.sistran.fastclaims.infrastructure.fluxo.models.AtualizarFluxoRequest;
import com.sistran.fastclaims.infrastructure.fluxo.models.CadastrarFluxoRequest;
import com.sistran.fastclaims.infrastructure.fluxo.models.FluxoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Fluxos", description = "Gerencia os fluxos")
public interface FluxoControllerOpenApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra um fluxo")
    @ApiResponse(responseCode = "201", description = "Fluxo cadastrado")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<?> cadastrarFluxo(@RequestBody @Valid final CadastrarFluxoRequest fluxoRequest);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista os fluxos com base no parâmetro informado")
    @ApiResponse(responseCode = "200", description = "Fluxos listados")
    @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    Pagination<FluxoResponse> listarFluxos(
            @RequestParam(name = "termo", required = false, defaultValue = "") final String termo,
            @RequestParam(name = "pagina", required = false, defaultValue = "0") final int pagina,
            @RequestParam(name = "porPagina", required = false, defaultValue = "10") final int porPagina,
            @RequestParam(name = "ordenarPor", required = false, defaultValue = "codigoNatureza") final String ordenarPor,
            @RequestParam(name = "ordem", required = false, defaultValue = "asc") final String ordem
    );

    @GetMapping("/{fluxoId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Busca um fluxo por ID")
    @ApiResponse(responseCode = "200", description = "Fluxo encontrado")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "404", description = "Fluxo não encontrado")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    FluxoResponse pesquisarFluxoPorId(@PathVariable final String fluxoId);

    @PutMapping("/{fluxoId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualiza um fluxo")
    @ApiResponse(responseCode = "200", description = "Fluxo atualizado")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "404", description = "Fluxo não encontrado")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<?> atualizarFluxo(@PathVariable final String fluxoId, @RequestBody @Valid final AtualizarFluxoRequest fluxoRequest);

    @DeleteMapping("/{fluxoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui um fluxo por ID")
    @ApiResponse(responseCode = "204", description = "Fluxo excluído")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "404", description = "Fluxo não encontrado")
    ResponseEntity<?> excluirFluxo(@PathVariable final String fluxoId);

}



