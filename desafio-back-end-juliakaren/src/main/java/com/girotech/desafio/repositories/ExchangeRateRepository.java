package com.girotech.desafio.repositories;

import com.girotech.desafio.models.ExchangeRateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRateModel, Long> {
}
