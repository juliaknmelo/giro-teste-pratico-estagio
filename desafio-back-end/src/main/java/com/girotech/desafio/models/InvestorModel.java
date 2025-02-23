package com.girotech.desafio.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "tb_investor")
public class InvestorModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Email
    @Schema(example = "email@email.com.br")
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "investor", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("investor")
    private List<InvestmentHistoryModel> investmenthistory;


    public List<InvestmentHistoryModel> getInvestmenthistory() {
        return investmenthistory;
    }

    public void setInvestmenthistory(List<InvestmentHistoryModel> investmenthistory) {
        this.investmenthistory = investmenthistory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    public InvestorModel(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public InvestorModel() {

    }

}
