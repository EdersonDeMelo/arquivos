package com.sistran.fastclaims.infrastructure.fluxo;

import com.sistran.fastclaims.domain.fluxo.Fluxo;
import com.sistran.fastclaims.domain.fluxo.FluxoGateway;
import com.sistran.fastclaims.domain.fluxo.FluxoID;
import com.sistran.fastclaims.domain.pagination.Pagination;
import com.sistran.fastclaims.domain.pagination.SearchQuery;
import com.sistran.fastclaims.infrastructure.exceptions.FluxoInexistenteException;
import com.sistran.fastclaims.infrastructure.fluxo.persistence.FluxoCollection;
import com.sistran.fastclaims.infrastructure.fluxo.persistence.FluxoRepository;
import com.sistran.fastclaims.infrastructure.trilha.persistence.TrilhaCollection;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class FluxoGatewayImpl implements FluxoGateway {

    private final FluxoRepository fluxoRepository;

    private MongoTemplate mongoTemplate;

    public FluxoGatewayImpl(final FluxoRepository fluxoRepository) {
        this.fluxoRepository = fluxoRepository;
    }

    @Transactional
    @Override
    public Fluxo cadastrar(final Fluxo fluxo) {
        final FluxoCollection fluxoEntity = FluxoCollection.aPartirDe(fluxo);
        return fluxoRepository.save(fluxoEntity).paraAgregado();
    }

    @Override
    public Pagination<Fluxo> listar(final SearchQuery query) {
        final var pagina = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort())
        );

        final var resultado =
                this.fluxoRepository.listar(query.terms(), pagina);

        return new Pagination<>(
                resultado.getNumber(),
                resultado.getSize(),
                resultado.getTotalElements(),
                resultado.map(FluxoCollection::paraAgregado).toList()
        );
    }

    @Transactional
    @Override
    public Fluxo atualizar(final Fluxo fluxo) {
        final var fluxoEntity = FluxoCollection.aPartirDe(fluxo);
        return fluxoRepository.save(fluxoEntity).paraAgregado();
    }

    @Override
    public Optional<Fluxo> pesquisarPorId(final FluxoID id) {
        final String idValue = id.getValue();
        return fluxoRepository.findById(idValue).map(FluxoCollection::paraAgregado);
    }

    @Transactional
    @Override
    public void excluirPorId(FluxoID id) {
        if (fluxoRepository.existsById(id.getValue())) {
            fluxoRepository.deleteById(id.getValue());
            desvincularFluxoDeTrilha(id);
        } else {
            throw new FluxoInexistenteException(id.getValue());
        }
    }

    @Transactional
    private void desvincularFluxoDeTrilha(FluxoID id) {
        mongoTemplate.updateMulti(
                query(where("trilhas.fluxoId").is(id.getValue())),
                new Update().set("fluxoId", ""),
                TrilhaCollection.class
        );
    }
}





