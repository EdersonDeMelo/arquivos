package com.sistran.fastclaims.infrastructure.api.v1.openApi;

import com.sistran.fastclaims.infrastructure.colecao.models.AtualizarColecaoRequest;
import com.sistran.fastclaims.infrastructure.colecao.models.CadastrarColecaoRequest;
import com.sistran.fastclaims.infrastructure.colecao.models.ColecaoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Coleção", description = "Gerencia as coleções")
public interface ColecaoControllerOpenApi {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra uma coleção")
    @ApiResponse(responseCode = "201", description = "Coleção cadastrada")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<?> cadastrarColecao(@RequestBody @Valid final CadastrarColecaoRequest colecaoRequest);

    @GetMapping("/{colecaoId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Busca uma coleção por ID")
    @ApiResponse(responseCode = "200", description = "Coleção encontrada")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "404", description = "Coleção não encontrada")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ColecaoResponse pesquisarPorId(@PathVariable @NotNull final String colecaoId);

    @DeleteMapping("/{colecaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui uma coleção por ID")
    @ApiResponse(responseCode = "204", description = "Coleção excluida")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "404", description = "Coleção não encontrada")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    void excluirColecaoPorId(@PathVariable @NotNull final String colecaoId);

    @PutMapping("/{colecaoId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualiza o campo 'alias' da coleção")
    @ApiResponse(responseCode = "200", description = "Campo 'alias' da coleção atualizado")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<?> cadastrarColecaoCampo(@PathVariable final String colecaoId, @RequestBody @Valid final AtualizarColecaoRequest request);

}
