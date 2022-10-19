package com.prithvipatil.financiallyprudentcar.controller;

import com.prithvipatil.financiallyprudentcar.model.request.Specifications;
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

    @GetMapping(value = {"/car-price/country-code/{country-code}"})
    public String getCarPrice(@RequestBody @Valid Specifications inputSpecifications, @PathVariable(name = "country-code", required = false) String countryCode) {
        return applicationService.priceOfCar(inputSpecifications, countryCode);
    }

    @GetMapping(value = {"/emi/country-code/{country-code}"})
    public String getEMI(@RequestBody @Valid Specifications inputSpecifications, @PathVariable(name = "country-code", required = false) String countryCode) {
        return applicationService.getEMIForSpecifications(inputSpecifications, countryCode);
    }

    @GetMapping(value = {"/down-payment/country-code/{country-code}"})
    public String getDownPayment(@RequestBody @Valid Specifications inputSpecifications, @PathVariable(name = "country-code", required = false) String countryCode) throws Exception {
        return applicationService.computeDownPayment(inputSpecifications, countryCode);
    }
}
