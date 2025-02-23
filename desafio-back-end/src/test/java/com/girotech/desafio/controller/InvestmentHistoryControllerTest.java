package com.girotech.desafio.controller;

import com.girotech.desafio.models.CurrencyModel;
import com.girotech.desafio.models.InvestmentHistoryModel;
import com.girotech.desafio.models.InvestorModel;
import com.girotech.desafio.repositories.CurrencyRepository;
import com.girotech.desafio.repositories.InvestmentHistoryRepository;
import com.girotech.desafio.repositories.InvestorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InvestmentHistoryControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private InvestmentHistoryRepository investmentHistoryRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private InvestorRepository investorRepository;

    @BeforeEach
    void star() {
        investmentHistoryRepository.deleteAll();
    }

    @Test
    @DisplayName("Post investment")
    void postInvestmentHistory () {

        CurrencyModel currencyModel = new CurrencyModel("real", "brl");
        var currencySave = currencyRepository.save(currencyModel);

        InvestorModel investorModel = new InvestorModel("julia", "julia@email.com.br");
        var investorSave = investorRepository.save(investorModel);

        HttpEntity<InvestmentHistoryModel> bodyRequest =  new HttpEntity<InvestmentHistoryModel>(new InvestmentHistoryModel(
                30000.0F, 12, 5.5F, 30550.0F, currencySave, investorSave));

        ResponseEntity<InvestmentHistoryModel> bodyResponse = testRestTemplate.exchange("/investments", HttpMethod.POST,
                bodyRequest, InvestmentHistoryModel.class);

        assertEquals(HttpStatus.CREATED, bodyResponse.getStatusCode());

    }

    @Test
    @DisplayName("Get all investments")
    void getAllExchange() {

        CurrencyModel currencyModel = new CurrencyModel("real", "brl");
        var currencySave = currencyRepository.save(currencyModel);

        InvestorModel investorModel = new InvestorModel("julia", "julia@email.com.br");
        var investorSave = investorRepository.save(investorModel);

        investmentHistoryRepository.save((new InvestmentHistoryModel(30000.0F, 12, 5.5F,
                30550.0F, currencySave, investorSave)));
        investmentHistoryRepository.save((new InvestmentHistoryModel(40000.0F, 12, 5.5F,
                40550.0F, currencySave, investorSave)));

        ResponseEntity<String> bodyResponse = testRestTemplate.exchange("/investments/all", HttpMethod.GET,
                null, String.class);

        assertEquals(HttpStatus.OK, bodyResponse.getStatusCode());
        assertNotNull(bodyResponse.getBody());


    }

    @Test
    @DisplayName("Get by id")
    void getById() {

        CurrencyModel currencyModel = new CurrencyModel("real", "brl");
        var currencySave = currencyRepository.save(currencyModel);

        InvestorModel investorModel = new InvestorModel("julia", "julia@email.com.br");
        var investorSave = investorRepository.save(investorModel);

        var investmentSave = investmentHistoryRepository.save((new InvestmentHistoryModel(
                30000.0F, 12, 5.5F, 30550.0F, currencySave, investorSave)));

        ResponseEntity<String> bodyResponse = testRestTemplate.exchange("/investments/" +
                        investmentSave.getId(), HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, bodyResponse.getStatusCode());
    }

    @Test
    @DisplayName("Delete by id")
    void deleteInvestmentHistory () {

        CurrencyModel currencyModel = new CurrencyModel("real", "brl");
        var currencySave = currencyRepository.save(currencyModel);

        InvestorModel investorModel = new InvestorModel("julia", "julia@email.com.br");
        var investorSave = investorRepository.save(investorModel);

        var repository = investmentHistoryRepository.save((new InvestmentHistoryModel(
                30000.0F, 12, 5.5F, 30550.0F, currencySave, investorSave)));

        ResponseEntity<String> bodyResponse = testRestTemplate.exchange("/investments/" +
                repository.getId(), HttpMethod.DELETE, null, String.class);

        assertEquals(HttpStatus.OK, bodyResponse.getStatusCode());

    }


}
