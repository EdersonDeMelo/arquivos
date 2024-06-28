package com.sistran.fastclaims.application.dominio.pesquisar.lista;

import com.sistran.fastclaims.application.dominio.pesquisar.nome.ListarDominiosOutput;
import com.sistran.fastclaims.domain.dominio.DominioGateway;

import java.util.List;

public class DefaultListarDominiosUseCase extends ListarDominiosUseCase {

    private final DominioGateway dominioGateway;

    public DefaultListarDominiosUseCase(DominioGateway dominioGateway) {
        this.dominioGateway = dominioGateway;
    }

    @Override
    public List<ListarDominiosOutput> execute() {
        return dominioGateway.listarDominios().stream()
                .map(ListarDominiosOutput::aPartirDe)
                .toList();
    }
}

