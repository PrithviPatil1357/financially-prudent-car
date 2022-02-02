package com.prithvipatil.financiallyprudentcar.service;

import com.prithvipatil.financiallyprudentcar.Util.CurrencyUtil;
import com.prithvipatil.financiallyprudentcar.model.Buyer;
import com.prithvipatil.financiallyprudentcar.model.LoanDetails;
import com.prithvipatil.financiallyprudentcar.model.Specifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;


@Service
public class ApplicationService {

    @Autowired
    private InterestCalculator interestCalculator;

    @Autowired
    private CurrencyUtil currencyUtil;

    public String priceOfCar(Specifications specifications, String countryCode) {
        LoanDetails loanDetails = specifications.getLoanDetails();
        Double financedAmount = interestCalculator.getPrincipalAmount(loanDetails.getRateOfInterest(), loanDetails.getLoanTenureInMonths(), getEMI(specifications.getBuyer()));
        Double costOfCar = financedAmount * 100 / (100 - loanDetails.getCarDownPaymentPercentage());
        return currencyUtil.currencyWithChosenLocalisation((int) Math.ceil(costOfCar), new Locale("", countryCode != null ? countryCode : "IN"));
    }


    private Double getEMI(Buyer buyer) {
        return buyer.getMonthlyTakeHomeIncome() * buyer.getEmiAsPercentageOfMonthlyIncome() / 100;
    }

}
