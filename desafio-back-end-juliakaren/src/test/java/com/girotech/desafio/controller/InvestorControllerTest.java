package com.girotech.desafio.controller;

import com.girotech.desafio.dtos.InvestorRecordDto;
import com.girotech.desafio.models.CurrencyModel;
import com.girotech.desafio.models.InvestorModel;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InvestorControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private InvestorRepository investorRepository;

    @BeforeEach
    void start() {

        investorRepository.deleteAll();
    }

    @Test
    @DisplayName("Post investor")
    void postInvestor() {
        HttpEntity<InvestorModel> bodyRequest = new HttpEntity<InvestorModel>(new InvestorModel("julia",
                "julia@email.com.br"));

        ResponseEntity<InvestorModel> bodyResponse = testRestTemplate.exchange("/investors", HttpMethod.POST,
                bodyRequest, InvestorModel.class);

        assertEquals(HttpStatus.CREATED, bodyResponse.getStatusCode());
    }

    @Test
    @DisplayName("Get all currencies")
    void getAllCurrency() {

        investorRepository.save(new InvestorModel("Bruno", "julia@email.com.br"));
        investorRepository.save(new InvestorModel("Bruno", "bruno@email.com.br"));

        ResponseEntity<String> bodyResponse = testRestTemplate.exchange("/investors/all", HttpMethod.GET, null,
                String.class);

        assertEquals(HttpStatus.OK, bodyResponse.getStatusCode());
        assertNotNull(bodyResponse.getBody());

    }


    @Test
    @DisplayName("Get by id")
    void getById() {
        InvestorModel investorSave = new InvestorModel("julia", "julia@email.com.br");
        var repository = investorRepository.save(investorSave);

        ResponseEntity<String> bodyResponse = testRestTemplate.exchange("/investors/" + repository.getId(),
                HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, bodyResponse.getStatusCode());


    }

    @Test
    @DisplayName("Update")
    void updateInvestor() {
        InvestorModel investorSave = new InvestorModel("julia", "julia@email.com.br");
        var repository = investorRepository.save(investorSave);

        HttpEntity<InvestorRecordDto> bodyRequest = new HttpEntity<InvestorRecordDto>(new InvestorRecordDto(repository.getId(), "julia",
                "julia.melo@email.com.br"));

        ResponseEntity<String> bodyResponse = testRestTemplate.exchange("/investors/" + repository.getId(),
                HttpMethod.PUT, bodyRequest, String.class);

        assertEquals(HttpStatus.OK, bodyResponse.getStatusCode());

    }

    @Test
    @DisplayName("Delete by id")
    void deleteInvestor() {

        InvestorModel investorSave = new InvestorModel("julia", "julia@email.com.br");
        var repository = investorRepository.save(investorSave);

        ResponseEntity<String> bodyResponse = testRestTemplate.exchange("/investors/" + repository.getId(),
                HttpMethod.DELETE, null, String.class);

        assertEquals(HttpStatus.OK, bodyResponse.getStatusCode());

    }





}




