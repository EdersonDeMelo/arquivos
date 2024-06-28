package com.sistran.fastclaims.domain.dominio;

import com.sistran.fastclaims.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class DominioID extends Identifier {

    private final String value;

    public DominioID(final String value) {
        Objects.requireNonNull(value, "'id' não pode ser nulo!");
        this.value = value;
    }

    public static DominioID unique() {
        return DominioID.from(UUID.randomUUID());
    }

    public static DominioID from(final String anId) {
        return new DominioID(anId);
    }

    public static DominioID from(final UUID anId) {
        return new DominioID(anId.toString().toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final DominioID dominioID = (DominioID) obj;
        return Objects.equals(value, dominioID.value);
    }

    @Override
    public String getValue() {
        return value;
    }
}
