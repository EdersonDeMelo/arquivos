package com.sistran.fastclaims.application.colecaocampo.pesquisar.id;

import com.sistran.fastclaims.domain.colecao.ColecaoID;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampo;
import com.sistran.fastclaims.domain.colecaocampo.TipoDado;
import com.sistran.fastclaims.domain.colecaocampo.TipoFormato;
import com.sistran.fastclaims.domain.dominio.DominioID;

public record PesquisarColecaoCampoPorIdOutput(String id, String campo, String alias, TipoDado tipoDado,
                                               TipoFormato tipoFormato, String rastro, ColecaoID colecaoId,
                                               DominioID dominioId) {
    public static PesquisarColecaoCampoPorIdOutput aPartirDe(final ColecaoCampo colecaoCampo) {
        return new PesquisarColecaoCampoPorIdOutput(colecaoCampo.getId().getValue(), colecaoCampo.getCampo(),
                colecaoCampo.getAlias(), colecaoCampo.getTipoDado(),
                colecaoCampo.getTipoFormato(), colecaoCampo.getRastro(),
                colecaoCampo.getColecaoID(), colecaoCampo.getDominioID());
    }
}
