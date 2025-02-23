package com.girotech.desafio.dtos;

import jakarta.validation.constraints.NotNull;

public record InvestmentHistoryRecordDto(Long id, @NotNull Float initial_amount, @NotNull int months, @NotNull Float interest_rate, @NotNull Float final_amount, Long currency_id, Long investor_id) {
}
