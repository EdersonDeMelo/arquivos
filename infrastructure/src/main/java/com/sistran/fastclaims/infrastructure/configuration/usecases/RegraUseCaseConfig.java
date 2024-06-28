package com.sistran.fastclaims.infrastructure.configuration.usecases;

import com.sistran.fastclaims.application.regra.atualizar.AtualizarRegraUseCase;
import com.sistran.fastclaims.application.regra.atualizar.DefaultAtualizarRegraUseCase;
import com.sistran.fastclaims.application.regra.cadastrar.CadastrarRegraUseCase;
import com.sistran.fastclaims.application.regra.cadastrar.DefaultCadastrarRegraUseCase;
import com.sistran.fastclaims.application.regra.excluir.DefaultExcluirRegraUseCase;
import com.sistran.fastclaims.application.regra.excluir.ExcluirRegraUseCase;
import com.sistran.fastclaims.application.regra.pesquisar.id.DefaultPesquisarRegraPorIdUseCase;
import com.sistran.fastclaims.application.regra.pesquisar.id.PesquisarRegraPorIdUseCase;
import com.sistran.fastclaims.application.regra.pesquisar.lista.DefaultListarRegrasUseCase;
import com.sistran.fastclaims.application.regra.pesquisar.lista.ListarRegrasUseCase;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoGateway;
import com.sistran.fastclaims.domain.operador.OperadorGateway;
import com.sistran.fastclaims.domain.regra.RegraGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class RegraUseCaseConfig {

    @Bean
    public CadastrarRegraUseCase cadastrarRegraUseCase(final RegraGateway regraGateway, final ColecaoCampoGateway colecaoCampoGateway, final OperadorGateway operadorGateway) {
        return new DefaultCadastrarRegraUseCase(regraGateway, colecaoCampoGateway, operadorGateway);
    }

    @Bean
    public PesquisarRegraPorIdUseCase pesquisarRegraPorIdUseCase(final RegraGateway regraGateway) {
        return new DefaultPesquisarRegraPorIdUseCase(regraGateway);
    }

    @Bean
    public AtualizarRegraUseCase atualizarRegraUseCase(final RegraGateway regraGateway, final ColecaoCampoGateway colecaoCampoGateway, final OperadorGateway operadorGateway) {
        return new DefaultAtualizarRegraUseCase(regraGateway, colecaoCampoGateway, operadorGateway);
    }

    @Bean
    public ExcluirRegraUseCase excluirRegraUseCase(final RegraGateway regraGateway) {
        return new DefaultExcluirRegraUseCase(regraGateway);
    }

    @Bean
    public ListarRegrasUseCase listarRegrasUseCase(final RegraGateway regraGateway) {
        return new DefaultListarRegrasUseCase(regraGateway);
    }
}
