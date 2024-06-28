package com.sistran.fastclaims.infrastructure.api.v1.openApi;

import com.sistran.fastclaims.domain.pagination.Pagination;
import com.sistran.fastclaims.infrastructure.regra.models.AtualizarRegraRequest;
import com.sistran.fastclaims.infrastructure.regra.models.CadastrarRegraRequest;
import com.sistran.fastclaims.infrastructure.regra.models.RegraResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Regras", description = "Gerencia as regras")
public interface RegraControllerOpenApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra uma regra")
    @ApiResponse(responseCode = "201", description = "Regra cadastrado")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<?> cadastrarRegra(@RequestBody @Valid final CadastrarRegraRequest cadastrarRegraRequest);

    @GetMapping("/{regraId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Busca uma regra por ID")
    @ApiResponse(responseCode = "200", description = "Regra encontrada")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "404", description = "Regra não encontrada")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    RegraResponse pesquisarRegraPorId(@PathVariable final String regraId);

    @PutMapping("/{regraId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualiza uma regra")
    @ApiResponse(responseCode = "200", description = "Regra atualizada")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<?> atualizarRegra(@RequestBody @Valid AtualizarRegraRequest atualizarRegraRequest, @PathVariable String regraId);

    @DeleteMapping("/{regraId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Realiza a exclusão de uma regra")
    @ApiResponse(responseCode = "204", description = "Regra excluída")
    @ApiResponse(responseCode = "404", description = "Regra não encontrada")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<Void> excluirRegra(@PathVariable String regraId);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista as regras com base no parâmetro informado")
    @ApiResponse(responseCode = "200", description = "Regras listadas")
    @ApiResponse(responseCode = "400", description = "Parâmetros Inválidos")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    Pagination<RegraResponse> listarRegras(
            @RequestParam(name = "nome", required = false, defaultValue = "") final String nome,
            @RequestParam(name = "pagina", required = false, defaultValue = "0") final int pagina,
            @RequestParam(name = "porPagina", required = false, defaultValue = "10") final int porPagina,
            @RequestParam(name = "ordenarPor", required = false, defaultValue = "nome") final String ordenarPor,
            @RequestParam(name = "ordem", required = false, defaultValue = "asc") final String ordem
    );
}


