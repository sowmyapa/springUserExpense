package com.practise.codechallenge.repository;

import com.practise.codechallenge.dbmodel.Expense;
import com.practise.codechallenge.dbmodel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,String> {

    Expense findExpenseByUserAndExpenseId(User user, String expenseId);

    List<Expense> findAllByUser(User user);

}
