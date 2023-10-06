package com.prithvipatil.financiallyprudentcar.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Buyer {
    private final Double yearlyTakeHomeIncome;
    private final Double emiAsPercentageOfMonthlyIncome;
    private final Double emiAmountAfforded;
    private final Double carDownPaymentAmountAfforded;
    private final Double carDownPaymentPercentage;


    public Double getMonthlyTakeHomeIncome() {
        return this.yearlyTakeHomeIncome / 12;
    }

    public Double getEMIAsPercentageOfMonthlyIncome(Double emi) {
        Double monthlyIncome = getMonthlyTakeHomeIncome();
        return emi / monthlyIncome * 100;
    }
}