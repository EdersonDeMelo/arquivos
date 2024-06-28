package com.sistran.fastclaims.infrastructure.api.v1.openApi;

import com.sistran.fastclaims.infrastructure.dominio.models.AtualizarDominioRequest;
import com.sistran.fastclaims.infrastructure.dominio.models.CadastrarDominioRequest;
import com.sistran.fastclaims.infrastructure.dominiovalor.models.CadastrarDominioValorRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Dominios", description = "Gerencia os domínios")
public interface DominioControllerOpenApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra um domínio")
    @ApiResponse(responseCode = "201", description = "Domínio cadastrado")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<?> cadastrarDominio(@RequestBody @Valid final CadastrarDominioRequest request);

    @GetMapping("/{dominioId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Busca um domínio por ID")
    @ApiResponse(responseCode = "200", description = "Domínio encontrado")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "404", description = "Domínio não encontrado")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<?> pesquisarDominioPorId(@PathVariable("dominioId") final String dominioId);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista todos os Domínios ou somente pelo nome")
    @ApiResponse(responseCode = "200", description = "Domínio encontrado")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "404", description = "Domínio não encontrado")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<?> pesquisarDominioPorNome(@RequestParam(value = "nome", required = false, defaultValue = "") final String nome);

    @PutMapping("/{dominioId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualiza um domínio")
    @ApiResponse(responseCode = "200", description = "Domínio atualizado")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "404", description = "Domínio não encontrado")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<?> atualizarDominio(@PathVariable("dominioId") final String dominioId,
                                       @RequestBody @Valid final AtualizarDominioRequest request);

    @PutMapping("/{dominioId}/valores")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Cadastra valores em um domínio")
    @ApiResponse(responseCode = "200", description = "Valores do domínio atualizados")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "404", description = "Domínio não encontrado")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<?> cadastrarDominioValores(@PathVariable("dominioId") final String dominioId,
                                             @RequestBody @Valid final CadastrarDominioValorRequest request);

    @DeleteMapping("/{dominioId}/valores/{dominioValorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui um valor de um domínio")
    @ApiResponse(responseCode = "204", description = "Valor excluído")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "404", description = "Domínio ou valor não encontrado")
    @ApiResponse(responseCode = "500", description = "Ocorreu um erro no servidor")
    ResponseEntity<?> excluirDominioValor(@PathVariable("dominioId") final String dominioId,
                                         @PathVariable("dominioValorId") final String dominioValorId);

}
