package com.girotech.desafio.controller;


import com.girotech.desafio.dtos.ExchangeRateRecordDto;
import com.girotech.desafio.dtos.InvestmentHistoryRecordDto;
import com.girotech.desafio.models.ExchangeRateModel;
import com.girotech.desafio.models.InvestmentHistoryModel;
import com.girotech.desafio.repositories.CurrencyRepository;
import com.girotech.desafio.repositories.InvestmentHistoryRepository;
import com.girotech.desafio.repositories.InvestorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class InvestmentHistoryController {

    @Autowired
    InvestmentHistoryRepository investmenthistoryRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    InvestorRepository investorRepository;

    @PostMapping("/investments")
    public ResponseEntity<Object> postExchange(@Valid @RequestBody InvestmentHistoryModel investmentHistoryModel){

        if(currencyRepository.existsById(investmentHistoryModel.getCurrency().getId()) && investorRepository
                .existsById(investmentHistoryModel.getInvestor().getId())){
            var currencyModel = currencyRepository.findById(investmentHistoryModel.getCurrency().getId());
            var investorModel = investorRepository.findById(investmentHistoryModel.getInvestor().getId());
            investmenthistoryRepository.save(investmentHistoryModel);
            var investmentDTO = new InvestmentHistoryRecordDto(investmentHistoryModel.getId(),
                    investmentHistoryModel.getInitial_amount(), investmentHistoryModel.getMonths(),
                    investmentHistoryModel.getInterest_rate(), investmentHistoryModel.getFinal_amount(),
                    currencyModel.get().getId(), investorModel.get().getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(investmentDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Currency or investor not found");
    }

    @GetMapping("/investments/all")
    public ResponseEntity<List<InvestmentHistoryRecordDto>> getAllInvestmentHistory(){
        var investmentAll = investmenthistoryRepository.findAll();
        List<InvestmentHistoryRecordDto> investmentDTO = new ArrayList<>();
        investmentAll.forEach(investment -> {
            var investmentRecord = new InvestmentHistoryRecordDto(investment.getId(), investment.getInitial_amount(),
                    investment.getMonths(), investment.getInterest_rate(), investment.getFinal_amount(),
                    investment.getCurrency().getId(), investment.getInvestor().getId());
                    investmentDTO.add(investmentRecord);
                });
        return ResponseEntity.status(HttpStatus.OK).body(investmentDTO);
    }

    @GetMapping("/investments/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") Long id){
        Optional<InvestmentHistoryModel> investment = investmenthistoryRepository.findById(id);
        if(investment.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("investment not found.");
        }
        var investmentRecord = new InvestmentHistoryRecordDto(investment.get().getId(),
                investment.get().getInitial_amount(), investment.get().getMonths(), investment.get().getInterest_rate(),
                investment.get().getFinal_amount(), investment.get().getCurrency().getId(),
                investment.get().getInvestor().getId());
        return ResponseEntity.status(HttpStatus.OK).body(investmentRecord);
    }


    @DeleteMapping("/investments/{id}")
    public ResponseEntity<Object> deleteInvestmentHistory(@PathVariable(value="id") Long id) {
        Optional<InvestmentHistoryModel> investment = investmenthistoryRepository.findById(id);
        if (investment.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("investment not found");
        }
        investmenthistoryRepository.delete(investment.get());
        return ResponseEntity.status(HttpStatus.OK).body("investment deleted successfully.");
    }

}
