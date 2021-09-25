package com.prithvipatil.financiallyprudentcar.service;

import com.prithvipatil.financiallyprudentcar.model.CarBuyer;
import com.prithvipatil.financiallyprudentcar.model.Specifications;

public class ApplicationService {
    private Integer loanTenureInMonths;
    private Double rateOfInterest;
    private CarBuyer carBuyer;

    public ApplicationService(Specifications inputSpecifications) {
        this.loanTenureInMonths = inputSpecifications.getLoanTenureInMonths();
        this.rateOfInterest = inputSpecifications.getRateOfInterest();
        carBuyer= new CarBuyer(inputSpecifications.getYearlyTakeHomeIncome(),inputSpecifications.getCarBudgetAsPercentageOfTakeHomeIncome(),inputSpecifications.getCarDownPaymentPercentage());
    }

    public int priceOfCar() {
        Double emiAmount = carBuyer.getMonthlyTakeHomeIncome() * carBuyer.getCarBudgetAsPercentageOfTakeHomeIncome() / 100;
        //financed amount is 80% of car cost
        Double financedAmount = InterestCalculator.getPrincipalAmount(this.rateOfInterest, this.loanTenureInMonths, emiAmount);
        Double costOfCar = financedAmount * 100 / (100 - carBuyer.getCarDownPaymentPercentage());
        return (int) Math.ceil(costOfCar);
    }
}
