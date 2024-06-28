package com.sistran.fastclaims.application.natureza.pesquisar.lista;

import com.sistran.fastclaims.domain.natureza.NaturezaGateway;

import java.util.List;


public class DefaultListarNaturezasUseCase extends ListarNaturezasUseCase {

    private final NaturezaGateway naturezaGateway;

    public DefaultListarNaturezasUseCase(final NaturezaGateway naturezaGateway) {
        this.naturezaGateway = naturezaGateway;
    }

    @Override
    public List<ListarNaturezasOutput> executar(final String termo) {
        return naturezaGateway.listar(termo).stream().map(ListarNaturezasOutput::aPartirDe).toList();
    }
}
