package com.girotech.desafio.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ExchangeResponse(Long id, @NotNull LocalDate date, @NotNull Float daily_variation, @NotNull Float daily_rate, @NotBlank String currency_name, @NotBlank String currency_type) {
}
