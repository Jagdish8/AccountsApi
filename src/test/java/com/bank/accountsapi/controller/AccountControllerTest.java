package com.bank.accountsapi.controller;

import com.bank.accountsapi.model.Account;
import com.bank.accountsapi.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebMvcTest(AccountController.class)
class AccountControllerTest extends AbstractTest{

    @MockBean
    private AccountService accountService;

    private Account account;

    @BeforeEach
    void setUpAccount() {
        account = Account.builder()
                .id("IdOFOwner")
                .owner("Owner")
                .balance(100.0)
                .currency("INR")
                .build();
    }

    @Test
    void saveAccount() throws Exception {

        ResponseEntity<Object> responseEntity = ResponseEntity.status(HttpStatus.OK).body("New Account created successfully.");
        when(accountService.addNewAccount(account)).thenReturn(responseEntity);

        String uri = "/v1/accounts/";
        String inputJson = mapToJson(account);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(inputJson))
                                .andReturn();
        assertEquals(mvcResult.getResponse().getStatus(),200);
        assertEquals(mvcResult.getResponse().getContentAsString(),"New Account created successfully." );

    }

    @Test
    void getAccount() throws Exception {

        ResponseEntity<Object> responseEntity = ResponseEntity.status(HttpStatus.OK).body(account);
        when(accountService.findByAccountId("IdOFOwner")).thenReturn(responseEntity);

        String uri = "/v1/accounts/IdOFOwner";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andReturn();
        assertEquals(mvcResult.getResponse().getStatus(),200);
        assertEquals(mvcResult.getResponse().getContentAsString(),mapToJson(account));

    }

    @Test
    void updateAccountBalance() throws Exception {

        ResponseEntity<Object> responseEntity = ResponseEntity.status(HttpStatus.OK).body("Money added successfully");
        when(accountService.updateBalance("IdOFOwner", 100.0)).thenReturn(responseEntity);

        String uri = "/v1/accounts/IdOFOwner";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(100.0)))
                        .andReturn();
        assertEquals(mvcResult.getResponse().getStatus(),200);
        assertEquals(mvcResult.getResponse().getContentAsString(),"Money added successfully");

    }

    @Test
    void deleteAccount() throws Exception {

        ResponseEntity<Object> responseEntity = ResponseEntity.status(HttpStatus.OK).body("Account deleted successfully");
        when(accountService.deleteAccount("IdOFOwner")).thenReturn(responseEntity);

        String uri = "/v1/accounts/IdOFOwner";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andReturn();
        assertEquals(mvcResult.getResponse().getStatus(),200);
        assertEquals(mvcResult.getResponse().getContentAsString(),"Account deleted successfully");

    }

}