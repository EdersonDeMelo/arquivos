package com.sistran.fastclaims.application.operador.pesquisar.lista;

import com.sistran.fastclaims.domain.operador.OperadorID;
import com.sistran.fastclaims.domain.operador.TipoOperador;

public record ListarOperadoresOutput(OperadorID id, String nome, String simbolo, TipoOperador tipoOperador) {

    public static ListarOperadoresOutput aPartirDe(OperadorID id, String nome, String simbolo,
                                                   TipoOperador tipoOperador) {
        return new ListarOperadoresOutput(id, nome, simbolo, tipoOperador);
    }
}
