package com.girotech.desafio.controller;


import com.girotech.desafio.dtos.InvestorRecordDto;
import com.girotech.desafio.models.InvestorModel;
import com.girotech.desafio.repositories.InvestorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class InvestorController {

    @Autowired
    InvestorRepository investorRepository;

    @PostMapping("/investors")
    public ResponseEntity<InvestorRecordDto> postInvestor(@RequestBody @Valid InvestorRecordDto investorRecordDto) {
        var investorModel = new InvestorModel();
        BeanUtils.copyProperties(investorRecordDto, investorModel);
        investorRepository.save(investorModel);
        var investorDTO = new InvestorRecordDto(investorModel.getId(), investorModel.getName(), investorModel.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(investorDTO);
    }

    @GetMapping("/investors/all")
    public ResponseEntity<List<InvestorRecordDto>> getAllInvestor(){
        var investorAll = investorRepository.findAll();
        List<InvestorRecordDto> inverstorDTO = new ArrayList<>();
        investorAll.forEach(investor -> {
            var invertorRecord = new InvestorRecordDto(investor.getId(), investor.getName(), investor.getEmail());
            inverstorDTO.add(invertorRecord);
        });
        return ResponseEntity.status(HttpStatus.OK).body(inverstorDTO);
    }

    @GetMapping("/investors/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") Long id){
        Optional<InvestorModel> investor = investorRepository.findById(id);
        if(investor.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Investor not found.");
        }
        var investorRecord = new InvestorRecordDto(investor.get().getId(), investor.get().getName(),
                investor.get().getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(investorRecord);
    }

    @PutMapping("/investors/{id}")
    public ResponseEntity<Object> updateInvestor(@PathVariable(value="id") Long id,
                                                 @RequestBody @Valid InvestorRecordDto investorRecordDto){
        Optional<InvestorModel> investor = investorRepository.findById(id);
        if(investor.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Investor not found.");
        }
        var investorModel = investor.get();
        BeanUtils.copyProperties(investorRecordDto, investorModel);
        investorRepository.save(investorModel);
        var investorDTO = new InvestorRecordDto(investor.get().getId(), investor.get().getName(),
                investor.get().getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(investorDTO);
    }

    @DeleteMapping("/investors/{id}")
    public ResponseEntity<Object> deleteInvestor(@PathVariable(value="id") Long id) {
        Optional<InvestorModel> investor = investorRepository.findById(id);
        if (investor.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Investor not found");
        }
        investorRepository.delete(investor.get());
        return ResponseEntity.status(HttpStatus.OK).body("Investor deleted successfully.");
    }

    }


