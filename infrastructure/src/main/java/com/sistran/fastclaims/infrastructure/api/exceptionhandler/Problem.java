package com.sistran.fastclaims.infrastructure.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.lang.NonNull;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@JsonInclude(Include.NON_NULL)
public class Problem {

    private final String status;
    private final String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final OffsetDateTime timestamp;

    private List<FieldError> fieldErrors;

    public Problem(@NonNull final String status, @NonNull final String message) {
        this.status = Objects.requireNonNull(status);
        this.message = Objects.requireNonNull(message);
        this.timestamp = OffsetDateTime.now();
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(final List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
