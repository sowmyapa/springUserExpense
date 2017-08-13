package com.practise.codechallenge.controller;

import com.practise.codechallenge.dbmodel.Expense;
import com.practise.codechallenge.dbmodel.User;
import com.practise.codechallenge.representationdto.ExpenseRequest;
import com.practise.codechallenge.representationdto.ExpenseResponse;
import com.practise.codechallenge.representationdto.UserRequest;
import com.practise.codechallenge.representationdto.UserResponse;
import com.practise.codechallenge.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Api(basePath = "/user", description = "API for code challenge", produces = "application/json")
@Controller
@RequestMapping(value = "/user",produces= MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Add User", notes = "")
//    @ApiImplicitParam(name = "authorization", value = "Bearer 'tokenId'", required = true, dataType = "String", paramType = "header")
    public ResponseEntity addUser(@RequestBody UserRequest userRequest){
        UserResponse user = userService.addUser(userRequest);
        addLink(user);
        if(user==null)
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity(user,HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET,path = "/{userId}")
    @ApiOperation(value = "Get User Details", notes = "")
//    @ApiImplicitParam(name = "authorization", value = "Bearer 'tokenId'", required = true, dataType = "String", paramType = "header")
    public ResponseEntity getUser(@PathVariable(value = "userId") String userId){
        UserResponse user = userService.getUser(userId);
        addLink(user);
        if(user==null)
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity(user,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET,path = "/{userId}/expense")
//    @ApiImplicitParam(name = "authorization", value = "Bearer 'tokenId'", required = true, dataType = "String", paramType = "header")
    @ApiOperation(value = "Get All Expenses of this user", notes = "")
    public ResponseEntity<Expense> getExpenses(@PathVariable(value = "userId") String userId){
        List<Expense> expenses = userService.getAllExpenses(userId);
        List<ExpenseResponse> expenseResponses = expenses.stream().map(e->new ExpenseResponse(e)).collect(Collectors.toList());
        return new ResponseEntity(expenseResponses,HttpStatus.OK);
    }

    /**
     * add the link for HATEOAS support
     * @param responseDTO
     */
    public void addLink(UserResponse responseDTO) {
        responseDTO.add(linkTo(methodOn(UserController.class).getUser(responseDTO.getUserId())).withSelfRel());
    }



}
