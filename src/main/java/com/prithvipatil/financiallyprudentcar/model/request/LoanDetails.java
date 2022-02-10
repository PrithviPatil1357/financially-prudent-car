package com.prithvipatil.financiallyprudentcar.model.request;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LoanDetails {
    @NotNull
    private final Integer loanTenureInMonths;
    @NotNull
    private final Double rateOfInterest;
}
