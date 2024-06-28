package com.sistran.fastclaims.application.natureza.pesquisar.id;

import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.natureza.Natureza;
import com.sistran.fastclaims.domain.natureza.NaturezaGateway;
import com.sistran.fastclaims.domain.natureza.NaturezaID;

import java.util.function.Supplier;

public class DefaultPesquisarNaturezaPorIdUseCase extends PesquisarNaturezaPorIdUseCase {

    private final NaturezaGateway naturezaGateway;

    public DefaultPesquisarNaturezaPorIdUseCase(final NaturezaGateway naturezaGateway) {
        this.naturezaGateway = naturezaGateway;
    }

    @Override
    public PesquisarNaturezaPorIdOutput executar(final String id) {

        final NaturezaID naturezaID = NaturezaID.aPartirDe(id);

        return naturezaGateway.pesquisarPorId(naturezaID)
                .map(PesquisarNaturezaPorIdOutput::aPartirDe)
                .orElseThrow(naturezaNaoEncontrada(naturezaID));
    }


    private static Supplier<DomainException> naturezaNaoEncontrada(final NaturezaID id) {
        return () -> NotFoundException.with(Natureza.class, id);
    }
}
