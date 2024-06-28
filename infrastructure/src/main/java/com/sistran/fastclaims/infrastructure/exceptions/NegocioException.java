package com.sistran.fastclaims.infrastructure.exceptions;


public class NegocioException extends RuntimeException {

    public NegocioException(final String message) {
        super(message);
    }

    public NegocioException(final String message, final Throwable cause) {
        super(message, cause);
    }
}