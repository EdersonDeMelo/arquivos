package com.sistran.fastclaims.infrastructure.trilha;

import com.sistran.fastclaims.domain.pagination.Pagination;
import com.sistran.fastclaims.domain.pagination.SearchQuery;
import com.sistran.fastclaims.domain.regra.Regra;
import com.sistran.fastclaims.domain.trilha.*;
import com.sistran.fastclaims.infrastructure.trilha.persistence.RegraTrilhaCollection;
import com.sistran.fastclaims.infrastructure.trilha.persistence.TrilhaCollection;
import com.sistran.fastclaims.infrastructure.trilha.persistence.TrilhaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TrilhaGatewayImpl implements TrilhaGateway {

    private final TrilhaRepository trilhaRepository;

    private final MongoTemplate mongoTemplate;

    public TrilhaGatewayImpl(final TrilhaRepository trilhaRepository, final MongoTemplate mongoTemplate) {
        this.trilhaRepository = trilhaRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<Trilha> pesquisarPorId(final TrilhaID id) {
        String idValue = id.getValue();
        return trilhaRepository.findById(idValue).map(TrilhaCollection::paraAgregado);
    }

    @Override
    public Pagination<Trilha> listar(final SearchQuery query) {
        final var pagina = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort())
        );

        final var resultado =
                this.trilhaRepository.listar(query.terms(), pagina);

        return new Pagination<>(
                resultado.getNumber(),
                resultado.getSize(),
                resultado.getTotalElements(),
                resultado.map(TrilhaCollection::paraAgregado).toList()
        );
    }

    @Transactional
    @Override
    public Trilha cadastrar(Trilha trilha) {
        TrilhaCollection trilhaCollection = TrilhaCollection.aPartirDe(trilha);
        return trilhaRepository.save(trilhaCollection).paraAgregado();
    }

    @Transactional
    @Override
    public Trilha atualizar(Trilha trilha) {
        final var query = criarQuery(trilha);
        final var update = criarUpdate(trilha);

        mongoTemplate.updateFirst(query, update, TrilhaCollection.class);

        TrilhaCollection trilhaCollection = TrilhaCollection.aPartirDeTrilhaComRegras(trilha);
        return trilhaCollection.paraAgregado();
    }

    @Transactional
    @Override
    public void ativar(Trilha trilha) {
        final var trilhaCollection = TrilhaCollection.aPartirDeTrilhaComRegras(trilha);
        trilhaRepository.save(trilhaCollection);
    }

    @Transactional
    @Override
    public void desativar(Trilha trilha) {
        final var trilhaCollection = TrilhaCollection.aPartirDeTrilhaComRegras(trilha);
        trilhaRepository.save(trilhaCollection);
    }

    @Transactional
    @Override
    public void excluir(TrilhaID id) {
        trilhaRepository.deleteById(id.getValue());
    }

    @Transactional
    @Override
    public RegraTrilhaPreview cadastrarRegraTrilha(Trilha trilha, Regra regra, RegraTrilha regraTrilha) {
        final TrilhaCollection trilhaCollection = TrilhaCollection.aPartirDeTrilhaComRegras(trilha);
        return trilhaRepository.save(trilhaCollection).paraRegraTrilhaPreview(regra, regraTrilha);
    }

    @Transactional
    @Override
    public RegraTrilha atualizarRegraTrilha(Trilha trilha, RegraTrilha regraTrilha) {
        final TrilhaCollection trilhaCollection = TrilhaCollection.aPartirDeTrilhaComRegras(trilha);
        return trilhaRepository.save(trilhaCollection).paraRegraTrilha(regraTrilha);
    }

    @Transactional
    @Override
    public void ativarRegraTrilha(Trilha trilha) {
        final var trilhaCollection = TrilhaCollection.aPartirDeTrilhaComRegras(trilha);
        trilhaRepository.save(trilhaCollection);
    }

    @Transactional
    @Override
    public void desativarRegraTrilha(Trilha trilha) {
        final var trilhaCollection = TrilhaCollection.aPartirDeTrilhaComRegras(trilha);
        trilhaRepository.save(trilhaCollection);
    }

    private Update criarUpdate(final Trilha trilha) {
        Update update = new Update();
        update.set("nome", trilha.getNome());
        update.set("descricao", trilha.getDescricao());
        update.set("dataAlteracao", trilha.getDataAlteracao());
        update.set("regrasTrilha", trilha.getRegras().stream().map(RegraTrilhaCollection::aPartirDe).toList());
        return update;
    }

    private Query criarQuery(final Trilha trilha) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(trilha.getId().getValue()));
        return query;
    }
}

