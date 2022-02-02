package com.prithvipatil.financiallyprudentcar.model;

import lombok.Data;

@Data
public class Buyer {
    private final Double yearlyTakeHomeIncome;
    private final Double emiAsPercentageOfMonthlyIncome;

    public Double getMonthlyTakeHomeIncome() {
        return this.yearlyTakeHomeIncome / 12;
    }

    public Double getEMIAsPercentageOfMonthlyIncome(Double emi) {
        Double monthlyIncome = getMonthlyTakeHomeIncome();
        return emi / monthlyIncome * 100;
    }
}