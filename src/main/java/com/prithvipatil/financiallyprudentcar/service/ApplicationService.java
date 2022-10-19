package com.prithvipatil.financiallyprudentcar.service;

import com.prithvipatil.financiallyprudentcar.Util.CurrencyUtil;
import com.prithvipatil.financiallyprudentcar.model.request.Buyer;
import com.prithvipatil.financiallyprudentcar.model.request.LoanDetails;
import com.prithvipatil.financiallyprudentcar.model.request.Specifications;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;


@Service
@Slf4j
public class ApplicationService {

    @Autowired
    private InterestCalculator interestCalculator;

    @Autowired
    private CurrencyUtil currencyUtil;

    public String priceOfCar(Specifications specifications, String countryCode) {
        Double costOfCar = getCostOfCar(specifications);
        return currencyUtil.currencyWithChosenLocalisation((int) Math.ceil(costOfCar), new Locale("", countryCode != null ? countryCode : "IN"));
    }


    private Double getEMI(Buyer buyer) {
        if (buyer.getEmiAmountAfforded() == null) {
            return buyer.getMonthlyTakeHomeIncome() * buyer.getEmiAsPercentageOfMonthlyIncome() / 100;
        } else {
            return buyer.getEmiAmountAfforded();
        }
    }

    private Double getCostOfCar(Specifications specifications) {
        Double costOfCar;
        try {
            LoanDetails loanDetails = specifications.getLoanDetails();
            Buyer buyer = specifications.getBuyer();
            Double principalAmount = interestCalculator.getPrincipalAmount(loanDetails.getRateOfInterest(), loanDetails.getLoanTenureInMonths(), getEMI(specifications.getBuyer()));
            Double financedAmountWithInterest = interestCalculator.getTotalAmountPayable(principalAmount, loanDetails.getRateOfInterest(), loanDetails.getLoanTenureInMonths());
            costOfCar = financedAmountWithInterest + getDownPaymentAmount(buyer, principalAmount);
            return costOfCar;
        } catch (Exception e) {
            log.error("Error ocuured while computing getCostOfCar {}", e);
            throw e;
        }
    }

    private Double getDownPaymentAmount(Buyer buyer, Double principalAmount) {
        if (buyer.getCarDownPaymentAmountAfforded() != null) {
            return buyer.getCarDownPaymentAmountAfforded();
        } else {
            return principalAmount * buyer.getCarDownPaymentPercentage() / 100;
        }
    }

    public String computeDownPayment(Specifications specifications, String countryCode) throws Exception {
        if (specifications.getCost() != null && specifications.getCost() > 0 && specifications.getBuyer().getCarDownPaymentAmountAfforded() == null && specifications.getBuyer().getCarDownPaymentPercentage() == null) {
            LoanDetails loanDetails = specifications.getLoanDetails();
            Buyer buyer = specifications.getBuyer();
            Double emi = getEMI(buyer);
            Double principalAmount = interestCalculator.getPrincipalAmount(loanDetails.getRateOfInterest(), loanDetails.getLoanTenureInMonths(), emi);
            Double interest = interestCalculator.calculateInterest(principalAmount, loanDetails.getRateOfInterest(), loanDetails.getLoanTenureInMonths());
            Double downPaymentNeeded = specifications.getCost() + interest - (emi * loanDetails.getLoanTenureInMonths());
            return currencyUtil.currencyWithChosenLocalisation((int) Math.ceil(downPaymentNeeded), new Locale("", countryCode != null ? countryCode : "IN"));
        } else {
            throw new Exception("Bad input");
        }
    }

    public String getEMIForSpecifications(Specifications inputSpecifications, String countryCode) {
        try {
            Double costOfCar = inputSpecifications.getCost();
            LoanDetails loanDetails = inputSpecifications.getLoanDetails();
            Buyer buyer = inputSpecifications.getBuyer();
            Double loanPrincipalAmount = costOfCar - buyer.getCarDownPaymentAmountAfforded();
            Double emi = interestCalculator.getEMI(loanPrincipalAmount,loanDetails.getRateOfInterest(),loanDetails.getLoanTenureInMonths());
            return currencyUtil.currencyWithChosenLocalisation((int) Math.ceil(emi), new Locale("", countryCode != null ? countryCode : "IN"));
        } catch (Exception e) {
            log.error("Error ocuured while computing getEMIForSpecifications {}", e);
            throw e;
        }
    }
}
