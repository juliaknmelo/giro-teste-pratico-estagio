package com.girotech.desafio.repositories;


import com.girotech.desafio.models.CurrencyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyModel, Long> {

}
