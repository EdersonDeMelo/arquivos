package com.sistran.fastclaims.domain.natureza;

import com.sistran.fastclaims.domain.validation.Error;
import com.sistran.fastclaims.domain.validation.ValidationHandler;
import com.sistran.fastclaims.domain.validation.Validator;

public class NaturezaValidator extends Validator {

    private final Natureza natureza;

    public NaturezaValidator(final Natureza natureza, final ValidationHandler handler) {
        super(handler);
        this.natureza = natureza;
    }

    @Override
    public void validate() {
        checkConstraints();
    }

    private void checkConstraints() {
        final var nome = this.natureza.getNome();
        final var codigoNatureza = this.natureza.getCodigoNatureza();

        if (nome == null && codigoNatureza == null) {
            this.validationHandler().append(new Error("'nome' e 'codigoNatureza' não podem ser nulos!"));
            return;
        }
        if (nome == null) {
            this.validationHandler().append(new Error("'nome' não pode ser nulo!"));
            return;
        }
        if (codigoNatureza == null) {
            this.validationHandler().append(new Error("'codigoNatureza' não pode ser nulo!"));
            return;
        }
        if (nome.isBlank() && codigoNatureza.isBlank()) {
            this.validationHandler().append(new Error("'nome' e 'codigoNatureza' não podem ser vazios!"));
            return;
        }
        if (nome.isBlank()) {
            this.validationHandler().append(new Error("'nome' não pode ser vazio!"));
            return;
        }
        if (codigoNatureza.isBlank()) {
            this.validationHandler().append(new Error("'codigoNatureza' não pode ser vazio!"));
            return;
        }
        if (!codigoNatureza.matches("\\d+")) {
            this.validationHandler().append(new Error("'codigoNatureza' deve ser um número!"));
        }
    }
}
