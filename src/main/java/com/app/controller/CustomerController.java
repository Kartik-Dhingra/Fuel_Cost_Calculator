//######################### Developed By: Kartik Dhingra ##########################################
package com.app.controller;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.app.dto.CustomerDTO;
import com.app.kafka.Producer;
import com.app.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController 
{
	
	private Producer producer;
	
	@Autowired
	void KafkaController(Producer producer) {
	    this.producer = producer;
	}


	@Autowired
	private CustomerService customerService;
	//to get request
	@PostMapping(consumes="application/json")
	public ResponseEntity<String> createCustomer( @RequestBody CustomerDTO customerDTO) throws Exception 
	{
		return ResponseEntity.ok(customerService.calcFuelCost(customerDTO));
		  
			
	}
	
	//to handle request for Kafka
	@PostMapping(value="/{message}", produces="text/plain")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
		if (message.toUpperCase() == "FALSE") {
			LocalDateTime date = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy hh:mm:ss");
	        String strDate = date.format(formatter);
			
			this.producer.sendMessage(strDate);
			}
    }


}
