package com.example;

import static org.testng.Assert.assertTrue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AvailabilityTest extends TestUtil {
	private static final Log logger = LogFactory.getLog(AvailabilityTest.class);
	
	@Test
	public void availabiltyJson() {
		String uri = getProperty(Constants.UI_BASE_URL) ;
		Response response = RestAssured.given().contentType("application/json").when().get(uri).then().extract()
				.response();
		// String jsonString =response.getBody().asString();
		logger.debug(uri);
		assertTrue((response.statusCode() == 200),
				"Assertion Failed:Expecting response 200 , Response Code returned is:" + response.statusCode());

	}
	/*
	
	@Test
	public void availabiltyJson1() {
		String uri = getProperty(Constants.UI_BASE_URL) ;
		Response response = RestAssured.given().contentType("application/json").when().get(uri).then().extract()
				.response();
		// String jsonString =response.getBody().asString();
		logger.debug(uri);
		assertTrue((response.statusCode() == 404),
				"Assertion Failed:Expecting response 200 , Response Code returned is:" + response.statusCode());

	}*/
}
