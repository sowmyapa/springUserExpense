package com.practise.codechallenge.repository;

import com.practise.codechallenge.dbmodel.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginUserRepository extends JpaRepository<LoginUser, Long> {

    LoginUser findOneByUsername(String username);
}