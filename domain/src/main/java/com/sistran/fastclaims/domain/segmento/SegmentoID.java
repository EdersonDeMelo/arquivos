package com.sistran.fastclaims.domain.segmento;

import com.sistran.fastclaims.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class SegmentoID extends Identifier {

    private final String value;

    public SegmentoID(final String value) {
        this.value = value;
    }

    public static SegmentoID unique() {
        return SegmentoID.aPartirDe(UUID.randomUUID());
    }

    public static SegmentoID aPartirDe(final String anId) {
        return new SegmentoID(anId);
    }

    public static SegmentoID aPartirDe(UUID id) {
        return new SegmentoID(id.toString().toLowerCase());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SegmentoID that = (SegmentoID) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}

