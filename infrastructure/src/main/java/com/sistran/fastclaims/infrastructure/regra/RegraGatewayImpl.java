package com.sistran.fastclaims.infrastructure.regra;

import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoID;
import com.sistran.fastclaims.domain.pagination.Pagination;
import com.sistran.fastclaims.domain.pagination.SearchQuery;
import com.sistran.fastclaims.domain.regra.Regra;
import com.sistran.fastclaims.domain.regra.RegraGateway;
import com.sistran.fastclaims.domain.regra.RegraID;
import com.sistran.fastclaims.infrastructure.regra.persistence.RegraCollection;
import com.sistran.fastclaims.infrastructure.regra.persistence.RegraRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RegraGatewayImpl implements RegraGateway {

    private final RegraRepository regraRepository;

    public RegraGatewayImpl(final RegraRepository regraRepository) {
        this.regraRepository = regraRepository;
    }

    @Transactional
    @Override
    public Regra cadastrar(final Regra regra) {
        final RegraCollection regraCollection = RegraCollection.aPartirDe(regra);
        return regraRepository.save(regraCollection).paraAgregado();
    }

    @Override
    public Optional<Regra> pesquisarPorId(final RegraID id) {
        final String idValue = id.getValue();
        return regraRepository.findById(idValue).map(RegraCollection::paraAgregado);
    }

    @Transactional
    @Override
    public Regra atualizar(final Regra regra) {
        final RegraCollection regraCollection = RegraCollection.aPartirDe(regra);
        return regraRepository.save(regraCollection).paraAgregado();
    }

    @Transactional
    @Override
    public void excluir(final RegraID id) {
        regraRepository.deleteById(id.getValue());
    }

    @Override
    public Pagination<Regra> listar(final SearchQuery query) {
        final var pagina = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort())
        );

        final var resultado =
                this.regraRepository.listar(query.terms(), pagina);

        return new Pagination<>(
                resultado.getNumber(),
                resultado.getSize(),
                resultado.getTotalElements(),
                resultado.map(RegraCollection::paraAgregado).toList()
        );
    }

    @Override
    public Optional<Regra> pesquisarCampoUm(ColecaoCampoID id) {
        final String idValue = id.getValue();
        return regraRepository.findByCampoUm(idValue).map(RegraCollection::paraAgregado);
    }

    @Override
    public Optional<Regra> pesquisarCampoDois(ColecaoCampoID id) {
        final String idValue = id.getValue();
        return regraRepository.findByCampoDois(idValue).map(RegraCollection::paraAgregado);
    }

    @Override
    public Optional<Regra> pesquisarCampoTres(ColecaoCampoID id) {
        final String idValue = id.getValue();
        return regraRepository.findByCampoTres(idValue).map(RegraCollection::paraAgregado);

    }


}

