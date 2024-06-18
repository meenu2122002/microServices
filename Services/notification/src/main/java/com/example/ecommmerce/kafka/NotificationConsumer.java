package com.example.ecommmerce.kafka;

import com.example.ecommmerce.email.EmailService;
import com.example.ecommmerce.kafka.order.OrderConfirmation;
import com.example.ecommmerce.kafka.payment.PaymentConfirmation;
import com.example.ecommmerce.notification.Notification;
import com.example.ecommmerce.notification.NotificationRepository;
import com.example.ecommmerce.notification.NotificationType;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;
    @KafkaListener(topics = "payment-topic")
    private void consumerPaymentSuccessNotification (PaymentConfirmation paymentConfirmation) throws MessagingException {
       log.info(String.format("Consuming the message from payment-topic :: %s",paymentConfirmation));

       notificationRepository.save(
               Notification.builder()
                       .notificationType(NotificationType.PAYMENT_CONFIRMATION)
                       .notificationDate(LocalDateTime.now())
                       .paymentConfirmation(paymentConfirmation)
                       .build()
       );
//       to do send the email
emailService.sentPaymentSuccessEmail(
        paymentConfirmation.customerEmail(),
        paymentConfirmation.customerFirstName()+" "+paymentConfirmation.customerLastName(),
        paymentConfirmation.amount(),
        paymentConfirmation.orderReference()

);






    }



    @KafkaListener(topics = "order-topic")
    private void consumerOrderSuccessNotification (OrderConfirmation orderConfirmation) throws MessagingException {
        log.info(String.format("Consuming the message from payment-topic :: %s",orderConfirmation));

        notificationRepository.save(
                Notification.builder()
                        .notificationType(NotificationType.ORDER_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );
//       to do send the email
        emailService.sentOrderSuccessEmail(
               orderConfirmation.customer().email(),
                orderConfirmation.customer().firstname()+" "+orderConfirmation.customer().lastname(),
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()

        );




    }

}
