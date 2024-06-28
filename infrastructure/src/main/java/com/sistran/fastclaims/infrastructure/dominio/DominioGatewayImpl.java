package com.sistran.fastclaims.infrastructure.dominio;

import com.sistran.fastclaims.domain.dominio.Dominio;
import com.sistran.fastclaims.domain.dominio.DominioGateway;
import com.sistran.fastclaims.domain.dominio.DominioID;
import com.sistran.fastclaims.infrastructure.dominio.persistence.DominioCollection;
import com.sistran.fastclaims.infrastructure.dominio.persistence.DominioRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DominioGatewayImpl implements DominioGateway {

    private final DominioRepository dominioRepository;

    private final MongoTemplate mongoTemplate;

    public DominioGatewayImpl(final DominioRepository fluxoRepository, MongoTemplate mongoTemplate) {
        this.dominioRepository = fluxoRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Dominio cadastrar(Dominio dominio) {
        final DominioCollection dominioEntity = DominioCollection.aPartirDe(dominio);
        return dominioRepository.save(dominioEntity).paraAgregado();
    }

    @Override
    public Optional<Dominio> pesquisarPorNome(String nome) {
        return dominioRepository.pesquisarPorNome(nome).map(DominioCollection::paraAgregado);
    }

    @Override
    public Optional<Dominio> pesquisarPorId(DominioID id) {
        final String idValue = id.getValue();
        return dominioRepository.findById(idValue).map(DominioCollection::paraAgregado);
    }

    @Override
    public List<Dominio> listarDominios() {
        return dominioRepository.findAll().stream().map(DominioCollection::paraAgregado).toList();
    }

    @Override
    public Dominio atualizar(Dominio dominio) {
        final var dominioEntity = DominioCollection.aPartirDe(dominio);
        return dominioRepository.save(dominioEntity).paraAgregado();
    }

    @Override
    public Dominio adicionarValor(Dominio dominio) {
        return atualizar(dominio);
    }

    @Override
    public boolean exists(DominioID id) {
        return dominioRepository.existsById(id.getValue());
    }

}
