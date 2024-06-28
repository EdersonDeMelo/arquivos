package com.sistran.fastclaims.domain.colecao;

import com.sistran.fastclaims.domain.validation.Error;
import com.sistran.fastclaims.domain.validation.ValidationHandler;
import com.sistran.fastclaims.domain.validation.Validator;

public class ColecaoValidator extends Validator {

    public static final int NAME_MAX_LENGTH = 40;
    public static final int NAME_MIN_LENGTH = 3;

    private final Colecao colecao;

    public ColecaoValidator(final Colecao colecao, final ValidationHandler aHandler) {
        super(aHandler);
        this.colecao = colecao;
    }

    @Override
    public void validate() {
        checkNomeConstraints();
        checkAliasConstraints();
    }

    private void checkNomeConstraints() {
        final var nome = this.colecao.getNome();
        if (nome == null) {
            this.validationHandler().append(new Error("'nome' não pode ser nulo!"));
            return;
        }

        if (nome.isBlank()) {
            this.validationHandler().append(new Error("'nome' não pode ser vazio"));
            return;
        }

        if (nome.contains(" ")) {
            this.validationHandler().append(new Error("'nome' não pode conter espaços em branco"));
        }

        final var length = nome.trim().length();
        if (length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            this.validationHandler().append(new Error("'nome' dever ter entre 3 e 40 caracteres"));
        }
    }

    private void checkAliasConstraints() {
        final var alias = this.colecao.getAlias();
        if (alias == null) {
            this.validationHandler().append(new Error("'alias' não pode ser nulo!"));
            return;
        }

        if (alias.isBlank()) {
            this.validationHandler().append(new Error("'alias' não pode ser vazio"));
            return;
        }

        final var length = alias.trim().length();
        if (length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            this.validationHandler().append(new Error("'alias' dever ter entre 3 e 40 caracteres"));
        }
    }
}
