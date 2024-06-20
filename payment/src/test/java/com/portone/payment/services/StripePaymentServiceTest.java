package com.portone.payment.services;

import com.portone.payment.dto.PaymentIntentReqDto;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class StripePaymentServiceTest {

    @Autowired
    private StripePaymentService stripePaymentService;


    private String transectionId;

    @Test
    public void testCreatePaymentIntent() {

        PaymentIntentReqDto paymentIntentReqDto=new PaymentIntentReqDto();
        paymentIntentReqDto.setAmount((long) 10000);
        paymentIntentReqDto.setCurrency("inr");

        try {
            Map<String,Object> response= stripePaymentService.createPaymentIntent(paymentIntentReqDto);
            assertEquals ("requires_capture",
                    response.get("status")
            );
            transectionId=(String) response.get("id");
        }
        catch (Exception exp){

        }
    }


    @Test
    public void testCapturePaymentIntent() {

        try {
            Map<String,Object> response= stripePaymentService.capturePaymentIntent(transectionId);
            assertEquals ("succeeded",
                    response.get("status")
            );
            transectionId=(String) response.get("id");
        }
        catch (Exception exp){

        }
    }


    @Test
    public void testCreateRefund() {
        try {
            Map<String,Object> response= stripePaymentService.createRefund(transectionId);
            assertEquals ("succeeded",
                    response.get("status")
            );
            transectionId=(String) response.get("id");
        }
        catch (Exception exp){

        }
    }

    @Test
    public void testGetPaymentIntents() {

        try {
            assertTrue(stripePaymentService.getPaymentIntents().size() > -1, "List of PaymentIntents should be greater than -1");
        }
        catch (Exception exp){

        }

    }
}
