package com.sistran.fastclaims.domain.exceptions;

import com.sistran.fastclaims.domain.AggregateRoot;
import com.sistran.fastclaims.domain.Identifier;
import com.sistran.fastclaims.domain.validation.Error;

import java.util.Collections;
import java.util.List;

public class ParametroInexistenteException extends DomainException {

    protected ParametroInexistenteException(final String aMessage, final List<Error> anErrors) {
        super(aMessage, anErrors);
    }

    public static NotFoundException with(
            final Class<? extends AggregateRoot<?>> anAggregate,
            final Identifier id
    ) {
        final var anError = generateErrorMessage(anAggregate, "de código %s inexistente", id.getValue());
        return new NotFoundException(anError, Collections.emptyList());
    }

    public static NotFoundException with(
            final Class<? extends AggregateRoot<?>> anAggregate,
            final String nome
    ) {
        final var anError = generateErrorMessage(anAggregate, "com nome %s inexistente", nome);
        return new NotFoundException(anError, Collections.emptyList());
    }

    public static NotFoundException com(
            final Class<? extends AggregateRoot<?>> anAggregate,
            final String id
    ) {
        final var anError = generateErrorMessage(anAggregate, "com id %s inexistente", id);
        return new NotFoundException(anError, Collections.emptyList());
    }


    public static NotFoundException with(final Error error) {
        return new NotFoundException(error.message(), List.of(error));
    }

    private static String generateErrorMessage(
            final Class<? extends AggregateRoot<?>> anAggregate,
            final String format,
            final String value
    ) {
        return "%s ".formatted(anAggregate.getSimpleName()) + format.formatted(value);
    }
}