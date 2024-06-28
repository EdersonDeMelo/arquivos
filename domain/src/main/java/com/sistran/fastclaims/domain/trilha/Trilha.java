package com.sistran.fastclaims.domain.trilha;

import com.sistran.fastclaims.domain.AggregateRoot;
import com.sistran.fastclaims.domain.fluxo.FluxoID;
import com.sistran.fastclaims.domain.utils.InstantUtils;
import com.sistran.fastclaims.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trilha extends AggregateRoot<TrilhaID> implements Cloneable {

    private String nome;
    private String descricao;
    private Instant dataCadastro;
    private Instant dataAlteracao;
    private boolean ativo;
    private FluxoID fluxoId;
    private List<RegraTrilha> regrasTrilha;


    private Trilha(
            final TrilhaID id,
            final String nome,
            final String descricao,
            final Instant dataCadastro,
            final Instant dataAlteracao,
            final boolean ativo,
            final FluxoID fluxoId,
            final List<RegraTrilha> regrasTrilha
    ) {
        super(id);
        this.nome = nome;
        this.descricao = descricao;
        this.dataCadastro = Objects.requireNonNull(dataCadastro, "'dataCadastro' não pode ser nula!");
        this.dataAlteracao = Objects.requireNonNull(dataAlteracao, "'dataAlteracao' não pode ser nula!");
        this.ativo = ativo;
        this.fluxoId = fluxoId;
        this.regrasTrilha = regrasTrilha == null ? new ArrayList<>() : regrasTrilha;
    }

    public static Trilha novaTrilha(
            final String nome,
            final String descricao,
            final FluxoID fluxoId
    ) {
        final var id = TrilhaID.unique();
        final var dataAtual = InstantUtils.now();
        return new Trilha(id, nome, descricao, dataAtual, dataAtual, true, fluxoId, null);
    }

    public static Trilha novaTrilha(
            final String nome,
            final String descricao,
            final boolean ativo,
            final FluxoID fluxoId
    ) {
        final var id = TrilhaID.unique();
        final var dataAtual = InstantUtils.now();
        return new Trilha(id, nome, descricao, dataAtual, dataAtual, ativo, fluxoId, null);
    }

    public static Trilha com(
            final TrilhaID id,
            final String nome,
            final String descricao,
            final Instant dataCadastro,
            final Instant dataAlteracao,
            final boolean ativo,
            final FluxoID fluxoId,
            final List<RegraTrilha> regras
    ) {
        return new Trilha(id, nome, descricao, dataCadastro, dataAlteracao, ativo, fluxoId, regras == null ? new ArrayList<>() : regras);
    }

    public static Trilha com(final Trilha trilha) {
        return com(
                trilha.getId(),
                trilha.getNome(),
                trilha.getDescricao(),
                trilha.getDataCadastro(),
                trilha.getDataAlteracao(),
                trilha.isAtivo(),
                trilha.getFluxoId(),
                trilha.getRegras()
        );
    }

    public Trilha ativar() {
        this.ativo = true;
        this.dataAlteracao = InstantUtils.now();
        return this;
    }

    public Trilha desativar() {
        this.ativo = false;
        this.dataAlteracao = InstantUtils.now();
        return this;
    }

    public Trilha atualizar(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataAlteracao = InstantUtils.now();
        return this;
    }

    public Trilha adicionarRegra(RegraTrilha regra) {
        this.regrasTrilha.add(regra);
        return this;
    }

    public String getNome() {
        return nome;
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

    public boolean isAtivo() {
        return ativo;
    }

    public FluxoID getFluxoId() {
        return fluxoId;
    }

    public List<RegraTrilha> getRegras() {
        return regrasTrilha;
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new TrilhaValidator(this, handler).validate();
    }

    @Override
    public Trilha clone() {
        try {
            return (Trilha) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
