package com.sistran.fastclaims.domain;

import com.sistran.fastclaims.domain.validation.ValidationHandler;

public abstract class AggregateRoot<ID extends Identifier> extends Entity<ID> {

    protected AggregateRoot(ID id) {
        super(id);
    }

    public abstract void validate(final ValidationHandler handler);
}
