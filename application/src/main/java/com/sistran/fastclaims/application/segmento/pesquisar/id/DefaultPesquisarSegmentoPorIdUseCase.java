package com.sistran.fastclaims.application.segmento.pesquisar.id;

import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.segmento.Segmento;
import com.sistran.fastclaims.domain.segmento.SegmentoGateway;
import com.sistran.fastclaims.domain.segmento.SegmentoID;

import java.util.function.Supplier;

public class DefaultPesquisarSegmentoPorIdUseCase extends PesquisarSegmentoPorIdUseCase {

    private final SegmentoGateway segmentoGateway;

    public DefaultPesquisarSegmentoPorIdUseCase(final SegmentoGateway segmentoGateway) {
        this.segmentoGateway = segmentoGateway;
    }

    @Override
    public PesquisarSegmentoPorIdOutput executar(final String id) {

        final var segmentoId = SegmentoID.aPartirDe(id);

        return segmentoGateway.pesquisarPorId(segmentoId)
                .map(PesquisarSegmentoPorIdOutput::aPartirDe)
                .orElseThrow(segmentoNaoEncontrado(segmentoId));
    }

    private static Supplier<DomainException> segmentoNaoEncontrado(final SegmentoID id) {
        return () -> NotFoundException.with(Segmento.class, id);
    }
}

