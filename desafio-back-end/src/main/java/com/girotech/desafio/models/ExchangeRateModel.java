package com.girotech.desafio.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_exchange")
public class ExchangeRateModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private Float daily_variation;

    private Float daily_rate;

    @ManyToOne
    @JsonIgnoreProperties("exchangerate")
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private CurrencyModel currency;

    public ExchangeRateModel(LocalDate of, float v, float v1) {
    }


    public CurrencyModel getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyModel currency) {
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Float getDaily_variation() {
        return daily_variation;
    }

    public void setDaily_variation(Float daily_variation) {
        this.daily_variation = daily_variation;
    }

    public Float getDaily_rate() {
        return daily_rate;
    }

    public void setDaily_rate(Float daily_rate) {
        this.daily_rate = daily_rate;
    }
    

    public ExchangeRateModel(LocalDate date, float daily_variation, float daily_rate, CurrencyModel currency) {
        this.date = date;
        this.daily_variation = daily_variation;
        this.daily_rate = daily_rate;
        this.currency = currency;
    }

    public ExchangeRateModel() {        }
}
