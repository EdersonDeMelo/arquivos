package com.sistran.fastclaims.domain.regra;

import com.sistran.fastclaims.domain.AggregateRoot;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoID;
import com.sistran.fastclaims.domain.operador.OperadorID;
import com.sistran.fastclaims.domain.trilha.TrilhaID;
import com.sistran.fastclaims.domain.utils.InstantUtils;
import com.sistran.fastclaims.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Regra extends AggregateRoot<RegraID> implements Cloneable {

    private String nome;
    private String descricao;
    private ColecaoCampoID campoUm;
    private OperadorID operadorUm;
    private String campoDois;
    private OperadorID operadorDois;
    private String campoTres;
    private Instant dataCadastro;
    private Instant dataAlteracao;
    private List<TrilhaID> trilhas;

    private Regra(
            final RegraID id,
            final String nome,
            final String descricao,
            final ColecaoCampoID campoUm,
            final OperadorID operadorUm,
            final String campoDois,
            final OperadorID operadorDois,
            final String campoTres,
            final Instant dataCadastro,
            final Instant dataAlteracao,
            final List<TrilhaID> trilhas
    ) {
        super(id);
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

    public static Regra novaRegra(
            final String nome,
            final String descricao,
            final ColecaoCampoID campoUm,
            final OperadorID operadorUm,
            final String campoDois,
            final OperadorID operadorDois,
            final String campoTres
    ) {
        final var id = RegraID.unique();
        final var data = InstantUtils.now();
        return new Regra(
                id,
                nome,
                descricao,
                campoUm,
                operadorUm,
                campoDois,
                operadorDois,
                campoTres,
                data,
                data,
                null
        );
    }

    public static Regra aPartirDe(
            final RegraID id,
            final String nome,
            final String descricao,
            final ColecaoCampoID campoUm,
            final OperadorID operadorUm,
            final String campoDois,
            final OperadorID operadorDois,
            final String campoTres,
            final Instant dataCadastro,
            final Instant dataAlteracao,
            final List<TrilhaID> trilhas
    ) {
        return new Regra(
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

    public Regra atualizar(
            final String nome,
            final String descricao,
            final ColecaoCampoID campoUm,
            final OperadorID operadorUm,
            final String campoDois,
            final OperadorID operadorDois,
            final String campoTres
    ) {
        this.nome = nome;
        this.descricao = descricao;
        this.campoUm = campoUm;
        this.operadorUm = operadorUm;
        this.campoDois = campoDois;
        this.operadorDois = operadorDois;
        this.campoTres = campoTres;
        this.dataAlteracao = InstantUtils.now();
        return this;
    }

    public Regra adicionarTrilhaID(TrilhaID trilhaID) {
        this.trilhas.add(trilhaID);
        return this;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public ColecaoCampoID getCampoUm() {
        return campoUm;
    }

    public OperadorID getOperadorUm() {
        return operadorUm;
    }

    public String getCampoDois() {
        return campoDois;
    }

    public OperadorID getOperadorDois() {
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

    public List<TrilhaID> getTrilhas() {
        return trilhas;
    }

    @Override
    public void validate(ValidationHandler handler) {
        new RegraValidator(this, handler).validate();
    }

    @Override
    public Regra clone() {
        try {
            Regra clone = (Regra) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
