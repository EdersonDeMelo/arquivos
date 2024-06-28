package com.sistran.fastclaims.infrastructure.configuration.usecases;

import com.sistran.fastclaims.application.segmento.atualizar.AtualizarSegmentoUseCase;
import com.sistran.fastclaims.application.segmento.atualizar.DefaultAtualizarSegmentoUseCase;
import com.sistran.fastclaims.application.segmento.cadastrar.CadastrarSegmentoUseCase;
import com.sistran.fastclaims.application.segmento.cadastrar.DefaultCadastrarSegmentoUseCase;
import com.sistran.fastclaims.application.segmento.pesquisar.id.DefaultPesquisarSegmentoPorIdUseCase;
import com.sistran.fastclaims.application.segmento.pesquisar.id.PesquisarSegmentoPorIdUseCase;
import com.sistran.fastclaims.application.segmento.pesquisar.lista.DefaultListarSegmentosUseCase;
import com.sistran.fastclaims.application.segmento.pesquisar.lista.ListarSegmentosUseCase;
import com.sistran.fastclaims.domain.segmento.SegmentoGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class SegmentoUseCaseConfig {

    @Bean
    public CadastrarSegmentoUseCase cadastrarSegmentoUseCase(final SegmentoGateway segmentoGateway) {
        return new DefaultCadastrarSegmentoUseCase(segmentoGateway);
    }

    @Bean
    public AtualizarSegmentoUseCase atualizarSegmentoUseCase(final SegmentoGateway segmentoGateway) {
        return new DefaultAtualizarSegmentoUseCase(segmentoGateway);
    }

    @Bean
    public PesquisarSegmentoPorIdUseCase pesquisarSegmentoPorIdUseCase(final SegmentoGateway segmentoGateway) {
        return new DefaultPesquisarSegmentoPorIdUseCase(segmentoGateway);
    }

    @Bean
    public ListarSegmentosUseCase listarSegmentosUseCase(final SegmentoGateway segmentoGateway) {
        return new DefaultListarSegmentosUseCase(segmentoGateway);
    }
}
