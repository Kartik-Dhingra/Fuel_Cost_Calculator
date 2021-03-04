//######################### Developed By: Kartik Dhingra ##########################################
package com.app.controller.test;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.app.controller.CustomerController;
import com.app.dto.CustomerDTO;


import com.app.service.CustomerService;



@RunWith(SpringRunner.class)
@WebMvcTest(value = CustomerController.class)
public class CustomerControllerTest 
{
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerService customerService;
	
	private CustomerDTO customerDTO = null;
	
	
	@Before
	public void initializeCustomer()
	{
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFuellid(true);
		customerDTO.setCity("DL"); 
	}
	
	
	//Testing POST method
	@Test
	public void createCustomerTest() throws Exception {
		

		// setting behaviour for createCustomer of customerservice that is mocked
		Mockito.when(
				customerService.calcFuelCost(
						Mockito.any(CustomerDTO.class))).thenReturn("Test");

		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		 
		// Send Customer as request body to /customers
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/customers")
				.accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(customerDTO))
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		Assert.assertEquals("Test", response.getContentAsString());

		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());

		
	}
	
}
//51
