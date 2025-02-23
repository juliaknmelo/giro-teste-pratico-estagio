package com.girotech.desafio.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_history")
public class InvestmentHistoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float initial_amount;

    private int months;

    private Float interest_rate;

    private Float final_amount;

    @ManyToOne
    @JsonIgnoreProperties("investmenthistory")
    private CurrencyModel currency;

    @ManyToOne
    @JsonIgnoreProperties("investmenthistory")
    private InvestorModel investor;



    public CurrencyModel getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyModel currency) {
        this.currency = currency;
    }

    public InvestorModel getInvestor() {
        return investor;
    }

    public void setInvestor(InvestorModel investor) {
        this.investor = investor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getInitial_amount() {
        return initial_amount;
    }

    public void setInitial_amount(Float initial_amount) {
        this.initial_amount = initial_amount;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public Float getInterest_rate() {
        return interest_rate;
    }

    public void setInterest_rate(Float interest_rate) {
        this.interest_rate = interest_rate;
    }

    public Float getFinal_amount() {
        return final_amount;
    }

    public void setFinal_amount(Float final_amount) {
        this.final_amount = final_amount;
    }

    public InvestmentHistoryModel(float initial_amount, int months, float interest_rate, float final_amount, CurrencyModel currency, InvestorModel investor) {
        this.initial_amount = initial_amount;
        this.months = months;
        this.interest_rate = interest_rate;
        this.final_amount = final_amount;
        this.currency = currency;
        this.investor = investor;
    }

    public InvestmentHistoryModel(Long id, Float initial_amount, int months, Float interest_rate, Float final_amount) {
        this.initial_amount = initial_amount;
        this.months = months;
        this.interest_rate = interest_rate;
        this.final_amount = final_amount;
    }

    public InvestmentHistoryModel() {
    }


}
