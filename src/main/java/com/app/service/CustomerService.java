//######################### Developed By: Kartik Dhingra ##########################################
package com.app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dto.CustomerDTO;
import com.app.exception.CustomerException;
import com.app.repository.CustomerRepository;
@Service
public class CustomerService
{
	@Autowired
	private CustomerRepository customerRepository;

	public String calcFuelCost(CustomerDTO customerDTO) throws CustomerException
	{
		return customerRepository.calcFuelCost(customerDTO);
	}
	
}
