package com.sistran.fastclaims.infrastructure.api.v1.openApi;

import com.sistran.fastclaims.domain.pagination.Pagination;
import com.sistran.fastclaims.infrastructure.trilha.models.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Trilhas", description = "Gerencia as trilhas")
public interface TrilhaControllerOpenApi {

    @GetMapping("/{trilhaId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Busca uma trilha por ID")
    @ApiResponse(responseCode = "200", description = "Trilha encontrada")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "404", description = "Trilha não encontrada")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    TrilhaResponse pesquisarTrilhaPorId(@PathVariable String trilhaId);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista as trilhas com base no parâmetro informado")
    @ApiResponse(responseCode = "200", description = "Trilhas listadas")
    @ApiResponse(responseCode = "400", description = "Parâmetros Inválidos")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    Pagination<TrilhaResponse> listarTrilhas(
            @RequestParam(name = "termo", required = false, defaultValue = "") String termo,
            @RequestParam(name = "pagina", required = false, defaultValue = "0") final int pagina,
            @RequestParam(name = "porPagina", required = false, defaultValue = "10") final int porPagina,
            @RequestParam(name = "ordenarPor", required = false, defaultValue = "codigoNatureza") final String ordenarPor,
            @RequestParam(name = "ordem", required = false, defaultValue = "asc") final String ordem
    );

    @PutMapping("/{trilhaId}/ativacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Realiza a ativação de uma trilha")
    @ApiResponse(responseCode = "204", description = "Trilha ativada")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "404", description = "Trilha não encontrada")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<Void> ativarTrilha(@PathVariable String trilhaId);

    @PutMapping("/{trilhaId}/desativacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Realiza a desativação de uma trilha")
    @ApiResponse(responseCode = "204", description = "Trilha desativada")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "404", description = "Trilha não encontrada")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<Void> desativarTrilha(@PathVariable String trilhaId);

    @DeleteMapping("/{trilhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Realiza a exclusão de uma trilha")
    @ApiResponse(responseCode = "204", description = "Trilha excluída")
    @ApiResponse(responseCode = "404", description = "Trilha não encontrada")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<Void> excluirTrilha(@PathVariable String trilhaId);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra uma trilha")
    @ApiResponse(responseCode = "201", description = "Trilha cadastrada")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<?> cadastrarTrilha(@RequestBody @Valid CadastrarTrilhaRequest trilhaRequest);

    @PostMapping("/regra")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra uma regra em uma trilha")
    @ApiResponse(responseCode = "201", description = "Regra cadastrado")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<?> cadastrarRegraTrilha(@RequestBody @Valid final CadastrarRegraTrilhaRequest cadastrarRegraTrilhaRequest);

    @PutMapping("/{trilhaId}/regra/{regraId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualiza a regra de uma trilha")
    @ApiResponse(responseCode = "200", description = "Regra da trilha atualizada")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<?> atualizarRegraTrilha(@RequestBody @Valid AtualizarRegraTrilhaRequest atualizarRegraTrilhaRequest, @PathVariable String trilhaId, @PathVariable String regraId);

    @PutMapping("/{trilhaId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualizar uma trilha")
    @ApiResponse(responseCode = "200", description = "Trilha editada")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<?> atualizarTrilha(@RequestBody @Valid AtualizarTrilhaRequest atualizarTrilhaRequest, @PathVariable String trilhaId);

    @DeleteMapping("/{trilhaId}/regra/{regraId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Realiza a exclusão de uma regra da trilha")
    @ApiResponse(responseCode = "204", description = "Regra da trilha excluída")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "404", description = "Trilha ou regra não encontrada")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<Void> excluirRegraTrilha(@PathVariable String trilhaId, @PathVariable String regraId);

    @PutMapping("/{trilhaId}/regra/{regraId}/ativacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Realiza a ativação da regra de uma trilha")
    @ApiResponse(responseCode = "204", description = "Regra da trilha ativada")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "404", description = "Trilha não encontrada")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<Void> ativarRegraTrilha(@PathVariable String trilhaId, @PathVariable String regraId);

    @PutMapping("/{trilhaId}/regra/{regraId}/desativacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Realiza a desativação da regra de uma trilha")
    @ApiResponse(responseCode = "204", description = "Regra da trilha desativada")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "404", description = "Trilha não encontrada")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<Void> desativarRegraTrilha(@PathVariable String trilhaId, @PathVariable String regraId);
}
