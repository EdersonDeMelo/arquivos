package com.sistran.fastclaims.domain.fluxo;

import com.sistran.fastclaims.domain.validation.Error;
import com.sistran.fastclaims.domain.validation.ValidationHandler;
import com.sistran.fastclaims.domain.validation.Validator;

public class FluxoValidator extends Validator {

    public static final int NAME_MAX_LENGTH = 50;
    public static final int NAME_MIN_LENGTH = 3;

    private final Fluxo fluxo;

    protected FluxoValidator(final Fluxo fluxo, final ValidationHandler aHandler) {
        super(aHandler);
        this.fluxo = fluxo;
    }

    @Override
    public void validate() {
        checkDescricaoConstraints();
        checkNaturezaConstraints();
    }

    private void checkDescricaoConstraints() {
        final var descricao = this.fluxo.getDescricao();
        if (descricao == null) {
            this.validationHandler().append(new Error("'descrição' não pode ser nula!"));
            return;
        }

        if (descricao.isBlank()) {
            this.validationHandler().append(new Error("'descricao' não pode ser vazia"));
            return;
        }

        final var length = descricao.trim().length();
        if (length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            this.validationHandler().append(new Error("'descricao' dever ter entre 3 e 50 caracteres"));
        }
    }

    private void checkNaturezaConstraints() {
        final var natureza = this.fluxo.getNaturezaId();
        if (natureza == null) {
            this.validationHandler().append(new Error("'naturezaId' não pode ser nulo!"));
            return;
        }
        if (natureza.getValue().isBlank()) {
            this.validationHandler().append(new Error("'naturezaId' não pode ser vazio"));
        }
    }
}

