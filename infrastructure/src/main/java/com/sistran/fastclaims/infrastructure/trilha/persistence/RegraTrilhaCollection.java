package com.sistran.fastclaims.infrastructure.trilha.persistence;

import com.sistran.fastclaims.domain.regra.RegraID;
import com.sistran.fastclaims.domain.regra.TipoAcao;
import com.sistran.fastclaims.domain.trilha.RegraTrilha;

import java.time.Instant;

public class RegraTrilhaCollection {

    private String id;
    private boolean resultadoEsperado;
    private TipoAcao tipoAcao;
    private boolean ativa;
    private Instant dataAlteracao;

    private RegraTrilhaCollection(
            final String id,
            final boolean resultadoEsperado,
            final TipoAcao tipoAcao,
            final boolean ativa,
            final Instant dataAlteracao
    ) {
        this.id = id;
        this.resultadoEsperado = resultadoEsperado;
        this.tipoAcao = tipoAcao;
        this.ativa = ativa;
        this.dataAlteracao = dataAlteracao;
    }

    public static RegraTrilhaCollection aPartirDe(RegraTrilha regra) {
        return new RegraTrilhaCollection(regra.id().getValue(), regra.resultadoEsperado(), regra.tipoAcao(), regra.ativa(), regra.dataAlteracao());
    }

    public static RegraTrilha paraAgregado(RegraTrilhaCollection regra) {
        return RegraTrilha.aPartirDe(
                RegraID.from(regra.id),
                regra.resultadoEsperado,
                regra.tipoAcao,
                regra.ativa,
                regra.dataAlteracao
        );
    }

    public String id() {
        return id;
    }

    public boolean resultadoEsperado() {
        return resultadoEsperado;
    }

    public TipoAcao tipoAcao() {
        return tipoAcao;
    }

    public boolean ativa() {
        return ativa;
    }

    public Instant dataAlteracao() {
        return dataAlteracao;
    }
}

