package com.sistran.fastclaims.domain.operador;

import com.sistran.fastclaims.domain.AggregateRoot;
import com.sistran.fastclaims.domain.validation.ValidationHandler;

public class Operador extends AggregateRoot<OperadorID> implements Cloneable {

    private String nome;
    private String simbolo;
    private TipoOperador tipoOperador;

    public Operador(final OperadorID id, final String nome, final String simbolo, final TipoOperador tipoOperador) {
        super(id);
        this.nome = nome;
        this.simbolo = simbolo;
        this.tipoOperador = tipoOperador;
    }

    public static Operador novoOperador(final String nome, final String simbolo, final TipoOperador tipoOperador) {
        final var id = OperadorID.unique();
        return new Operador(id, nome, simbolo, tipoOperador);
    }

    public static Operador com(final OperadorID id, final String nome, final String simbolo, final TipoOperador tipoOperador) {
        return new Operador(id, nome, simbolo, tipoOperador);
    }

    public static Operador com(final String id, final String nome, final String simbolo, final TipoOperador tipoOperador) {
        return new Operador(new OperadorID(id), nome, simbolo, tipoOperador);
    }

    public String getNome() {
        return nome;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public TipoOperador getTipoOperador() {
        return tipoOperador;
    }

    @Override
    public void validate(ValidationHandler handler) {

    }

    @Override
    public Operador clone() {
        try {
            Operador clone = (Operador) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
