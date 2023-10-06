package com.prithvipatil.financiallyprudentcar.service;

import com.prithvipatil.financiallyprudentcar.Util.CurrencyUtil;
import com.prithvipatil.financiallyprudentcar.exceptions.CustomException;
import com.prithvipatil.financiallyprudentcar.model.request.Buyer;
import com.prithvipatil.financiallyprudentcar.model.request.LoanDetails;
import com.prithvipatil.financiallyprudentcar.model.request.Specifications;
import com.prithvipatil.financiallyprudentcar.model.response.CarPrice;
import com.prithvipatil.financiallyprudentcar.model.response.Income;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.Locale;


@Service
@Slf4j
public class ApplicationService {

    @Autowired
    private InterestCalculator interestCalculator;

    @Autowired
    private CurrencyUtil currencyUtil;

    public CarPrice priceOfCar(Specifications specifications, String countryCode) {
        log.info("Entering method priceOfCar with specifications: {} and countryCode: {}", specifications, countryCode);
        Double costOfCar = getCostOfCar(specifications);
        log.info("Exiting method priceOfCar with result: {}", costOfCar);
        Currency cur = Currency.getInstance("INR");
        return new CarPrice(Math.ceil(costOfCar), cur.getSymbol());
    }


    private Double getEMI(Buyer buyer) {
        if (buyer.getEmiAmountAfforded() == null) {
            return buyer.getMonthlyTakeHomeIncome() * buyer.getEmiAsPercentageOfMonthlyIncome() / 100;
        } else {
            return buyer.getEmiAmountAfforded();
        }
    }

    private Double getCostOfCar(Specifications specifications) {
        log.info("Entering method getCostOfCar with specifications: {}", specifications);
        Double costOfCar;
        try {
            LoanDetails loanDetails = specifications.getLoanDetails();
            Buyer buyer = specifications.getBuyer();
            Double principalAmount = interestCalculator.getPrincipalAmount(loanDetails.getRateOfInterest(), loanDetails.getLoanTenureInMonths(), getEMI(buyer));
            costOfCar = (buyer.getCarDownPaymentAmountAfforded() != null ? principalAmount + buyer.getCarDownPaymentAmountAfforded() : principalAmount / (1 - buyer.getCarDownPaymentPercentage() / 100));
            log.info("Exiting method getCostOfCar with result: {}", costOfCar);
            return costOfCar;
        } catch (Exception e) {
            log.error("Error occurred while computing getCostOfCar: {}", e);
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

    private Double computeEmiForSpecifications(Specifications inputSpecifications) {
        Double costOfCar = inputSpecifications.getCost();
        LoanDetails loanDetails = inputSpecifications.getLoanDetails();
        Buyer buyer = inputSpecifications.getBuyer();
        Double loanPrincipalAmount = inputSpecifications.getBuyer().getCarDownPaymentAmountAfforded() != null ? costOfCar - buyer.getCarDownPaymentAmountAfforded() : costOfCar - costOfCar * buyer.getCarDownPaymentPercentage() / 100;
        return interestCalculator.getEMI(loanPrincipalAmount, loanDetails.getRateOfInterest(), loanDetails.getLoanTenureInMonths());
    }

    public String getEMIForSpecifications(Specifications inputSpecifications, String countryCode) {
        try {
            Double emi = computeEmiForSpecifications(inputSpecifications);
            return currencyUtil.currencyWithChosenLocalisation((int) Math.ceil(emi), new Locale("", countryCode != null ? countryCode : "IN"));
        } catch (Exception e) {
            log.error("Error occured while computing getEMIForSpecifications {}", e);
            throw e;
        }
    }


    public Income computeIncomeRequired(Specifications input, String countryCode) throws CustomException {
        try {
            if (input.getCost() == null) {
                throw new CustomException("Car cost not provided!");
            }
            if (input.getBuyer().getEmiAsPercentageOfMonthlyIncome() == null) {
                throw new CustomException("Emi as percentage of Income not provided");
            }
            Double emi = computeEmiForSpecifications(input);
            Double income = emi * 100 / input.getBuyer().getEmiAsPercentageOfMonthlyIncome()*12;
            return new Income(income);
        } catch (Exception e) {
            log.error("Error occured while computing getEMIForSpecifications {}", e);
            throw e;
        }
    }
}
