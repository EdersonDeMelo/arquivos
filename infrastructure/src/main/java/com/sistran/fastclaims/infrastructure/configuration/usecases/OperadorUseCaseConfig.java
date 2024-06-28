package com.sistran.fastclaims.infrastructure.configuration.usecases;

import com.sistran.fastclaims.application.operador.pesquisar.id.DefaultPesquisarOperadorPorIdUseCase;
import com.sistran.fastclaims.application.operador.pesquisar.id.PesquisarOperadorPorIdUseCase;
import com.sistran.fastclaims.application.operador.pesquisar.lista.DefaultListarOperadoresUseCase;
import com.sistran.fastclaims.application.operador.pesquisar.lista.ListarOperadoresUseCase;
import com.sistran.fastclaims.domain.operador.OperadorGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class OperadorUseCaseConfig {

    @Bean
    public PesquisarOperadorPorIdUseCase pesquisarOperadorPorIdUseCase(OperadorGateway operadorGateway) {
        return new DefaultPesquisarOperadorPorIdUseCase(operadorGateway);
    }

    @Bean
    public ListarOperadoresUseCase listarOperadoresUseCase(OperadorGateway operadorGateway) {
        return new DefaultListarOperadoresUseCase(operadorGateway);
    }

}
