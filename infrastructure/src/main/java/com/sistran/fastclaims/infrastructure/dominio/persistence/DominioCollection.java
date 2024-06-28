package com.sistran.fastclaims.infrastructure.dominio.persistence;


import com.sistran.fastclaims.domain.dominio.Dominio;
import com.sistran.fastclaims.domain.dominio.DominioID;
import com.sistran.fastclaims.infrastructure.dominiovalor.persistence.DominioValorCollection;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "dominio")
public class DominioCollection {

    @Id
    private String id;
    private String nome;
    private String descricao;
    private Instant dataCadastro;
    private Instant dataAlteracao;
    private List<DominioValorCollection> valores;

    public DominioCollection() {
    }

    private DominioCollection(final String id, final String nome, final String descricao, final Instant dataCadastro, final Instant dataAlteracao, final List<DominioValorCollection> valores) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataCadastro = dataCadastro;
        this.dataAlteracao = dataAlteracao;
        this.valores = valores == null ? new ArrayList<>() : valores;
    }

    public static DominioCollection aPartirDe(final Dominio dominio) {
        return new DominioCollection(dominio.getId().getValue(), dominio.getNome(), dominio.getDescricao(), dominio.getDataCadastro(), dominio.getDataAlteracao(),
                dominio.getValores().stream().map(v -> DominioValorCollection.aPartirDe(v, dominio.getId().getValue())).toList());
    }

    public Dominio paraAgregado() {
        return Dominio.com(
                DominioID.from(getId()),
                getNome(),
                getDescricao(),
                getDataCadastro(),
                getDataAlteracao(),
                getValores().stream().map(DominioValorCollection::paraAgregado).toList()
        );
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Instant getDataCadastro() {
        return dataCadastro;
    }

    public Instant getDataAlteracao() {
        return dataAlteracao;
    }

    public List<DominioValorCollection> getValores() {
        return valores;
    }
}
