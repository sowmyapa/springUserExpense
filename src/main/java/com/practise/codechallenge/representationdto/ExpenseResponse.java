package com.practise.codechallenge.representationdto;

import com.practise.codechallenge.dbmodel.Expense;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseResponse extends ResourceSupport {

    private Date expenseDate;

    private double expenseVat;

    private double expenseAmount;

    private String expenseReason;

    public ExpenseResponse(Expense expense){
        this.expenseDate = expense.getExepenseDate();
        this.expenseVat = expense.getExpenseVat();
        this.expenseAmount = expense.getExpenseAmount();
        this.expenseReason = expense.getExpenseReason();
    }

}
