package com.example.ecommerce.payment.notification;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {

//    log.info("Payment Info Processing");
    private final KafkaTemplate<String,PaymentNotificationRequest> kafkaTemplate;


    public void sendPaymentNotification(PaymentNotificationRequest paymentNotificationRequest){
        log.info("Sending notification with body{}");
        Message<PaymentNotificationRequest>message= MessageBuilder
                .withPayload(paymentNotificationRequest)
                .setHeader(KafkaHeaders.TOPIC,"payment-topic")
                .build();
        kafkaTemplate.send(message);
    }

}
