package com.prithvipatil.financiallyprudentcar.controller;

import com.prithvipatil.financiallyprudentcar.model.Specifications;
import com.prithvipatil.financiallyprudentcar.service.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("financially-prudent-car")
@Slf4j
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping(value = {"/car-price/country-code/{country-code}"})
    public String getCarPrice(@RequestBody Specifications inputSpecifications, @PathVariable(name = "country-code", required = false) String countryCode) {
        return applicationService.priceOfCar(inputSpecifications, countryCode);
    }
}
