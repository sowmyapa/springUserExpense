package com.practise.codechallenge.service;

import com.practise.codechallenge.dbmodel.Expense;
import com.practise.codechallenge.representationdto.ExpenseRequest;

/**
 * Created by sowmyaparameshwara on 8/12/17.
 */
public interface ExpenseService {

    Expense addExpense(ExpenseRequest expenseRequest, String userId);

    Expense getExpense(String userId, String expenseId);
}
