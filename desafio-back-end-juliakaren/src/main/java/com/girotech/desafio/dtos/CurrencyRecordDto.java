package com.girotech.desafio.dtos;

import jakarta.validation.constraints.NotBlank;


public record CurrencyRecordDto(Long  id, @NotBlank String name, @NotBlank String type) {
}
