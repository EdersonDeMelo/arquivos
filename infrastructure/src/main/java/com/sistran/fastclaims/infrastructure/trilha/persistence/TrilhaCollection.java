package com.sistran.fastclaims.infrastructure.trilha.persistence;

import com.sistran.fastclaims.domain.fluxo.FluxoID;
import com.sistran.fastclaims.domain.regra.Regra;
import com.sistran.fastclaims.domain.regra.RegraID;
import com.sistran.fastclaims.domain.trilha.RegraTrilha;
import com.sistran.fastclaims.domain.trilha.RegraTrilhaPreview;
import com.sistran.fastclaims.domain.trilha.Trilha;
import com.sistran.fastclaims.domain.trilha.TrilhaID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Document(collection = "trilha")
public class TrilhaCollection {

    @Id
    private String id;
    private String nome;
    private String descricao;
    private Instant dataCadastro;
    private Instant dataAlteracao;
    private boolean ativo;
    private String fluxoId;
    private List<RegraTrilhaCollection> regrasTrilha;

    public TrilhaCollection() {
    }

    public TrilhaCollection(
            final String id,
            final String nome,
            final String descricao,
            final Instant dataCadastro,
            final Instant dataAlteracao,
            final boolean ativo,
            final String fluxoId,
            final List<RegraTrilhaCollection> regrasTrilha
    ) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataCadastro = dataCadastro;
        this.dataAlteracao = dataAlteracao;
        this.ativo = ativo;
        this.fluxoId = fluxoId;
        this.regrasTrilha = regrasTrilha == null ? new ArrayList<>() : regrasTrilha;
    }


    public static TrilhaCollection aPartirDe(final Trilha trilha) {
        return new TrilhaCollection(
                trilha.getId().getValue(),
                trilha.getNome(),
                trilha.getDescricao(),
                trilha.getDataCadastro(),
                trilha.getDataAlteracao(),
                trilha.isAtivo(),
                trilha.getFluxoId().getValue(),
                trilha.getRegras() == null ? new ArrayList<>() : trilha.getRegras().stream()
                        .map(RegraTrilhaCollection::aPartirDe)
                        .collect(Collectors.toList())
        );
    }

    public static TrilhaCollection aPartirDeTrilhaComRegras(final Trilha trilha) {
        return new TrilhaCollection(
                trilha.getId().getValue(),
                trilha.getNome(),
                trilha.getDescricao(),
                trilha.getDataCadastro(),
                trilha.getDataAlteracao(),
                trilha.isAtivo(),
                trilha.getFluxoId().getValue(),
                trilha.getRegras() == null ? new ArrayList<>() : trilha.getRegras().stream()
                        .map(RegraTrilhaCollection::aPartirDe)
                        .collect(Collectors.toList())
        );
    }

    public Trilha paraAgregado() {
        return Trilha.com(
                TrilhaID.from(getId()),
                getNome(),
                getDescricao(),
                getDataCadastro(),
                getDataAlteracao(),
                isAtivo(),
                FluxoID.from(getFluxoId()),
                getRegrasTrilha().stream().map(RegraTrilhaCollection::paraAgregado).collect(Collectors.toList())
        );
    }

    public RegraTrilhaPreview paraRegraTrilhaPreview(final Regra regra, final RegraTrilha regraTrilha) {
        return new RegraTrilhaPreview(
                regra.getId().getValue(),
                regra.getNome(),
                regra.getDescricao(),
                regra.getCampoUm().getValue(),
                regra.getOperadorUm().getValue(),
                regra.getCampoDois(),
                regra.getOperadorDois().getValue(),
                regra.getCampoTres(),
                regraTrilha.resultadoEsperado(),
                regraTrilha.tipoAcao().getNome(),
                regraTrilha.ativa()
        );
    }

    public RegraTrilha paraRegraTrilha(final RegraTrilha regraTrilha) {
        return RegraTrilha.novaRegraTrilha(
                RegraID.from(regraTrilha.id().getValue()),
                regraTrilha.resultadoEsperado(),
                regraTrilha.tipoAcao(),
                regraTrilha.ativa()
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

    public Instant getDataAlteracao() {
        return dataAlteracao;
    }

    public Instant getDataCadastro() {
        return dataCadastro;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public String getFluxoId() {
        return fluxoId;
    }

    public List<RegraTrilhaCollection> getRegrasTrilha() {
        return regrasTrilha;
    }
}
