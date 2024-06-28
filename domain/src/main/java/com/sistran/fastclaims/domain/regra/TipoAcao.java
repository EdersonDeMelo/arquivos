package com.sistran.fastclaims.domain.regra;

import java.util.Arrays;
import java.util.Optional;

public enum TipoAcao {

    ANALISAR("ANALISAR"),
    ENCERRAR("ENCERRAR");

    private final String nome;

    TipoAcao(final String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public static Optional<TipoAcao> of(final String label) {
        return Arrays.stream(TipoAcao.values())
                .filter(it -> it.nome.equalsIgnoreCase(label))
                .findFirst();
    }
}
