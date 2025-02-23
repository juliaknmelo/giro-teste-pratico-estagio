package com.girotech.desafio.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ExchangeRateRecordDto(Long id, @NotNull LocalDate date, @NotNull Float daily_variation, @NotNull Float daily_rate, Long currency_id) {
}
