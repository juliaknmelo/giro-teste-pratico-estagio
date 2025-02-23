package com.girotech.desafio.response;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ExchangeUpdateResponse(Long id, @NotNull LocalDate date, @NotNull Float daily_variation, @NotNull Float daily_rate) {
}
