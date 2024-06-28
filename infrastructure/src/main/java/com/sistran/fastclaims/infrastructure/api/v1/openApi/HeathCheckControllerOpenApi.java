package com.sistran.fastclaims.infrastructure.api.v1.openApi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Health Check", description = "Verifica se a aplicação está ativa")
public interface HeathCheckControllerOpenApi {

    @GetMapping
    @Operation(summary = "Verifica se a aplicação está ativa")
    String healthcheck();
}
