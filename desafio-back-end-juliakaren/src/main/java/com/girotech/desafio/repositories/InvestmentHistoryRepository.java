package com.girotech.desafio.repositories;

import com.girotech.desafio.models.InvestmentHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestmentHistoryRepository extends JpaRepository<InvestmentHistoryModel, Long> {
}
