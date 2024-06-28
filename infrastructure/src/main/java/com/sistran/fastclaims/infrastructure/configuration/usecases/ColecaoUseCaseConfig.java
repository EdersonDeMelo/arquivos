package com.sistran.fastclaims.infrastructure.configuration.usecases;

import com.sistran.fastclaims.application.colecao.atualizar.AtualizarColecaoUseCase;
import com.sistran.fastclaims.application.colecao.atualizar.DefaultAtualizarColecaoUseCase;
import com.sistran.fastclaims.application.colecao.cadastrar.CadastrarColecaoUseCase;
import com.sistran.fastclaims.application.colecao.cadastrar.DefaultCadastrarColecaoUseCase;
import com.sistran.fastclaims.application.colecao.excluir.DefaultExcluirColecaoUseCase;
import com.sistran.fastclaims.application.colecao.excluir.ExcluirColecaoUseCase;
import com.sistran.fastclaims.application.colecao.pesquisar.id.DefaultPesquisarColecaoPorIdUseCase;
import com.sistran.fastclaims.application.colecao.pesquisar.id.PesquisarColecaoPorIdUseCase;
import com.sistran.fastclaims.domain.colecao.ColecaoGateway;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ColecaoUseCaseConfig {

    @Bean
    public CadastrarColecaoUseCase cadastrarColecaoUseCase(final ColecaoGateway colecaoGateway) {
        return new DefaultCadastrarColecaoUseCase(colecaoGateway);
    }

    @Bean
    public PesquisarColecaoPorIdUseCase pesquisarColecaoPorIdUseCase(final ColecaoGateway colecaoGateway) {
        return new DefaultPesquisarColecaoPorIdUseCase(colecaoGateway);
    }

    @Bean
    public ExcluirColecaoUseCase excluirColecaoUseCase(final ColecaoGateway colecaoGateway,
                                                       final ColecaoCampoGateway colecaoCampoGateway) {
        return new DefaultExcluirColecaoUseCase(colecaoGateway, colecaoCampoGateway);
    }

    @Bean
    public AtualizarColecaoUseCase atualizarColecaoUseCase(final ColecaoGateway colecaoGateway) {
        return new DefaultAtualizarColecaoUseCase(colecaoGateway);
    }
}
