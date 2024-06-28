package com.sistran.fastclaims.infrastructure.api.v1.openApi;


import com.sistran.fastclaims.infrastructure.natureza.models.NaturezaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Natureza", description = "Busca de Naturezas")
public interface NaturezaControllerOpenApi {

    @GetMapping("/{naturezaId}")
    @Operation(summary = "Busca uma natureza por ID")
    @ApiResponse(responseCode = "200", description = "Natureza encontrada")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "404", description = "Natureza não encontrada")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    NaturezaResponse buscarNaturezaPorId(@PathVariable final String naturezaId);


    @GetMapping()
    @Operation(summary = "Lista as naturezas com base no parâmetro informado")
    @ApiResponse(responseCode = "200", description = "Naturezas listadas")
    @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    List<NaturezaResponse> listarNaturezas(@RequestParam(name = "termo", required = false, defaultValue = "") final String termo);
}
