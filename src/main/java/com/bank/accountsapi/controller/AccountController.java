package com.bank.accountsapi.controller;

import com.bank.accountsapi.model.Account;
import com.bank.accountsapi.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/v1/accounts")
@Api(tags = { "Accounts REST endpoints" })
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping(path = "/{accountId}")
    @ApiOperation(value = "Get account details", notes = "Find account details by account Id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error") })

    public ResponseEntity<Object> getByAccountId(@PathVariable String accountId) {

        return accountService.findByAccountId(accountId);
    }

    @PostMapping(path = "/")
    @ApiOperation(value = "Add a new account", notes = "Create an new account for existing customer.")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error") })

    public ResponseEntity<Object> addNewAccount(@RequestBody Account account) {

        return accountService.addNewAccount(account);
    }

    @PutMapping(path = "/{accountId}")
    @ApiOperation(value = "update balance in account", notes = "update balance in account")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Object.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error") })

    public ResponseEntity<Object> updateBalance(@PathVariable String accountId, @RequestBody Double balance) {

        return accountService.updateBalance(accountId, balance);
    }

    @DeleteMapping(path = "/{accountId}")
    @ApiOperation(value = "Delete account by Id", notes = "Delete account by Id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Object.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error") })

    public ResponseEntity<Object> deleteAccount(@PathVariable String accountId) {
        return accountService.deleteAccount(accountId);
    }

}
