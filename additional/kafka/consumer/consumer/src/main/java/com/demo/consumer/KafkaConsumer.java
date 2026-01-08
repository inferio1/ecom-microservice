//package com.demo.consumer;
//
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class KafkaConsumer {
//    @KafkaListener(topics = "my-topic-new",groupId = "my-new-group-rider")
//    public void listenRiderLocation(RiderLocation location)
//    {
//        System.out.println("Recieved Location :"+location.getRiderId());
//        System.out.println("Recieved Location :"+location.getLatitude());
//        System.out.println("Recieved Location :"+location.getLongitutde());
//    }
//
////    @KafkaListener(topics = "my-topic",groupId = "my-new-group")
////    public void listen2(String message)
////    {
////        System.out.println("Recieved Messagae2 :"+message);
////    }
//}
