package com.sistran.fastclaims.application.colecaocampo.pesquisar.listar;

import com.sistran.fastclaims.domain.colecao.ColecaoID;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampo;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoID;
import com.sistran.fastclaims.domain.colecaocampo.TipoDado;
import com.sistran.fastclaims.domain.colecaocampo.TipoFormato;
import com.sistran.fastclaims.domain.dominio.DominioID;

public record ListarColecaoCampoOutput(ColecaoCampoID id, String campo, String alias, TipoDado tipoDado, TipoFormato tipoFormato,
                                       String rastro, ColecaoID colecaoID, DominioID dominioID) {

    public static ListarColecaoCampoOutput aPartirDe(final ColecaoCampo colecaoCampo) {
        return new ListarColecaoCampoOutput(colecaoCampo.getId(), colecaoCampo.getCampo(), colecaoCampo.getAlias(),
                colecaoCampo.getTipoDado(), colecaoCampo.getTipoFormato(), colecaoCampo.getRastro(),
                colecaoCampo.getColecaoID(), colecaoCampo.getDominioID());
    }
}
