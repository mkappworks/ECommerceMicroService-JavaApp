package com.mkappworks.paymentservice.notification;

import com.mkappworks.paymentservice.payment.Payment;
import com.mkappworks.paymentservice.payment.PaymentRequest;
import org.springframework.stereotype.Service;

@Service
public class PaymentNotificationMapper {
    public PaymentNotificationRequest toPaymentNotifcationRequest(Payment payment, PaymentRequest paymentRequest) {
        return PaymentNotificationRequest.builder()
                .orderReference(paymentRequest.orderReference())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .customerFirstName(paymentRequest.customer().firstName())
                .customerLastName(paymentRequest.customer().lastName())
                .customerEmail(paymentRequest.customer().email())
                .build();
    }
}
