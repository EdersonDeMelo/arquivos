package com.sistran.fastclaims.application.operador.pesquisar.id;

import com.sistran.fastclaims.domain.operador.Operador;
import com.sistran.fastclaims.domain.operador.OperadorID;
import com.sistran.fastclaims.domain.operador.TipoOperador;

public record PesquisarOperadorPorIdOutput(OperadorID id, String nome, String simbolo, TipoOperador tipoOperador) {

    public static PesquisarOperadorPorIdOutput aPartirDe(final Operador operador) {
        return new PesquisarOperadorPorIdOutput(operador.getId(), operador.getNome(), operador.getSimbolo(),
                operador.getTipoOperador());
    }

}
