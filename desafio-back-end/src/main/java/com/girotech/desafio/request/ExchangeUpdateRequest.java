package com.girotech.desafio.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ExchangeUpdateRequest(@NotNull Float daily_variation, @NotNull Float daily_rate, Long currency_id) {
}
