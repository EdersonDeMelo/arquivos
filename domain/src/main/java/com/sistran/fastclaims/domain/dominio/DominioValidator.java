package com.sistran.fastclaims.domain.dominio;

import com.sistran.fastclaims.domain.dominiovalor.DominioValor;
import com.sistran.fastclaims.domain.dominiovalor.DominioValorValidator;
import com.sistran.fastclaims.domain.validation.Error;
import com.sistran.fastclaims.domain.validation.ValidationHandler;
import com.sistran.fastclaims.domain.validation.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DominioValidator extends Validator {

    public static final int NAME_MAX_LENGTH = 40;
    public static final int NAME_MIN_LENGTH = 2;

    private final Dominio dominio;

    public DominioValidator(Dominio dominio, ValidationHandler handler) {
        super(handler);
        this.dominio = dominio;
    }

    @Override
    public void validate() {
        checkNomeConstraints();
        checkDescricaoConstraints();
        checkValoresConstraints();
    }

    private void checkNomeConstraints() {
        final var nome = this.dominio.getNome();
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

    private void checkDescricaoConstraints() {
        final var descricao = this.dominio.getDescricao();
        if (descricao == null) {
            this.validationHandler().append(new Error("'descricao' não pode ser nula!"));
            return;
        }
        if (descricao.isBlank()) {
            this.validationHandler().append(new Error("'descricao' não pode ser vazia"));
            return;
        }
        final var length = descricao.trim().length();
        if (length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            this.validationHandler().append(new Error("'descricao' dever ter entre 2 e 40 caracteres"));
        }
    }

    private void checkValoresConstraints() {
        final var valores = this.dominio.getValores();
        if (valores == null) {
            this.validationHandler().append(new Error("'valores' não pode ser nulo!"));
            return;
        }
        if (valores.isEmpty()) {
            this.validationHandler().append(new Error("'valores' não pode ser vazio"));
            return;
        }

        if (codigoValorExistente(valores)) return;
        for (DominioValor valor : valores) {
            new DominioValorValidator(valor, this.validationHandler()).validate();
        }
    }

    private boolean codigoValorExistente(List<DominioValor> valores) {
        Map<String, Integer> codigoValorCount = new HashMap<>();
        for (DominioValor valor : valores) {
            codigoValorCount.put(valor.getCodigoValor(), codigoValorCount.getOrDefault(valor.getCodigoValor(), 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : codigoValorCount.entrySet()) {
            if (entry.getValue() > 1) {
                this.validationHandler().append(new Error("'codigoValor' " + entry.getKey() + " já existente"));
                return true;
            }
        }
        return false;
    }
}