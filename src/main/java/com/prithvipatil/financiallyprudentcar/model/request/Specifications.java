package com.prithvipatil.financiallyprudentcar.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Specifications {
    private final Double cost;
    @NotNull
    private final LoanDetails loanDetails;
    @NotNull
    private final Buyer buyer;
}