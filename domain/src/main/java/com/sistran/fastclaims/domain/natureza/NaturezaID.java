package com.sistran.fastclaims.domain.natureza;

import com.sistran.fastclaims.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class NaturezaID extends Identifier {

    private final String value;

    public NaturezaID(final String value) {
        Objects.requireNonNull(value, "'id' não pode ser nulo");
        this.value = value;
    }

    public static NaturezaID unique() {
        return NaturezaID.aPartirDe(UUID.randomUUID());
    }

    public static NaturezaID aPartirDe(final String anId) {
        return new NaturezaID(anId);
    }

    public static NaturezaID aPartirDe(final UUID anId) {
        return new NaturezaID(anId.toString().toLowerCase());
    }

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final NaturezaID that = (NaturezaID) o;
        return Objects.equals(value, that.value);
    }
}
