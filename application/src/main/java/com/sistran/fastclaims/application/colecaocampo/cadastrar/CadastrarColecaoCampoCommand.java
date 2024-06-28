package com.sistran.fastclaims.application.colecaocampo.cadastrar;

import com.sistran.fastclaims.domain.colecao.ColecaoID;
import com.sistran.fastclaims.domain.colecaocampo.TipoDado;
import com.sistran.fastclaims.domain.colecaocampo.TipoFormato;
import com.sistran.fastclaims.domain.dominio.DominioID;

public record CadastrarColecaoCampoCommand(String campo, String alias, TipoDado tipoDado, TipoFormato tipoFormato,
                                           String colecaoId, String dominioId) {

    public static CadastrarColecaoCampoCommand com(final String campo, final String alias, final TipoDado tipoDado,
                                                   final TipoFormato tipoFormato, final String colecaoId,
                                                   final String dominioId) {
        return new CadastrarColecaoCampoCommand(campo, alias, tipoDado, tipoFormato, colecaoId, dominioId);
    }
}
