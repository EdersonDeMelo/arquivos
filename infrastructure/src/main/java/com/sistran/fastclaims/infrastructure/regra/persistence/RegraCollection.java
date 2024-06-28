package com.sistran.fastclaims.infrastructure.regra.persistence;

import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoID;
import com.sistran.fastclaims.domain.operador.OperadorID;
import com.sistran.fastclaims.domain.regra.Regra;
import com.sistran.fastclaims.domain.regra.RegraID;
import com.sistran.fastclaims.domain.trilha.TrilhaID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "regra")
public class RegraCollection {

    @Id
    private String id;
    private String nome;
    private String descricao;
    private String campoUm;
    private String operadorUm;
    private String campoDois;
    private String operadorDois;
    private String campoTres;
    private Instant dataCadastro;
    private Instant dataAlteracao;
    private List<String> trilhas;

    public RegraCollection() {
    }

    private RegraCollection(
            final String id,
            final String nome,
            final String descricao,
            final String campoUm,
            final String operadorUm,
            final String campoDois,
            final String operadorDois,
            final String campoTres,
            final Instant dataCadastro,
            final Instant dataAlteracao,
            final List<String> trilhas
    ) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.campoUm = campoUm;
        this.operadorUm = operadorUm;
        this.campoDois = campoDois;
        this.operadorDois = operadorDois;
        this.campoTres = campoTres;
        this.dataCadastro = dataCadastro;
        this.dataAlteracao = dataAlteracao;
        this.trilhas = trilhas == null ? new ArrayList<>() : trilhas;
    }

    public static RegraCollection aPartirDe(final Regra regra) {
        return new RegraCollection(
                regra.getId().getValue(),
                regra.getNome(),
                regra.getDescricao(),
                regra.getCampoUm().getValue(),
                regra.getOperadorUm().getValue(),
                regra.getCampoDois(),
                regra.getOperadorDois().getValue(),
                regra.getCampoTres(),
                regra.getDataCadastro(),
                regra.getDataAlteracao(),
                regra.getTrilhas() == null ? new ArrayList<>() : regra.getTrilhas().stream().map(TrilhaID::getValue).toList()
        );
    }

    public static RegraCollection aPartirDe(
            final String id,
            final String nome,
            final String descricao,
            final String campoUm,
            final String operadorUm,
            final String campoDois,
            final String operadorDois,
            final String campoTres,
            final Instant dataCadastro,
            final Instant dataAlteracao,
            final List<String> trilhas
    ) {
        return new RegraCollection(
                id,
                nome,
                descricao,
                campoUm,
                operadorUm,
                campoDois,
                operadorDois,
                campoTres,
                dataCadastro,
                dataAlteracao,
                trilhas == null ? new ArrayList<>() : new ArrayList<>(trilhas)
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

    public String getCampoUm() {
        return campoUm;
    }

    public String getOperadorUm() {
        return operadorUm;
    }

    public String getCampoDois() {
        return campoDois;
    }

    public String getOperadorDois() {
        return operadorDois;
    }

    public String getCampoTres() {
        return campoTres;
    }

    public Instant getDataCadastro() {
        return dataCadastro;
    }

    public Instant getDataAlteracao() {
        return dataAlteracao;
    }

    public List<String> getTrilhas() {
        return trilhas;
    }

    public Regra paraAgregado() {
        return Regra.aPartirDe(
                RegraID.from(getId()),
                getNome(),
                getDescricao(),
                ColecaoCampoID.from(getCampoUm()),
                OperadorID.from(getOperadorUm()),
                getCampoDois(),
                OperadorID.from(getOperadorDois()),
                getCampoTres(),
                getDataCadastro(),
                getDataAlteracao(),
                getTrilhas().stream().map(TrilhaID::from).toList()
        );
    }
}
