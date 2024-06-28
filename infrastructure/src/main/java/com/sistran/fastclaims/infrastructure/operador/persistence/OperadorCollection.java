package com.sistran.fastclaims.infrastructure.operador.persistence;

import com.sistran.fastclaims.domain.operador.Operador;
import com.sistran.fastclaims.domain.operador.TipoOperador;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "operador")
public class OperadorCollection {

    @Id
    private String id;
    private String nome;
    private String simbolo;
    private TipoOperador tipoOperador;

    public OperadorCollection() {
    }

    public OperadorCollection(final String id, final String nome, final String simbolo,
                              final TipoOperador tipoOperador) {
        this.id = id;
        this.nome = nome;
        this.simbolo = simbolo;
        this.tipoOperador = tipoOperador;
    }

    public Operador paraAgregado() {
        return Operador.com(getId(), getNome(), getSimbolo(), getTipoOperador());
    }

    public String getId() {
        return id;
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
}
