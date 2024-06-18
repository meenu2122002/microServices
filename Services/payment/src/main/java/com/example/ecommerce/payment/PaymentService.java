package com.example.ecommerce.payment;


import com.example.ecommerce.payment.notification.NotificationProducer;
import com.example.ecommerce.payment.notification.PaymentNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final NotificationProducer notificationProducer;
    public Integer savePayment(PaymentRequest paymentRequest) {
       var payment= paymentRepository.save(paymentMapper.toPayment(paymentRequest));

       notificationProducer.sendPaymentNotification(
               new PaymentNotificationRequest(
                   paymentRequest.orderReference(),
                   paymentRequest.amount(),
                   paymentRequest.paymentMethod(),
                   paymentRequest.customer().firstname(),
                   paymentRequest.customer().lastname(),
                   paymentRequest.customer().email()
               )
       );



return payment.getId();

    }
}
