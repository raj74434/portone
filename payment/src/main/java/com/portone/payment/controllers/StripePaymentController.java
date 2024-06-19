package com.portone.payment.controllers;

import com.portone.payment.dto.PaymentIntentReqDto;
import com.portone.payment.services.StripePaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import com.stripe.param.PaymentIntentCaptureParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentIntentListParams;
import com.stripe.param.RefundCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class StripePaymentController {


   @Autowired
   private StripePaymentService stripePaymentService;


    @PostMapping("/create_intent")
    public ResponseEntity<Map<String, Object>> createPaymentIntent(@RequestBody PaymentIntentReqDto paymentIntentReqDto) throws StripeException {

        return new ResponseEntity<>(stripePaymentService.createPaymentIntent(paymentIntentReqDto), HttpStatus.CREATED) ;
    }



    @PostMapping("/capture_intent/{id}")
    public ResponseEntity<Map<String, Object>> capturePaymentIntent(@PathVariable("id") String id) throws StripeException {

        return new ResponseEntity<>(stripePaymentService.capturePaymentIntent(id),HttpStatus.ACCEPTED);
    }

    @PostMapping("/create_refund/{id}")
    public ResponseEntity<Map<String, Object>> createRefund(@PathVariable("id") String id) throws StripeException {
        return new ResponseEntity(stripePaymentService.createRefund(id),HttpStatus.OK);
    }

    @GetMapping("/get_intents")
    public ResponseEntity<List<PaymentIntent>> getPaymentIntents() throws StripeException {

        return new ResponseEntity(stripePaymentService.getPaymentIntents(),HttpStatus.OK);
    }


}
