package com.sistran.fastclaims.domain.regra;

import com.sistran.fastclaims.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class RegraID extends Identifier {

    private final String value;

    public RegraID(final String value) {
        Objects.requireNonNull(value, "'id' não pode ser nulo!");
        this.value = value;
    }

    public static RegraID from(String anId) {
        return new RegraID(anId);
    }

    public static RegraID from(UUID anId) {
        return new RegraID(anId.toString().toLowerCase());
    }

    public static RegraID unique() {
        return from(UUID.randomUUID());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegraID regraID = (RegraID) o;
        return Objects.equals(value, regraID.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
