package com.sistran.fastclaims.infrastructure.dominiovalor.persistence;

import com.sistran.fastclaims.domain.dominio.DominioID;
import com.sistran.fastclaims.domain.dominiovalor.DominioValor;
import com.sistran.fastclaims.domain.dominiovalor.DominioValorID;
import org.springframework.data.annotation.Id;

import java.time.Instant;

public class DominioValorCollection {

    @Id
    private String id;
    private String codigoValor;
    private String nomeValor;
    private Instant dataCadastro;
    private Instant dataAlteracao;
    private String dominioID;

    public DominioValorCollection() {
    }

    private DominioValorCollection(final String id, final String codigoValor, final String nomeValor, final Instant dataCadastro,
                                   final Instant dataAlteracao, final String dominioID) {
        this.id = id;
        this.codigoValor = codigoValor;
        this.nomeValor = nomeValor;
        this.dataCadastro = dataCadastro;
        this.dataAlteracao = dataAlteracao;
        this.dominioID = dominioID;
    }

    public static DominioValorCollection aPartirDe(final DominioValor dominioValor, final String dominioID) {
        return new DominioValorCollection(dominioValor.getId().getValue(), dominioValor.getCodigoValor(), dominioValor.getNomeValor(),
                dominioValor.getDataCadastro(), dominioValor.getDataAlteracao(), dominioID);
    }

    public DominioValor paraAgregado() {
        return DominioValor.com(DominioValorID.from(getId()), getCodigoValor(), getNomeValor(), DominioID.from(getDominioID()), getDataCadastro(),
                getDataAlteracao());
    }

    public String getId() {
        return id;
    }

    public String getCodigoValor() {
        return codigoValor;
    }

    public String getNomeValor() {
        return nomeValor;
    }

    public Instant getDataCadastro() {
        return dataCadastro;
    }

    public Instant getDataAlteracao() {
        return dataAlteracao;
    }

    public String getDominioID() {
        return dominioID;
    }
}
