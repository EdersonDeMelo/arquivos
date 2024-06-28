package com.sistran.fastclaims.domain.natureza;

import com.sistran.fastclaims.domain.AggregateRoot;
import com.sistran.fastclaims.domain.validation.ValidationHandler;

public class Natureza extends AggregateRoot<NaturezaID> implements Cloneable {

    private String nome;
    private String codigoNatureza;

    private Natureza(final NaturezaID naturezaID, final String nome, final String codigoNatureza) {
        super(naturezaID);
        this.nome = nome;
        this.codigoNatureza = codigoNatureza;
    }

    public static Natureza novaNatureza(final String nome, final String codigoNatureza) {
        final var id = NaturezaID.unique();
        return new Natureza(id, nome, codigoNatureza);
    }

    public static Natureza com(final NaturezaID id, final String nome, final String codigoNatureza) {
        return new Natureza(id, nome, codigoNatureza);
    }

    public static Natureza com(final Natureza natureza) {
        return new Natureza(natureza.getId(), natureza.getNome(), natureza.getCodigoNatureza());

    }

    @Override
    public void validate(final ValidationHandler handler) {
        new NaturezaValidator(this, handler).validate();
    }

    public String getNome() {
        return nome;
    }

    public String getCodigoNatureza() {
        return codigoNatureza;
    }

    @Override
    public Natureza clone() {
        try {
            return (Natureza) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
