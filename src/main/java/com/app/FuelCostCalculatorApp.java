//######################### Developed By: Kartik Dhingra ##########################################
package com.app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
public class FuelCostCalculatorApp implements WebMvcConfigurer{
	
	
	public static void main(String[] args)
	{
		SpringApplication.run(FuelCostCalculatorApp.class, args);
	}
}
