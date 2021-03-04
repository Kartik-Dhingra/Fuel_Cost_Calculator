
This API works on 2 URls:
1. For sending Post request
	URL: http://localhost:8080/app/customers
	
	JSON request body: {"fuellid":true,
			"fueltype":"PETROL",
			"city":"Central+Delhi",
			"state":"Delhi"}

2. For updating fuel lid status:
	Lid open :  http://localhost:8080/app/customers/true
	Lid close :   http://localhost:8080/app/customers/false

********************************************************************************************

The 3rd party API for fuel cost is referenced from : https://github.com/anshikakaythwas/fuel-prices-india-api

For values of State and city please refer StateAndCity.txt for format.

********************************************************************************************

Please setup Kafka server on machine as this API uses Kafka for updating fuel lid status.

