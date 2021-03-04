//######################### Developed By: Kartik Dhingra ##########################################
package com.app.kafka;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.app.repository.CustomerRepository;


@Service
public class Consumer {
	
	@Autowired
	CustomerRepository customerRepository;

	private final Logger logger = LoggerFactory.getLogger(Producer.class);

    @KafkaListener(topics = "users", groupId = "msg_queue")
    public void consume(String message) throws IOException {
        logger.info(String.format("#### -> Consumed message -> %s", message));
        customerRepository.setQueueUpdate(message); //value from kafka queue is updated to variable
    }
	
}
