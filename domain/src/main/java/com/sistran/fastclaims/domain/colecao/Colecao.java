package com.sistran.fastclaims.domain.colecao;

import com.sistran.fastclaims.domain.AggregateRoot;
import com.sistran.fastclaims.domain.validation.ValidationHandler;

public class Colecao extends AggregateRoot<ColecaoID> implements Cloneable {

    private String nome;
    private String alias;

    private Colecao(final ColecaoID id, final String nome, final String alias) {
        super(id);
        this.nome = nome;
        this.alias = alias;
    }

    public static Colecao novaColecao(final String nome, final String alias) {
        final var id = ColecaoID.unique();
        return new Colecao(id, nome, alias);
    }

    public static Colecao com(final ColecaoID id, final String nome, final String alias) {
        return new Colecao(id, nome, alias);
    }

    public static Colecao com(final Colecao colecao) {
        return com(colecao.getId(), colecao.getNome(), colecao.getAlias());
    }

    public Colecao atualizar(final String alias) {
        this.alias = alias;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public void validate(ValidationHandler handler) {
        new ColecaoValidator(this, handler).validate();
    }
}
