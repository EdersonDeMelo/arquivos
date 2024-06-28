package com.sistran.fastclaims.infrastructure.exceptions;

public abstract class EntidadeNaoEncontradaException extends NegocioException {

    public EntidadeNaoEncontradaException(final String message) {
        super(message);
    }
}
