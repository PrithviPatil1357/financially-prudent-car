package com.prithvipatil.financiallyprudentcar.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CarBuyer {
    private final Double yearlyTakeHomeIncome;
    private final Double carBudgetAsPercentageOfTakeHomeIncome;
    private final Double carDownPaymentPercentage;

    public Double getMonthlyTakeHomeIncome() {
        return this.yearlyTakeHomeIncome / 12;
    }
}