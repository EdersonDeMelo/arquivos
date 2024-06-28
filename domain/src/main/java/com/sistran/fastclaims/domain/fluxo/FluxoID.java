package com.sistran.fastclaims.domain.fluxo;

import com.sistran.fastclaims.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class FluxoID extends Identifier {

    private final String value;

    public FluxoID(final String value) {
        Objects.requireNonNull(value, "'id' não pode ser nulo!");
        this.value = value;
    }

    public static FluxoID unique() {
        return FluxoID.from(UUID.randomUUID());
    }

    public static FluxoID from(final String anId) {
        return new FluxoID(anId);
    }

    public static FluxoID from(final UUID anId) {
        return new FluxoID(anId.toString().toLowerCase());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FluxoID fluxoID = (FluxoID) o;
        return Objects.equals(value, fluxoID.value);
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
