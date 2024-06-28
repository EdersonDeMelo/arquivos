package com.sistran.fastclaims.application.trilha.cadastrar.regra_trilha;

import com.sistran.fastclaims.application.regra.cadastrar.CadastrarRegraOutput;
import com.sistran.fastclaims.application.validation.RegraUseCaseValidator;
import com.sistran.fastclaims.domain.AggregateRoot;
import com.sistran.fastclaims.domain.Identifier;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampo;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoGateway;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoID;
import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.operador.Operador;
import com.sistran.fastclaims.domain.operador.OperadorGateway;
import com.sistran.fastclaims.domain.operador.OperadorID;
import com.sistran.fastclaims.domain.regra.Regra;
import com.sistran.fastclaims.domain.regra.RegraGateway;
import com.sistran.fastclaims.domain.regra.RegraID;
import com.sistran.fastclaims.domain.regra.TipoAcao;
import com.sistran.fastclaims.domain.trilha.RegraTrilha;
import com.sistran.fastclaims.domain.trilha.Trilha;
import com.sistran.fastclaims.domain.trilha.TrilhaGateway;
import com.sistran.fastclaims.domain.trilha.TrilhaID;
import com.sistran.fastclaims.domain.validation.Error;
import com.sistran.fastclaims.domain.validation.ValidationHandler;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import static io.vavr.API.Try;

public class DefaultCadastrarRegraTrilhaUseCase extends CadastrarRegraTrilhaUseCase {

    private final RegraGateway regraGateway;
    private final OperadorGateway operadorGateway;
    private final ColecaoCampoGateway colecaoCampoGateway;
    private final TrilhaGateway trilhaGateway;

    public DefaultCadastrarRegraTrilhaUseCase(
            final RegraGateway regraGateway,
            final OperadorGateway operadorGateway,
            final ColecaoCampoGateway colecaoCampoGateway,
            final TrilhaGateway trilhaGateway
    ) {
        this.regraGateway = regraGateway;
        this.operadorGateway = operadorGateway;
        this.colecaoCampoGateway = colecaoCampoGateway;
        this.trilhaGateway = trilhaGateway;
    }

    @Override
    public Either<Notification, CadastrarRegraTrilhaOutput> executar(final CadastrarRegraTrilhaCommand cadastrarRegraTrilhaCommand) {

        final var notification = Notification.create();

        final var trilha = obterTrilha(TrilhaID.from(cadastrarRegraTrilhaCommand.trilhaId()));

        if (cadastrarRegraTrilhaCommand.regraId() == null) {

            if (!Objects.equals(cadastrarRegraTrilhaCommand.operadorUm(), "")) {

                final var operadorUm = obterOperador(OperadorID.from(cadastrarRegraTrilhaCommand.operadorUm()));

                notification.append(RegraUseCaseValidator.validarOperadorUm(operadorUm));
                notification.append(RegraUseCaseValidator.validarTipoOperadorUmCampoDois(operadorUm, cadastrarRegraTrilhaCommand.campoDois()));
            }

            final var operadorDois = obterOperador(OperadorID.from(cadastrarRegraTrilhaCommand.operadorDois()));

            notification.append(RegraUseCaseValidator.validarOperadorDois(operadorDois));
            notification.append(RegraUseCaseValidator.validarCampoDoisOperadorUm(cadastrarRegraTrilhaCommand.operadorUm(), cadastrarRegraTrilhaCommand.campoDois()));

            obterColecaoCampo(ColecaoCampoID.from(cadastrarRegraTrilhaCommand.campoUm()));

            final var regra = montarRegra(cadastrarRegraTrilhaCommand);

            regra.validate(notification);

            if (notification.hasErrors()) {
                return Either.left(notification);
            }

            final var regraTrilha = montarRegraTrilha(cadastrarRegraTrilhaCommand, regra.getId());

            regra.adicionarTrilhaID(TrilhaID.from(cadastrarRegraTrilhaCommand.trilhaId()));

            final var regraCadastrada = cadastrarRegra(regra);

            if (regraCadastrada.isLeft()) {
                return Either.left(regraCadastrada.getLeft());
            }

            trilha.adicionarRegra(regraTrilha);

            return cadastrarRegraTrilha(trilha, regra, regraTrilha);
        }

        final var regra = obterRegra(RegraID.from(cadastrarRegraTrilhaCommand.regraId()));

        notification.append(verificaDuplicidadeDeTrilhasNaRegra(regra.getTrilhas(), trilha.getId()));

        if (notification.hasErrors()) {
            return Either.left(notification);
        }

        final var regraTrilha = montarRegraTrilha(cadastrarRegraTrilhaCommand, regra.getId());

        regra.adicionarTrilhaID(TrilhaID.from(cadastrarRegraTrilhaCommand.trilhaId()));
        cadastrarRegra(regra);

        trilha.adicionarRegra(regraTrilha);

        final var regraCadastrada = cadastrarRegraTrilha(trilha, regra, regraTrilha);

        if (regraCadastrada.isLeft()) {
            return Either.left(regraCadastrada.getLeft());
        }

        return regraCadastrada;
    }

    private Supplier<DomainException> naoEncontrado(final Identifier id, final Class<? extends AggregateRoot<?>> aggregateRoot) {
        return () -> NotFoundException.with(aggregateRoot, id);
    }

    private Either<Notification, CadastrarRegraOutput> cadastrarRegra(final Regra regra) {
        return Try(() -> this.regraGateway.cadastrar(regra))
                .toEither()
                .bimap(Notification::create, CadastrarRegraOutput::aPartirDe);
    }

    private Either<Notification, CadastrarRegraTrilhaOutput> cadastrarRegraTrilha(final Trilha trilha, final Regra regra, final RegraTrilha regraTrilha) {
        return Try(() -> this.trilhaGateway.cadastrarRegraTrilha(trilha, regra, regraTrilha))
                .toEither()
                .bimap(Notification::create, CadastrarRegraTrilhaOutput::aPartirDe);
    }

    private ValidationHandler verificaDuplicidadeDeTrilhasNaRegra(final List<TrilhaID> trilhas, final TrilhaID trilhaId) {
        final var notification = Notification.create();

        if (trilhas.contains(trilhaId)) {
            notification.append(new Error("A trilha já está vinculada a regra"));
        }
        return notification;
    }

    private Trilha obterTrilha(final TrilhaID id) {
        return trilhaGateway.pesquisarPorId(id)
                .orElseThrow(naoEncontrado(id, Trilha.class));
    }

    private Operador obterOperador(final OperadorID id) {
        return operadorGateway.pesquisarPorId(id)
                .orElseThrow(naoEncontrado(id, Operador.class));
    }

    private ColecaoCampo obterColecaoCampo(final ColecaoCampoID id) {
        return colecaoCampoGateway.pesquisarPorId(id)
                .orElseThrow(naoEncontrado(id, ColecaoCampo.class));
    }

    private Regra obterRegra(final RegraID id) {
        return regraGateway.pesquisarPorId(id)
                .orElseThrow(naoEncontrado(id, Regra.class));
    }

    private Regra montarRegra(final CadastrarRegraTrilhaCommand command) {
        return Regra.novaRegra(
                command.nome(),
                command.descricao(),
                ColecaoCampoID.from(command.campoUm()),
                OperadorID.from(command.operadorUm()),
                command.campoDois(),
                OperadorID.from(command.operadorDois()),
                command.campoTres()
        );
    }

    private RegraTrilha montarRegraTrilha(final CadastrarRegraTrilhaCommand command, final RegraID regraId) {
        return RegraTrilha.novaRegraTrilha(
                regraId,
                command.resultadoEsperado(),
                TipoAcao.of(command.tipoAcao()).orElseThrow(() -> DomainException.with(new Error("Tipo de ação nulo ou inválido."))),
                command.ativa()
        );
    }
}

