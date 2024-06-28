package com.sistran.fastclaims.infrastructure.trilha.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrilhaRepository extends MongoRepository<TrilhaCollection, String> {

    @Query("{'nome': { $regex: ?0, $options: 'i' }}")
    Page<TrilhaCollection> listar(String descricao, Pageable page);
}


