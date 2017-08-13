package com.practise.codechallenge.service.impl;

import com.practise.codechallenge.dbmodel.Expense;
import com.practise.codechallenge.dbmodel.User;
import com.practise.codechallenge.repository.ExpenseRepository;
import com.practise.codechallenge.repository.UserRepository;
import com.practise.codechallenge.representationdto.ExpenseRequest;
import com.practise.codechallenge.representationdto.UserRequest;
import com.practise.codechallenge.representationdto.UserResponse;
import com.practise.codechallenge.service.ExpenseService;
import com.practise.codechallenge.service.UserService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    public UserResponse addUser(UserRequest userRequest){
        UUID userId = UUID.randomUUID();
        User user = new User(userId.toString(),userRequest.getFirstName(),userRequest.getLastName(),userRequest.getEmail(),new Date());
        userRepository.save(user);
        UserResponse userResponse = new UserResponse(user);
        return userResponse;
    }

    @Override
    public UserResponse getUser(String userId) {
        User user = userRepository.getOne(userId);
        UserResponse userResponse = new UserResponse(user);
        return userResponse;
    }

    public List<Expense> getAllExpenses(String userId){
        User user = userRepository.getOne(userId);
        List<Expense> expenses = expenseRepository.findAllByUser(user);
        return expenses;
    }

}
