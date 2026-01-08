package com.demo.producer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;
import java.util.function.Supplier;

@Configuration
public class KafkaProducerStreams {

    @Bean
    public Supplier<RiderLocation> sendRiderLocation() {
        Random random=new Random();
        return () -> {
            String riderId="rider"+random.nextInt(20);
            RiderLocation location = new RiderLocation(riderId, 16.7, 88.2);
            System.out.println("sending:" + location.getRiderId());
            return location;
        };
    }

    @Bean
    public Supplier<String> sendRiderStatus() {
        Random random=new Random();
        return () -> {
            String riderId="rider"+random.nextInt(20);
            RiderLocation location = new RiderLocation(riderId, 16.7, 88.2);
            String status="Recieved;"+location.getRiderId()+": rider started";
            return status;
        };
    }


}
