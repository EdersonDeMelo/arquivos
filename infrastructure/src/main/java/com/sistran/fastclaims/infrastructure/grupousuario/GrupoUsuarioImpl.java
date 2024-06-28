package com.sistran.fastclaims.infrastructure.grupousuario;

import com.sistran.fastclaims.domain.grupousuario.GrupoUsuario;
import com.sistran.fastclaims.domain.grupousuario.GrupoUsuarioGateway;
import com.sistran.fastclaims.domain.grupousuario.GrupoUsuarioID;
import com.sistran.fastclaims.infrastructure.grupousuario.persistence.GrupoUsuarioCollection;
import com.sistran.fastclaims.infrastructure.grupousuario.persistence.GrupoUsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GrupoUsuarioImpl implements GrupoUsuarioGateway {

    private final GrupoUsuarioRepository grupoUsuarioRepository;

    public GrupoUsuarioImpl(GrupoUsuarioRepository grupoUsuarioRepository) {
        this.grupoUsuarioRepository = grupoUsuarioRepository;
    }

    @Override
    public GrupoUsuario cadastrar(GrupoUsuario grupoUsuario) {
        final GrupoUsuarioCollection grupoUsuarioEntity = GrupoUsuarioCollection.aPartirDe(grupoUsuario);
        return grupoUsuarioRepository.save(grupoUsuarioEntity).paraAgregado();
    }

    @Override
    public Optional<GrupoUsuario> pesquisarPorId(GrupoUsuarioID id) {
        final String idValue = id.getValue();
        return grupoUsuarioRepository.findById(idValue).map(GrupoUsuarioCollection::paraAgregado);
    }

    @Override
    public Optional<GrupoUsuario> pesquisarPorNome(String nome) {
        return grupoUsuarioRepository.pesquisarPorNome(nome).map(GrupoUsuarioCollection::paraAgregado);
    }
}
