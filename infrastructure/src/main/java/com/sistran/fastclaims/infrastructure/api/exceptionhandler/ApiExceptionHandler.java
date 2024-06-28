package com.sistran.fastclaims.infrastructure.api.exceptionhandler;

import com.sistran.fastclaims.domain.exceptions.DomainException;
import com.sistran.fastclaims.domain.exceptions.NotFoundException;
import com.sistran.fastclaims.infrastructure.exceptions.EntidadeNaoEncontradaException;
import com.sistran.fastclaims.infrastructure.exceptions.NegocioException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

@Hidden
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = DomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Problem handleDomainException(final DomainException ex) {
        return new Problem(String.valueOf(HttpStatus.BAD_REQUEST.value()), ex.getMessage());
    }

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Problem handleNotFoundException(final NotFoundException ex) {
        return new Problem(String.valueOf(HttpStatus.NOT_FOUND.value()), ex.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Problem handleException(final Exception e) {
        return new Problem(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage());
    }

    @ExceptionHandler(value = EntidadeNaoEncontradaException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Problem entidadeNaoEncontrada(final EntidadeNaoEncontradaException e) {
        return new Problem(String.valueOf(HttpStatus.NOT_FOUND.value()), e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Problem methodArgument(final MethodArgumentTypeMismatchException ex) {
        final String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
                        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        return new Problem(String.valueOf(HttpStatus.BAD_REQUEST.value()), detail);
    }

    @ExceptionHandler(value = NegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Problem regraNegocio(final NegocioException ex) {
        return new Problem(String.valueOf(HttpStatus.BAD_REQUEST.value()), ex.getMessage());
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Problem methodArgumentValidation(final MethodArgumentNotValidException e) {
        final var fieldErrors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> {
                    String field = fieldError.getField();
                    String message = fieldError.getDefaultMessage();

                    return new FieldError(field, message);
                }).collect(Collectors.toList());

        final Problem problem = new Problem(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                "Um ou mais campos estão inválidos!");
        problem.setFieldErrors(fieldErrors);
        return problem;
    }
}
