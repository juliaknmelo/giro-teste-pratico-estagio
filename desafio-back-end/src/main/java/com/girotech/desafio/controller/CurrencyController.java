package com.girotech.desafio.controller;

import com.girotech.desafio.dtos.CurrencyRecordDto;
import com.girotech.desafio.models.CurrencyModel;
import com.girotech.desafio.repositories.CurrencyRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CurrencyController {

    @Autowired
    CurrencyRepository currencyRepository;

    @PostMapping("/currencies")
    public ResponseEntity<CurrencyRecordDto> postCurrency(@RequestBody @Valid CurrencyRecordDto currencyRecordDto) {
       var currencyModel = new CurrencyModel();
        BeanUtils.copyProperties(currencyRecordDto, currencyModel);
        currencyRepository.save(currencyModel);
        var currencyDTO = new CurrencyRecordDto(currencyModel.getId(), currencyModel.getName(), currencyModel.getType());
        return ResponseEntity.status(HttpStatus.CREATED).body(currencyDTO);
    }

    @GetMapping("/currencies/all")
    public ResponseEntity<List<CurrencyRecordDto>> getAllCurrency(){
        var currencyAll = currencyRepository.findAll();
        List<CurrencyRecordDto> currencyDTO = new ArrayList<>();
        currencyAll.forEach(value -> {
            var currencyRecord = new CurrencyRecordDto(value.getId(), value.getName(), value.getType());
            currencyDTO.add(currencyRecord);
        });
        return ResponseEntity.status(HttpStatus.OK).body(currencyDTO);
    }

    @GetMapping("/currencies/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") Long id){
        Optional<CurrencyModel> currency = currencyRepository.findById(id);
        if(currency.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Currency not found.");
        }
        var currencyRecord = new CurrencyRecordDto(currency.get().getId(), currency.get().getName(), currency.get().getType());
        return ResponseEntity.status(HttpStatus.OK).body(currencyRecord);
    }

    @DeleteMapping("/currencies/{id}")
    public ResponseEntity<Object> deleteCurrency(@PathVariable(value="id") Long id) {
        Optional<CurrencyModel> currency = currencyRepository.findById(id);
        if (currency.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Currency not found");
        }
        currencyRepository.delete(currency.get());
        return ResponseEntity.status(HttpStatus.OK).body("Currency deleted successfully.");
    }

}
