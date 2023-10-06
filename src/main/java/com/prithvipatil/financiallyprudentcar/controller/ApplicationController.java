package com.prithvipatil.financiallyprudentcar.controller;

import com.prithvipatil.financiallyprudentcar.exceptions.CustomException;
import com.prithvipatil.financiallyprudentcar.model.request.Specifications;
import com.prithvipatil.financiallyprudentcar.model.response.CarPrice;
import com.prithvipatil.financiallyprudentcar.model.response.Income;
import com.prithvipatil.financiallyprudentcar.service.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("financially-prudent-car")
@Slf4j
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping(value = {"/car-price/country-code/{country-code}"})
    public CarPrice getCarPrice(@RequestBody @Valid Specifications inputSpecifications, @PathVariable(name = "country-code", required = false) String countryCode) {
        return applicationService.priceOfCar(inputSpecifications, countryCode);
    }

    @PostMapping(value = {"/emi/country-code/{country-code}"})
    public String getEMI(@RequestBody @Valid Specifications inputSpecifications, @PathVariable(name = "country-code", required = false) String countryCode) {
        return applicationService.getEMIForSpecifications(inputSpecifications, countryCode);
    }

    @PostMapping(value = {"/down-payment/country-code/{country-code}"})
    public String getDownPayment(@RequestBody @Valid Specifications inputSpecifications, @PathVariable(name = "country-code", required = false) String countryCode) throws Exception {
        return applicationService.computeDownPayment(inputSpecifications, countryCode);
    }

    @PostMapping(value = {"/required-income/country-code/{country-code}"})
    public Income getRequiredIncome(@RequestBody @Valid Specifications inputSpecifications, @PathVariable(name = "country-code", required = false) String countryCode) throws CustomException {
        return applicationService.computeIncomeRequired(inputSpecifications, countryCode);
    }
}
