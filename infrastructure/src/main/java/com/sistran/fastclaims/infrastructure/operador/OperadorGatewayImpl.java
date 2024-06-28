package com.sistran.fastclaims.infrastructure.operador;

import com.sistran.fastclaims.domain.operador.Operador;
import com.sistran.fastclaims.domain.operador.OperadorGateway;
import com.sistran.fastclaims.domain.operador.OperadorID;
import com.sistran.fastclaims.infrastructure.operador.persistence.OperadorCollection;
import com.sistran.fastclaims.infrastructure.operador.persistence.OperadorRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OperadorGatewayImpl implements OperadorGateway {

    private final OperadorRepository operadorRepository;

    public OperadorGatewayImpl(final OperadorRepository operadorRepository) {
        this.operadorRepository = operadorRepository;
    }

    @Override
    public Optional<Operador> pesquisarPorId(final OperadorID id) {
        String idValue = id.getValue();
        return operadorRepository.findById(idValue).map(OperadorCollection::paraAgregado);
    }

    @Override
    public List<Operador> listar() {
        Sort sort = Sort.by(Sort.Direction.ASC, "nome");
        return operadorRepository.findAll(sort).stream()
                .map(OperadorCollection::paraAgregado)
                .collect(Collectors.toList());
    }
}
