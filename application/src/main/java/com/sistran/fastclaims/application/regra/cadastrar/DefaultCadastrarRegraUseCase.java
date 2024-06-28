package com.sistran.fastclaims.application.regra.cadastrar;

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
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.Objects;
import java.util.function.Supplier;

import static io.vavr.API.Try;

public class DefaultCadastrarRegraUseCase extends CadastrarRegraUseCase {

    private final RegraGateway regraGateway;
    private final ColecaoCampoGateway colecaoCampoGateway;
    private final OperadorGateway operadorGateway;

    public DefaultCadastrarRegraUseCase(
            final RegraGateway regraGateway,
            final ColecaoCampoGateway colecaoCampoGateway,
            final OperadorGateway operadorGateway
    ) {
        this.regraGateway = regraGateway;
        this.colecaoCampoGateway = colecaoCampoGateway;
        this.operadorGateway = operadorGateway;
    }

    @Override
    public Either<Notification, CadastrarRegraOutput> executar(final CadastrarRegraCommand cadastrarRegraCommand) {

        final var notification = Notification.create();

        if (!Objects.equals(cadastrarRegraCommand.operadorUm(), "")) {

            final var operadorUm = obterOperador(OperadorID.from(cadastrarRegraCommand.operadorUm()));

            notification.append(RegraUseCaseValidator.validarOperadorUm(operadorUm));
            notification.append(RegraUseCaseValidator.validarTipoOperadorUmCampoDois(operadorUm, cadastrarRegraCommand.campoDois()));
        }

        final var operadorDois = obterOperador(OperadorID.from(cadastrarRegraCommand.operadorDois()));

        notification.append(RegraUseCaseValidator.validarOperadorDois(operadorDois));
        notification.append(RegraUseCaseValidator.validarCampoDoisOperadorUm(cadastrarRegraCommand.operadorUm(), cadastrarRegraCommand.campoDois()));

        obterColecaoCampo(ColecaoCampoID.from(cadastrarRegraCommand.campoUm()));

        final var regra = montarRegra(cadastrarRegraCommand);
        regra.validate(notification);

        return notification.hasErrors() ? Either.left(notification) : cadastrarRegra(regra);
    }

    private Either<Notification, CadastrarRegraOutput> cadastrarRegra(final Regra regra) {
        return Try(() -> this.regraGateway.cadastrar(regra))
                .toEither()
                .bimap(Notification::create, CadastrarRegraOutput::aPartirDe);
    }

    private static Supplier<DomainException> colecaoCampoNaoEncontrada(final ColecaoCampoID id) {
        return () -> NotFoundException.with(ColecaoCampo.class, id);
    }

    private static Supplier<DomainException> operadorNaoEncontrado(final OperadorID id) {
        return () -> NotFoundException.with(Operador.class, id);
    }

    private Regra montarRegra(CadastrarRegraCommand command) {
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

    private ColecaoCampo obterColecaoCampo(final ColecaoCampoID id) {
        return colecaoCampoGateway.pesquisarPorId(id)
                .orElseThrow(colecaoCampoNaoEncontrada(id));
    }

    private Operador obterOperador(final OperadorID id) {
        return operadorGateway.pesquisarPorId(id)
                .orElseThrow(operadorNaoEncontrado(id));
    }
}

