package com.sistran.fastclaims.infrastructure.colecao.persistence;

import com.sistran.fastclaims.domain.colecao.Colecao;
import com.sistran.fastclaims.domain.colecao.ColecaoID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "colecao")
public class ColecaoCollection {

    @Id
    private String id;
    private String nome;
    private String alias;

    public ColecaoCollection() {
    }

    private ColecaoCollection(final String id, final String nome, final String alias) {
        this.id = id;
        this.nome = nome;
        this.alias = alias;
    }

    public static ColecaoCollection aPartirDe(final Colecao colecao) {
        return new ColecaoCollection(colecao.getId().getValue(), colecao.getNome(), colecao.getAlias());
    }

    public Colecao paraAgregado() {
        return Colecao.com(ColecaoID.from(getId()), getNome(), getAlias());
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getAlias() {
        return alias;
    }
}
