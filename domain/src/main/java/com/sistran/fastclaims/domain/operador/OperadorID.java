package com.sistran.fastclaims.domain.operador;

import com.sistran.fastclaims.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class OperadorID extends Identifier {

    private final String value;

    public OperadorID(String value) {
        Objects.requireNonNull(value, "'id' não pode ser nulo!");
        this.value = value;
    }

    public static OperadorID unique() {
        return OperadorID.from(UUID.randomUUID());
    }

    public static OperadorID from(String anId) {
        return new OperadorID(anId);
    }

    public static OperadorID from(UUID anId) {
        return new OperadorID(anId.toString().toLowerCase());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperadorID that = (OperadorID) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String getValue() {
        return value;
    }
}
