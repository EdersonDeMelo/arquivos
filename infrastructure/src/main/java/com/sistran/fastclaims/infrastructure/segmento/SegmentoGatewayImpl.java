package com.sistran.fastclaims.infrastructure.segmento;

import com.sistran.fastclaims.domain.segmento.Segmento;
import com.sistran.fastclaims.domain.segmento.SegmentoGateway;
import com.sistran.fastclaims.domain.segmento.SegmentoID;
import com.sistran.fastclaims.infrastructure.segmento.persistence.SegmentoCollection;
import com.sistran.fastclaims.infrastructure.segmento.persistence.SegmentoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SegmentoGatewayImpl implements SegmentoGateway {

    private final SegmentoRepository segmentoRepository;

    public SegmentoGatewayImpl(final SegmentoRepository segmentoRepository) {
        this.segmentoRepository = segmentoRepository;
    }

    @Override
    public Optional<Segmento> pesquisarPorId(final SegmentoID id) {
        return segmentoRepository.findById(id.getValue()).map(SegmentoCollection::paraAgregado);
    }

    @Override
    public List<Segmento> listar() {
        Sort sort = Sort.by(Sort.Direction.ASC, "nome");
        return segmentoRepository.findAll(sort).stream()
                .map(SegmentoCollection::paraAgregado)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Segmento cadastrar(final Segmento segmento) {
        final var segmentoCollection = SegmentoCollection.aPartirDe(segmento);
        return segmentoRepository.save(segmentoCollection).paraAgregado();
    }

    @Transactional
    @Override
    public Segmento atualizar(final Segmento segmento) {
        final var segmentoCollection = SegmentoCollection.aPartirDe(segmento);
        return segmentoRepository.save(segmentoCollection).paraAgregado();
    }
}
