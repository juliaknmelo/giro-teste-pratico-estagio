package com.girotech.desafio.controller;

import com.girotech.desafio.dtos.CurrencyRecordDto;
import com.girotech.desafio.models.CurrencyModel;
import com.girotech.desafio.repositories.CurrencyRepository;
import com.girotech.desafio.repositories.ExchangeRateRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CurrencyControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CurrencyRepository currencyRepository;

    @BeforeEach
    void start() {

        currencyRepository.deleteAll();
    }

    @Test
    @DisplayName("Post currency")
    void postCurrency() {

        HttpEntity<CurrencyModel> bodyRequest = new HttpEntity<CurrencyModel>(new CurrencyModel( "Dólar americano",
                "USD"));

        ResponseEntity<CurrencyModel> bodyResponse = testRestTemplate.exchange("/currencies", HttpMethod.POST,
               bodyRequest, CurrencyModel.class);

        assertEquals(HttpStatus.CREATED, bodyResponse.getStatusCode());
    }

    @Test
    @DisplayName("Get all currencies")
    void getAllCurrency() {

        currencyRepository.save(new CurrencyModel("Dólar americano", "USD"));
        currencyRepository.save(new CurrencyModel("Real brasileiro", "BRL"));

        ResponseEntity<String> bodyResponse = testRestTemplate.exchange("/currencies/all", HttpMethod.GET, null,
                String.class);

        assertEquals(HttpStatus.OK, bodyResponse.getStatusCode());
        assertNotNull(bodyResponse.getBody());

    }

   @Test
   @DisplayName("Get by id")
    void getById() {
       CurrencyModel currencySave = new CurrencyModel("Real brasileiro", "BRL");
       var repository = currencyRepository.save(currencySave);

       ResponseEntity<String> bodyResponse = testRestTemplate.exchange("/currencies/" + repository.getId(),
               HttpMethod.GET, null, String.class);

       assertEquals(HttpStatus.OK, bodyResponse.getStatusCode());


    }

    @Test
    @DisplayName("Delete by id")
    void deleteCurrency() {
        CurrencyModel currencySave = new CurrencyModel("Real brasileiro", "BRL");
        var repository = currencyRepository.save(currencySave);

        ResponseEntity<String> bodyResponse = testRestTemplate.exchange("/currencies/" + repository.getId(),
                HttpMethod.DELETE, null, String.class);

        assertEquals(HttpStatus.OK, bodyResponse.getStatusCode());
    }
}