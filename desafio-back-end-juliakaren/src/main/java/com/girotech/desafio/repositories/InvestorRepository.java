package com.girotech.desafio.repositories;

import com.girotech.desafio.models.InvestorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestorRepository extends JpaRepository<InvestorModel, Long> {
}
