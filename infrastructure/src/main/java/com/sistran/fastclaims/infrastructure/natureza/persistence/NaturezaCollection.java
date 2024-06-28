package com.sistran.fastclaims.infrastructure.natureza.persistence;

import com.sistran.fastclaims.domain.natureza.Natureza;
import com.sistran.fastclaims.domain.natureza.NaturezaID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "natureza")
public class NaturezaCollection {

    @Id
    private String id;
    private String nome;
    private String codigoNatureza;

    public NaturezaCollection() {
    }

    private NaturezaCollection(final String id, final String nome, final String codigoNatureza) {
        this.id = id;
        this.nome = nome;
        this.codigoNatureza = codigoNatureza;
    }

    public static NaturezaCollection aPartirDe(final Natureza natureza) {
        return new NaturezaCollection(natureza.getId().getValue(), natureza.getNome(), natureza.getCodigoNatureza());
    }

    public Natureza toAggregate() {
        return Natureza.com(NaturezaID.aPartirDe(getId()), getNome(), getCodigoNatureza());
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public String getCodigoNatureza() {
        return codigoNatureza;
    }

    public void setCodigoNatureza(final String codigoNatureza) {
        this.codigoNatureza = codigoNatureza;
    }
}
