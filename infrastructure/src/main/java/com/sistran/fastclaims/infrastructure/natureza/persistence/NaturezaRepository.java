package com.sistran.fastclaims.infrastructure.natureza.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface NaturezaRepository extends MongoRepository<NaturezaCollection, String> {

    @Query("{ '$or': [ { 'nome': { '$regex': ?0, '$options': 'i' } }, { 'codigoNatureza': { '$regex': ?0, '$options': 'i' } } ] }")
    List<NaturezaCollection> listar(String termo);
}
