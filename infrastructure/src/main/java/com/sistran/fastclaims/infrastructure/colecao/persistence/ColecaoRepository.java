package com.sistran.fastclaims.infrastructure.colecao.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColecaoRepository extends MongoRepository<ColecaoCollection, String> {
}
