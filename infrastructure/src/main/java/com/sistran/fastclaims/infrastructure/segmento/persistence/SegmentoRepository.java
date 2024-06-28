package com.sistran.fastclaims.infrastructure.segmento.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SegmentoRepository extends MongoRepository<SegmentoCollection, String> {
}
