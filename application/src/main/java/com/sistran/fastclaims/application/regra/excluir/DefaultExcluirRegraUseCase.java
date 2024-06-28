package com.sistran.fastclaims.application.regra.excluir;

import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.regra.Regra;
import com.sistran.fastclaims.domain.regra.RegraGateway;
import com.sistran.fastclaims.domain.regra.RegraID;
import com.sistran.fastclaims.domain.validation.Error;

import java.util.function.Supplier;

public class DefaultExcluirRegraUseCase extends ExcluirRegraUseCase {

    private final RegraGateway regraGateway;

    public DefaultExcluirRegraUseCase(RegraGateway regraGateway) {
        this.regraGateway = regraGateway;
    }

    @Override
    public void executar(final String regraId) {

        final var regra = regraGateway.pesquisarPorId(RegraID.from(regraId))
                .orElseThrow(regraNaoEncontrada(RegraID.from(regraId)));

        if (!regra.getTrilhas().isEmpty() && regra.getTrilhas() != null) {
            throw DomainException.with(new Error("Regra não pode ser excluída, pois está associada a uma ou mais trilhas!"));
        }

        regraGateway.excluir(regra.getId());
    }

    private static Supplier<DomainException> regraNaoEncontrada(final RegraID id) {
        return () -> NotFoundException.with(Regra.class, id);
    }
}
