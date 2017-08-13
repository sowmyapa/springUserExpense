package com.practise.codechallenge.service;

import com.practise.codechallenge.dbmodel.Expense;
import com.practise.codechallenge.dbmodel.User;
import com.practise.codechallenge.representationdto.ExpenseRequest;
import com.practise.codechallenge.representationdto.UserRequest;
import com.practise.codechallenge.representationdto.UserResponse;

import java.util.List;

/**
 * Created by sowmyaparameshwara on 8/12/17.
 */
public interface UserService {

    UserResponse addUser(UserRequest userRequest);

    UserResponse getUser(String userId);

    List<Expense> getAllExpenses(String userId);
}
