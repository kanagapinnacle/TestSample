package com.example.tests;

import static org.testng.Assert.assertTrue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.Test;

import com.example.dataProvider.AvailabilityTestDataProvider;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AvailabilityTest extends TestUtil {
	private static final Log logger = LogFactory.getLog(AvailabilityTest.class);
	
	@Test
	public void availabiltyJson() {
		String uri = getProperty(Constants.UI_BASE_URL) ;
		Response response = RestAssured.given().contentType("application/json")
				.when().get(uri).then().extract()
				.response();
		// String jsonString =response.getBody().asString();
		logger.info(uri);
		assertTrue((response.statusCode() == 200),
				"Assertion Failed:Expecting response 200 , Response Code returned is:" + response.statusCode());
	}
	
	@Test(dataProvider="petIdDataProvider",dataProviderClass=AvailabilityTestDataProvider.class)
	public void testPet(Integer petId){
		assertTrue(petId > 1);
		String url ="http://petstore.swagger.io/v2/pet/"+petId;
		Response response = RestAssured.given().contentType("application/json")
				.when().get(url).then().extract()
				.response();
		logger.info(url);
		assertTrue((response.statusCode() == 200),
				"Assertion Failed:Expecting response 200 , Response Code returned is: " 
						+ response.statusCode()+"\n Petid : "+petId+"\n");
	}
	
}
