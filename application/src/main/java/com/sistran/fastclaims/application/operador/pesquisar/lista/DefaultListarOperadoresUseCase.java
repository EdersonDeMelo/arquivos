package com.sistran.fastclaims.application.operador.pesquisar.lista;

import com.sistran.fastclaims.domain.operador.OperadorGateway;

import java.util.List;

public class DefaultListarOperadoresUseCase extends ListarOperadoresUseCase {

    private final OperadorGateway operadorGateway;

    public DefaultListarOperadoresUseCase(OperadorGateway operadorGateway) {
        this.operadorGateway = operadorGateway;
    }

    @Override
    public List<ListarOperadoresOutput> execute() {
        return operadorGateway.listar()
                .stream().map(operador -> ListarOperadoresOutput.aPartirDe(
                        operador.getId(),
                        operador.getNome(),
                        operador.getSimbolo(),
                        operador.getTipoOperador())).toList();
    }
}
