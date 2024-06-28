package com.sistran.fastclaims.application.dominio.pesquisar.nome;

import com.sistran.fastclaims.domain.dominio.Dominio;
import com.sistran.fastclaims.domain.dominio.DominioGateway;
import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.ParametroInexistenteException;
import com.sistran.fastclaims.domain.utils.FormatStringUtils;
import com.sistran.fastclaims.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.function.Supplier;

public class DefaultPesquisarDominioPorNomeUseCase extends PesquisarDominioPorNomeUseCase {

    private final DominioGateway dominioGateway;

    public DefaultPesquisarDominioPorNomeUseCase(DominioGateway dominioGateway) {
        this.dominioGateway = dominioGateway;
    }

    @Override
    public Either<Notification, ListarDominiosOutput> executar(String nomeDominio) {
        final var nomeFormatadado = FormatStringUtils.formatString(nomeDominio);
        return dominioGateway.pesquisarPorNome(nomeFormatadado)
                .map(ListarDominiosOutput::aPartirDe)
                .map(Either::<Notification, ListarDominiosOutput>right)
                .orElseThrow(dominioNaoEncontrado(nomeFormatadado));
    }

    private static Supplier<DomainException> dominioNaoEncontrado(final String nomeDominio) {
        return () -> ParametroInexistenteException.with(Dominio.class, nomeDominio);
    }
}
