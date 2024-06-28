package com.sistran.fastclaims.domain.fluxo;


import com.sistran.fastclaims.domain.AggregateRoot;
import com.sistran.fastclaims.domain.natureza.NaturezaID;
import com.sistran.fastclaims.domain.utils.InstantUtils;
import com.sistran.fastclaims.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.Objects;

public class Fluxo extends AggregateRoot<FluxoID> implements Cloneable {

    private String descricao;
    private Instant dataCadastro;
    private Instant dataAlteracao;
    private NaturezaID naturezaId;

    private Fluxo(final FluxoID id, final String descricao, final Instant dataCadastro, final Instant dataAlteracao, final NaturezaID naturezaId) {
        super(id);
        this.descricao = descricao;
        this.dataCadastro = Objects.requireNonNull(dataCadastro, "'dataCadastro' não pode ser nula!");
        this.dataAlteracao = Objects.requireNonNull(dataAlteracao, "'dataAlteracao' não pode ser nula!");
        this.naturezaId = naturezaId;
    }

    public static Fluxo novoFluxo(final String descricao, final NaturezaID naturezaId) {
        final var id = FluxoID.unique();
        final var dataAtual = InstantUtils.now();
        return new Fluxo(id, descricao, dataAtual, dataAtual, naturezaId);
    }

    public static Fluxo com(final FluxoID id, final String descricao, final Instant dataCadastro, final Instant dataAlteracao, final NaturezaID naturezaId) {
        return new Fluxo(id, descricao, dataCadastro, dataAlteracao, naturezaId);
    }

    public static Fluxo com(final Fluxo fluxo) {
        return com(fluxo.getId(), fluxo.getDescricao(), fluxo.getDataCadastro(), fluxo.getDataAlteracao(), fluxo.getNaturezaId());
    }

    public Fluxo atualizar(final String descricao) {
        this.descricao = descricao;
        this.dataAlteracao = InstantUtils.now();
        return this;
    }

    public String getDescricao() {
        return descricao;
    }

    public Instant getDataCadastro() {
        return dataCadastro;
    }

    public Instant getDataAlteracao() {
        return dataAlteracao;
    }

    public NaturezaID getNaturezaId() {
        return naturezaId;
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new FluxoValidator(this, handler).validate();
    }

    @Override
    public Fluxo clone() {
        try {
            return (Fluxo) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}


