package com.portone.payment.services;

import com.portone.payment.dto.PaymentIntentReqDto;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.Refund;
import com.stripe.param.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StripePaymentServiceImpl implements StripePaymentService{

    @Value("${Secret_key}")
    private String stripeApiKey;


    public String createCard() throws StripeException {
        Stripe.apiKey = stripeApiKey;

        PaymentMethodCreateParams params = PaymentMethodCreateParams.builder()

                .setType(PaymentMethodCreateParams.Type.CARD)
//                .setCard(PaymentMethodCreateParams.Card.builder()
                .setCard(PaymentMethodCreateParams.CardDetails.builder()
                        .setNumber("4242424242424242")
                        .setExpMonth(12L)
                        .setExpYear(2024L)
                        .setCvc("123")
                        .build())
                .build();

        PaymentMethod paymentMethod = PaymentMethod.create(params);
        return paymentMethod.getId();
    }



    @Override
    public Map<String, Object> createPaymentIntent( PaymentIntentReqDto paymentIntentReqDto) throws StripeException {

        Stripe.apiKey = stripeApiKey;


        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(paymentIntentReqDto.getAmount())
                .setCurrency(paymentIntentReqDto.getCurrency())
                .setPaymentMethod("pm_card_visa")
                .setCaptureMethod(PaymentIntentCreateParams.CaptureMethod.MANUAL)
                .setConfirm(true)
                .build();

        PaymentIntent intent = PaymentIntent.create(params);

        Map<String, Object> response = new HashMap<>();
        response.put("clientSecret", intent.getClientSecret());
        response.put("id", intent.getId());
        response.put("status", intent.getStatus());

        return response;
    }



    @Override
    public Map<String, Object> capturePaymentIntent( String id) throws StripeException {
        Stripe.apiKey = stripeApiKey;

        PaymentIntent intent = PaymentIntent.retrieve(id);

        PaymentIntentCaptureParams captureParams = PaymentIntentCaptureParams.builder().build();
        PaymentIntent capturedIntent = intent.capture(captureParams);

        Map<String, Object> response = new HashMap<>();
        response.put("status", capturedIntent.getStatus());

        return response;
    }

    @Override
    public Map<String, Object> createRefund( String id) throws StripeException {
        Stripe.apiKey = stripeApiKey;

        RefundCreateParams params =
                RefundCreateParams.builder()
                        .setPaymentIntent(id)
                        .build();

        Refund refund = Refund.create(params);

        Map<String, Object> response = new HashMap<>();
        response.put("status", refund.getStatus());

        return response;
    }

    @Override
    public List<PaymentIntent> getPaymentIntents() throws StripeException {
        Stripe.apiKey = stripeApiKey;

        PaymentIntentListParams params = PaymentIntentListParams.builder().build();
        return PaymentIntent.list(params).getData();
    }

}
