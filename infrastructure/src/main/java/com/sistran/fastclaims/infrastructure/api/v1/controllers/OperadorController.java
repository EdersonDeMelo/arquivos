package com.sistran.fastclaims.infrastructure.api.v1.controllers;

import com.sistran.fastclaims.application.operador.pesquisar.id.PesquisarOperadorPorIdOutput;
import com.sistran.fastclaims.application.operador.pesquisar.id.PesquisarOperadorPorIdUseCase;
import com.sistran.fastclaims.application.operador.pesquisar.lista.ListarOperadoresOutput;
import com.sistran.fastclaims.application.operador.pesquisar.lista.ListarOperadoresUseCase;
import com.sistran.fastclaims.infrastructure.api.v1.openApi.OperadorControllerOpenApi;
import com.sistran.fastclaims.infrastructure.operador.models.OperadorResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/operadores")
public class OperadorController implements OperadorControllerOpenApi {

    private final PesquisarOperadorPorIdUseCase pesquisarOperadorPorIdUseCase;
    private final ListarOperadoresUseCase listarOperadoresUseCase;

    public OperadorController(final PesquisarOperadorPorIdUseCase pesquisarOperadorPorIdUseCase,
                              final ListarOperadoresUseCase listarOperadoresUseCase) {
        this.pesquisarOperadorPorIdUseCase = pesquisarOperadorPorIdUseCase;
        this.listarOperadoresUseCase = listarOperadoresUseCase;
    }

    @Override
    public OperadorResponse pesquisarOperadorPorId(final String operadorId) {
        final PesquisarOperadorPorIdOutput operadorOutput = pesquisarOperadorPorIdUseCase.executar(operadorId);
        return OperadorResponse.aPartirDe(operadorOutput.id().getValue(), operadorOutput.nome(),
                operadorOutput.simbolo(), operadorOutput.tipoOperador());
    }

    @Override
    public List<OperadorResponse> listarOperadores() {
        final List<ListarOperadoresOutput> operadoresOutput = listarOperadoresUseCase.execute();
        return operadoresOutput.stream().map(operador -> OperadorResponse.aPartirDe(operador.id().getValue(),
                operador.nome(), operador.simbolo(), operador.tipoOperador())).toList();
    }
}
