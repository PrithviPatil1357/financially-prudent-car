package com.prithvipatil.financiallyprudentcar.service;

import org.springframework.stereotype.Component;

@Component
public class InterestCalculator {
    public Double calculateInterest(Double principalAmount, Double rateOfInterest, Integer tenureInMonths) {
        return principalAmount * tenureInMonths * rateOfInterest / (12 * 100);
    }

    public Double calculateTotalAmountPayable(Double principalAmount, Double rateOfInterest, Integer tenureInMonths) {
        Double interestAmount = calculateInterest(principalAmount, rateOfInterest, tenureInMonths);
        return principalAmount + interestAmount;
    }

    public int getEMI(Double principalAmount, Double rateOfInterest, Integer tenureInMonths) {
        Double totalAmountPayable = calculateTotalAmountPayable(principalAmount, rateOfInterest, tenureInMonths);
        return (int) Math.ceil(totalAmountPayable / tenureInMonths);
    }

    public Double getPrincipalAmount(Double rateOfInterest, Integer tenureInMonths, Double emiAmount) {
        Double totalAmountPayable = tenureInMonths * emiAmount;
        return totalAmountPayable / (1 + (tenureInMonths * rateOfInterest / (12 * 100)));
    }
}
