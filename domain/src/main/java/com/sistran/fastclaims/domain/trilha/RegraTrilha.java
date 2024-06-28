package com.sistran.fastclaims.domain.trilha;

import com.sistran.fastclaims.domain.ValueObject;
import com.sistran.fastclaims.domain.regra.RegraID;
import com.sistran.fastclaims.domain.regra.TipoAcao;
import com.sistran.fastclaims.domain.utils.InstantUtils;

import java.time.Instant;
import java.util.Objects;

public class RegraTrilha extends ValueObject {

    private RegraID id;
    private boolean resultadoEsperado;
    private TipoAcao tipoAcao;
    private boolean ativa;
    private Instant dataAlteracao;

    private RegraTrilha(
            final RegraID id,
            final boolean resultadoEsperado,
            final TipoAcao tipoAcao,
            final boolean ativa,
            final Instant dataAlteracao
    ) {
        this.id = Objects.requireNonNull(id, "'id' não pode ser nulo!");
        this.resultadoEsperado = resultadoEsperado;
        this.tipoAcao = Objects.requireNonNull(tipoAcao, "'tipoAcao' não pode ser nulo!");
        this.ativa = ativa;
        this.dataAlteracao = dataAlteracao;
    }

    public static RegraTrilha novaRegraTrilha(
            final RegraID id,
            final boolean resultadoEsperado,
            final TipoAcao tipoAcao,
            final boolean ativa
    ) {
        var data = InstantUtils.now();
        return new RegraTrilha(id, resultadoEsperado, tipoAcao, ativa, data);
    }

    public static RegraTrilha aPartirDe(final RegraID id, final boolean resultadoEsperado, final TipoAcao tipoAcao, final boolean ativa, final Instant dataAlteracao) {
        return new RegraTrilha(id, resultadoEsperado, tipoAcao, ativa, dataAlteracao);
    }


    public RegraID id() {
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

    public void ativar() {
        this.ativa = true;
        this.dataAlteracao = InstantUtils.now();
    }

    public void desativar() {
        this.ativa = false;
        this.dataAlteracao = InstantUtils.now();
    }
}
