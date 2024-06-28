package com.sistran.fastclaims.application.validation;

import com.sistran.fastclaims.domain.operador.Operador;
import com.sistran.fastclaims.domain.operador.TipoOperador;
import com.sistran.fastclaims.domain.validation.Error;
import com.sistran.fastclaims.domain.validation.ValidationHandler;
import com.sistran.fastclaims.domain.validation.handler.Notification;

public final class RegraUseCaseValidator {

    private RegraUseCaseValidator() {
    }

    public static ValidationHandler validarOperadorUm(final Operador operador) {
        final var notification = Notification.create();

        if (operador != null && (operador.getTipoOperador() == TipoOperador.VALOR || operador.getTipoOperador() == TipoOperador.RELACIONAL)) {
            notification.append(new Error("O Operador Um deve ser do tipo ARITMETICO, FUNCAO ou FUNCAOAGRUPADA"));
        }
        return notification;
    }

    public static ValidationHandler validarOperadorDois(final Operador operador) {
        final var notification = Notification.create();

        if (operador != null && (!operador.getTipoOperador().equals(TipoOperador.RELACIONAL))) {
            notification.append(new Error("O Operador Dois deve ser do tipo RELACIONAL"));
        }
        return notification;
    }

    public static ValidationHandler validarCampoDoisOperadorUm(final String operadorUm, final String campoDois) {
        final var notification = Notification.create();

        if (operadorUm.isEmpty() || operadorUm.isBlank()) {
            if (campoDois != null && !campoDois.isEmpty()) {
                notification.append(new Error("'operadorUm' não pode ser nulo se 'campoDois' estiver preenchido"));
            }
        }
        return notification;
    }

    public static ValidationHandler validarTipoOperadorUmCampoDois(final Operador operadorUm, final String campoDois) {
        final var notification = Notification.create();

        if (operadorUm != null && (operadorUm.getTipoOperador() != TipoOperador.FUNCAOAGRUPADA && operadorUm.getTipoOperador() != TipoOperador.FUNCAO)) {
            if (campoDois == null || campoDois.isEmpty()) {
                notification.append(new Error("'campoDois' não pode ser nulo se 'operadorUm' for preenchido"));
            }
        }
        if (operadorUm != null && (operadorUm.getTipoOperador().equals(TipoOperador.FUNCAOAGRUPADA) || operadorUm.getTipoOperador().equals(TipoOperador.FUNCAO))) {
            if (campoDois != null && !campoDois.isEmpty()) {
                notification.append(new Error("'campoDois' não deve ser preenchido se 'operadorUm' for do tipo FUNCAO"));
            }
        }
        return notification;
    }
}
