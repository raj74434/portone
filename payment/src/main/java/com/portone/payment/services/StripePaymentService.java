package com.portone.payment.services;

import com.portone.payment.dto.PaymentIntentReqDto;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

public interface StripePaymentService {

    public Map<String, Object> createPaymentIntent(PaymentIntentReqDto paymentIntentReqDto) throws StripeException;


    public Map<String, Object> capturePaymentIntent( String id) throws StripeException;

    public Map<String, Object> createRefund(String id) throws StripeException;

    public List<PaymentIntent> getPaymentIntents() throws StripeException;

}
