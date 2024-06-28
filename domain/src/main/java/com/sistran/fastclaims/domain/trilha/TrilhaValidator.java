package com.sistran.fastclaims.domain.trilha;

import com.sistran.fastclaims.domain.validation.Error;
import com.sistran.fastclaims.domain.validation.ValidationHandler;
import com.sistran.fastclaims.domain.validation.Validator;

public class TrilhaValidator extends Validator {

    private final Trilha trilha;

    protected TrilhaValidator(final Trilha trilha, final ValidationHandler handler) {
        super(handler);
        this.trilha = trilha;
    }

    @Override
    public void validate() {
        checkNomeContraints();
    }

    private void checkNomeContraints() {
        final var nome = this.trilha.getNome();
        if (nome == null) {
            this.validationHandler().append(new Error("'nome' não pode ser nulo!"));
            return;
        }
        if (nome.isBlank()) {
            this.validationHandler().append(new Error("'nome' não pode ser vazio!"));
        }
    }
}

