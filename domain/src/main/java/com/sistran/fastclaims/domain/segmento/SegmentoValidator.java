package com.sistran.fastclaims.domain.segmento;

import com.sistran.fastclaims.domain.validation.Error;
import com.sistran.fastclaims.domain.validation.ValidationHandler;
import com.sistran.fastclaims.domain.validation.Validator;

public class SegmentoValidator extends Validator {

    private final Segmento segmento;

    public SegmentoValidator(final Segmento segmento, final ValidationHandler handler) {
        super(handler);
        this.segmento = segmento;
    }

    @Override
    public void validate() {
        checkNomeContraints();
    }

    private void checkNomeContraints() {
        final var nome = this.segmento.getNome();
        if (nome == null) {
            this.validationHandler().append(new Error("'nome' não pode ser nulo!"));
            return;
        }
        if (nome.isBlank()) {
            this.validationHandler().append(new Error("'nome' não pode ser vazio!"));
        }
    }
}
