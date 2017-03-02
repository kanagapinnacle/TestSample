package com.example.tests;

import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import com.example.dataProvider.AvailabilityTestDataProvider;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AvailabilityTest extends TestUtil {
	private static final Log logger = LogFactory.getLog(AvailabilityTest.class);
	private String PET_ID = "";
	private String ORDER_ID = "";
	private static final String JSON_FILE_PATH = "src/main/resources/data/json/";
	
	@Test
	public void testPostOrder() throws IOException, ParseException{
		String uri = "http://petstore.swagger.io/v2/store/order";
		String orderJson = readFromFile(JSON_FILE_PATH + "order.json");
		Response response = RestAssured.given().contentType("application/json").body(orderJson).post(uri).then()
				.log().ifError().assertThat().statusCode(200).extract().response();
		String jsonString = response.getBody().asString();
	}
	
	@Test
	public void testPostPet() throws IOException, ParseException{
		String uri = "http://petstore.swagger.io/v2/pet";
		String orderJson = readFromFile(JSON_FILE_PATH + "pet.json");
		Response response = RestAssured.given().contentType("application/json").body(orderJson).post(uri).then()
				.log().ifError().assertThat().statusCode(200).extract().response();
		String jsonString = response.getBody().asString();
	}
	
	@Test(dependsOnMethods={"testPostOrder"})
	public void testGetOrder(){
		
	}
	
	@Test(dependsOnMethods={"testPostPet"})
	public void testPutPet(){
		
	}
	
	@Test(dependsOnMethods={"testPutPet"})
	public void testGetPet(){
		
	}
	
	@Test(dependsOnMethods={"testGetOrder"})
	public void testDeleteOrder(){
		String uri = "http://petstore.swagger.io/v2/store/order/14";
		Response response = RestAssured.given().delete(uri).then()
				.log().ifError().assertThat().statusCode(200).extract().response();
	}
	
	@Test(dependsOnMethods={"testGetPet"})
	public void testDeletePet(){
		String uri = "http://petstore.swagger.io/v2/pet/14";
		Response response = RestAssured.given().delete(uri).then()
				.log().ifError().assertThat().statusCode(200).extract().response();
	}
	
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
	
	@Test(dataProvider="orderIdDataProvider",dataProviderClass=AvailabilityTestDataProvider.class)
	public void testOrder(String orderId,String desc){
		String url ="http://petstore.swagger.io/v2/store/order/"+orderId;
		Response response = RestAssured.given().contentType("application/json")
				.when().get(url).then().extract()
				.response();
		logger.info(url);
		logger.info(desc);
		assertTrue((response.statusCode() == 200),
				"Assertion Failed:Expecting response 200 , Response Code returned is: " 
						+ response.statusCode()+"\n Petid : "+orderId+"\n"+desc);
	}
	
	public String readFromFile(String path) throws IOException, ParseException {
		String jsonFile = path;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(new File(jsonFile)));
		String line = "";
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		return sb.toString();
	}
}
