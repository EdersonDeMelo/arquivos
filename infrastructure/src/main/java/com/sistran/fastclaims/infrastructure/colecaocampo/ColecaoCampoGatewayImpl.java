package com.sistran.fastclaims.infrastructure.colecaocampo;

import com.sistran.fastclaims.domain.colecao.ColecaoID;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampo;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoGateway;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoID;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.pagination.Pagination;
import com.sistran.fastclaims.domain.pagination.SearchQuery;
import com.sistran.fastclaims.infrastructure.colecaocampo.persistence.ColecaoCampoCollection;
import com.sistran.fastclaims.infrastructure.colecaocampo.persistence.ColecaoCampoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ColecaoCampoGatewayImpl implements ColecaoCampoGateway {

    private final ColecaoCampoRepository colecaoCampoRepository;

    public ColecaoCampoGatewayImpl(final ColecaoCampoRepository colecaoCampoRepository) {
        this.colecaoCampoRepository = colecaoCampoRepository;
    }

    @Override
    public Optional<ColecaoCampo> pesquisarPorColecaoId(ColecaoID colecaoID) {
        return colecaoCampoRepository.findByColecaoID(colecaoID.getValue())
                .map(ColecaoCampoCollection::paraAgregado);
    }

    @Override
    public Optional<ColecaoCampo> pesquisarPorId(ColecaoCampoID colecaoCampoID) {
        return colecaoCampoRepository.findById(colecaoCampoID.getValue())
                .map(ColecaoCampoCollection::paraAgregado);
    }

    @Override
    public Pagination<ColecaoCampo> listar(SearchQuery query) {
        final var pagina = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort())
        );

        final var resultado =
                this.colecaoCampoRepository.listar(query.terms(), pagina);

        return new Pagination<>(resultado.getNumber(),
                resultado.getSize(),
                resultado.getTotalElements(),
                resultado.map(ColecaoCampoCollection::paraAgregado).toList());
    }

    @Override
    public void excluir(final ColecaoCampoID id) {
        if (!colecaoCampoRepository.existsById(id.getValue()))
            throw NotFoundException.with(ColecaoCampo.class, id);
        colecaoCampoRepository.deleteById(id.getValue());
    }

    @Override
    public ColecaoCampo cadastrar(ColecaoCampo colecaoCampo) {
        final ColecaoCampoCollection colecaoCampoEntity = ColecaoCampoCollection.aPartirDe(colecaoCampo);
        return colecaoCampoRepository.save(colecaoCampoEntity).paraAgregado();
    }
}
