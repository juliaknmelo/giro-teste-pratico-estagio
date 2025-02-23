package com.girotech.desafio.controller;


import com.girotech.desafio.models.CurrencyModel;
import com.girotech.desafio.models.ExchangeRateModel;
import com.girotech.desafio.repositories.CurrencyRepository;
import com.girotech.desafio.repositories.ExchangeRateRepository;
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

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT )
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ExchangeRateControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Test
    @DisplayName("Post exchange")
    public void postExchange () {

       CurrencyModel currencyModel = new CurrencyModel("real", "brl");
        var currencySave = currencyRepository.save(currencyModel);

        HttpEntity<ExchangeRateModel> bodyRequest = new HttpEntity<ExchangeRateModel>(new ExchangeRateModel(LocalDate.of(
                2025, 02, 01), 0.50F, 0.50F, currencySave));

        ResponseEntity<ExchangeRateModel> bodyResponse = testRestTemplate.exchange("/exchange-rates", HttpMethod.POST,
                bodyRequest, ExchangeRateModel.class);

        assertEquals(HttpStatus.CREATED, bodyResponse.getStatusCode());

    }



    @Test
    @DisplayName("Get all exchange")
    void getAllExchange() {

        CurrencyModel currencyModel = new CurrencyModel("real", "brl");
        var currencySave = currencyRepository.save(currencyModel);

        exchangeRateRepository.save((new ExchangeRateModel(LocalDate.of(2025, 02, 01),
                0.50F, 0.50F, currencySave)));
        exchangeRateRepository.save((new ExchangeRateModel(LocalDate.of(2025, 01, 01),
                0.40F, 0.40F, currencySave)));

        ResponseEntity<String> bodyResponse = testRestTemplate.exchange("/exchange-rates/all", HttpMethod.GET,
                null, String.class);

        assertEquals(HttpStatus.OK, bodyResponse.getStatusCode());
        assertNotNull(bodyResponse.getBody());



    }

    @Test
    @DisplayName("Get recents")
    void getRecentExchange() {

        CurrencyModel currencyModel = new CurrencyModel("real", "brl");
        var currencySave = currencyRepository.save(currencyModel);

        exchangeRateRepository.save((new ExchangeRateModel(LocalDate.of(2025, 02, 20),
                0.50F, 0.50F, currencySave)));
        exchangeRateRepository.save((new ExchangeRateModel(LocalDate.of(2025, 02, 21),
                0.40F, 0.40F, currencySave)));
        exchangeRateRepository.save((new ExchangeRateModel(LocalDate.of(2025, 01, 01),
                0.40F, 0.40F, currencySave)));



        ResponseEntity<String> bodyResponse = testRestTemplate.exchange("/exchange-rates/recent", HttpMethod.GET,
                null, String.class);

        assertEquals(HttpStatus.OK, bodyResponse.getStatusCode());
        assertNotNull(bodyResponse.getBody());

    }


    @Test
    @DisplayName("Get by id")
    void getById() {

        CurrencyModel currencyModel = new CurrencyModel("real", "brl");
        var currencySave = currencyRepository.save(currencyModel);

        var exchangeSave = exchangeRateRepository.save((new ExchangeRateModel(LocalDate.of(2025, 02, 20),
                0.50F, 0.50F, currencySave)));

        ResponseEntity<String> bodyResponse = testRestTemplate.exchange("/exchange-rates/" + exchangeSave.getId(),
                HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, bodyResponse.getStatusCode());
    }

   @Test
   @DisplayName("Update")
    void updateExchange() {

       CurrencyModel currencyModel = new CurrencyModel("real", "brl");
       var currencySave = currencyRepository.save(currencyModel);

       var exchangeSave = exchangeRateRepository.save((new ExchangeRateModel(LocalDate.of(2025, 02, 20),
               0.50F, 0.50F, currencySave)));

       HttpEntity<ExchangeRateModel> exchangeUpdate = new HttpEntity<ExchangeRateModel>(new ExchangeRateModel(
               LocalDate.of(2025, 02, 22),
               0.30F, 0.30F, currencySave));

       ResponseEntity<String> bodyResponse = testRestTemplate.exchange("/exchange-rates/" + exchangeSave.getId(),
               HttpMethod.PUT, exchangeUpdate, String.class);

       assertEquals(HttpStatus.OK, bodyResponse.getStatusCode());

    }

    @Test
    @DisplayName("Delete by id")
    void deleteExchange() {
        CurrencyModel currencyModel = new CurrencyModel("real", "brl");
        var currencySave = currencyRepository.save(currencyModel);

        var repository = exchangeRateRepository.save((new ExchangeRateModel(LocalDate.of(2025, 02, 20),
                0.50F, 0.50F, currencySave)));

        ResponseEntity<String> bodyResponse = testRestTemplate.exchange("/exchange-rates/" + repository.getId(),
                HttpMethod.DELETE, null, String.class);

        assertEquals(HttpStatus.OK, bodyResponse.getStatusCode());
    }

    @Test
    @DisplayName("Delete old")
    void deleteOldExchange() {
        CurrencyModel currencyModel = new CurrencyModel("real", "brl");
        var currencySave = currencyRepository.save(currencyModel);

        exchangeRateRepository.save((new ExchangeRateModel(LocalDate.of(2025, 02, 20),
                0.50F, 0.50F, currencySave)));
        exchangeRateRepository.save((new ExchangeRateModel(LocalDate.of(2025, 01, 21),
                0.40F, 0.40F, currencySave)));
        exchangeRateRepository.save((new ExchangeRateModel(LocalDate.of(2025, 01, 05),
                0.40F, 0.40F, currencySave)));

        ResponseEntity<String> bodyResponse = testRestTemplate.exchange("/exchange-rates/old", HttpMethod.DELETE,
                null, String.class);

        assertEquals(HttpStatus.OK, bodyResponse.getStatusCode());
        assertNotNull(bodyResponse.getBody());
    }
}
