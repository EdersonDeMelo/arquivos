package com.sistran.fastclaims.application.segmento.pesquisar.lista;

import com.sistran.fastclaims.domain.segmento.SegmentoGateway;

import java.util.List;

public class DefaultListarSegmentosUseCase extends ListarSegmentosUseCase {

    private final SegmentoGateway segmentoGateway;

    public DefaultListarSegmentosUseCase(final SegmentoGateway segmentoGateway) {
        this.segmentoGateway = segmentoGateway;
    }

    @Override
    public List<ListarSegmentosOutput> execute() {
        return segmentoGateway.listar().stream().map(ListarSegmentosOutput::aPartirDe).toList();
    }
}
