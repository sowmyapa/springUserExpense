package com.practise.codechallenge.service.impl;

import com.practise.codechallenge.dbmodel.Expense;
import com.practise.codechallenge.dbmodel.User;
import com.practise.codechallenge.repository.ExpenseRepository;
import com.practise.codechallenge.repository.UserRepository;
import com.practise.codechallenge.representationdto.CurrencyResponse;
import com.practise.codechallenge.representationdto.ExpenseRequest;
import com.practise.codechallenge.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    private RestTemplate restTemplate;

    @Autowired
    public ExpenseServiceImpl(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

    public Expense addExpense(ExpenseRequest expenseRequest, String userId){
        UUID expenseId = UUID.randomUUID();
        String expenseTotalString = expenseRequest.getExpenseTotal();
        double expenseTotal = 0;
        if(expenseTotalString.contains("EUR")){
            expenseTotalString = expenseTotalString.substring(0,expenseTotalString.indexOf("E"));
            CurrencyResponse currencyResponse = restTemplate.getForObject("http://api.fixer.io/latest", CurrencyResponse.class);
            expenseTotal = Double.parseDouble(expenseTotalString)*currencyResponse.getRates().get("GBP");
        }else{
            expenseTotal=Double.parseDouble(expenseTotalString);
        }
        double expenseValue = expenseTotal/1.2;
        double vat = expenseValue*0.2;
        User user = userRepository.getOne(userId);
        return expenseRepository.save(new Expense(user,expenseId.toString(),expenseRequest.getExpenseDate(),expenseValue,vat,expenseRequest.getExpenseReason()));
    }

    @Override
    public Expense getExpense(String userId, String expenseId) {
        User user = userRepository.findOne(userId);
        Expense expense = expenseRepository.findExpenseByUserAndExpenseId(user,expenseId);
        return expense;
    }

}
