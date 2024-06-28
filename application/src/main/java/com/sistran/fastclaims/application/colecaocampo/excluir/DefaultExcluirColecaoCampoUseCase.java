package com.sistran.fastclaims.application.colecaocampo.excluir;

import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoGateway;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoID;
import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.regra.RegraGateway;
import com.sistran.fastclaims.domain.validation.Error;

public class DefaultExcluirColecaoCampoUseCase extends ExcluirColecaoCampoUseCase{

    private final ColecaoCampoGateway colecaoCampoGateway;
    private final RegraGateway regraGateway;

    public DefaultExcluirColecaoCampoUseCase(
            final ColecaoCampoGateway colecaoCampoGateway,
            final RegraGateway regraGateway) {
        this.colecaoCampoGateway = colecaoCampoGateway;
        this.regraGateway = regraGateway;
    }

    @Override
    public void executar(String anIn) {
        if (validarCampos(anIn))
            throw DomainException.with(new Error("Campo associado a regras"));
        colecaoCampoGateway.excluir(ColecaoCampoID.from(anIn));
    }

    private boolean validarCampos(String anIn) {
        final var campoID = ColecaoCampoID.from(anIn);
        return
                regraGateway.pesquisarCampoUm(campoID).isPresent() ||
                regraGateway.pesquisarCampoDois(campoID).isPresent() ||
                regraGateway.pesquisarCampoTres(campoID).isPresent();
    }
}
