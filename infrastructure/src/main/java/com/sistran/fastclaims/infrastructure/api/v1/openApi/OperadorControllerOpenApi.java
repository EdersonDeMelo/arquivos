package com.sistran.fastclaims.infrastructure.api.v1.openApi;

import com.sistran.fastclaims.infrastructure.operador.models.OperadorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Tag(name = "Operadores", description = "Gerencia os operadores")
public interface OperadorControllerOpenApi {

    @GetMapping("/{operadorId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Busca um operador por ID")
    @ApiResponse(responseCode = "200", description = "Operador encontrado")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "404", description = "Operador não encontrado")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    OperadorResponse pesquisarOperadorPorId(@PathVariable final String id);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista todos os operadores")
    @ApiResponse(responseCode = "200", description = "Operadores listados")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    List<OperadorResponse> listarOperadores();
}
