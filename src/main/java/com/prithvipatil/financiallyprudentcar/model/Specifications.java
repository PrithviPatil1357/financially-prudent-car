package com.prithvipatil.financiallyprudentcar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Specifications {
    private final Integer loanTenureInMonths;
    private final Double rateOfInterest;
    private final Double carDownPaymentPercentage;
    private final Double carBudgetAsPercentageOfTakeHomeIncome;
    private final Double yearlyTakeHomeIncome;
}