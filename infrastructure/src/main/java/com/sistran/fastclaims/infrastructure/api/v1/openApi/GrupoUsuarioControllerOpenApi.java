package com.sistran.fastclaims.infrastructure.api.v1.openApi;

import com.sistran.fastclaims.infrastructure.grupousuario.models.CadastrarGrupoUsuarioRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Grupo de Usuários", description = "Gerencia os grupos de usuários")
public interface GrupoUsuarioControllerOpenApi {

    @PostMapping
    @Operation(summary = "Cadastra um grupo de usuário")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201", description = "Grupo de usuário cadastrado")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<?> cadastrarGrupoUsuario(@RequestBody @Valid final CadastrarGrupoUsuarioRequest request);

    @GetMapping("/{grupoUsuarioId}")
    @Operation(summary = "Lista os grupos de usuários")
    @ApiResponse(responseCode = "200", description = "Grupo de usuário")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "404", description = "Grupo de usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<?> pesquisarGrupoUsuarioPorId(@PathVariable("grupoUsuarioId") final String grupoUsuarioId);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista todos os Domínios ou somente pelo nome")
    @ApiResponse(responseCode = "200", description = "Domínio encontrado")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "404", description = "Domínio não encontrado")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<?> pesquisarGrupoUsuarioPorNome(@RequestParam(value = "nome", required = false, defaultValue = "") final String nome);

}
