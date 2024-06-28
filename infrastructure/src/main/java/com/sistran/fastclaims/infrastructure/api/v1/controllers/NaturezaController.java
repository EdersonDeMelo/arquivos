package com.sistran.fastclaims.infrastructure.api.v1.controllers;

import com.sistran.fastclaims.application.natureza.pesquisar.id.PesquisarNaturezaPorIdOutput;
import com.sistran.fastclaims.application.natureza.pesquisar.id.PesquisarNaturezaPorIdUseCase;
import com.sistran.fastclaims.application.natureza.pesquisar.lista.ListarNaturezasOutput;
import com.sistran.fastclaims.application.natureza.pesquisar.lista.ListarNaturezasUseCase;
import com.sistran.fastclaims.infrastructure.api.v1.openApi.NaturezaControllerOpenApi;
import com.sistran.fastclaims.infrastructure.natureza.models.NaturezaResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/v1/naturezas")
public class NaturezaController implements NaturezaControllerOpenApi {

    private final PesquisarNaturezaPorIdUseCase pesquisarNaturezaPorIdUseCase;
    private final ListarNaturezasUseCase listarNaturezasUseCase;

    public NaturezaController(final PesquisarNaturezaPorIdUseCase pesquisarNaturezaPorIdUseCase, final ListarNaturezasUseCase listarNaturezasUseCase) {
        this.pesquisarNaturezaPorIdUseCase = pesquisarNaturezaPorIdUseCase;
        this.listarNaturezasUseCase = listarNaturezasUseCase;
    }

    @Override
    public NaturezaResponse buscarNaturezaPorId(final String naturezaId) {
        final PesquisarNaturezaPorIdOutput natureza = pesquisarNaturezaPorIdUseCase.executar(naturezaId);
        return NaturezaResponse.aPartirDe(natureza.id().getValue(), natureza.nome(), natureza.codigoNatureza());
    }

    @Override
    public List<NaturezaResponse> listarNaturezas(final String termo) {
        final List<ListarNaturezasOutput> naturezaPorTermoOutput = listarNaturezasUseCase.executar(termo);
        return Optional.of(naturezaPorTermoOutput.stream().map(NaturezaResponse::aPartirDe).toList()).orElse(List.of());
    }
}
