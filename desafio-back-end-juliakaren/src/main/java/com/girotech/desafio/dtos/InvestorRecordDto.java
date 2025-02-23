package com.girotech.desafio.dtos;

import jakarta.validation.constraints.NotBlank;

public record InvestorRecordDto(Long id, @NotBlank String name, @NotBlank String email) {
}
