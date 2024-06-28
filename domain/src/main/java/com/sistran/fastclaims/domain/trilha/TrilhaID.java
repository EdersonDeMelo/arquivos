package com.sistran.fastclaims.domain.trilha;

import com.sistran.fastclaims.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class TrilhaID extends Identifier {

    private final String value;

    public TrilhaID(final String value) {
        Objects.requireNonNull(value, "'id' não pode ser nulo!");
        this.value = value;
    }

    public static TrilhaID unique() {
        return TrilhaID.from(UUID.randomUUID());
    }

    public static TrilhaID from(final String anId) {
        return new TrilhaID(anId);
    }

    public static TrilhaID from(UUID id) {
        return new TrilhaID(id.toString().toLowerCase());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TrilhaID trilhaID = (TrilhaID) o;
        return Objects.equals(value, trilhaID.value);
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