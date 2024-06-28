package com.sistran.fastclaims.infrastructure.configuration.usecases;

import com.sistran.fastclaims.application.colecaocampo.cadastrar.CadastrarColecaoCampoUseCase;
import com.sistran.fastclaims.application.colecaocampo.cadastrar.DefaultCadastrarColecaoCampoUseCase;
import com.sistran.fastclaims.application.colecaocampo.excluir.DefaultExcluirColecaoCampoUseCase;
import com.sistran.fastclaims.application.colecaocampo.excluir.ExcluirColecaoCampoUseCase;
import com.sistran.fastclaims.application.colecaocampo.pesquisar.id.DefaultPesquisarColecaoCampoPorIdUseCase;
import com.sistran.fastclaims.application.colecaocampo.pesquisar.id.PesquisarColecaoCampoPorIdUseCase;
import com.sistran.fastclaims.application.colecaocampo.pesquisar.listar.DefaultListarColecaoCampoUseCase;
import com.sistran.fastclaims.application.colecaocampo.pesquisar.listar.ListarColecaoCampoUseCase;
import com.sistran.fastclaims.domain.colecao.ColecaoGateway;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoGateway;
import com.sistran.fastclaims.domain.dominio.DominioGateway;
import com.sistran.fastclaims.domain.regra.RegraGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ColecaoCampoUseCaseConfig {

    @Bean
    public CadastrarColecaoCampoUseCase cadastrarColecaoCampoUseCase(
            final ColecaoCampoGateway colecaoCampoGateway,
            final DominioGateway dominioGateway,
            final ColecaoGateway colecaoGateway) {
        return new DefaultCadastrarColecaoCampoUseCase(colecaoCampoGateway, dominioGateway, colecaoGateway);
    }

    @Bean
    public PesquisarColecaoCampoPorIdUseCase pesquisarColecaoCampoPorIdUseCase(final ColecaoCampoGateway colecaoCampoGateway) {
        return new DefaultPesquisarColecaoCampoPorIdUseCase(colecaoCampoGateway);
    }

    @Bean
    public ListarColecaoCampoUseCase listarColecaoCampoUseCase(final ColecaoCampoGateway colecaoCampoGateway) {
        return new DefaultListarColecaoCampoUseCase(colecaoCampoGateway);
    }

    @Bean
    public ExcluirColecaoCampoUseCase excluirColecaoCampoUseCase(
            final ColecaoCampoGateway colecaoCampoGateway,
            final RegraGateway regraGateway) {
        return new DefaultExcluirColecaoCampoUseCase(colecaoCampoGateway, regraGateway);
    }
}
