package com.bank.accountsapi.controller;

import com.bank.accountsapi.model.Transaction;
import com.bank.accountsapi.service.PaymentService;
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

@RestController
@RequestMapping("/v1/payments")
@Api(tags = { "Payments REST endpoints" })
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping(path = "/{accountId}")
    @ApiOperation(value = "Get list of payment by account Id", notes = "Get list of payment by account Id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Object.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error") })

    public ResponseEntity<Object> getPaymentsByAccountId(@PathVariable String accountId) {

        return paymentService.getPaymentsByAccountId(accountId);
    }

    @GetMapping(path = "/")
    @ApiOperation(value = "Get list of all payments", notes = "Get list of all payments")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Object.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error") })

    public ResponseEntity<Object> getPayments() {

        return paymentService.getPayments();
    }

    @PostMapping(path = "/")
    @ApiOperation(value = "transfer money among accounts", notes = "transfer money among accounts")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error") })

    public ResponseEntity<Object> transferMoney(@RequestBody Transaction transaction) {

        return paymentService.transferMoney(transaction);
    }


}
