package com.prithvipatil.financiallyprudentcar.controller;

import com.prithvipatil.financiallyprudentcar.model.Specifications;
import com.prithvipatil.financiallyprudentcar.Util.NumberFormatUtil;
import com.prithvipatil.financiallyprudentcar.service.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.Locale;

@RestController
@RequestMapping("financially-prudent-car")
@Slf4j
public class ApplicationController {
    private ApplicationService applicationService;

    @GetMapping
    public String getCarPrice(@RequestBody Specifications inputSpecifications){
        applicationService= new ApplicationService(inputSpecifications);
        return NumberFormatUtil.currencyWithChosenLocalisation(applicationService.priceOfCar(), new Locale("en_IN", "IN"));
    }
}
