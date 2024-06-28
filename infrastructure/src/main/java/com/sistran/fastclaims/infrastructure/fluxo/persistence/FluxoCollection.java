package com.sistran.fastclaims.infrastructure.fluxo.persistence;

import com.sistran.fastclaims.domain.fluxo.Fluxo;
import com.sistran.fastclaims.domain.fluxo.FluxoID;
import com.sistran.fastclaims.domain.natureza.NaturezaID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "fluxo")
public class FluxoCollection {

    @Id
    private String id;
    private String descricao;
    private Instant dataCadastro;
    private Instant dataAlteracao;
    private String naturezaId;

    public FluxoCollection() {
    }

    private FluxoCollection(final String id, final String descricao, final Instant dataCadastro, final Instant dataAlteracao, final String naturezaId) {
        this.id = id;
        this.descricao = descricao;
        this.dataCadastro = dataCadastro;
        this.dataAlteracao = dataAlteracao;
        this.naturezaId = naturezaId;
    }

    public static FluxoCollection aPartirDe(final Fluxo fluxo) {
        return new FluxoCollection(fluxo.getId().getValue(), fluxo.getDescricao(), fluxo.getDataCadastro(), fluxo.getDataAlteracao(), fluxo.getNaturezaId().getValue());
    }

    public Fluxo paraAgregado() {
        return Fluxo.com(FluxoID.from(getId()), getDescricao(), getDataCadastro(), getDataAlteracao(), NaturezaID.aPartirDe(getNaturezaId()));
    }

    public String getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Instant getDataAlteracao() {
        return dataAlteracao;
    }

    public Instant getDataCadastro() {
        return dataCadastro;
    }

    public String getNaturezaId() {
        return naturezaId;
    }
}
