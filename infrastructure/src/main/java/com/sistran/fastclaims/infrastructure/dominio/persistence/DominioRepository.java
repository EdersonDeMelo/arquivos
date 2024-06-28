package com.sistran.fastclaims.infrastructure.dominio.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DominioRepository extends MongoRepository<DominioCollection, String> {

    @Query("{ 'nome' : ?0}")
    Optional<DominioCollection> pesquisarPorNome(String nome);

    @Query("{'nome': { $regex: ?0, $options: 'i' }}")
    List<DominioCollection> pesquisarDominios(String nome);
}
