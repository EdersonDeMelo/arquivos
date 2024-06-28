package com.sistran.fastclaims.application.regra.pesquisar.id;

import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.regra.Regra;
import com.sistran.fastclaims.domain.regra.RegraGateway;
import com.sistran.fastclaims.domain.regra.RegraID;

import java.util.function.Supplier;

public class DefaultPesquisarRegraPorIdUseCase extends PesquisarRegraPorIdUseCase {

    private final RegraGateway regraGateway;

    public DefaultPesquisarRegraPorIdUseCase(final RegraGateway regraGateway) {
        this.regraGateway = regraGateway;
    }

    @Override
    public PesquisarRegraPorIdOutput executar(final String id) {

        final var regraId = RegraID.from(id);

        return regraGateway.pesquisarPorId(regraId)
                .map(PesquisarRegraPorIdOutput::aPartirDe)
                .orElseThrow(regraNaoEncontrada(regraId));
    }

    private static Supplier<DomainException> regraNaoEncontrada(final RegraID id) {
        return () -> NotFoundException.with(Regra.class, id);
    }
}
