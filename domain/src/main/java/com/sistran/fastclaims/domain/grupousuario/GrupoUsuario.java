package com.sistran.fastclaims.domain.grupousuario;

import com.sistran.fastclaims.domain.AggregateRoot;
import com.sistran.fastclaims.domain.utils.InstantUtils;
import com.sistran.fastclaims.domain.validation.ValidationHandler;

import java.time.Instant;

public class GrupoUsuario extends AggregateRoot<GrupoUsuarioID> implements Cloneable {

    private String nome;
    private Instant dataCadastro;
    private Instant dataAlteracao;
    private boolean ativo;


    private GrupoUsuario(final GrupoUsuarioID id, final String nome, final Instant dataCadastro, final Instant dataAlteracao, final Boolean ativo) {
        super(id);
        this.nome = nome;
        this.dataCadastro = dataCadastro;
        this.dataAlteracao = dataAlteracao;
        this.ativo = ativo;
    }

    public static GrupoUsuario novoGrupoUsuario(final String nome) {
        final var id = GrupoUsuarioID.unique();
        final var dataAtual = Instant.now();
        final var ativo = true;
        return new GrupoUsuario(id, nome, dataAtual, dataAtual, ativo);
    }

    public static GrupoUsuario com(final GrupoUsuarioID id, final String nome, final Instant dataCadastro,
                                   final Instant dataAlteracao, final boolean ativo) {
        return new GrupoUsuario(id, nome, dataCadastro, dataAlteracao, ativo);
    }

    public static GrupoUsuario com(final GrupoUsuario grupoUsuario) {
        return com(grupoUsuario.getId(), grupoUsuario.getNome(), grupoUsuario.getDataCadastro(), grupoUsuario.getDataAlteracao(), grupoUsuario.getAtivo());
    }

    @Override
    public void validate(ValidationHandler handler) {
        new GrupoUsuarioValidator(this, handler).validate();
    }

    @Override
    public GrupoUsuario clone() {
        try {
            return (GrupoUsuario) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Erro ao clonar GrupoUsuario", e);
        }
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

    public void ativar() {
        this.ativo = true;
        this.dataAlteracao = InstantUtils.now();
    }

    public void desativar() {
        this.ativo = false;
        this.dataAlteracao = InstantUtils.now();
    }
}
