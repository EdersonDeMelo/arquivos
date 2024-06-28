package com.sistran.fastclaims.domain.colecaocampo;

import com.sistran.fastclaims.domain.AggregateRoot;
import com.sistran.fastclaims.domain.colecao.Colecao;
import com.sistran.fastclaims.domain.colecao.ColecaoID;
import com.sistran.fastclaims.domain.dominio.DominioID;
import com.sistran.fastclaims.domain.validation.ValidationHandler;

public class ColecaoCampo extends AggregateRoot<ColecaoCampoID> implements Cloneable {

    private String campo;
    private String alias;
    private TipoDado tipoDado;
    private TipoFormato tipoFormato;
    private String rastro;
    private ColecaoID colecaoID;
    private DominioID dominioID;

    public ColecaoCampo(final ColecaoCampoID id,
                        final String campo,
                        final String alias,
                        final TipoDado tipoDado,
                        final TipoFormato tipoFormato,
                        final String rastro,
                        final ColecaoID colecaoID,
                        final DominioID dominioID) {
        super(id);
        this.campo = campo;
        this.alias = alias;
        this.tipoDado = tipoDado;
        this.tipoFormato = tipoFormato;
        this.rastro = rastro;
        this.colecaoID = colecaoID;
        this.dominioID = dominioID;
    }

    public static ColecaoCampo novaColecaoCampo(
            final String campo,
            final String alias,
            final TipoDado tipoDado,
            final TipoFormato tipoFormato,
            final ColecaoID colecaoID,
            final DominioID dominioID,
            final String nomeColecao) {
        return new ColecaoCampo(ColecaoCampoID.unique(), campo, alias, tipoDado, tipoFormato,
                gerarRastro(nomeColecao, campo), colecaoID, dominioID);
    }

    private static String gerarRastro(final String nomeColecao, final String campo) {
        return nomeColecao + "." + campo;
    }

    public static ColecaoCampo com(final ColecaoCampoID id, final String campo, final String alias,
                                   final TipoDado tipoDado, final TipoFormato tipoFormato,
                                   final String rastro, final ColecaoID colecaoID, final DominioID dominioID) {
        return new ColecaoCampo(id, campo, alias, tipoDado, tipoFormato, rastro, colecaoID, dominioID);
    }

    public static ColecaoCampo com(final String id, final String campo, final String alias, final TipoDado tipoDado,
                                   final TipoFormato tipoFormato, final String rastro, final ColecaoID colecaoID,
                                   final DominioID dominioID) {
        return new ColecaoCampo(new ColecaoCampoID(id), campo, alias, tipoDado, tipoFormato, rastro, colecaoID,
                dominioID);
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

    public ColecaoID getColecaoID() {
        return colecaoID;
    }

    public DominioID getDominioID() {
        return dominioID;
    }

    @Override
    public void validate(ValidationHandler handler) {
        new ColecaoCampoValidator(handler, this).validate();
    }
}
