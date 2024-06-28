package com.sistran.fastclaims.infrastructure.colecaocampo.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ColecaoCampoRepository extends MongoRepository<ColecaoCampoCollection, String> {

    Optional<ColecaoCampoCollection> findByColecaoID(String colecaoID);

    @Query(value = "{'alias': { $regex: ?0, $options: 'i' }}")
    Page<ColecaoCampoCollection> listar(String alias, Pageable page);

}
