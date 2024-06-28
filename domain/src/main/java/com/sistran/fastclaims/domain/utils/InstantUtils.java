package com.sistran.fastclaims.domain.utils;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public final class InstantUtils {

    private InstantUtils() {
    }

    public static Instant now() {
        return Instant.now().minus(Duration.ofHours(3)).truncatedTo(ChronoUnit.MICROS);
    }
}