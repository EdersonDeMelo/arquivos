package com.sistran.fastclaims.infrastructure.colecaocampo.persistence;

import com.sistran.fastclaims.domain.colecao.ColecaoID;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampo;
import com.sistran.fastclaims.domain.colecaocampo.TipoDado;
import com.sistran.fastclaims.domain.colecaocampo.TipoFormato;
import com.sistran.fastclaims.domain.dominio.DominioID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "colecaoCampo")
public class ColecaoCampoCollection {

    @Id
    private String id;
    private String campo;
    private String alias;
    private TipoDado tipoDado;
    private TipoFormato tipoFormato;
    private String rastro;
    private String colecaoID;
    private String dominioID;

    public ColecaoCampoCollection() {
    }

    public ColecaoCampoCollection(final String id,
                                  final String campo,
                                  final String alias,
                                  final TipoDado tipoDado,
                                  final TipoFormato tipoFormato,
                                  final String rastro,
                                  final String colecaoID,
                                  final String dominioID) {
        this.id = id;
        this.campo = campo;
        this.alias = alias;
        this.tipoDado = tipoDado;
        this.tipoFormato = tipoFormato;
        this.rastro = rastro;
        this.colecaoID = colecaoID;
        this.dominioID = dominioID;
    }

    public ColecaoCampo paraAgregado() {
        return ColecaoCampo.com(getId(), getCampo(), getAlias(), getTipoDado(), getTipoFormato(), getRastro(),
                ColecaoID.from(getColecaoID()), DominioID.from(getDominioID()));
    }

    public static ColecaoCampoCollection aPartirDe(final ColecaoCampo colecaoCampo) {
        return new ColecaoCampoCollection(
                colecaoCampo.getId().getValue(),
                colecaoCampo.getCampo(),
                colecaoCampo.getAlias(),
                colecaoCampo.getTipoDado(),
                colecaoCampo.getTipoFormato(),
                colecaoCampo.getRastro(),
                colecaoCampo.getColecaoID().getValue(),
                colecaoCampo.getDominioID().getValue()
        );
    }

    public String getId() {
        return id;
    }

    public String getCampo() {
        return campo;
    }

    public String getAlias() {
        return alias;
    }

    public TipoDado getTipoDado() {
        return tipoDado;
    }

    public TipoFormato getTipoFormato() {
        return tipoFormato;
    }

    public String getRastro() {
        return rastro;
    }

    public String getColecaoID() {
        return colecaoID;
    }

    public String getDominioID() {
        return dominioID;
    }
}
