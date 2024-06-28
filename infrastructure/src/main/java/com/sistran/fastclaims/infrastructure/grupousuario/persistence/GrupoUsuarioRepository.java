package com.sistran.fastclaims.infrastructure.grupousuario.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface GrupoUsuarioRepository extends MongoRepository<GrupoUsuarioCollection, String> {

    @Query("{ 'nome' : ?0}")
    Optional<GrupoUsuarioCollection> pesquisarPorNome(String nome);
}
