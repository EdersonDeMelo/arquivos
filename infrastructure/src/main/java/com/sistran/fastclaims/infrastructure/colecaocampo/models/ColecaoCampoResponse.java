package com.sistran.fastclaims.infrastructure.colecaocampo.models;

import com.sistran.fastclaims.application.colecaocampo.pesquisar.listar.ListarColecaoCampoOutput;

public record ColecaoCampoResponse(String id, String campo, String alias, String tipoDado, String tipoFormato,
                                   String rastro, String colecaoId, String dominioId) {

    public static ColecaoCampoResponse aPartirDe(final String id, final String campo, final String alias, final String tipoDado,
                                                 final String tipoFormato, final String rastro, final String colecaoId, final String dominioId) {
        return new ColecaoCampoResponse(id, campo, alias, tipoDado, tipoFormato, rastro, colecaoId, dominioId);
    }

    public static ColecaoCampoResponse aPartirDe(final ListarColecaoCampoOutput colecaoCampo) {
        return new ColecaoCampoResponse(colecaoCampo.id().getValue(), colecaoCampo.campo(), colecaoCampo.alias(),
                colecaoCampo.tipoDado().name(), colecaoCampo.tipoFormato().name(), colecaoCampo.rastro(),
                colecaoCampo.colecaoID().getValue(), colecaoCampo.dominioID().getValue());
    }
}
