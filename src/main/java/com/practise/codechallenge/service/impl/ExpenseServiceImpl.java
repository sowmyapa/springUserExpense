package com.practise.codechallenge.service.impl;

import com.practise.codechallenge.dbmodel.Expense;
import com.practise.codechallenge.dbmodel.User;
import com.practise.codechallenge.repository.ExpenseRepository;
import com.practise.codechallenge.repository.UserRepository;
import com.practise.codechallenge.representationdto.ExpenseRequest;
import com.practise.codechallenge.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    public Expense addExpense(ExpenseRequest expenseRequest, String userId){
        UUID expenseId = UUID.randomUUID();

        double expenseValue = expenseRequest.getExpenseTotal()/1.2;
        double vat = expenseValue*0.2;
        User user = userRepository.getOne(userId);
        return expenseRepository.save(new Expense(user,expenseId.toString(),expenseRequest.getExpenseDate(),vat,expenseValue,expenseRequest.getExpenseReason()));
    }

    @Override
    public Expense getExpense(String userId, String expenseId) {
        User user = userRepository.findOne(userId);
        Expense expense = expenseRepository.findExpenseByUserAndExpenseId(user,expenseId);
        return expense;
    }

}
