package com.mkappworks.paymentservice.payment;

import com.mkappworks.paymentservice.notification.NotificationProducer;
import com.mkappworks.paymentservice.notification.PaymentNotificationMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/payment")
public class PaymentController {

    private PaymentService paymentService;
    private PaymentMapper paymentMapper;

    private NotificationProducer notificationProducer;
    private PaymentNotificationMapper paymentNotificationMapper;

    @PostMapping
    public ResponseEntity<Integer> createPayment(@RequestBody @Valid PaymentRequest paymentRequest) {
        var payment = paymentService.createPayment(paymentMapper.toPayment(paymentRequest));

        notificationProducer.sendNotification(
                paymentNotificationMapper.toPaymentNotifcationRequest(payment, paymentRequest)
        );

        return ResponseEntity.ok(payment.getId());
    }
}
