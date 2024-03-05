//package com.example.orderservice.service;
//
//import com.example.orderservice.dto.OrderCreatedEvent;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class OrderCreatedEventProducer {
//
//    @Value("${spring.kafka.producer.topic}")
//    private String orderCreateEventTopic;
//
////    private final KafkaTemplate<Integer,String> kafkaTemplate;
//
////    public void produceOrderCreatedEvent(Long productId){
////        log.info("Send order created {}",productId);
////        kafkaTemplate.send(orderCreateEventTopic,productId.toString());
////
////    }
//
//    private final KafkaTemplate<Integer, OrderCreatedEvent> kafkaTemplate;
//
//    public void produceOrderCreatedEvent(OrderCreatedEvent event){
//        log.info("Sending {}", event);
//
//        kafkaTemplate.send(orderCreateEventTopic,event);
//    }
//}
