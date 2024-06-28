package com.sistran.fastclaims.infrastructure.segmento.persistence;

import com.sistran.fastclaims.domain.segmento.Segmento;
import com.sistran.fastclaims.domain.segmento.SegmentoID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "segmento")
public class SegmentoCollection {

    @Id
    private String id;
    private String nome;
    private Instant dataCadastro;
    private Instant dataAlteracao;

    public SegmentoCollection() {
    }

    private SegmentoCollection(final String id, final String nome, final Instant dataCadastro, final Instant dataAlteracao) {
        this.id = id;
        this.nome = nome;
        this.dataCadastro = dataCadastro;
        this.dataAlteracao = dataAlteracao;
    }

    public static SegmentoCollection aPartirDe(final Segmento segmento) {
        return new SegmentoCollection(segmento.getId().getValue(), segmento.getNome(), segmento.getDataCadastro(), segmento.getDataAlteracao());
    }

    public static SegmentoCollection aPartirDe(final String id, final String nome, final Instant dataCadastro, final Instant dataAlteracao) {
        return new SegmentoCollection(id, nome, dataCadastro, dataAlteracao);
    }

    public Segmento paraAgregado() {
        return Segmento.aPartirDe(SegmentoID.aPartirDe(getId()), getNome(), getDataCadastro(), getDataAlteracao());
    }

    public String getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public Instant getDataCadastro() {
        return this.dataCadastro;
    }

    public Instant getDataAlteracao() {
        return this.dataAlteracao;
    }
}
