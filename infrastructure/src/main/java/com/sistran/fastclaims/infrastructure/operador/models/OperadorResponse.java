package com.sistran.fastclaims.infrastructure.operador.models;

import com.sistran.fastclaims.domain.operador.TipoOperador;

public record OperadorResponse(String id, String nome, String simbolo, TipoOperador tipoOperador) {
    public static OperadorResponse aPartirDe(String id, String nome, String simbolo, TipoOperador tipoOperador) {
        return new OperadorResponse(id, nome, simbolo, tipoOperador);
    }
}
