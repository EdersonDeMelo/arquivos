package com.sistran.fastclaims.domain.dominiovalor;

import com.sistran.fastclaims.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class DominioValorID extends Identifier {

    private final String value;

    public DominioValorID(final String value) {
        Objects.requireNonNull(value, "'id' não pode ser nulo!");
        this.value = value;
    }

    public static DominioValorID unique() {
        return DominioValorID.from(java.util.UUID.randomUUID().toString());
    }

    public static DominioValorID from(final String anId) {
        return new DominioValorID(anId);
    }

    public static DominioValorID from(final UUID anId) {
        return new DominioValorID(anId.toString().toLowerCase());
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final DominioValorID dominioValorID = (DominioValorID) obj;
        return value.equals(dominioValorID.value);
    }

    @Override
    public String getValue() {
        return value;
    }
}
