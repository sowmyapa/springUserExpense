package com.practise.codechallenge.controller;

import com.practise.codechallenge.dbmodel.Expense;
import com.practise.codechallenge.representationdto.ExpenseRequest;
import com.practise.codechallenge.representationdto.ExpenseResponse;
import com.practise.codechallenge.representationdto.UserResponse;
import com.practise.codechallenge.service.ExpenseService;
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

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Api(basePath = "/expense", description = "API for code challenge", produces = "application/json")
@Controller
@RequestMapping(value = "/user/{userId}/expense",produces= MediaType.APPLICATION_JSON_VALUE)
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @RequestMapping(method = RequestMethod.GET,path="/{expenseId}")
//    @ApiImplicitParam(name = "authorization", value = "Bearer 'tokenId'", required = true, dataType = "String", paramType = "header")
    @ApiOperation(value = "Get Expense", notes = "")
    public ResponseEntity<ExpenseResponse> getExpense(@PathVariable(value = "userId")String userId,
                                                          @PathVariable(value = "expenseId")String expenseId){
        Expense expense = expenseService.getExpense(userId,expenseId);
        if(expense==null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        ExpenseResponse expenseResponse = new ExpenseResponse(expense);
        addLink(userId,expenseId,expenseResponse);
        return new ResponseEntity(expenseResponse,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT,path="/resource")
//    @ApiImplicitParam(name = "authorization", value = "Bearer 'tokenId'", required = true, dataType = "String", paramType = "header")
    @ApiOperation(value = "Update Resource", notes = "")
    public ResponseEntity updateResource(){
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST)
//    @ApiImplicitParam(name = "authorization", value = "Bearer 'tokenId'", required = true, dataType = "String", paramType = "header")
    @ApiOperation(value = "Add Resource", notes = "")
    public ResponseEntity<ExpenseResponse> addResource(@PathVariable(value = "userId") String userId,
            @RequestBody ExpenseRequest expenseRequest){
        Expense expense = expenseService.addExpense(expenseRequest,userId);
        if(expense == null)
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        ExpenseResponse expenseResponse = new ExpenseResponse(expense);
        addLink(userId,expense.getExpenseId(),expenseResponse);
        return new ResponseEntity(expenseResponse,HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.DELETE,path="/resource")
//    @ApiImplicitParam(name = "authorization", value = "Bearer 'tokenId'", required = true, dataType = "String", paramType = "header")
    @ApiOperation(value = "Remove Resource", notes = "")
    public ResponseEntity removeResource() {
        return ResponseEntity.ok().build();
    }

    /**
     * add the link for HATEOAS support
     * @param expenseId
     * @param responseDTO
     */
    public void addLink(String userId,String expenseId, ExpenseResponse responseDTO) {
        responseDTO.add(linkTo(methodOn(ExpenseController.class).getExpense(userId,expenseId)).withSelfRel());
    }


}
