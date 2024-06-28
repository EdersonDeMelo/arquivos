package com.sistran.fastclaims.domain.colecaocampo;

import java.util.Arrays;
import java.util.Optional;

public enum TipoFormato {
    ASTERISCO("*"),
    ZERO("0"),
    DATA("AAAA-MM-DD"),
    PERCENT("%"),
    DECIMAL("0.0");

    private final String value;

    TipoFormato(String formato) {
        this.value = formato;
    }

    public String getValue() {
        return this.value;
    }

    public static Optional<TipoFormato> of(final String label) {
        return Arrays.stream(TipoFormato.values())
                .filter(it -> it.value.equalsIgnoreCase(label))
                .findFirst();
    }
}