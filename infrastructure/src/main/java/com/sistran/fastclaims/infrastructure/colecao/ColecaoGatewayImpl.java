package com.sistran.fastclaims.infrastructure.colecao;

import com.sistran.fastclaims.domain.colecao.Colecao;
import com.sistran.fastclaims.domain.colecao.ColecaoGateway;
import com.sistran.fastclaims.domain.colecao.ColecaoID;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.infrastructure.colecao.persistence.ColecaoCollection;
import com.sistran.fastclaims.infrastructure.colecao.persistence.ColecaoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ColecaoGatewayImpl implements ColecaoGateway {

    private final ColecaoRepository colecaoRepository;

    public ColecaoGatewayImpl(final ColecaoRepository colecaoRepository) {
        this.colecaoRepository = colecaoRepository;
    }

    @Override
    public Colecao cadastrar(final Colecao colecao) {
        final ColecaoCollection colecaoEntity = ColecaoCollection.aPartirDe(colecao);
        return colecaoRepository.save(colecaoEntity).paraAgregado();
    }

    @Override
    public Optional<Colecao> pesquisarPorId(ColecaoID id) {
        final String idValue = id.getValue();
        return colecaoRepository.findById(idValue)
                .map(ColecaoCollection::paraAgregado);
    }

    @Override
    public void excluirPorId(ColecaoID id) {
        if (!colecaoRepository.existsById(id.getValue()))
            throw NotFoundException.with(Colecao.class, id);
        colecaoRepository.deleteById(id.getValue());
    }

    @Override
    public Colecao atualizar(Colecao colecao) {
        final var colecaoEntity = ColecaoCollection.aPartirDe(colecao);
        return colecaoRepository.save(colecaoEntity).paraAgregado();
    }
}
