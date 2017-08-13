package com.practise.codechallenge.service.impl;

import com.practise.codechallenge.dbmodel.Expense;
import com.practise.codechallenge.dbmodel.User;
import com.practise.codechallenge.repository.ExpenseRepository;
import com.practise.codechallenge.repository.UserRepository;
import com.practise.codechallenge.representationdto.CurrencyResponse;
import com.practise.codechallenge.representationdto.ExpenseRequest;
import com.practise.codechallenge.representationdto.FaceBookResponse;
import com.practise.codechallenge.representationdto.OAuthResponse;
import com.practise.codechallenge.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
        //MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();

        //headers.add("AppSecret", "8d40e02657542fe524664b678f62318f");

        //restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        OAuthResponse oAuthResponse = restTemplate.getForObject("https://graph.facebook.com/oauth/access_token?client_id=130972340847223&client_secret=8d40e02657542fe524664b678f62318f&grant_type=client_credentials", OAuthResponse.class);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer "+oAuthResponse.getAccess_token());
        HttpEntity entity = new HttpEntity(headers);


        ResponseEntity<FaceBookResponse> faceBookResponse = restTemplate.exchange("https://graph.facebook.com/v2.5/4", HttpMethod.GET,entity,FaceBookResponse.class);
        User user = userRepository.findOne(userId);
        Expense expense = expenseRepository.findExpenseByUserAndExpenseId(user,expenseId);
        return expense;
    }

}
