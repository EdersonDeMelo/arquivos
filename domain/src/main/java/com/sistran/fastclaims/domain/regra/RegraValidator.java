package com.sistran.fastclaims.domain.regra;

import com.sistran.fastclaims.domain.validation.Error;
import com.sistran.fastclaims.domain.validation.ValidationHandler;
import com.sistran.fastclaims.domain.validation.Validator;

public class RegraValidator extends Validator {

    private final Regra regra;

    public RegraValidator(final Regra regra, final ValidationHandler handler) {
        super(handler);
        this.regra = regra;
    }

    @Override
    public void validate() {
        checkNomeConstraints();
        checkCampoUmConstraints();
        checkOperadorDoisConstraints();
        checkCampoTresConstraints();
    }

    private void checkNomeConstraints() {
        final var nome = this.regra.getNome();
        if (nome == null) {
            this.validationHandler().append(new Error("'nome' não pode ser nulo!"));
            return;
        }

        if (nome.isBlank()) {
            this.validationHandler().append(new Error("'nome' não pode ser vazio"));
        }
    }

    private void checkCampoUmConstraints() {
        final var campoUm = this.regra.getCampoUm();
        if (campoUm == null) {
            this.validationHandler().append(new Error("'campoUm' não pode ser nulo!"));
            return;
        }

        if (campoUm.getValue().isBlank()) {
            this.validationHandler().append(new Error("'campoUm' não pode ser vazio"));
        }
    }

    private void checkOperadorDoisConstraints() {
        final var operadorDois = this.regra.getOperadorDois();
        if (operadorDois == null) {
            this.validationHandler().append(new Error("'operadorDois' não pode ser nulo!"));
            return;
        }

        if (operadorDois.getValue().isBlank()) {
            this.validationHandler().append(new Error("'operadorDois' não pode ser vazio"));
        }
    }

    private void checkCampoTresConstraints() {
        final var campoTres = this.regra.getCampoTres();
        if (campoTres == null) {
            this.validationHandler().append(new Error("'campoTres' não pode ser nulo!"));
            return;
        }
        if (campoTres.isBlank()) {
            this.validationHandler().append(new Error("'campoTres' não pode ser vazio"));
        }
    }
}
