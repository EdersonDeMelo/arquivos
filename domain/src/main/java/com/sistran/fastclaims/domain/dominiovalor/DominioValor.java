package com.sistran.fastclaims.domain.dominiovalor;

import com.sistran.fastclaims.domain.AggregateRoot;
import com.sistran.fastclaims.domain.dominio.DominioID;
import com.sistran.fastclaims.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.Objects;

public class DominioValor extends AggregateRoot<DominioValorID> implements Cloneable {

    private String codigoValor;
    private String nomeValor;
    private Instant dataCadastro;
    private Instant dataAlteracao;
    private DominioID dominioID;

    private DominioValor(final DominioValorID id, final String codigoValor, final String nomeValor, final DominioID dominioID,
                         final Instant dataCadastro, final Instant dataAlteracao) {
        super(id);
        this.codigoValor = codigoValor;
        this.nomeValor = nomeValor;
        this.dominioID = dominioID;
        this.dataCadastro = Objects.requireNonNull(dataCadastro, "'dataCadastro' não pode ser nula!");
        this.dataAlteracao = Objects.requireNonNull(dataAlteracao, "'dataAlteracao' não pode ser nula!");
    }

    public static DominioValor novoDominioValor(final String codigoValor, final String nomeValor,  final DominioID dominioID) {
        final var id = DominioValorID.unique();
        final var dataAtual = Instant.now();
        return new DominioValor(id, codigoValor, nomeValor, dominioID, dataAtual, dataAtual);
    }

    public static DominioValor com(final DominioValorID id, final String codigoValor, final String nomeValor, final DominioID dominioID, final Instant dataCadastro, final Instant dataAlteracao) {
        return new DominioValor(id, codigoValor, nomeValor, dominioID, dataCadastro, dataAlteracao);
    }

    public static DominioValor com(final DominioValor dominioValor) {
        return com(dominioValor.getId(), dominioValor.getCodigoValor(), dominioValor.getNomeValor(),
                dominioValor.getDominioID(), dominioValor.getDataCadastro(), dominioValor.getDataAlteracao());
    }

    public String getCodigoValor() {
        return codigoValor;
    }

    public String getNomeValor() {
        return nomeValor;
    }

    public DominioID getDominioID() {
        return dominioID;
    }

    public Instant getDataCadastro() {
        return dataCadastro;
    }

    public Instant getDataAlteracao() {
        return dataAlteracao;
    }

    @Override
    public void validate(ValidationHandler handler) {
        new DominioValorValidator(this, handler).validate();
    }

    @Override
    public DominioValor clone() {
        try {
            return (DominioValor) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Erro ao clonar a entidade DominioValor", e);
        }
    }
}
