package com.sistran.fastclaims.infrastructure.operador.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperadorRepository extends MongoRepository<OperadorCollection, String> {
}
