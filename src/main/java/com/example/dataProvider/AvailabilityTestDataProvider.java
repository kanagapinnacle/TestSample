package com.example.dataProvider;

import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.opencsv.CSVReader;

public class AvailabilityTestDataProvider {
	
	@DataProvider(name = "petIdDataProvider")
	public static Object[][] getPetId(ITestContext context) throws URISyntaxException {
		Object[][] ret = new Object[10][1];
		for (int i = 0; i < 10; i++) {
			ret[i][0] = i+2;
		}
		return ret;
	}
	
	@DataProvider(name = "orderIdDataProvider")
	public static Object[][] getOrderId(ITestContext context) throws URISyntaxException, IOException {
		CSVReader csvAssetReader = new CSVReader(new FileReader(
				"src/main/resources/data/orders.csv"));
		List<String[]> lines = csvAssetReader.readAll();
		Object[][] ret = new Object[lines.size()][2];
		for (int i = 0; i < lines.size(); i++) {
			ret[i][0] = lines.get(i)[0];
			ret[i][1] = lines.get(i)[1];
		}
		csvAssetReader.close();
		return ret;
	}
	
}
