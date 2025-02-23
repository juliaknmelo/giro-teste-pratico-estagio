package com.girotech.desafio.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;


import java.util.List;


@Entity
@Table(name = "tb_currency")
public class CurrencyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "currency", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("currency")
    private List<InvestmentHistoryModel> investmenthistory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "currency", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("currency")
    private List<ExchangeRateModel> exchangerate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ExchangeRateModel> getExchangerate() {
        return exchangerate;
    }

    public void setExchangerate(List<ExchangeRateModel> exchangerate) {
        this.exchangerate = exchangerate;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<InvestmentHistoryModel> getInvestmenthistory() {
        return investmenthistory;
    }

    public void setInvestmenthistory(List<InvestmentHistoryModel> investmenthistory) {
        this.investmenthistory = investmenthistory;
    }

    public CurrencyModel(long id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public CurrencyModel(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public CurrencyModel () {

    }
}
