package com.sistran.fastclaims.application.colecaocampo.cadastrar;

import com.sistran.fastclaims.domain.colecao.Colecao;
import com.sistran.fastclaims.domain.colecao.ColecaoGateway;
import com.sistran.fastclaims.domain.colecao.ColecaoID;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampo;
import com.sistran.fastclaims.domain.colecaocampo.ColecaoCampoGateway;
import com.sistran.fastclaims.domain.dominio.Dominio;
import com.sistran.fastclaims.domain.dominio.DominioGateway;
import com.sistran.fastclaims.domain.dominio.DominioID;
import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.function.Supplier;

import static io.vavr.API.Try;

public class DefaultCadastrarColecaoCampoUseCase extends CadastrarColecaoCampoUseCase {

    private final ColecaoCampoGateway colecaoCampoGateway;
    private final DominioGateway dominioGateway;
    private final ColecaoGateway colecaoGateway;

    public DefaultCadastrarColecaoCampoUseCase(final ColecaoCampoGateway colecaoCampoGateway,
                                               final DominioGateway dominioGateway,
                                               final ColecaoGateway colecaoGateway) {
        this.colecaoCampoGateway = colecaoCampoGateway;
        this.dominioGateway = dominioGateway;
        this.colecaoGateway = colecaoGateway;
    }

    @Override
    public Either<Notification, CadastrarColecaoCampoOutput> executar(CadastrarColecaoCampoCommand comando) {

        final var colecao = obterColecaoPorId(ColecaoID.from(comando.colecaoId()));

        final var dominioId = validarDominioID(comando.dominioId());

        if (!dominioId.isEmpty())
            verificarDominioPorId(DominioID.from(dominioId));

        final var colecaoCampo = criarColecaoCampo(comando, colecao);

        Notification notification = Notification.create();

        colecaoCampo.validate(notification);

        if (notification.hasErrors()) {
            return Either.left(notification);
        }

        return cadastrar(colecaoCampo);
    }

    private Colecao obterColecaoPorId(final ColecaoID id) {
        return colecaoGateway.pesquisarPorId(id)
                .orElseThrow(colecaoNaoEncontrada(id));
    }

    private void verificarDominioPorId(final DominioID id) {
        if (!dominioGateway.exists(id)) {
            throw dominioNaoEncontrado(id).get();
        }
    }

    private ColecaoCampo criarColecaoCampo(CadastrarColecaoCampoCommand comando, Colecao colecao) {
        return ColecaoCampo.novaColecaoCampo(
                comando.campo(),
                comando.alias(),
                comando.tipoDado(),
                comando.tipoFormato(),
                ColecaoID.from(comando.colecaoId()),
                DominioID.from(validarDominioID(comando.dominioId())),
                colecao.getNome());
    }

    private Either<Notification, CadastrarColecaoCampoOutput> cadastrar(final ColecaoCampo colecaoCampo) {
        return Try(() -> this.colecaoCampoGateway.cadastrar(colecaoCampo))
                .toEither()
                .bimap(Notification::create, CadastrarColecaoCampoOutput::aPartirDe);
    }

    private static Supplier<DomainException> colecaoNaoEncontrada(final ColecaoID id) {
        return () -> NotFoundException.with(Colecao.class, id);
    }

    private static Supplier<DomainException> dominioNaoEncontrado(final DominioID id) {
        return () -> NotFoundException.with(Dominio.class, id);
    }

    private String validarDominioID(final String id) {
        if (id == null || id.isEmpty())
            return "";
        return id;
    }
}
