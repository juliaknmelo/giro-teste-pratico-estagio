package com.girotech.desafio.controller;


import com.girotech.desafio.dtos.ExchangeRateRecordDto;
import com.girotech.desafio.models.ExchangeRateModel;
import com.girotech.desafio.repositories.CurrencyRepository;
import com.girotech.desafio.repositories.ExchangeRateRepository;
import com.girotech.desafio.request.ExchangeUpdateRequest;
import com.girotech.desafio.response.ExchangeRecentResponse;
import com.girotech.desafio.response.ExchangeResponse;
import com.girotech.desafio.response.ExchangeUpdateResponse;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ExchangeRateController {

    @Autowired
    ExchangeRateRepository exchangeRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    @PostMapping("/exchange-rates")
    public ResponseEntity<Object> postExchange(@Valid @RequestBody ExchangeRateModel exchangeRateModel){

        if(currencyRepository.existsById(exchangeRateModel.getCurrency().getId())){
           var currencyModel = currencyRepository.findById(exchangeRateModel.getCurrency().getId());
            exchangeRepository.save(exchangeRateModel);
            var exchangeDTO = new ExchangeRateRecordDto(exchangeRateModel.getId(), exchangeRateModel.getDate(),
                    exchangeRateModel.getDaily_variation(), exchangeRateModel.getDaily_rate(),
                    currencyModel.get().getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(exchangeDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Currency not found");
    }


    @GetMapping("/exchange-rates/all")
    public ResponseEntity<List<ExchangeResponse>> getAllExchange(){
        var exchangeAll = exchangeRepository.findAll();
        List<ExchangeResponse> exchangeResponseList = new ArrayList<>();
        exchangeAll.forEach(exchange -> {
            var exchangeResponse = new ExchangeResponse(exchange.getId(), exchange.getDate(),
                    exchange.getDaily_variation(), exchange.getDaily_rate(), exchange.getCurrency().getName(),
                    exchange.getCurrency().getType());
            exchangeResponseList.add(exchangeResponse);
        });

        return ResponseEntity.status(HttpStatus.OK).body(exchangeResponseList);
    }

    @GetMapping("/exchange-rates/recent")
    public ResponseEntity<List<ExchangeRecentResponse>> getRecentExchange(){
        LocalDate dateNow = LocalDate.now();
        LocalDate dateRecent = dateNow.minusDays(7);

        var exchangeAll = exchangeRepository.findAll();
        List<ExchangeRecentResponse> exchangeHistory = new ArrayList<>();
        exchangeAll.forEach(value -> {
            if(value.getDate().isEqual(dateRecent) || value.getDate().isAfter(dateRecent)){
                var exchangeRecent = new ExchangeRecentResponse(value.getId(), value.getDate(),
                        value.getDaily_variation(), value.getDaily_rate(), value.getCurrency().getName(),
                        value.getCurrency().getType());
                exchangeHistory.add(exchangeRecent);
            }
        });
        return ResponseEntity.status(HttpStatus.OK).body(exchangeHistory);
    }

    @GetMapping("/exchange-rates/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") Long id){
        Optional<ExchangeRateModel> exchange = exchangeRepository.findById(id);
        if(exchange.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exchange not found.");
        }
        var exchangeResponse = new ExchangeResponse(exchange.get().getId(), exchange.get().getDate(),
                exchange.get().getDaily_variation(), exchange.get().getDaily_rate(), exchange.get().getCurrency().getName(),
                exchange.get().getCurrency().getType());
        return ResponseEntity.status(HttpStatus.OK).body(exchangeResponse);
    }



    @PutMapping("/exchange-rates/{id}")
    public ResponseEntity<Object> updateExchange(@PathVariable(value="id") Long id,
                                                 @RequestBody @Valid ExchangeUpdateRequest exchangeUpdateRequest){
        Optional<ExchangeRateModel> exchange = exchangeRepository.findById(id);
        if(exchange.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exchange not found.");
        }
        var exchangeModel = exchange.get();
        BeanUtils.copyProperties(exchangeUpdateRequest, exchangeModel);
        exchangeRepository.save(exchangeModel);
        var exchangeDTO = new ExchangeUpdateResponse(exchange.get().getId(), exchange.get().getDate(),
                exchange.get().getDaily_variation(), exchange.get().getDaily_rate());
        return ResponseEntity.status(HttpStatus.OK).body(exchangeDTO);
    }

    @DeleteMapping("/exchange-rates/{id}")
    public ResponseEntity<Object> deleteExchange(@PathVariable(value="id") Long id) {
        Optional<ExchangeRateModel> exchange = exchangeRepository.findById(id);
        if (exchange.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exchange not found");
        }
        exchangeRepository.delete(exchange.get());
        return ResponseEntity.status(HttpStatus.OK).body("Exchange deleted successfully.");
    }

    @DeleteMapping("/exchange-rates/old")
    public ResponseEntity<String> deleteOldExchange() {

        LocalDate dateNow = LocalDate.now();
        LocalDate dateRecent = dateNow.minusDays(30);
        System.out.println(dateRecent);
        var exchangeAll = exchangeRepository.findAll();

        exchangeAll.forEach(exchange -> {
            if(exchange.getDate().isBefore(dateRecent)){
                var exchangeOld = new ExchangeRateModel();
                BeanUtils.copyProperties(exchange, exchangeOld);
                exchangeRepository.delete(exchangeOld);
            }
        });

        return ResponseEntity.status(HttpStatus.OK).body("Exchange deleted successfully.");
    }

}
