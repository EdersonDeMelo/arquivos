package com.sistran.fastclaims.infrastructure.natureza;

import com.sistran.fastclaims.domain.natureza.Natureza;
import com.sistran.fastclaims.domain.natureza.NaturezaGateway;
import com.sistran.fastclaims.domain.natureza.NaturezaID;
import com.sistran.fastclaims.infrastructure.natureza.persistence.NaturezaCollection;
import com.sistran.fastclaims.infrastructure.natureza.persistence.NaturezaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NaturezaGatewayImpl implements NaturezaGateway {

    private final NaturezaRepository naturezaRepository;

    public NaturezaGatewayImpl(NaturezaRepository naturezaRepository) {
        this.naturezaRepository = naturezaRepository;
    }


    @Override
    public Optional<Natureza> pesquisarPorId(final NaturezaID id) {
        String idValue = id.getValue();
        return naturezaRepository.findById(idValue).map(NaturezaCollection::toAggregate);
    }

    @Override
    public List<Natureza> listar(String termo) {
        var naturezasCollection = naturezaRepository.listar(termo);
        List<Natureza> naturezas = new ArrayList<>(naturezasCollection.stream().map(NaturezaCollection::toAggregate).toList());
        if (naturezas.size() != 1) {
            naturezas.sort((n1, n2) -> n1.getNome().compareToIgnoreCase(n2.getNome()));
        }
        return naturezas;
    }
}
