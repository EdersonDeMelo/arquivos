package com.sistran.fastclaims.infrastructure.dominiovalor.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DominioValorRepository extends MongoRepository<DominioValorCollection, String> {
}
