package com.sistran.fastclaims.domain.colecaocampo;

import com.sistran.fastclaims.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class ColecaoCampoID extends Identifier {

    private final String value;

    public ColecaoCampoID(String value) {
        Objects.requireNonNull(value, "'id' não pode ser nulo!");
        this.value = value;
    }

    public static ColecaoCampoID unique() {
        return ColecaoCampoID.from(UUID.randomUUID());
    }

    public static ColecaoCampoID from(String anId) {
        return new ColecaoCampoID(anId);
    }

    public static ColecaoCampoID from(UUID anId) {
        return new ColecaoCampoID(anId.toString().toLowerCase());
    }

    @Override
    public String getValue() {
        return value;
    }
}
