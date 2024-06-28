package com.sistran.fastclaims.infrastructure.regra.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface RegraRepository extends MongoRepository<RegraCollection, String> {

    @Query("{'nome': { $regex: ?0, $options: 'i' }}")
    Page<RegraCollection> listar(String nome, Pageable page);
    Optional<RegraCollection> findByCampoUm(String campoUm);
    Optional<RegraCollection> findByCampoDois(String campoDois);
    Optional<RegraCollection> findByCampoTres(String campoTres);

}

