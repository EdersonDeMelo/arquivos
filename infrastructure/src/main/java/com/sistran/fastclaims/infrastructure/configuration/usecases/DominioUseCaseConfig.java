package com.sistran.fastclaims.infrastructure.configuration.usecases;

import com.sistran.fastclaims.application.dominio.atualizar.AtualizarDominioUseCase;
import com.sistran.fastclaims.application.dominio.atualizar.DefaultAtualizarDominioUseCase;
import com.sistran.fastclaims.application.dominio.cadastrar.CadastrarDominioUseCase;
import com.sistran.fastclaims.application.dominio.cadastrar.DefaultCadastrarDominioUseCase;
import com.sistran.fastclaims.application.dominio.pesquisar.id.DefaultPesquisarDominioPorIdUseCase;
import com.sistran.fastclaims.application.dominio.pesquisar.id.PesquisarDominioPorIdUseCase;
import com.sistran.fastclaims.application.dominio.pesquisar.lista.DefaultListarDominiosUseCase;
import com.sistran.fastclaims.application.dominio.pesquisar.lista.ListarDominiosUseCase;
import com.sistran.fastclaims.application.dominio.pesquisar.nome.DefaultPesquisarDominioPorNomeUseCase;
import com.sistran.fastclaims.application.dominio.pesquisar.nome.PesquisarDominioPorNomeUseCase;
import com.sistran.fastclaims.application.dominiovalor.cadastrar.CadastrarDominioValorUseCase;
import com.sistran.fastclaims.application.dominiovalor.cadastrar.DefaultCadastrarDominioValorUseCase;
import com.sistran.fastclaims.application.dominiovalor.excluir.DefaultExcluirDominioValorUseCase;
import com.sistran.fastclaims.application.dominiovalor.excluir.ExcluirDominioValorUseCase;
import com.sistran.fastclaims.domain.dominio.DominioGateway;
import com.sistran.fastclaims.domain.dominiovalor.DominioValorGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class DominioUseCaseConfig {

    @Bean
    public CadastrarDominioUseCase cadastrarDominioUseCase(final DominioGateway dominioGateway) {
        return new DefaultCadastrarDominioUseCase(dominioGateway);
    }

    @Bean
    public PesquisarDominioPorIdUseCase pesquisarDominioPorIdUseCase(final DominioGateway dominioGateway) {
        return new DefaultPesquisarDominioPorIdUseCase(dominioGateway);
    }

    @Bean
    public PesquisarDominioPorNomeUseCase pesquisarDominioPorNomeUseCase(final DominioGateway dominioGateway) {
        return new DefaultPesquisarDominioPorNomeUseCase(dominioGateway);
    }

    @Bean
    public ListarDominiosUseCase listarDominiosUseCase(final DominioGateway dominioGateway) {
        return new DefaultListarDominiosUseCase(dominioGateway);
    }

    @Bean
    public AtualizarDominioUseCase atualizarDominioUseCase(final DominioGateway dominioGateway) {
        return new DefaultAtualizarDominioUseCase(dominioGateway);
    }

    @Bean
    public CadastrarDominioValorUseCase cadastrarDominioValorUseCase(final DominioGateway dominioGateway) {
        return new DefaultCadastrarDominioValorUseCase(dominioGateway);
    }

    @Bean
    public ExcluirDominioValorUseCase excluirDominioValorUseCase(final DominioGateway dominioGateway,
                                                                 final DominioValorGateway dominioValorGateway
    ) {
        return new DefaultExcluirDominioValorUseCase(dominioGateway, dominioValorGateway);
    }

}
