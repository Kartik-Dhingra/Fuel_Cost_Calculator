//######################### Developed By: Kartik Dhingra ##########################################
package com.app.repository;



import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.app.dto.CustomerDTO;
import com.app.exception.CustomerException;
import com.app.kafka.Producer;
import com.app.network.Network;
import com.app.exception.CustomerException;


@Repository
public class CustomerRepository 
{
	private Producer producer;
	
	String queueUpdate = null;
	
	public String getQueueUpdate() {
		return queueUpdate;
	}

	public void setQueueUpdate(String fueltype) {
		this.queueUpdate = queueUpdate;
	}
	
	@Autowired
	Network network;
	//populates value in hard-coded way
	@PostConstruct
	public void initializer()
	{
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFuellid(true);
		customerDTO.setCity("DL"); 
	}
	//calculate fuel cost
	public String calcFuelCost(CustomerDTO customerDTO) throws CustomerException
	{
		//logic
		float total_Fuel_Cost=0;
		int flag=0;
		boolean lid = customerDTO.getFuellid();
		String type = customerDTO.getFuelType();
		String city = customerDTO.getCity();
		String state = customerDTO.getState();
		
		if (lid == true) {
			
			LocalDateTime date = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy hh:mm:ss");
	        String strDate = date.format(formatter);
			
			this.producer.sendMessage(strDate); 
			
			String apiurl = "https://fuelprice-api-india.herokuapp.com/price/"+state+"/"+city;
			
			String rawJson = null;
			try {
				rawJson = network.request(apiurl);// to get raw json output from url
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			JSONObject root = new JSONObject(rawJson);
			
			JSONArray product = root.getJSONArray("products");
			
			if (type.toUpperCase() == "PETROL") 
				flag = 0;
			else if (type.toUpperCase() == "DIESEL") 
				flag = 1;
			else
				throw new CustomerException("Enter Fuel Type correctly");
				
			
			JSONObject jsonProduct = product.getJSONObject(flag); // to redirect to the desired block in json b/w petrol or diesel
			float fuelprice = jsonProduct.getInt("productPrice"); // to get the value of element productPrice
			long diff_In_Second = 0;
			int loop = 1;
			while (loop != 0 && loop < 25) {// setting limit in iteration if no input from kafka
				//check for time stamp in queue greater then start date
				String sdate = getQueueUpdate();
				if (sdate != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss"); 
					Date queuedate = null, startdate = null;
					try {
						queuedate = sdf.parse(sdate);
						startdate = sdf.parse(strDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					if (queuedate.after(startdate)) {
						diff_In_Second = (queuedate.getTime() - startdate.getTime())/1000;
						loop = 0;
						break;
					}
				}
				loop +=1;
				try {
					TimeUnit.MINUTES.sleep(2); // time delay of 2 minutes
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			total_Fuel_Cost = (fuelprice * (diff_In_Second/30)) + (fuelprice * (diff_In_Second % 30) / 30); // calculating final fuel cost
		
		
		
		}
		
		return String.valueOf(total_Fuel_Cost);
	}

}
