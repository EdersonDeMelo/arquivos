package com.sistran.fastclaims.domain.colecao;

import com.sistran.fastclaims.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class ColecaoID extends Identifier {

    private final String value;

    public ColecaoID(String value) {
        Objects.requireNonNull(value, "'id' não pode ser nulo!");
        this.value = value;
    }

    public static ColecaoID unique() {
        return ColecaoID.from(UUID.randomUUID());
    }

    public static ColecaoID from(String anId) {
        return new ColecaoID(anId);
    }

    public static ColecaoID from(UUID anId) {
        return new ColecaoID(anId.toString().toLowerCase());
    }

    @Override
    public String getValue() {
        return value;
    }
}
