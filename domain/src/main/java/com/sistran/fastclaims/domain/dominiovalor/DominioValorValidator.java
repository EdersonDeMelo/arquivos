package com.sistran.fastclaims.domain.dominiovalor;

import com.sistran.fastclaims.domain.validation.Error;
import com.sistran.fastclaims.domain.validation.ValidationHandler;
import com.sistran.fastclaims.domain.validation.Validator;

public class DominioValorValidator extends Validator {

    public static final int NAME_MAX_LENGTH = 40;
    public static final int NAME_MIN_LENGTH = 2;

    private final DominioValor dominioValor;

    public DominioValorValidator(final DominioValor dominioValor, final ValidationHandler aHandler) {
        super(aHandler);
        this.dominioValor = dominioValor;
    }

    @Override
    public void validate() {
        checkNomeConstraints();
        checkValorConstraints();
    }

    private void checkNomeConstraints() {
        final var nome = this.dominioValor.getNomeValor();
        if (nome == null) {
            this.validationHandler().append(new Error("'nomeValor' não pode ser nulo!"));
            return;
        }
        if (nome.isBlank()) {
            this.validationHandler().append(new Error("'nomeValor' não pode ser vazio"));
            return;
        }
        final var length = nome.trim().length();
        if (length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            this.validationHandler().append(new Error("'nomeValor' dever ter entre 2 e 40 caracteres"));
        }
    }

    private void checkValorConstraints() {
        final var valor = this.dominioValor.getCodigoValor();
        if (valor == null) {
            this.validationHandler().append(new Error("'codigoValor' não pode ser nulo!"));
            return;
        }
        if (valor.isBlank()) {
            this.validationHandler().append(new Error("'codigoValor' não pode ser vazio"));
            return;
        }
        final var length = valor.trim().length();
        if (length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            this.validationHandler().append(new Error("'codigoValor' dever ter entre 2 e 40 caracteres"));
        }
    }

}
