package com.sistran.fastclaims.infrastructure.fluxo.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FluxoRepository extends MongoRepository<FluxoCollection, String> {

    @Query(value = "{'descricao': { $regex: ?0, $options: 'i' }}")
    Page<FluxoCollection> listar(String descricao, Pageable page);
}

