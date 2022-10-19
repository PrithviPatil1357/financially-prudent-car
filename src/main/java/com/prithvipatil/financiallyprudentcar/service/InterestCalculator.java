package com.prithvipatil.financiallyprudentcar.service;

import org.springframework.stereotype.Component;

@Component
public class InterestCalculator {
    public Double calculateInterest(Double principalAmount, Double rateOfInterest, Integer tenureInMonths) {
        return principalAmount * tenureInMonths * rateOfInterest / (12 * 100);
    }

    public Double getTotalAmountPayable(Double principalAmount, Double rateOfInterest, Integer tenureInMonths) {
        Double interestAmount = calculateInterest(principalAmount, rateOfInterest, tenureInMonths);
        return principalAmount + interestAmount;
    }

    public Double getEMI(Double principalAmount, Double rateOfInterest, Integer tenureInMonths) {
        Double totalAmountPayable = getTotalAmountPayable(principalAmount, rateOfInterest, tenureInMonths);
        return totalAmountPayable / tenureInMonths;
    }

    public Double getPrincipalAmount(Double rateOfInterest, Integer tenureInMonths, Double emiAmount) {
        Double totalAmountPayable = tenureInMonths * emiAmount;
        return totalAmountPayable / (1 + (tenureInMonths * rateOfInterest / (12 * 100)));
    }
}
