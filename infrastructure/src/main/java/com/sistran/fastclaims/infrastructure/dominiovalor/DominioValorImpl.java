package com.sistran.fastclaims.infrastructure.dominiovalor;

import com.mongodb.client.result.UpdateResult;
import com.sistran.fastclaims.domain.dominiovalor.DominioValor;
import com.sistran.fastclaims.domain.dominiovalor.DominioValorGateway;
import com.sistran.fastclaims.infrastructure.dominio.persistence.DominioCollection;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class DominioValorImpl implements DominioValorGateway {


    private final MongoTemplate mongoTemplate;

    public DominioValorImpl( MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void excluirDominioValorPorId(DominioValor dominioValor) {
        UpdateResult result = mongoTemplate.updateFirst(
                query(where("valores._id").is(dominioValor.getId().getValue())),
                new Update().pull("valores",
                        query(where("_id").
                                is(dominioValor.getId().getValue()))),
                                DominioCollection.class);

    }
}
