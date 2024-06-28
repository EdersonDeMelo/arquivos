package com.sistran.fastclaims.domain.operador;

import java.util.Arrays;
import java.util.Optional;

public enum TipoOperador {
    VALOR("VALOR"),
    ARITMETICO("ARITMETICO"),
    RELACIONAL("RELACIONAL"),
    FUNCAO("FUNCAO"),
    FUNCAOAGRUPADA("FUNCAOAGRUPADA");

    private final String nome;

    TipoOperador(final String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public static Optional<TipoOperador> of(final String label) {
        return Arrays.stream(TipoOperador.values())
                .filter(it -> it.nome.equalsIgnoreCase(label))
                .findFirst();
    }
}
