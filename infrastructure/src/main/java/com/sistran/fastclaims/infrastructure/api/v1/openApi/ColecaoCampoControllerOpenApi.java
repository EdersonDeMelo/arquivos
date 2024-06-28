package com.sistran.fastclaims.infrastructure.api.v1.openApi;

import com.sistran.fastclaims.domain.pagination.Pagination;
import com.sistran.fastclaims.infrastructure.colecaocampo.models.CadastrarColecaoCampoRequest;
import com.sistran.fastclaims.infrastructure.colecaocampo.models.ColecaoCampoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Campo da Coleção", description = "Gerencia os campos da coleção")
public interface ColecaoCampoControllerOpenApi {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra um campo da coleção")
    @ApiResponse(responseCode = "201", description = "Campo da coleção cadastrado")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<?> cadastrarColecaoCampo(@RequestBody @Valid final CadastrarColecaoCampoRequest request);

    @GetMapping("/{colecaoCampoId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Busca um campo da coleção por id")
    @ApiResponse(responseCode = "200", description = "Campo da coleção encontrado")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "404", description = "Campo da Coleção não encontrado")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ColecaoCampoResponse pesquisarPorId(@PathVariable final String colecaoCampoId);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista os campos de uma coleção com base no parâmetro informado")
    @ApiResponse(responseCode = "200", description = "Campos da coleção listados")
    @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    Pagination<ColecaoCampoResponse> listarColecaoCampo(
            @RequestParam(name = "termo", required = false, defaultValue = "") final String termo,
            @RequestParam(name = "pagina", required = false, defaultValue = "0") final int pagina,
            @RequestParam(name = "porPagina", required = false, defaultValue = "10") final int porPagina,
            @RequestParam(name = "ordenarPor", required = false, defaultValue = "codigoNatureza") final String ordenarPor,
            @RequestParam(name = "ordem", required = false, defaultValue = "asc") final String ordem
    );

    @DeleteMapping("/{colecaoCampoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui um campo da coleção por ID")
    @ApiResponse(responseCode = "204", description = "Campo excluido")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "404", description = "Campo não encontrado")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    void excluirColecaoPorId(@PathVariable @NotNull final String colecaoCampoId);

}
