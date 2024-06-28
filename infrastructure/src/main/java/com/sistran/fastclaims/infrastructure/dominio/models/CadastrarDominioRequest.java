package com.sistran.fastclaims.infrastructure.dominio.models;

import com.sistran.fastclaims.infrastructure.dominiovalor.models.CadastrarDominioValorRequest;

import java.util.List;

public record CadastrarDominioRequest(
        String nome,
        String descricao,
        List<CadastrarDominioValorRequest> valores
) {

}
