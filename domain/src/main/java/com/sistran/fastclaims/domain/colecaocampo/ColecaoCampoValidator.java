package com.sistran.fastclaims.domain.colecaocampo;

import com.sistran.fastclaims.domain.validation.Error;
import com.sistran.fastclaims.domain.validation.ValidationHandler;
import com.sistran.fastclaims.domain.validation.Validator;

import java.util.Arrays;

public class ColecaoCampoValidator extends Validator {

    private static final int NAME_MAX_LENGTH = 30;
    private static final int NAME_MIN_LENGTH = 3;

    private final ColecaoCampo colecaoCampo;

    private final TipoDado tipoDado;
    private final TipoFormato tipoFormato;

    protected ColecaoCampoValidator(ValidationHandler aHandler, ColecaoCampo colecaoCampo) {
        super(aHandler);
        this.colecaoCampo = colecaoCampo;
        this.tipoDado = colecaoCampo.getTipoDado();
        this.tipoFormato = colecaoCampo.getTipoFormato();
    }

    @Override
    public void validate() {
        checkCampoConstraints();
        checkAliasConstraints();
        checkTipoDadoConstraints();
        checkTipoFormatoConstraints();
        checkTipoDadoAndFormatoConstraints();
    }

    private void checkCampoConstraints() {
        final var campo = this.colecaoCampo.getCampo();
        if (campo == null) {
            this.validationHandler().append(new Error("'campo' não pode ser nulo!"));
            return;
        }

        if (campo.isBlank()) {
            this.validationHandler().append(new Error("'campo' não pode ser vazio"));
            return;
        }

        final var length = campo.trim().length();
        if (length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            this.validationHandler().append(new Error("'campo' dever ter entre 3 e 30 caracteres"));
        }
    }

    private void checkAliasConstraints() {
        final var alias = this.colecaoCampo.getAlias();
        final var length = alias.trim().length();
        if (length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            this.validationHandler().append(new Error("'alias' dever ter entre 3 e 30 caracteres"));
        }
    }

    private void checkTipoDadoConstraints() {
        if (tipoDado == null) {
            this.validationHandler().append(new Error("'tipoDado' não pode ser nulo!"));
        }
    }

    private void checkTipoFormatoConstraints() {
        if (tipoFormato == null) {
            this.validationHandler().append(new Error("'tipoFormato' não pode ser nulo!"));
        }
    }

    private void checkTipoDadoAndFormatoConstraints() {

        if (tipoDado == null || tipoFormato == null) {
            return;
        }

        switch (tipoDado) {
            case NUMERICO:
                verificarTipoDadoNumerico();
                break;
            case ALFA:
                verificarTipoDadoAlfa();
                break;
            case DATA:
                verificarTipoDadoData();
                break;
        }
    }

    private void verificarTipoDadoNumerico() {
        if (tipoFormato != TipoFormato.ZERO && tipoFormato != TipoFormato.DECIMAL && tipoFormato != TipoFormato.PERCENT) {
            this.validationHandler().append(new Error("'tipoDado' NUMERICO deve ter 'tipoFormato' 0, 0.0 ou %"));
        }
    }

    private void verificarTipoDadoAlfa() {
        if (tipoFormato != TipoFormato.ASTERISCO) {
            this.validationHandler().append(new Error("'tipoDado' ALFA deve ter 'tipoFormato' *"));
        }
    }

    private void verificarTipoDadoData() {
        if (tipoFormato != TipoFormato.DATA) {
            this.validationHandler().append(new Error("'tipoDado' DATA deve ter 'tipoFormato' AAAA-MM-DD"));
        }
    }
}
