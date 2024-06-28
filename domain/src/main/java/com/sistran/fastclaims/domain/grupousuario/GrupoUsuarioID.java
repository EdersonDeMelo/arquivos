package com.sistran.fastclaims.domain.grupousuario;

import com.sistran.fastclaims.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class GrupoUsuarioID extends Identifier {

    private final String value;

    @Override
    public String getValue() {
        return value;
    }

    public GrupoUsuarioID(final String value) {
        Objects.requireNonNull(value, "'id' não pode ser nulo!");
        this.value = value;
    }

    public static GrupoUsuarioID unique() {
        return GrupoUsuarioID.from(UUID.randomUUID());
    }

    public static GrupoUsuarioID from(final String anId) {
        return new GrupoUsuarioID(anId);
    }

    public static GrupoUsuarioID from(final UUID anId) {
        return new GrupoUsuarioID(anId.toString().toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final GrupoUsuarioID grupoUsuarioID = (GrupoUsuarioID) obj;
        return Objects.equals(value, grupoUsuarioID.value);
    }

}
