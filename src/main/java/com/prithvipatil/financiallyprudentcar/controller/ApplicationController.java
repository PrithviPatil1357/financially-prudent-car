package com.prithvipatil.financiallyprudentcar.controller;

import com.prithvipatil.financiallyprudentcar.model.Specifications;
import com.prithvipatil.financiallyprudentcar.Util.CurrencyUtil;
import com.prithvipatil.financiallyprudentcar.service.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.Locale;

@RestController
@RequestMapping("financially-prudent-car")
@Slf4j
public class ApplicationController {
    private ApplicationService applicationService;

    @GetMapping(value={"","/country-code/{country-code}"})
    public String getCarPrice(@RequestBody Specifications inputSpecifications,@PathVariable(name = "country-code",required = false) String countryCode){
        applicationService= new ApplicationService(inputSpecifications);
        log.info("Country-Code: "+countryCode);
        return CurrencyUtil.currencyWithChosenLocalisation(applicationService.priceOfCar(), new Locale("", countryCode!=null?countryCode:"IN"));
    }
}
