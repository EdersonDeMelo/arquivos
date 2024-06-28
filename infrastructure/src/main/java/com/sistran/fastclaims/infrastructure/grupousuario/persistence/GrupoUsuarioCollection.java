package com.sistran.fastclaims.infrastructure.grupousuario.persistence;

import com.sistran.fastclaims.domain.grupousuario.GrupoUsuario;
import com.sistran.fastclaims.domain.grupousuario.GrupoUsuarioID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "grupo_usuario")
public class GrupoUsuarioCollection {

    @Id
    private String id;
    private String nome;
    private Instant dataCadastro;
    private Instant dataAlteracao;
    private boolean ativo;

    public GrupoUsuarioCollection() {
    }

    private GrupoUsuarioCollection(final String id, final String nome, final Instant dataCadastro, final Instant dataAlteracao, final boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.dataCadastro = dataCadastro;
        this.dataAlteracao = dataAlteracao;
        this.ativo = ativo;
    }

    public static GrupoUsuarioCollection aPartirDe(final GrupoUsuario grupoUsuario) {
        return new GrupoUsuarioCollection(grupoUsuario.getId().getValue(), grupoUsuario.getNome(), grupoUsuario.getDataCadastro(), grupoUsuario.getDataAlteracao(), grupoUsuario.getAtivo());
    }

    public GrupoUsuario paraAgregado() {
        return GrupoUsuario.com(
                GrupoUsuarioID.from(getId()),
                getNome(),
                getDataCadastro(),
                getDataAlteracao(),
                getAtivo()
        );
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Instant getDataCadastro() {
        return dataCadastro;
    }

    public Instant getDataAlteracao() {
        return dataAlteracao;
    }

    public boolean getAtivo() {
        return ativo;
    }
}
