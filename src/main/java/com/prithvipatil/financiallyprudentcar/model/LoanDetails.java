package com.prithvipatil.financiallyprudentcar.model;


import lombok.Data;

@Data
public class LoanDetails {
    private final Integer loanTenureInMonths;
    private final Double rateOfInterest;
    private final Double carDownPaymentPercentage;
}
