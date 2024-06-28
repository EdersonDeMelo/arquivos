package com.sistran.fastclaims.infrastructure.configuration.usecases;

import com.sistran.fastclaims.application.natureza.pesquisar.id.DefaultPesquisarNaturezaPorIdUseCase;
import com.sistran.fastclaims.application.natureza.pesquisar.id.PesquisarNaturezaPorIdUseCase;
import com.sistran.fastclaims.application.natureza.pesquisar.lista.DefaultListarNaturezasUseCase;
import com.sistran.fastclaims.application.natureza.pesquisar.lista.ListarNaturezasUseCase;
import com.sistran.fastclaims.domain.natureza.NaturezaGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class NaturezaUseCaseConfig {

    @Bean
    public PesquisarNaturezaPorIdUseCase pesquisarNaturezaPorId(final NaturezaGateway naturezaGateway) {
        return new DefaultPesquisarNaturezaPorIdUseCase(naturezaGateway);

    }

    @Bean
    public ListarNaturezasUseCase pesquisarNaturezaPorTermo(final NaturezaGateway naturezaGateway) {
        return new DefaultListarNaturezasUseCase(naturezaGateway);
    }

}
