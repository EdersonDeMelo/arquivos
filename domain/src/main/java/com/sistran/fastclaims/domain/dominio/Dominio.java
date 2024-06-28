package com.sistran.fastclaims.domain.dominio;

import com.sistran.fastclaims.domain.AggregateRoot;
import com.sistran.fastclaims.domain.dominiovalor.DominioValor;
import com.sistran.fastclaims.domain.utils.InstantUtils;
import com.sistran.fastclaims.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dominio extends AggregateRoot<DominioID> implements Cloneable {

    private String nome;
    private String descricao;
    private Instant dataCadastro;
    private Instant dataAlteracao;
    private List<DominioValor> valores = new ArrayList<>();

    private Dominio(final DominioID id, final String nome, final String descricao, final Instant dataCadastro,
                    final Instant dataAlteracao, List<DominioValor> valores) {
        super(id);
        this.nome = nome;
        this.descricao = descricao;
        this.dataCadastro = Objects.requireNonNull(dataCadastro, "'dataCadastro' não pode ser nula!");
        this.dataAlteracao = Objects.requireNonNull(dataAlteracao, "'dataAlteracao' não pode ser nula!");
        if (valores != null) {
            this.valores.addAll(valores);
        }
    }

    public static Dominio novoDominio(final String nome, final String descricao, List<DominioValor> valores) {
        final var id = DominioID.unique();
        final var dataAtual = InstantUtils.now();
        return new Dominio(id, nome, descricao, dataAtual, dataAtual, valores);
    }

    public static Dominio com(final DominioID id, final String nome, final String descricao, final Instant dataCadastro,
                              final Instant dataAlteracao, List<DominioValor> valores) {
        return new Dominio(id, nome, descricao, dataCadastro, dataAlteracao, valores==null ? new ArrayList<>() : valores);
    }

    public static Dominio com(final Dominio dominio) {
        return com(dominio.getId(), dominio.getNome(), dominio.getDescricao(), dominio.getDataCadastro(),
                dominio.getDataAlteracao(), dominio.getValores());
    }

    public Dominio atualizar(final String nome, final String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataAlteracao = InstantUtils.now();
        return this;
    }

    public Dominio adicionarValor(final DominioValor valor) {
        this.valores.add(valor);
        return this;
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

    public List<DominioValor> getValores() {
        return valores;
    }

    @Override
    public void validate(ValidationHandler handler) {
        new DominioValidator(this, handler).validate();
    }

    @Override
    public Dominio clone() {
        try {
            return (Dominio) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Erro ao clonar a entidade Dominio", e);
        }
    }

}
