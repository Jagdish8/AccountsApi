package com.bank.accountsapi.controller;

import com.bank.accountsapi.model.Transaction;
import com.bank.accountsapi.model.domain.Direction;
import com.bank.accountsapi.model.domain.Payment;
import com.bank.accountsapi.model.domain.Sender;
import com.bank.accountsapi.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest extends AbstractTest {

    @MockBean
    private PaymentService paymentService;

    private Transaction transaction;

    @BeforeEach
    void setUpTransaction() {
        transaction = Transaction.builder()
                .id(1L)
                .toAccount("toAccount")
                .fromAccount("fromAccount")
                .amount(100.0)
                .build();
    }

    @Test
    void transferMoney() throws Exception {

        ResponseEntity<Object> responseEntity = ResponseEntity.status(HttpStatus.OK).body("Money transferred successfully");
        when(paymentService.transferMoney(transaction)).thenReturn(responseEntity);

        String uri = "/v1/payments/";
        String inputJson = mapToJson(transaction);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson))
                        .andReturn();
        assertEquals(mvcResult.getResponse().getStatus(),200);
        assertEquals(mvcResult.getResponse().getContentAsString(),"Money transferred successfully");

    }

    @Test
    void getAllPaymentsByAccountId() throws Exception {

        List<Payment> allPayments = new ArrayList<>();
        allPayments.add(paymentBuilder());
        ResponseEntity<Object> responseEntity = ResponseEntity.status(HttpStatus.OK).body(allPayments);
        when(paymentService.getPaymentsByAccountId("account")).thenReturn(responseEntity);

        String uri = "/v1/payments/account";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andReturn();
        assertEquals(mvcResult.getResponse().getStatus(),200);
        String content = mvcResult.getResponse().getContentAsString();
        Object[] payments = super.mapFromJson(content, Object[].class);
        assertTrue(payments.length > 0);

    }

    private Payment paymentBuilder() {
        return Sender.builder()
                .account("account")
                .amount(100.0)
                .toAccount("toAccount")
                .direction(Direction.OUTGOING)
                .build();
    }

}