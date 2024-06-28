package com.sistran.fastclaims.application.regra.atualizar;

import com.sistran.fastclaims.application.validation.RegraUseCaseValidator;
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
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.Objects;
import java.util.function.Supplier;

import static io.vavr.API.Try;

public class DefaultAtualizarRegraUseCase extends AtualizarRegraUseCase {

    private final RegraGateway regraGateway;
    private final ColecaoCampoGateway colecaoCampoGateway;
    private final OperadorGateway operadorGateway;

    public DefaultAtualizarRegraUseCase(
            final RegraGateway regraGateway,
            final ColecaoCampoGateway colecaoCampoGateway,
            final OperadorGateway operadorGateway
    ) {
        this.regraGateway = regraGateway;
        this.colecaoCampoGateway = colecaoCampoGateway;
        this.operadorGateway = operadorGateway;
    }

    @Override
    public Either<Notification, AtualizarRegraOutput> executar(final AtualizarRegraCommand atualizarRegraCommand) {

        final var notification = Notification.create();

        final var regra = obterRegra(RegraID.from(atualizarRegraCommand.id()));

        if (!Objects.equals(atualizarRegraCommand.operadorUm(), "")) {

            final var operadorUm = obterOperador(OperadorID.from(atualizarRegraCommand.operadorUm()));

            notification.append(RegraUseCaseValidator.validarOperadorUm(operadorUm));
            notification.append(RegraUseCaseValidator.validarTipoOperadorUmCampoDois(operadorUm, atualizarRegraCommand.campoDois()));
        }

        final var operadorDois = obterOperador(OperadorID.from(atualizarRegraCommand.operadorDois()));

        notification.append(RegraUseCaseValidator.validarOperadorDois(operadorDois));
        notification.append(RegraUseCaseValidator.validarCampoDoisOperadorUm(atualizarRegraCommand.operadorUm(), atualizarRegraCommand.campoDois()));

        obterColecaoCampo(ColecaoCampoID.from(atualizarRegraCommand.campoUm()));

        regra.atualizar(
                atualizarRegraCommand.nome(),
                atualizarRegraCommand.descricao(),
                ColecaoCampoID.from(atualizarRegraCommand.campoUm()),
                OperadorID.from(atualizarRegraCommand.operadorUm()),
                atualizarRegraCommand.campoDois(),
                OperadorID.from(atualizarRegraCommand.operadorDois()),
                atualizarRegraCommand.campoTres()
        );

        regra.validate(notification);

        return notification.hasErrors() ? Either.left(notification) : atualizarRegra(regra);
    }

    private static Supplier<DomainException> regraNaoEncontrada(final RegraID id) {
        return () -> NotFoundException.with(Regra.class, id);
    }

    private Either<Notification, AtualizarRegraOutput> atualizarRegra(final Regra regra) {
        return Try(() -> this.regraGateway.atualizar(regra))
                .toEither()
                .bimap(Notification::create, AtualizarRegraOutput::aPartirDe);
    }

    private static Supplier<DomainException> colecaoCampoNaoEncontrada(final ColecaoCampoID id) {
        return () -> NotFoundException.with(ColecaoCampo.class, id);
    }

    private static Supplier<DomainException> operadorNaoEncontrado(final OperadorID id) {
        return () -> NotFoundException.with(Operador.class, id);
    }

    private Regra obterRegra(final RegraID id) {
        return regraGateway.pesquisarPorId(id)
                .orElseThrow(regraNaoEncontrada(id));
    }

    private Operador obterOperador(final OperadorID id) {
        return operadorGateway.pesquisarPorId(id)
                .orElseThrow(operadorNaoEncontrado(id));
    }

    private ColecaoCampo obterColecaoCampo(final ColecaoCampoID id) {
        return colecaoCampoGateway.pesquisarPorId(id)
                .orElseThrow(colecaoCampoNaoEncontrada(id));
    }
}

