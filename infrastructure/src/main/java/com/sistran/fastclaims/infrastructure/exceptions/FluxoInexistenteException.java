package com.sistran.fastclaims.infrastructure.exceptions;

public class FluxoInexistenteException extends EntidadeNaoEncontradaException {

    public FluxoInexistenteException(final String id) {
        super(String.format("Não existe um cadastro de fluxo com código %s", id));
    }
}
