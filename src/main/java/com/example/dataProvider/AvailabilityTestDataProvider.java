package com.example.dataProvider;

import java.net.URISyntaxException;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

public class AvailabilityTestDataProvider {
	
	@DataProvider(name = "petIdDataProvider")
	public static Object[][] getPetId(ITestContext context) throws URISyntaxException {
		Object[][] ret = new Object[10][1];
		for (int i = 0; i < 10; i++) {
			ret[i][0] = i+2;
		}
		return ret;
	}
	
}
