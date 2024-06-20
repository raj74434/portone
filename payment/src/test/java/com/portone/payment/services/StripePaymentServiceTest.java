package com.portone.payment.services;

import com.portone.payment.dto.PaymentIntentReqDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class StripePaymentServiceTest {



    @Autowired
    private StripePaymentService stripePaymentService;

    private String transectionId="";


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



            Map<String,Object> captureResponse= stripePaymentService.capturePaymentIntent(transectionId);
            assertEquals ("succeeded",
                    captureResponse.get("status")
            );


            Map<String,Object> refundResponse= stripePaymentService.createRefund(transectionId);
            assertEquals ("succeeded",
                    refundResponse.get("status")
            );


            assertTrue(stripePaymentService.getPaymentIntents().size() > -1, "List of PaymentIntents should be greater than -1");

        }
        catch (Exception exp){
            assertEquals ("requires_capture",
                    ""
            );
        }
    }



}
