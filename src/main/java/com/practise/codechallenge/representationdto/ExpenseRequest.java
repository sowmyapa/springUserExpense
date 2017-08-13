package com.practise.codechallenge.representationdto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpenseRequest {

    private Date expenseDate;

    private double expenseTotal;

    private String expenseReason;

}
