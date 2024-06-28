package com.sistran.fastclaims.infrastructure.configuration.usecases;

import com.sistran.fastclaims.application.trilha.ativar.AtivarTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.ativar.DefaultAtivarTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.ativar.regra_trilha.AtivarRegraTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.ativar.regra_trilha.DefaultAtivarRegraTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.atualizar.AtualizarTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.atualizar.DefaultAtualizarTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.atualizar.regra_trilha.AtualizarRegraTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.atualizar.regra_trilha.DefaultAtualizarRegraTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.cadastrar.CadastrarTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.cadastrar.DefaultCadastrarTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.cadastrar.regra_trilha.CadastrarRegraTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.cadastrar.regra_trilha.DefaultCadastrarRegraTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.desativar.DefaultDesativarTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.desativar.DesativarTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.desativar.regra_trilha.DefaultDesativarRegraTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.desativar.regra_trilha.DesativarRegraTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.excluir.DefaultExcluirTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.excluir.ExcluirTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.excluir.regra_trilha.DefaultExcluirRegraTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.excluir.regra_trilha.ExcluirRegraTrilhaUseCase;
import com.sistran.fastclaims.application.trilha.pesquisar.id.DefaultPesquisarTrilhaPorIdUseCase;
import com.sistran.fastclaims.application.trilha.pesquisar.id.PesquisarTrilhaPorIdUseCase;
import com.sistran.fastclaims.application.trilha.pesquisar.lista.DefaultListarTrilhasUseCase;
import com.sistran.fastclaims.application.trilha.pesquisar.lista.ListarTrilhasUseCase;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoGateway;
import com.sistran.fastclaims.domain.fluxo.FluxoGateway;
import com.sistran.fastclaims.domain.operador.OperadorGateway;
import com.sistran.fastclaims.domain.regra.RegraGateway;
import com.sistran.fastclaims.domain.trilha.TrilhaGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class TrilhaUseCaseConfig {

    @Bean
    public PesquisarTrilhaPorIdUseCase pesquisarTrilhaPorIdUseCase(final TrilhaGateway trilhaGateway) {
        return new DefaultPesquisarTrilhaPorIdUseCase(trilhaGateway);
    }

    @Bean
    public ListarTrilhasUseCase pesquisarTrilhaPorNomeUseCase(final TrilhaGateway trilhaGateway) {
        return new DefaultListarTrilhasUseCase(trilhaGateway);
    }

    @Bean
    public AtivarTrilhaUseCase ativarTrilhaUseCase(final TrilhaGateway trilhaGateway) {
        return new DefaultAtivarTrilhaUseCase(trilhaGateway);
    }

    @Bean
    public DesativarTrilhaUseCase desativarTrilhaUseCase(final TrilhaGateway trilhaGateway) {
        return new DefaultDesativarTrilhaUseCase(trilhaGateway);
    }

    @Bean
    public ExcluirTrilhaUseCase excluirTrilhaUseCase(final TrilhaGateway trilhaGateway) {
        return new DefaultExcluirTrilhaUseCase(trilhaGateway);
    }

    @Bean
    public CadastrarTrilhaUseCase cadastrarTrilhaUseCase(final TrilhaGateway trilhaGateway, final FluxoGateway fluxoGateway) {
        return new DefaultCadastrarTrilhaUseCase(trilhaGateway, fluxoGateway);
    }

    @Bean
    public AtualizarTrilhaUseCase editarTrilhaUseCase(TrilhaGateway trilhaGateway, FluxoGateway fluxoGateway) {
        return new DefaultAtualizarTrilhaUseCase(trilhaGateway, fluxoGateway);
    }

    @Bean
    public CadastrarRegraTrilhaUseCase cadastrarRegraTrilhaUseCase(final RegraGateway regraGateway, final OperadorGateway operadorGateway, final ColecaoCampoGateway colecaoCampoGateway, final TrilhaGateway trilhaGateway) {
        return new DefaultCadastrarRegraTrilhaUseCase(regraGateway, operadorGateway, colecaoCampoGateway, trilhaGateway);
    }

    @Bean
    public AtualizarRegraTrilhaUseCase atualizarRegraTrilhaUseCase(final RegraGateway regraGateway, final TrilhaGateway trilhaGateway) {
        return new DefaultAtualizarRegraTrilhaUseCase(regraGateway, trilhaGateway);
    }

    @Bean
    public ExcluirRegraTrilhaUseCase excluirRegraTrilhaUseCase(final TrilhaGateway trilhaGateway, final RegraGateway regraGateway) {
        return new DefaultExcluirRegraTrilhaUseCase(trilhaGateway, regraGateway);
    }

    @Bean
    public AtivarRegraTrilhaUseCase ativarRegraTrilhaUseCase(final TrilhaGateway trilhaGateway) {
        return new DefaultAtivarRegraTrilhaUseCase(trilhaGateway);
    }

    @Bean
    public DesativarRegraTrilhaUseCase desativarRegraTrilhaUseCase(final TrilhaGateway trilhaGateway) {
        return new DefaultDesativarRegraTrilhaUseCase(trilhaGateway);
    }
}
