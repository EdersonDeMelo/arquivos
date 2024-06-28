package com.sistran.fastclaims.application.colecao.excluir;

import com.sistran.fastclaims.domain.colecao.ColecaoGateway;
import com.sistran.fastclaims.domain.colecao.ColecaoID;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoGateway;
import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.validation.Error;

public class DefaultExcluirColecaoUseCase extends ExcluirColecaoUseCase {

    private final ColecaoGateway colecaoGateway;
    private final ColecaoCampoGateway colecaoCampoGateway;

    public DefaultExcluirColecaoUseCase(final ColecaoGateway colecaoGateway,
                                        final ColecaoCampoGateway colecaoCampoGateway) {
        this.colecaoGateway = colecaoGateway;
        this.colecaoCampoGateway = colecaoCampoGateway;
    }

    @Override
    public void executar(String anIn) {
        final var campo = colecaoCampoGateway.pesquisarPorColecaoId(ColecaoID.from(anIn));
        campo.ifPresent(colecaoCampo -> {
            throw DomainException.with(new Error("Coleção possui campos associados"));
        });
        colecaoGateway.excluirPorId(ColecaoID.from(anIn));
    }
}
