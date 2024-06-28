package com.sistran.fastclaims.domain.segmento;

import com.sistran.fastclaims.domain.AggregateRoot;
import com.sistran.fastclaims.domain.utils.InstantUtils;
import com.sistran.fastclaims.domain.validation.ValidationHandler;

import java.time.Instant;

public class Segmento extends AggregateRoot<SegmentoID> implements Cloneable {

    private String nome;
    private Instant dataCadastro;
    private Instant dataAlteracao;

    private Segmento(final SegmentoID id, final String nome, final Instant dataCadastro, final Instant dataAlteracao) {
        super(id);
        this.nome = nome;
        this.dataCadastro = dataCadastro;
        this.dataAlteracao = dataAlteracao;
    }

    public static Segmento novoSegmento(final String nome) {
        final var id = SegmentoID.unique();
        final var dataCadastro = InstantUtils.now();
        return new Segmento(id, nome, dataCadastro, dataCadastro);
    }

    public static Segmento aPartirDe(final SegmentoID id, final String nome, final Instant dataCadastro, final Instant dataAlteracao) {
        return new Segmento(id, nome, dataCadastro, dataAlteracao);
    }

    public static Segmento aPartirDe(final Segmento segmento) {
        return new Segmento(segmento.getId(), segmento.getNome(), segmento.getDataCadastro(), segmento.getDataAlteracao());
    }

    public void atualizar(final String nome) {
        this.nome = nome;
        this.dataAlteracao = InstantUtils.now();
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

    @Override
    public void validate(ValidationHandler handler) {
        new SegmentoValidator(this, handler).validate();
    }

    @Override
    public Segmento clone() {
        try {
            Segmento clone = (Segmento) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
