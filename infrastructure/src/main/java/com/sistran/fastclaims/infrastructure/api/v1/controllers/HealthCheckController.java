package com.sistran.fastclaims.infrastructure.api.v1.controllers;

import com.sistran.fastclaims.infrastructure.api.v1.openApi.HeathCheckControllerOpenApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthcheck")
public class HealthCheckController implements HeathCheckControllerOpenApi {

    @Override
    public String healthcheck() {
        return "OK";
    }
}
