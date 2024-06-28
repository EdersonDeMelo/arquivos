package com.sistran.fastclaims.domain.grupousuario;

import com.sistran.fastclaims.domain.validation.Error;
import com.sistran.fastclaims.domain.validation.ValidationHandler;
import com.sistran.fastclaims.domain.validation.Validator;

public class GrupoUsuarioValidator extends Validator {

    public static final int NAME_MAX_LENGTH = 40;
    public static final int NAME_MIN_LENGTH = 2;

    private final GrupoUsuario grupoUsuario;

    protected GrupoUsuarioValidator(GrupoUsuario grupoUsuario, ValidationHandler aHandler) {
        super(aHandler);
        this.grupoUsuario = grupoUsuario;
    }

    @Override
    public void validate() {
        checkNomeConstraints();
    }

    private void checkNomeConstraints() {
        final var nome = this.grupoUsuario.getNome();
        if (nome == null) {
            this.validationHandler().append(new Error("'nome' não pode ser nulo!"));
            return;
        }
        if (nome.isBlank()) {
            this.validationHandler().append(new Error("'nome' não pode ser vazio"));
            return;
        }

        final var length = nome.trim().length();
        if (length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            this.validationHandler().append(new Error("'nome' dever ter entre 2 e 40 caracteres"));
        }
    }
}
